package com.sky.mapper;

import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.Page;
import com.sky.annotation.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

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

    @AutoFile(OperationType.INSERT)
    void insert2(Dish dish);

    Page<DishVO> pageQuery2(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);


    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);


    /**
     * 根据id修改菜品的状态
     * @param status
     * @param id
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void updateStatus(Integer status, Long id);


    @Select("select * from dish where id = #{id}")
    DishVO getById2(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateDish(Dish dish);


    List<Dish> getByCategoryId(Dish dish);

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

}
