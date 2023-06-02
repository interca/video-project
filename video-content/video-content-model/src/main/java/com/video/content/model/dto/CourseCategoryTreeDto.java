package com.video.content.model.dto;

import com.video.content.model.po.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 返回的课程分类信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseCategoryTreeDto extends CourseCategory {

    List<CourseCategoryTreeDto>childrenTreeNodes;


}
