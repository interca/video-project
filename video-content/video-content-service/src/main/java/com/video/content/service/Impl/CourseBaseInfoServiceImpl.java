package com.video.content.service.Impl;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.mapper.CourseBaseMapper;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import com.video.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {


    @Autowired
    private CourseBaseMapper courseBaseMapper;


    /**
     * 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParams 查询条件
     * @return
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamDto queryCourseParams) {
        // 构建条件查询器
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件：按照课程名称模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParams.getCourseName()),
                CourseBase::getName, queryCourseParams.getCourseName());
        // 构建查询条件，按照课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParams.getAuditStatus()),
                CourseBase::getAuditStatus, queryCourseParams.getAuditStatus());
        // 构建查询条件，按照课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParams.getPublishStatus()),
                CourseBase::getStatus, queryCourseParams.getPublishStatus());
        // 分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageInfo = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> items = pageInfo.getRecords();
        // 获取数据总条数
        long counts = pageInfo.getTotal();
        // 构建结果集
        return new PageResult<>(items, counts, pageParams.getPageNo(), pageParams.getPageSize());
    }
}
