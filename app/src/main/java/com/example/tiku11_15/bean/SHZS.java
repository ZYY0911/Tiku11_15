package com.example.tiku11_15.bean;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 15:12 ：）
 */
public class SHZS {

    /**
     * temperature : 17
     * humidity : 11
     * illumination : 980
     * co2 : 7849
     * pm25 : 235
     * RESULT : S
     */

    private int temperature;
    private int humidity;
    private int illumination;
    private int co2;
    private int pm25;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getIllumination() {
        return illumination;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }


}
