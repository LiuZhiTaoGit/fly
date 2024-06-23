package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 14:15
 * @description: com.sky.service
 * @version: 1.0
 */
public interface DishService {
    /**
     * 添加菜品和口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);


    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pagequery(DishPageQueryDTO dishPageQueryDTO);


    void saveWithFlavor2(DishDTO dishDTO);


    PageResult pagequery2(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void remove(List<Long> ids);

    /**
     * 更改菜品的状态
     * @param status
     * @param id
     */
    void startOrEnd(Integer status, Long id);

    DishVO getById(Long id);

    void update(DishDTO dishDTO);

    List<Dish> list(Long categoryId);

}
