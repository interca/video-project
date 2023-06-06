package com.video.content.service.Impl;

import com.video.content.mapper.TeachplanMapper;
import com.video.content.model.dto.TeachplanDto;
import com.video.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {



    @Autowired
    private TeachplanMapper teachplanMapper;


    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }
}