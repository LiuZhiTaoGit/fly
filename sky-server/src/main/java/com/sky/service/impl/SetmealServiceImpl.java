package com.sky.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/6/19 - 06 - 19 - 13:04
 * @description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public void insert(SetmealDTO setmealDTO) {
        //往套餐表中插入
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();

        //套餐包含的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        setmealDishMapper.insertDish(setmealDishes);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        //在售状态不能售卖
        for (Long id : ids) {
            Dish dish = setmealMapper.isSale(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
//                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //删除套餐
        setmealMapper.deleteMealByIds(ids);
    }

    @Override
    public void startOrEnd(Integer status, Long id) {
        //查看套餐内的菜品是否可售，如果不可售就无法上架  从dish根据id找到套餐的id，然后在
        if(status == StatusConstant.ENABLE){

            //查找该套餐下所有的商品
            List<SetmealDish> setmealDishes = setmealDishMapper.getDishIdById(id);
//        List<SetmealDish> setmealDishes = setmealMapper.getDishIdById(id);
            for (SetmealDish setmealDish : setmealDishes) {
                //判断这个菜是否可售
                Dish statusById = setmealMapper.getStatusById(setmealDish.getDishId());
                if(statusById.getStatus() == StatusConstant.DISABLE){
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        setmealMapper.startOrEnd(status,id);





    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }



    @Override
    public SetmealVO getSealById(Long id) {
        SetmealVO setmealVO = setmealMapper.getSealById(id);
        List<SetmealDish> dishIdById = setmealDishMapper.getDishIdById(id);
        setmealVO.setSetmealDishes(dishIdById);
        return setmealVO;
    }

    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //更新setmeal表
        setmealMapper.updateMeal(setmeal);
       //更新 dish表  -->直接删除，然后如果有的话就添加
        setmealDishMapper.deleteByIds(setmealDTO.getId());
        Long id = setmeal.getId();

        //套餐包含的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        setmealDishMapper.insertDish(setmealDishes);
        }
}
