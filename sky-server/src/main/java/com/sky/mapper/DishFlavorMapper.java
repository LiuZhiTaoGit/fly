package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 15:15
 * @description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    void insertBatch2(List<DishFlavor> flavors);


    @Delete("delete from dish_flavor where dish_id = #{dish_id}")
    void deleteById(Long dish_id);
}
