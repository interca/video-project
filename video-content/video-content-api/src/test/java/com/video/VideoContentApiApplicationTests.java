package com.video;

import com.video.content.mapper.TeachplanMapper;
import com.video.content.model.dto.TeachplanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class VideoContentApiApplicationTests {

    @Autowired
    private TeachplanMapper teachplanMapper;
    @Test
    void contextLoads() {
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(117L);
        System.out.println(teachplanDtos);
    }

}
