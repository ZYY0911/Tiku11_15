package com.example.tiku11_15.bean;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 22:38 ：）
 */
public class CLWZRight {
    private String Cp,Sj,KL,Msg;
    private int Kf,Fk;

    public CLWZRight(String cp, String sj, String KL, String msg, int kf, int fk) {
        Cp = cp;
        Sj = sj;
        this.KL = KL;
        Msg = msg;
        Kf = kf;
        Fk = fk;
    }

    public String getCp() {
        return Cp;
    }

    public void setCp(String cp) {
        Cp = cp;
    }

    public String getSj() {
        return Sj;
    }

    public void setSj(String sj) {
        Sj = sj;
    }

    public String getKL() {
        return KL;
    }

    public void setKL(String KL) {
        this.KL = KL;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getKf() {
        return Kf;
    }

    public void setKf(int kf) {
        Kf = kf;
    }

    public int getFk() {
        return Fk;
    }

    public void setFk(int fk) {
        Fk = fk;
    }
}
