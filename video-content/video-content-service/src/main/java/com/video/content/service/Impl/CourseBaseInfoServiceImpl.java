package com.video.content.service.Impl;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.base.exception.VideoException;
import com.video.base.model.PageParams;
import com.video.base.model.PageResult;
import com.video.content.mapper.CourseBaseMapper;
import com.video.content.mapper.CourseCategoryMapper;
import com.video.content.mapper.CourseMarketMapper;
import com.video.content.model.dto.AddCourseDto;
import com.video.content.model.dto.CourseBaseInfoDto;
import com.video.content.model.dto.EditCourseDto;
import com.video.content.model.dto.QueryCourseParamDto;
import com.video.content.model.po.CourseBase;
import com.video.content.model.po.CourseCategory;
import com.video.content.model.po.CourseMarket;
import com.video.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Resource
    CourseMarketMapper courseMarketMapper;


    @Autowired
    private CourseBaseMapper courseBaseMapper;


    @Resource
    CourseCategoryMapper courseCategoryMapper;

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


    /**
     * 新增课程
     * @param companyId 教学机构id
     * @param addCourseDto 课程基本信息
     * @return
     */
    @Override
    @Transactional
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {

        // 2. 封装请求参数
        // 封装课程基本信息
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBase);
        // 2.1 设置默认审核状态（去数据字典表中查询状态码）
        courseBase.setAuditStatus("202002");
        // 2.2 设置默认发布状态
        courseBase.setStatus("203001");
        // 2.3 设置机构id
        courseBase.setCompanyId(companyId);
        // 2.4 设置添加时间
        courseBase.setCreateDate(LocalDateTime.now());
        // 2.5 插入课程基本信息表
        int baseInsert = courseBaseMapper.insert(courseBase);
        Long courseId = courseBase.getId();
        // 封装课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDto, courseMarket);
        courseMarket.setId(courseId);
        // 2.6 判断收费规则，若课程收费，则价格必须大于0
        String charge = courseMarket.getCharge();
        if ("201001".equals(charge)) {
            Float price = addCourseDto.getPrice();
            if (price == null || price.floatValue() <= 0) {
                VideoException.cast("课程设置了收费，价格不能为空，且必须大于0");
            }
        }
        // 2.7 插入课程营销信息表
        int marketInsert = courseMarketMapper.insert(courseMarket);
        if (baseInsert <= 0 || marketInsert <= 0) {
            throw new RuntimeException("新增课程基本信息失败");
        }
        // 3. 返回添加的课程信息
        return getCourseBaseInfo(courseId);
    }



    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        // 1. 根据课程id查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null)
            return null;
        // 1.1 拷贝属性
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        // 2. 根据课程id查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 2.1 拷贝属性
        if (courseMarket != null)
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        // 3. 查询课程分类名称，并设置属性
        // 3.1 根据小分类id查询课程分类对象
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        // 3.2 设置课程的小分类名称
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        // 3.3 根据大分类id查询课程分类对象
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        // 3.4 设置课程大分类名称
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
    }


    /**
     * 修改课程
     * @param companyId 机构id，本机构只能修改本机构课程
     * @param editCourseDto
     * @return
     */
    @Override
    @Transactional
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        // 判断当前修改课程是否属于当前机构
        Long courseId = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (!companyId.equals(courseBase.getCompanyId())) {
            VideoException.cast("只允许修改本机构的课程");
        }
        // 拷贝对象
        BeanUtils.copyProperties(editCourseDto, courseBase);
        // 更新，设置更新时间
        courseBase.setChangeDate(LocalDateTime.now());
        courseBaseMapper.updateById(courseBase);
        // 查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 由于课程营销信息不是必填项，故这里先判断一下
        if (courseMarket == null) {
            courseMarket = new CourseMarket();
        }
        courseMarket.setId(courseId);
        // 获取课程收费状态并设置
        String charge = editCourseDto.getCharge();
        courseMarket.setCharge(charge);
        // 如果课程收费，则判断价格是否正常
        if (charge.equals("201001")) {
            Float price = editCourseDto.getPrice();
            if (price <= 0 || price == null) {
               VideoException.cast("课程设置了收费，价格不能为空，且必须大于0");
            }
        }
        // 对象拷贝
        BeanUtils.copyProperties(editCourseDto, courseMarket);
        // 有则更新，无则插入
        courseMarketMapper.updateById(courseMarket);
        return getCourseBaseInfo(courseId);
    }
}
