package com.haimin.code.enums;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/4  17:44
 */

public enum ToBeDB {
    ONE("oneCode","大娃",1),
    TWO("twoCode","二娃",2),
    THREE("threeCode","三娃",3),
    FOUR("fourCode","四娃",4),
    FIVE("fiveCode","五娃",5),
    SIX("sixCode","六娃",6),
    SEVEN("sevenCode","七娃",7);

    private String name;
    private String code;
    private int age;
    private ToBeDB(String code, String name, int age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }
    public static String getNameByCode(String code){
        for (ToBeDB toBeDB : ToBeDB.values()){
            if (code.equals(toBeDB.getCode())){
                return toBeDB.getName();
            }
        }
        return null;
    }
    public static String getNameByAge(int age) {
        for (ToBeDB toBeDB : ToBeDB.values()){
            if (age == toBeDB.getAge()){
                return toBeDB.getName();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
