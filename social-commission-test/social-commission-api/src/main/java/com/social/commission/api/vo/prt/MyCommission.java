package com.social.commission.api.vo.prt;

import lombok.Data;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/2  18:14
 */
public class MyCommission {
    private int status;
    private static volatile MyCommission myCommission;
    private MyCommission(){ }
    public static MyCommission getInst(){
        if (myCommission == null){
            synchronized (MyCommission.class){
                if (myCommission == null){
                    myCommission = new MyCommission();
                }
            }
        }
        return myCommission;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
