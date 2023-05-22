package com.video.content.api;

import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
public class CourseBaseInfoController {
    @PostMapping("/course/list")
    @ApiOperation("课程查询接口")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamDto queryCourseParams) {
        CourseBase courseBase = new CourseBase();
        courseBase.setId(15L);
        courseBase.setDescription("测试课程");
        PageResult<CourseBase> result = new PageResult<>();
        result.setItems(Arrays.asList(courseBase));
        result.setPage(1);
        result.setPageSize(10);
        result.setCounts(1);
        return result;
    }
}
