package com.louisgeek.louispintu;

import android.graphics.Bitmap;

/**
 * Created by louisgeek on 2016/8/2.
 */
public class ImageDataBean {
    private int x;
    private int y;
    private Bitmap bitmap;
    private int data_x;

    public ImageDataBean(int x, int y, Bitmap bitmap, int data_x, int data_y) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.data_x = data_x;
        this.data_y = data_y;
    }

    private int data_y;


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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getData_x() {
        return data_x;
    }

    public void setData_x(int data_x) {
        this.data_x = data_x;
    }

    public int getData_y() {
        return data_y;
    }

    public void setData_y(int data_y) {
        this.data_y = data_y;
    }


    public  boolean  isRightXY(){
        if (x==data_x&&y==data_y){
            return true;
        }
        return  false;
    }
}
