package com.video.media.service;

import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.media.model.dto.QueryMediaParamsDto;
import com.video.media.model.dto.UploadFileParamsDto;
import com.video.media.model.dto.UploadFileResultDto;
import com.video.media.model.po.MediaFiles;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */

public interface MediaFileService {

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
  * @author Mr.M
  * @date 2022/9/10 8:57
 */
 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


 /**
  * @description 上传文件的通用接口
  * @param companyId           机构id
  * @param uploadFileParamsDto 文件信息
  * @param localFilePath  本地文件路径
  * @return com.xuecheng.media.model.dto.UploadFileResultDto
  */
 UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto,  String localFilePath);

}
