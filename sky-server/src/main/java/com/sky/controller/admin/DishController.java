package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 14:12
 * @description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController
@Api(tags = "菜品相关")
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * 添加的是菜品和口味
     * @param dishDTO
     * @return
     */
    @ApiOperation("添加菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("开始添加菜品{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

//    @ApiOperation("分页查询")
//    @GetMapping("/page")
//    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){  //这个不用添加requestBody,因为使用的是query方式，在地址栏中使用key=value方式
//        log.info("分页查询");
//        PageResult pageResult = dishService.pagequery(dishPageQueryDTO);
//        return Result.success(pageResult);
//    }


    @PostMapping("/post2")
    @ApiOperation(value = "添加餐品2")
    public Result save2(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavor2(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation(value = "分页查询2")
    public Result<PageResult> page2(DishPageQueryDTO dishPageQueryDTO){  //请求参数是query，不用requestboby
        PageResult pageResult = dishService.pagequery2(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping()
    @ApiOperation(value = "批量删除菜品")
    public Result remove(@RequestParam List<Long> ids){  //这个注解的作用是  mvc框架自动解析这个字符串，可以把id都提取出来
        log.info("批量删除菜品的ids是： {}",ids);
        dishService.remove(ids);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    @ApiOperation(value = "菜品状态的修改")
    public Result startOrEnd(@PathVariable Integer status, Long id){
        log.info("更改菜品的状态，参数status:{}和id：{}", status, id);
        dishService.startOrEnd(status,id);
        return Result.success();
    }


    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        DishVO dish =dishService.getById(id);
        return Result.success(dish);
    }


    @PutMapping
    @ApiOperation(value = "更新数据")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> dish = dishService.list(categoryId);
        return Result.success(dish);

    }







}
