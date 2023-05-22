package com.video.content.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 课程查询条件模型类
 */
@Data
@AllArgsConstructor
public class QueryCourseParamDto {
    // 审核状态
    private String auditStatus;
    // 课程名称
    private String courseName;
    // 发布状态
    private String publishStatus;
}
