package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/6/19 - 06 - 19 - 10:20
 * @description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface SetmealDishMapper {

//    (1,2,3,4,5)
    List<Long> getSetmeal(List<Long> ids);


    void insertDish(List<SetmealDish> setmealDishes);

    List<SetmealDish> getDishIdById(Long id);


    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteByIds(Long id);

}
