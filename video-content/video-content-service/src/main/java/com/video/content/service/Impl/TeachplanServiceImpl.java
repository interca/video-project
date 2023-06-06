package com.video.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.video.content.mapper.TeachplanMapper;
import com.video.content.model.dto.SaveTeachplanDto;
import com.video.content.model.dto.TeachplanDto;
import com.video.content.model.po.Teachplan;
import com.video.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {



    @Autowired
    private TeachplanMapper teachplanMapper;


    /**
     *课程计划查询
     * @param courseId
     * @return
     */
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }


    /**
     * 课程计划新增或者修改
     * @param
     */
    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            //取出同父同级别的课程计划数量
            LambdaQueryWrapper<Teachplan>lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Teachplan::getCourseId,teachplanDto.getCourseId());
            lambdaQueryWrapper.eq(Teachplan::getParentid,teachplanDto.getParentid());
            int count =  teachplanMapper.selectCount(lambdaQueryWrapper);
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);
            teachplanMapper.insert(teachplanNew);
        }
    }
}