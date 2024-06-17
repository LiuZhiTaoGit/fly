package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工推出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 创建员工
     * @param employeeDTO   前端传入的数据，新创建了一个对应前端的DTO
     * @return
     */
    @PostMapping
    @ApiOperation("添加员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){   //传入的是一个对象，用requestBody注解
        log.info("添加员工成功");
        employeeService.save(employeeDTO);
        return Result.success();
    }





    @ApiOperation("员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询,参数为：{}",employeePageQueryDTO);

        PageResult pr = employeeService.pagequery(employeePageQueryDTO);

        return Result.success(pr);
    }
    @ApiOperation("启用或禁止状态")
    @PostMapping("/status/{status}")
    public Result startOrEnd(@PathVariable Integer status, Long id){
        log.info("启用或者禁止使用{}{}",status,id);
        employeeService.startOrEnd(status,id);
        return Result.success();
    }

    @ApiOperation("根据id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    /**
     *  编辑员工信息
     * @param employeeDTO
     * @return
     */
    @ApiOperation("编辑员工信息")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工：{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }


    /**
     * 添加员工2
     * @param employeeDTO
     * @return
     */
    @PostMapping("/save2")
    @ApiOperation(value = "添加员工——new")
    public Result save2(@RequestBody EmployeeDTO employeeDTO){
        log.info("新写的添加员工：");
        employeeService.save2(employeeDTO);
        return Result.success();
    }


    @GetMapping("/page2")
    @ApiOperation(value = "分页查询2")
    public Result<PageResult> page2(EmployeePageQueryDTO employeePageQueryDTO){//数据格式不是json的，不用加resuestbosy了
        log.info("分页查询2——--");
        PageResult pageResult = employeeService.pagequery2(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    @ApiOperation(value = "启用、禁用员工账号")
    @PostMapping("/status2/{status}")
    public Result startOrEnd2(@PathVariable Integer status, Long id){//路径参数要用pathvariable
        log.info("启用、禁用员工账号{},{}",status,id);
        employeeService.startOrEnd2(status,id);

        return Result.success();
    }



}
