package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


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


    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */

    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getById(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
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


    @Transactional
    @Override
    public void remove(List<Long> ids) {
        //根据产品原型发现四个需求：
//        1、 在售状态下不能删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

//        2、 被套餐关联不能删除
        List<Long> setmeal = setmealDishMapper.getSetmeal(ids);
        if(setmeal.size()>0 && setmeal != null ){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

//        3 删除菜品   --->这个是只删除一个菜品，需要重复执行sql语句，可以改用批量删除
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //        4 删除口味
//            dishFlavorMapper.deleteById(id);
//        }



//        批量删除
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);
    }


    @Override
    public void startOrEnd(Integer status, Long id) {
        dishMapper.updateStatus(status, id);
    }


    @Transactional
    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavor = dishFlavorMapper.getById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }

    @Transactional
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.updateDish(dish);

        //还有可能要修改口味  --- > 先删除，后插入
        List<DishFlavor> flavors = dishDTO.getFlavors();
//        dishFlavorMapper.updateFlavor(flavors);
        dishFlavorMapper.deleteById(dishDTO.getId());
//        dishFlavorMapper.insertBatch(flavors);
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                        dishFlavor.setDishId(dishDTO.getId());
                    }
            );
            //批量插入d
            dishFlavorMapper.insertBatch(flavors);
        }


    }


    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        List<Dish> byCategoryId = dishMapper.getByCategoryId(dish);

        return byCategoryId;
    }
}
