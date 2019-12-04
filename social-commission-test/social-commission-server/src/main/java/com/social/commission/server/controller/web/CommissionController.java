package com.social.commission.server.controller.web;

import com.google.common.collect.Lists;
import com.social.commission.api.dto.Employee;
import com.social.commission.api.vo.AccountCommissionResultVO;
import com.social.commission.api.vo.prt.MyCommission;
import com.social.common.model.BaseResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Haimin Li
 * @description: 佣金相关
 * @date 2019/7/8  17:47
 */

@Controller
@Slf4j
@Api(description = "佣金相关")
@RequestMapping("/commission")
@SuppressWarnings("All")
public class CommissionController {
    private static List<Employee> employeeList = Lists.newArrayList();
    static{
        employeeList.add(Employee.builder().name("王强").salary(5000).office("信用社").build());
        employeeList.add(Employee.builder().name("郭志成").salary(6000).office("海员").build());
        employeeList.add(Employee.builder().name("陈著名").salary(20000).office("司机").build());
        employeeList.add(Employee.builder().name("康佳旭").salary(7000).office("会计").build());
        employeeList.add(Employee.builder().name("王晓宇").salary(8000).office("程序员").build());
        employeeList.add(Employee.builder().name("汪相如").salary(29000).office("导游").build());
        employeeList.add(Employee.builder().name("张雪").salary(10000).office("教师").build());
    }


    @RequestMapping(value = "/getCommissionList", method = RequestMethod.POST)
    @ApiImplicitParam(name = "accountCommissionResultVO", value = "参数vo", dataType = "AccountCommissionResultVO")
    @ApiOperation(value = "获取佣金列表", notes = "获取佣金列表{必传(tenantId)}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResultResponse getCommissionList(@Valid @RequestBody AccountCommissionResultVO accountCommissionResultVO){

        //region Description
//        Map<String, String> map = new HashMap<>();
//        map.put("Aa", "wwqe");
//        map.put("BB", "wwqe");
//        map.put("123", "wwqe");
//        map.put("123", "wwqe");
//
//
//
//        Integer a = new Integer(0);
//        Integer b = new Integer(0);
//
//        Integer c = 0;
//        Integer d = 0;
//        Integer g1 = 40;
//        Integer g2 = 227;
//
//        Integer e = new Integer(227);
//        Integer f = new Integer(40);
//
//        boolean b1 = a >= b;
//        boolean b11 = a > b;
//        boolean b2 = a == c;
//        boolean b3 = c == d;
//        boolean b4 = e == f;
//        boolean b5 = a == 0;
//        boolean b6 = g1 == f;
//        boolean b7 = g2 == e;
//        boolean b8 = a.intValue() == b.intValue();
//        boolean b9 = g2.intValue() == e.intValue();
//        boolean b10 = g2 > g1;
//        boolean b12 = g2 > 0;
//        log.info("####################");
//        log.info("####################");
//        log.info("####################");
//        Integer i01 = 59;
//        int i02 = 59;
//        Integer i03 =Integer.valueOf(59);
//        Integer i04 = new Integer(59);
//
//        log.info("i01== i02 : [{}]", i01== i02);
//        log.info("i01== i03 : [{}]", i01== i03);
//        log.info("i03== i04 : [{}]", i03== i04);
//        log.info("i02== i04 : [{}]", i02== i04);
//endregion

        int n = accountCommissionResultVO.getIntFlag() - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        String a = "ab";
        int i = a.hashCode();
        int ii = i >>> 16;
        int hash = i ^ ii;
        int ia = 15 & hash;
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        //Java8之前遍历是这样遍历map
//         for(Map.Entry<String,String> entry:map.entrySet()){
//               log.info("打印日志 = {}","key:" + entry.getKey() + " value:" + entry.getValue());
//         }
//
//        //Java8遍历map
//        map.forEach((key,value)-> log.info("打印日志 = {}","key:" + key + " value:" + value));













        //是否含有符合条件的数据
        synchronized (Integer.class){

        }
        boolean isMatch = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
        boolean isMatch1 = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("教师"));
        log.info("是否含有符合条件的数据 = {}",isMatch);

