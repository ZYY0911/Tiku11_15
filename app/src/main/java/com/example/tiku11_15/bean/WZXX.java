package com.example.tiku11_15.bean;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 22:25 ：）
 */
public class WZXX {

    /**
     * id : 1
     * time : 2017-04-02 14:55:23
     * infoid : 10101
     * road : 学院路
     * message : 在交叉路口不按导向标线行驶在相应车道。
     * deduct : 1
     * fine : 0
     */

    private int id;
    private String time;
    private String infoid;
    private String road;
    private String message;
    private int deduct;
    private int fine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDeduct() {
        return deduct;
    }

    public void setDeduct(int deduct) {
        this.deduct = deduct;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
