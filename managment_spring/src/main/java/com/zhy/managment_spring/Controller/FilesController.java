package com.zhy.managment_spring.Controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhy.managment_spring.Entity.Files;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Service.FileService;
import com.zhy.managment_spring.common.Constants;
import com.zhy.managment_spring.common.Result;
import com.zhy.managment_spring.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zhy
 * @date 2023/10/16 22:33
 */
@RestController
@RequestMapping("files")
public class FilesController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FileService fileService;
    @PostMapping("/update")
    public Result updateFiles(@RequestBody Files files) {

        return Result.success(fileService.updateById(files));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize ,
                           @RequestParam(defaultValue = "") String name
    ){

        IPage<Files> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //查询未删除记录
        queryWrapper.eq("is_delete",false);
        if (!"".equals(name)){
            queryWrapper.like("name",name);

        }

        return Result.success(fileService.page(page,queryWrapper));
    }
    @DeleteMapping("/delId={id}")
    public Result deleteUserById(@PathVariable(value = "id") String id) {
        Files files=fileService.getBaseMapper().selectById(id);
        files.setIs_delete(true);
        return Result.success(fileService.updateById(files));

    }
    @PostMapping("/delete/delBatch")
    public Result deleteBatchByIds(@RequestBody List<String > ids) {
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> list=fileService.getBaseMapper().selectList(queryWrapper);
        for (Files file:list){
            file.setIs_delete(true);
            fileService.updateById(file);
        }
        // 在此处处理批量删除逻辑
        return Result.success();
    }
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file)throws IOException{
        String originalFilename=file.getOriginalFilename();
        String type= FileUtil.extName(originalFilename);

        long size=file.getSize();
        //定义一个文件唯一的标识码
        String uuid= IdUtil.fastSimpleUUID();
        String fileUUID=uuid+StrUtil.DOT+type;
        File uploadFile=new File(fileUploadPath+fileUUID);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parenFile=uploadFile.getParentFile();
        if (!parenFile.exists()){
            parenFile.mkdirs();
        }
        //获取文件的url
        String url;
        file.transferTo(uploadFile);
        //获取文件的MD5
        String  md5= SecureUtil.md5(uploadFile);
        Files dbFiles=getFileByMd5(md5);

        if (dbFiles!=null){
            url=dbFiles.getUrl();
            uploadFile.delete();
        }else {
            //把获取的文件存储到磁盘目录

            url="http://localhost:9099/files/"+fileUUID;
        }
        //存储到数据库
        Files saveFile=new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);

        fileService.saveFiles(saveFile);
        return url;

    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码
        File uploadFile = new File(fileUploadPath + fileUUID);

        if (!uploadFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setContentLength((int) uploadFile.length());

        // 使用文件流下载
        try (FileInputStream fis = new FileInputStream(uploadFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }
    }

    private Files getFileByMd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList=fileService.getBaseMapper().selectList(queryWrapper);

        return filesList.size()==0 ?null :filesList.get(0);
    }
}
