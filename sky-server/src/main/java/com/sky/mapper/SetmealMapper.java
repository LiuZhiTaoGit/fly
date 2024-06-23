package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFile;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 往dish表中插入数据
     * @param setmeal
     */
    @AutoFile(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Select("select * from setmeal where id= #{id}")
    Dish isSale(Long id);

    void deleteMealByIds(List<Long> ids);

    List<SetmealDish> getDishIdById(Long id);


    @Select("select * from dish where id = #{id}")
    Dish getStatusById(Long id);

    @AutoFile(OperationType.UPDATE)
    @Update("update setmeal set status = #{status} where id = #{id}")
    void startOrEnd(Integer status, Long id);


    SetmealVO getSealById(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateMeal(Setmeal setmeal);




    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

}
