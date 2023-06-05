package com.video.content.api;


import com.video.base.exception.ValidationGroups;
import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.model.dto.AddCourseDto;
import com.video.content.model.dto.CourseBaseInfoDto;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import com.video.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    /**
     * 课程查询
     * @param pageParams
     * @param queryCourseParams
     * @return
     */
    @PostMapping("/course/list")
    @ApiOperation("课程查询接口")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamDto queryCourseParams) {
        System.out.println(queryCourseParams);
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
    }


    /**
     * 新增课程
     * @param addCourseDto
     * @return
     */
    @ApiOperation("新增课程基础信息接口")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto) {
        // 机构id，暂时硬编码模拟假数据
        Long companyId = 22L;
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }


    /**
     * 查询课程
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {
        return null;
    }
}
