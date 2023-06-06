package com.video.content.api;

import com.video.content.model.dto.SaveTeachplanDto;
import com.video.content.model.dto.TeachplanDto;
import com.video.content.model.po.Teachplan;
import com.video.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
public class TeachplanController {

    /**
     * 查询课程计划
     */
    @Autowired
    private TeachplanService teachplanService;
    @ApiOperation("查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }



    /**
     * 课程计划
     * @param teachplanDto
     */
    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplanDto) {
      teachplanService.saveTeachplan(teachplanDto);
    }
}