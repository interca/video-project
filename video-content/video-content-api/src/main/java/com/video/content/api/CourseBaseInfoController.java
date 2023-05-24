package com.video.content.api;


import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import com.video.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;
    @PostMapping("/course/list")
    @ApiOperation("课程查询接口")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamDto queryCourseParams) {
        System.out.println(queryCourseParams);
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
    }

}
