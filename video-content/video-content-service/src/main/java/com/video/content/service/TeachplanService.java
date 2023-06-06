package com.video.content.service;

import com.video.content.model.dto.SaveTeachplanDto;
import com.video.content.model.dto.TeachplanDto;
import com.video.content.model.po.Teachplan;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程计划
 */
@Service
public interface TeachplanService {

    /**
     * 课程计划查询
     * @param courseId
     * @return
     */
    List<TeachplanDto> findTeachplanTree(Long courseId);


    /**
     * 课程计划修改
     */
    void saveTeachplan(SaveTeachplanDto teachplanDto);
}