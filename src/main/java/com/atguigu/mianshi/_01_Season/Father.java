package com.atguigu.mianshi._01_Season;

public class Father {

    private static int j = method();
    private int i = test();

    static {
        System.out.print("(1)");
    }

    Father() {
        System.out.print("(2)");
    }
    {
        System.out.print("(3)");
    }

    public int test() {
        System.out.print("(4)");
        return 1;
    }

    public static int method() {
        System.out.print("(5)");
        return 1;
    }

}
