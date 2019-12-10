package com.haimin.code.java8.stream;

import com.google.common.collect.Lists;
import com.social.commission.api.dto.Employee;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/9  14:36
 */
@Slf4j
public class StreamExample {
    private static List<Employee> employeeList = Lists.newArrayList();
    static{
        employeeList.add(Employee.builder().name("王强").salary(5000).office("信用社").age(25).build());
        employeeList.add(Employee.builder().name("郭志成").salary(6000).office("海员").age(26).build());
        employeeList.add(Employee.builder().name("陈著名").salary(20000).office("司机").age(35).build());
        employeeList.add(Employee.builder().name("康佳旭").salary(7000).office("会计").age(45).build());
        employeeList.add(Employee.builder().name("王晓宇").salary(8000).office("程序员").age(65).build());
        employeeList.add(Employee.builder().name("汪相如").salary(29000).office("导游").age(36).build());
        employeeList.add(Employee.builder().name("张雪").salary(10000).office("教师").age(53).build());
    }

    public static void main(String[] args) {
        /*
         * ①Stream 自己不会存储元素。
         * ②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
         * ③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行
         * Stream API 的操作步骤：
         * 1. 创建 Stream
         * 2. 中间操作
         * 3. 终止操作(终端操作)
         */
        //1. 创建 Stream
        createStream();
        //2. 中间操作
        middleHandler();
        //3. 终止操作(终端操作)
        endHandler();
        //一些特殊的例子
        someExample();
    }

    private static void someExample() {
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
        log.info("这个map是我想要的");
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
    }

    private static void endHandler() {
        /*
         * 终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。
         */
        /*
         * 1.0 查找与匹配
         * allMatch(Predicate p) 检查是否匹配所有元素
         * anyMatch(Predicate p) 检查是否至少匹配一个元素
         * noneMatch(Predicate p) 检查是否没有匹配所有元素
         * findFirst() 返回第一个元素
         * findAny() 返回当前流中的任意元素
         */
        Optional<Employee> first = employeeList.stream().findFirst();
        System.out.println(first.get());
        Optional<Employee> any = employeeList.parallelStream().findAny();
        System.out.println(any.get());
        boolean b = employeeList.stream().anyMatch((e) -> e.getAge() > 40);
        System.out.println(b);
        /*
         * count() 返回流中元素总数
         * max(Comparator c) 返回流中最大值
         * forEach(Consumer c) 内部迭代(使用 Collection 接口需要用户去做迭代，称为外部迭代。相反，Stream API 使用内部迭代——它帮你把迭代做了)
         */
        long count = employeeList.stream().count();
        System.out.println(count);
        Optional<Employee> max = employeeList.stream().max((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());
        /*
         * 2.0 规约
         * reduce(T iden, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 T
         * reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
         */
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //BinaryOperator
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        System.out.println("----------------------------------------");
        Optional<Integer> op = employeeList.stream()
                .map(Employee::getSalary)
                .reduce(Integer::sum);
        System.out.println(op.get());
        /*
         * 3.0 收集
         * collect(Collector c) 将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
         */
        List<Employee> collect = employeeList.stream().filter((e) -> e.getAge() < 40).collect(Collectors.toList());
        collect.forEach(System.out::println);
        //分组
        Map<String, List<Employee>> map = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getOffice));
        System.out.println(map);
        //多级分组
//        Map<Status, Map<String, List<Employee>>> map = emps.stream()
//                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
//                    if(e.getAge() >= 60)
//                        return "老年";
//                    else if(e.getAge() >= 35)
//                        return "中年";
//                    else
//                        return "成年";
//                })));
        Map<Boolean, List<Employee>> map1 = employeeList.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));

        System.out.println(map1);
        map1.forEach((k, v) -> System.out.println(k + "-" + v));
    }

    private static void middleHandler() {
        /*
         *多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，
         * 否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”
         */
        /*
         * 1.0 筛选与切片
         * filter(Predicate p) 接收 Lambda， 从流中排除某些元素。
         * distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素,所以对象要重写 hashCode() 和 equals()
         * limit(long maxSize) 截断流，使其元素不超过给定数量。
         * skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
         *
         */
        employeeList.stream().filter((e) -> e.getAge() >50).forEach(System.out::println);
        System.out.println("____________________________");
        employeeList.stream().filter((e) -> e.getAge() >30).limit(3).forEach(System.out::println);
        System.out.println("____________________________");
        /*
         * 2.0 映射
         * map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
         * mapToDouble(ToDoubleFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
         * mapToInt(ToIntFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
         * mapToLong(ToLongFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
         * flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
         */
        employeeList.stream().map((e) -> e.getSalary() + 2000).forEach(System.out::println);
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);
        strList.stream().flatMap((str) -> {
            List<Character> list = new ArrayList<>();
            for (Character ch : str.toCharArray()) {
                list.add(ch);
            }
            return list.stream();
        }).sorted((x,y) -> -x.compareTo(y)).forEach(System.out::println);

        /*
         * 3.0 排序
         * sorted() 产生一个新流，其中按自然顺序排序
         * sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
         */
    }

    private static void createStream() {
        //创建 Stream 的方式有四个
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个串行流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);
        /*
         * public static IntStream stream(int[] array)
         * public static LongStream stream(long[] array)
         * public static DoubleStream stream(double[] array)
         */

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1,2,3,4,5,6);
        stream2.forEach(System.out::println);

        //4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }
}
