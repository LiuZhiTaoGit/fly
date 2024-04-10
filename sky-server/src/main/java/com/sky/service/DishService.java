package com.sky.service;

import com.sky.dto.DishDTO;

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
}
