package com.video.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.content.model.dto.CourseCategoryTreeDto;
import com.video.content.model.po.CourseCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    /**
     * 课程分类查询
     * @param id
     * @return
     */

    @Select(" WITH RECURSIVE t1 AS (\n" +
            "        SELECT p.* FROM course_category p WHERE p.id = #{id}\n" +
            "        UNION ALL\n" +
            "        SELECT c.* FROM course_category c JOIN t1 WHERE c.parentid = t1.id\n" +
            "    )\n" +
            "    SELECT * FROM t1;")
   List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
