package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/6/19 - 06 - 19 - 13:04
 * @description: com.sky.service
 * @version: 1.0
 */

public interface SetmealService {
    void insert(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteByIds(List<Long> ids);

    void startOrEnd(Integer status, Long id);

    SetmealVO getSealById(Long id);

    void update(SetmealDTO setmealDTO);
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);


}