        //是否全部都符合条件
        boolean matched = employeeList.stream().allMatch(employee -> employee.getSalary()>10000);
        log.info("是否全部都符合条件 = {}",matched);

        //找出工资最高
        Optional<Employee> hightestSalary = employeeList.stream().max((e1, e2)->Integer.compare(e1.getSalary(),e2.getSalary()));
        log.info("工资最高 = {}",hightestSalary);

        //返回姓名列表
        List<String> names = employeeList.stream().map(employee -> employee.getName()).collect(Collectors.toList());
        Stream<Employee> stream = employeeList.stream();
        log.info("姓名列表 = {}",names);

        //List转换成Map
        Map<String,Employee> employeeMap = employeeList.stream().collect(Collectors.toMap((key->(key.getName() + key.getOffice())),(value->value)));
        employeeMap.forEach((key,value)-> log.info("打印日志 = {}",key + "=" + value));

        //统计办公室是New York的个数
        long officeCount = employeeList.stream().filter(employee -> employee.getOffice().equals("Shanghai")).count();
        log.info("打印日志 = {}",officeCount);

        //List转换为Set
        Set<String> officeSet = employeeList.stream().map(employee -> employee.getOffice()).distinct().collect(Collectors.toSet());
        log.info("打印日志 = {}",officeSet);

        //查找办公室地点是New York的员工
        Optional<Employee> allMatchedEmployees = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).findAny();
        log.info("打印日志 = {}",allMatchedEmployees);

        //按照工资的降序来列出员工信息
        List<Employee> sortEmployeeList =  employeeList.stream().sorted((e1,e2)->Integer.compare(e2.getSalary(),e1.getSalary())).collect(Collectors.toList());
        //按照名字的升序列出员工信息
        List<Employee> sortEmployeeByName = employeeList.stream().sorted((e1,e2)->e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
        log.info("打印日志 = {}",sortEmployeeList);
        log.info("打印日志 = {}","按照名字的升序列出员工信息:" + sortEmployeeByName);

        //获取工资最高的前2条员工信息
        List<Employee> top2EmployeeList= employeeList.stream()
                .sorted((e1,e2)->Integer.compare(e2.getSalary(),e1.getSalary()))
                .limit(2)
                .collect(Collectors.toList());
        log.info("打印日志 = {}",top2EmployeeList);

        //获取平均工资
        OptionalDouble averageSalary = employeeList.stream().mapToInt(employee->employee.getSalary()).average();
        log.info("打印日志 = {}","平均工资:" + averageSalary);

        //查找New York
        OptionalDouble averageSalaryByOffice = employeeList.stream().filter(employee -> employee.getOffice()
                .equals("New York"))
                .mapToInt(employee->employee.getSalary())
                .average();
        log.info("打印日志 = {}","New York办公室平均工资new Array:" + averageSalaryByOffice);













        return null;
    }

    @RequestMapping(value = "/getStatus", method = RequestMethod.GET)
    @ApiOperation(value = "状态", notes = "状态{必传(tenantId)}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResultResponse getStatus(){
        List<Integer> list = new ArrayList<>();
        MyCommission inst = MyCommission.getInst();
        list.add(inst.getStatus());
        int i = 0;
        List<AccountCommissionResultVO> list1 = new ArrayList<>();
         while (true){
             i++;
             log.info("对象个数 {} ", i);
             AccountCommissionResultVO accountCommissionResultVO = new AccountCommissionResultVO();
             list1.add(accountCommissionResultVO);
         }
        // return BaseResultResponse.success(list);
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    @ApiOperation(value = "状态", notes = "状态{必传(tenantId)}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResultResponse updateStatus(@RequestParam("status") Integer status){
        MyCommission inst = MyCommission.getInst();
        inst.setStatus(status);
        return BaseResultResponse.success(null);
    }
}
