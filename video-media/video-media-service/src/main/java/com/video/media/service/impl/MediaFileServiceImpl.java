package com.video.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.video.base.exception.VideoException;
import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.media.mapper.MediaFilesMapper;
import com.video.media.model.dto.QueryMediaParamsDto;
import com.video.media.model.dto.UploadFileParamsDto;
import com.video.media.model.dto.UploadFileResultDto;
import com.video.media.model.po.MediaFiles;
import com.video.media.service.MediaFileService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * @description TODO
 * @author Mr.M
 * @date 2022/9/10 8:58
 * @version 1.0
 */

@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {

 @Autowired
 MediaFilesMapper mediaFilesMapper;


 @Autowired
 MinioClient minioClient;


 /**
  * 普通文件
  */
 @Value("${minio.bucket.files}")
 private String bucket_mediafiles;


 /**
  * 视频
  */
 @Value("${minio.bucket.videofiles}")
 private String bucket_video;

 @Override
 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

  //构建查询条件对象
  LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
  
  //分页对象
  Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
  // 查询数据内容获得结果
  Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
  // 获取数据列表
  List<MediaFiles> list = pageResult.getRecords();
  // 获取数据总数
  long total = pageResult.getTotal();
  // 构建结果集
  PageResult<MediaFiles> mediaListResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
  return mediaListResult;

 }







 /**
  * 根据扩展名获取mimeType
  */
 public String getMimeType(String extension){
  ContentInfo contentInfo = ContentInfoUtil.findMimeTypeMatch(extension);
  String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用字节流
  if(contentInfo != null){
    mimeType = contentInfo.getMimeType();
  }
  return mimeType;
 }



 /**
  * 将文件上传到minio
  */
 public boolean addMediaFilesToMinIO(String localFilePath ,String mimeType , String  bucket ,String objectName) {
  try {
   UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
           .bucket(bucket)//桶
           .filename(localFilePath)//本地文件路径
           .object(objectName)  //对象名 //放在子目录下
           .contentType(mimeType)  //文件类型
           .build();
   return true;
  } catch (IOException e) {
    log.error("上传文件出错");
  }
   return  false;
 }



 /**
  * 自动生成目录
**/
 private String getFileFolder() {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  String folder = dateFormat.format(new Date()).replace("-","/") + "/";
  return folder;
 }


 /**
  * 获取文件的Md5值
  */
 private String getFileMd5(File file){
  try {
   FileInputStream fileInputStream = new FileInputStream(file);
   String fileMd5 = DigestUtils.md5Hex(fileInputStream);
   return fileMd5;
  } catch (IOException e) {
    log.error(e.getMessage());
    return null;
  }
 }


 /**
  * 上传文件信息
  * @param companyId  机构id
  * @param fileMd5   文件id
  * @param uploadFileParamsDto
  * @param bucKet  桶
  * @param objectName  文件名
  * @return
  */
 public  MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,
                                      UploadFileParamsDto uploadFileParamsDto,String bucKet,String objectName){
  MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
  if(mediaFiles ==null){
   mediaFiles = new MediaFiles();
   BeanUtils.copyProperties(uploadFileParamsDto,mediaFiles);
   //文件id
   mediaFiles.setId(fileMd5);
   //机构id
   mediaFiles.setCompanyId(companyId);
   mediaFiles.setBucket(bucKet);
   mediaFiles.setFilePath(objectName);
   mediaFiles.setFileId(fileMd5);
   mediaFiles.setUrl("/" + bucKet + "/" + objectName);
   mediaFiles.setCreateDate(LocalDateTime.now());
   mediaFiles.setStatus("1");
   mediaFiles.setAuditStatus("002003");
   int insert = mediaFilesMapper.insert(mediaFiles);
   if(insert <= 0){
    log.debug("保存文件到数据库失败");
   }
  }
  return mediaFiles;
 }

 /**
  * @description 上传文件的通用接口
  * @param companyId           机构id
  * @param uploadFileParamsDto 文件信息
  * @param localFilePath  本地文件路径
  * @return com.xuecheng.media.model.dto.UploadFileResultDto
  */
 @Override
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
  String filename = uploadFileParamsDto.getFilename();
  //将文件上传到minio
  String mimeType = getMimeType(filename);
  String fileFolder = getFileFolder();
  //文件的md5值
  String fileMd5 = getFileMd5(new File(localFilePath));
  String objectName = fileFolder + fileMd5;
  boolean b = addMediaFilesToMinIO(localFilePath, mimeType, bucket_mediafiles, objectName);
  if(!b){
   VideoException.cast("上传文件失败");
  }
  //将文件信息保存到数据库
  MediaFiles mediaFiles = addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_mediafiles, objectName);
  if(mediaFiles == null){
   VideoException.cast("入库失败");
  }
  UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
  BeanUtils.copyProperties(mediaFiles,uploadFileResultDto);
  return uploadFileResultDto;
 }





}
