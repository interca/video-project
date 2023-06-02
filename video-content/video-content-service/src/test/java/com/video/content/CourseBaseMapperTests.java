package com.video.content;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.video.content.mapper.CourseBaseMapper;
import com.video.content.mapper.CourseCategoryMapper;
import com.video.content.model.dto.CourseCategoryTreeDto;
import com.video.content.model.po.CourseBase;
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
