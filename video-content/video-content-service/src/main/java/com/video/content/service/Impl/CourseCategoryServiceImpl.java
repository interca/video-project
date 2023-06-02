package com.video.content.service.Impl;

import com.video.content.mapper.CourseCategoryMapper;
import com.video.content.model.dto.CourseCategoryTreeDto;
import com.video.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程分类
 */
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 课程分类查询
     * @param id 根节点id
     * @return
     */
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.queryTreeNodes(id);
        //根结点过滤
        Map<String, CourseCategoryTreeDto> collect =
                courseCategoryTreeDtos.stream()
                        .filter(item ->!item.getId().equals(id))
                        .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        List<CourseCategoryTreeDto> result = new ArrayList<>();
        courseCategoryTreeDtos.stream().filter(item ->!item.getId().equals(id)).forEach(item->{
          if(item.getParentid().equals(id)){
              result.add(item);
          }
          //找到当前结点的父节点
             CourseCategoryTreeDto courseCategoryTreeDto = collect.get(item.getParentid());
          //父节点不为空
          if(courseCategoryTreeDto != null){
              //如果父结点的儿子为空
              if(courseCategoryTreeDto.getChildrenTreeNodes() == null){
                  courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<>());
              }
              courseCategoryTreeDto.getChildrenTreeNodes().add(item);
          }
        });
        return result;
    }
}
