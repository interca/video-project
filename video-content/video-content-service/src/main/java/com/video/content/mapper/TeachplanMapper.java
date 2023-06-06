package com.video.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.content.model.dto.TeachplanDto;
import com.video.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface TeachplanMapper extends BaseMapper<Teachplan> {


    /**
     * 查询课程计划
     * @param courseId
     * @return
     */
    List<TeachplanDto> selectTreeNodes(Long courseId);
}
