package com.atguigu.mianshi._02_Season._01_Volatile;

public class T1 {
    volatile int n = 0;
    public void add() {
        n++;
    }
}