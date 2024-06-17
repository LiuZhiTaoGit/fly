package com.sky.mapper;

import com.sky.annotation.AutoFile;
import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入用户
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) VALUES " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})" )
    @AutoFile(value = OperationType.INSERT)
    void insert(Employee employee);


    /**
     * 分页查找
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新数据，
     * @param employee
     */
    @AutoFile(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);


    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values" +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}) ")
    void insert2(Employee employee);


    @Select("select count(*) from employee")
    Integer getCount();


    List<Employee> getList(String name, Integer offset,Integer lim);


    void update2(Employee employee);

}
