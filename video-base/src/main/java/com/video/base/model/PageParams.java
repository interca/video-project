package com.video.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询的参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    // 默认起始页码
    public static final long DEFAULT_PAGE_CURRENT = 1L;
    // 默认每页记录数
    public static final long DEFAULT_PAGE_SIZE = 10L;

    // 当前页码
    private Long pageNo = DEFAULT_PAGE_CURRENT;

    // 当前每页记录数
    private Long pageSize = DEFAULT_PAGE_SIZE;
}
