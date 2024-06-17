package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加用户
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pagequery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 禁止或者开启状态
     * @param status
     * @param id
     */
    void startOrEnd(Integer status, Long id);

    /**
     * 根据id查询所有的数据
     * @param id
     * @return
     */
    Employee selectById(Long id);

    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 新写的添加员工
     * @param employeeDTO
     */
    void save2(EmployeeDTO employeeDTO);

    /**
     * 分页查询2
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pagequery2(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用、禁用员工账号
     * @param status
     */
    void startOrEnd2(Integer status,Long id);

    Employee selectById2(Long id);

    void update2(EmployeeDTO employeeDTO);

}
