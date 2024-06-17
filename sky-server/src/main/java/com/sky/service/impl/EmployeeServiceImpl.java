package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
//        对前端的md5进行了处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 添加用户
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //因为数据库的表是完整的，所以我们还是用employee对象
        Employee employee = new Employee();
        //不能一个个的复制所有已有的属性，使用属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        //添加其他的的属性
        employee.setStatus(StatusConstant.ENABLE);//账号状态
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);//mima

        //创始人、创世时间，修改人，修改时间
        //TODO 修改创建人id 和修改人id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.insert(employee);
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pagequery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page  = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total, result);
    }


    @Override
    public void startOrEnd(Integer status, Long id) {
        //传统方式
//        Employee employee = new Employee();
//        employee.setStatus(status);
//        employee.setId(id);

        //build方式
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();

        employeeMapper.update(employee);
    }

    /**
     * 根据id返回查询的
     * @param id
     * @return
     */
    @Override
    public Employee selectById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    /**
     * 更新数据
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);

    }

    /**
     * 新写的添加用户
     * @param employeeDTO
     */
    @Override
    public void save2(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
//          需要获取当前的用户和修改的用户，不会，先设置为固定的
//        employee.setCreateUser(10L);
//        employee.setUpdateUser(10L);
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert2(employee);






    }


    /**
     * 分页查询2
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pagequery2(EmployeePageQueryDTO employeePageQueryDTO) {

//        //得到总数
//        long total = employeeMapper.getCount();
////        得到所有的记录
//        Integer limit = employeePageQueryDTO.getPageSize();
//        Integer Offset = (employeePageQueryDTO.getPage() - 1 ) * limit;
//        List<Employee> list = employeeMapper.getList(employeePageQueryDTO.getName(),Offset,limit);
//        PageResult pageResult = new PageResult(total, list);
//        return pageResult;


        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total1 = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total1, result);


    }
}
