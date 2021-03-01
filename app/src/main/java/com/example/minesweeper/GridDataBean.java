package com.example.minesweeper;

public class GridDataBean {

    private int number;
    private boolean isShow;
    private boolean isBlood;

    public GridDataBean(int number, boolean isShow) {
        this.number = number;
        this.isShow = isShow;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isBlood() {
        return isBlood;
    }

    public void setIsBlood(boolean isBlood) {
        this.isBlood = isBlood;
    }
}
