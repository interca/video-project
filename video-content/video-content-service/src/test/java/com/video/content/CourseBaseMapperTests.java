package com.video.content;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.video.content.mapper.CourseBaseMapper;
import com.video.content.model.po.CourseBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class CourseBaseMapperTests {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void test1(){
        System.out.println("ssss");
        LambdaQueryWrapper<CourseBase> lq = new LambdaQueryWrapper<>();
        List<CourseBase> courseBases = courseBaseMapper.selectList(lq);
        System.out.println(courseBases);
        System.out.println("sss");
    }
}
