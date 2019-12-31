package com.haimin.li.spring.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private int age;
    private String name;
    private String work;
    public void myWork(){
        System.out.println("我的工作" + this.work);
    }
    public void myDream(){
        System.out.println("我的理想" + this.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", work='" + work + '\'' +
                '}';
    }
}
