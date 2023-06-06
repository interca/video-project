package com.video.content.service;

import com.video.content.model.dto.TeachplanDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程计划
 */
@Service
public interface TeachplanService {
    List<TeachplanDto> findTeachplanTree(Long courseId);
}