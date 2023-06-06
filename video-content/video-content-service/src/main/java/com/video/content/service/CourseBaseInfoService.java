package com.video.content.service;

import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.model.dto.AddCourseDto;
import com.video.content.model.dto.CourseBaseInfoDto;
import com.video.content.model.dto.EditCourseDto;
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


    /**
     * 新增课程基本信息
     * @param companyId 教学机构id
     * @param addCourseDto 课程基本信息
     * @return
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);



    /**
     * 根据课程id查询课程基本信息
     * @param courseId  课程id
     * @return
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程信息
     * @param companyId 机构id，本机构只能修改本机构课程
     * @return
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
