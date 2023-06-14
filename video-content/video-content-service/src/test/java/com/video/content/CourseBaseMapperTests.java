package com.video.content;


import com.video.content.mapper.CourseBaseMapper;
import com.video.content.mapper.CourseCategoryMapper;
import com.video.content.model.dto.CourseCategoryTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class CourseBaseMapperTests {

    @Autowired
    private CourseBaseMapper courseBaseMapper;


    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Test
    public void test1(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
