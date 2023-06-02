package com.video.content.service;

import com.video.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * 课程分类
 */
public interface CourseCategoryService {
    /**
     * 课程分类查询
     * @param id 根节点id
     * @return 根节点下面的所有子节点
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
