package com.video.content.service;

import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import org.springframework.stereotype.Service;

/**
 * 课程基础信息
 */
public interface CourseBaseInfoService {
    /**
     * 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParams 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamDto queryCourseParams);
}
