package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 14:15
 * @description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class DishServiceImpl implements DishService {
    //注入需要的mapper类
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    /**
     * 新增菜品  设计到了多个表的操作，所以需要保证数据的一致性，用到了事务！
     * @param dishDTO
     */
    @Transactional//事务   要不全成功，要不全失败
    public void saveWithFlavor(DishDTO dishDTO) {
//        Dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //向菜品插入一条数据
        dishMapper.insert(dish);
        //获取菜品的主键
        Long id = dish.getId();
//        flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
                    }
            );
            //批量插入d
            dishFlavorMapper.insertBatch(flavors);
        }


    }

    @Override
    public PageResult pagequery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page =  dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    @Override
    public void saveWithFlavor2(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert2(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(flavor->{
                flavor.setId(id);
            });
            dishFlavorMapper.insertBatch2(flavors);
        }
    }


    @Override
    public PageResult pagequery2(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> dishVOS = dishMapper.pageQuery2(dishPageQueryDTO);

        return new PageResult(dishVOS.getTotal(), dishVOS.getResult());

    }
}
