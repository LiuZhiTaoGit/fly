package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品
     * @param dish
     */
    @AutoFile(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页进行查询2
     * @param dishPageQueryDTO
     * @return
     */

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

}
