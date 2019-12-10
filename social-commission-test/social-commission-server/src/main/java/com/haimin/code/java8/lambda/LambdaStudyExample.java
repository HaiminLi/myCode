package com.haimin.code.java8.lambda;

import com.social.commission.api.dto.Employee;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/9  10:13
 */
public class LambdaStudyExample {

    /*
     *一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
     * 						    箭头操作符将 Lambda 表达式拆分成两部分：
     * 左侧：Lambda 表达式的参数列表
     * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
     *
     * 语法格式一：无参数，无返回值
     * 		() -> System.out.println("Hello Lambda!");
     *
     * 语法格式二：有一个参数，并且无返回值
     * 		(x) -> System.out.println(x)
     *
     * 语法格式三：若只有一个参数，小括号可以省略不写
     * 		x -> System.out.println(x)
     *
     * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句,语句用{}包上
     *		Comparator<Integer> com = (x, y) -> {
     *			System.out.println("函数式接口");
     *			return Integer.compare(x, y);
     *		};
     *
     * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
     * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
     *
     * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
     *
     * 二、Lambda 表达式需要“函数式接口”的支持
     * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
     * 			 可以检查是否是函数式接口
     *
     * Java8 内置的四大核心函数式接口
     * Consumer<T> : 消费型接口
     * 		void accept(T t);
     * Supplier<T> : 供给型接口
     * 		T get();
     * Function<T, R> : 函数型接口
     * 		R apply(T t);
     * Predicate<T> : 断言型接口
     * 		boolean test(T t);
     *
     * 三、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
     * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
     * 1. 对象的引用 :: 实例方法名
     * 2. 类名 :: 静态方法名
     * 3. 类名 :: 实例方法名
     * 注意：
     * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
     * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
     * 四、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
     *    类名 :: new
     * 五、数组引用
     * 	类型[] :: new;
     */
    public static void main(String[] args) {
        //相当 = 号左边要是一个接口，右边的lambda表达式，是对这个接口方法的一个实现
        //返回一个对象
        Supplier<Employee> supplier = () -> new Employee("王强",5000,"信用社",28);
        Employee employee = supplier.get();
        //构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
        Supplier<Employee> supplier1 = Employee :: new;
        Employee employee1 = supplier1.get();
        //消费一个对象
        Consumer<Employee> consumer = (e) -> System.out.println(e.getName());
        consumer.accept(employee);
        //传入一个对象，返回一个对象
        Function<Employee, String> function = (e) -> e.getName();
        String name = function.apply(employee);
        System.out.println(name);
        //传入一个对象，返回 boolean
        Predicate predicate = null;

        //方法引用
        //方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
        //若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
        BiPredicate<String, String> biPredicate = (x ,y) -> x.equals(y);
        boolean test = biPredicate.test("abd", "abd");
        System.out.println(test);
        BiPredicate<String, String> biPredicate1 = String :: equals;
        boolean test1 = biPredicate1.test("abd", "abd");
        System.out.println(test1);
        //类名 :: 静态方法名
        BiFunction<Double, Double, Double> fun = (x, y) -> Math.max(x, y);
        System.out.println(fun.apply(1.5, 22.2));
        BiFunction<Double, Double, Double> fun2 = Math::max;
        System.out.println(fun2.apply(1.2, 1.5));
        //对象的引用 :: 实例方法名
        Supplier<String> sup = () -> employee.getName();
        System.out.println(sup.get());
        Supplier<String> sup2 = employee::getName;
        System.out.println(sup2.get());
        //数组引用   类型[] :: new;
        FunctionInterfaceDemo<Integer, String[]> fun3 = (max) -> new String[max];
        String[] strs = fun3.get(10);
        System.out.println(strs.length);
        Function<Integer, Employee[]> fun4 = Employee[] :: new;
        Employee[] emps = fun4.apply(20);
        System.out.println(emps.length);
    }

}
