package com.louisgeek.louispintu;

/**
 * Created by louisgeek on 2016/8/2.
 */
public class TxtBean {
    private int x;

    public TxtBean(int x, int y, String data_x, String data_y) {
        this.x = x;
        this.y = y;
        this.data_x = data_x;
        this.data_y = data_y;
    }

    private int y;
    private String data_x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "TxtBean{" +
                "x=" + x +
                ", y=" + y +
                ", data_x='" + data_x + '\'' +
                ", data_y='" + data_y + '\'' +
                '}';
    }

    public String getData_x() {
        return data_x;
    }

    public void setData_x(String data_x) {
        this.data_x = data_x;
    }

    public String getData_y() {
        return data_y;
    }

    public void setData_y(String data_y) {
        this.data_y = data_y;
    }

    private String data_y;


}
