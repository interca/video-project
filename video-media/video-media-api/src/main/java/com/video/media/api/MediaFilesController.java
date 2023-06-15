package com.video.media.api;

import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.media.model.dto.QueryMediaParamsDto;
import com.video.media.model.dto.UploadFileParamsDto;
import com.video.media.model.dto.UploadFileResultDto;
import com.video.media.model.po.MediaFiles;
import com.video.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description 媒资文件管理接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
 @Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
 @RestController
public class MediaFilesController {


  @Autowired
  MediaFileService mediaFileService;


    /**
     * 查询
     * @param pageParams
     * @param queryMediaParamsDto
     * @return
     */
   @ApiOperation("媒资列表查询接口")
   @PostMapping("/files")
   public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto) throws IOException {
      Long companyId = 1232141425L;
       UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
       File tempFile = File.createTempFile("minio",".temp");
       //文件路径
       String absolutePath = tempFile.getAbsolutePath();
     return mediaFileService.queryMediaFiels(companyId,pageParams,queryMediaParamsDto);
   }




    /**
     * 上传文件
     * @param filedata
     * @return
     */
    @ApiOperation("上传文件")
    @RequestMapping(value = "/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResultDto upload(@RequestPart("filedata") MultipartFile filedata) throws IOException {
        //上传文件信息
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());
        uploadFileParamsDto.setFileSize(filedata.getSize());
        uploadFileParamsDto.setFileType("001001");
        Long companyId = 1232141425L;
        //创建一个临时文件
        File tempFile = File.createTempFile("minio",".temp");
        //文件路径
        String absolutePath = tempFile.getAbsolutePath();
        return mediaFileService.uploadFile(companyId,uploadFileParamsDto,absolutePath);
    }

}
