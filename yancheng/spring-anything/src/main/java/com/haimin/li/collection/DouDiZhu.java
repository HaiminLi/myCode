package com.haimin.li.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DouDiZhu {
    static {

    }

    public static void main(String[] args) {
        List<Integer> num = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        String[] strs = {"红", "黑", "方", "花"};
        String[] numbers = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int index = 0;
        for (String s1 : numbers){
            for (String s2 : strs){
                map.put(index, s1 + s2);
                num.add(index);
                index++;
            }
        }
        map.put(52, "大猫");
        num.add(52);
        map.put(53, "小猫");
        num.add(53);
        List<Integer> p1 = new ArrayList<>();
        List<Integer> p2 = new ArrayList<>();
        List<Integer> p3 = new ArrayList<>();
        List<Integer> last = new ArrayList<>();
        Collections.shuffle(num);
        for (int i = 0; i < num.size(); i++){
            if (i < 3){
                last.add(num.get(i));
            }else if (i % 3 == 0){
                p1.add(num.get(i));
            }else if (i % 3 == 1){
                p2.add(num.get(i));
            }else if (i % 3 == 2){
                p3.add(num.get(i));
            }
        }
        Collections.sort(last);
        Collections.sort(p1);
        Collections.sort(p2);
        Collections.sort(p3);
        System.out.println("底牌");
        look(last, map);
        System.out.println("第一个人：");
        look(p1, map);
        System.out.println("第二个人：");
        look(p2, map);
        System.out.println("第三个人：");
        look(p3, map);
    }

    public static void look(List<Integer> num, Map<Integer, String> map){
        for (Integer integer : num){
            System.out.print(map.get(integer));
            System.out.print("   ");
        }
        System.out.println();
    }
}
