package com.atguigu.mianshi._01_Season;

public class Son extends Father{

    private static int j = method();
    private int i = test();

    static {
        System.out.print("(6)");
    }

    Son() {
        System.out.print("(7)");
    }
    {
        System.out.print("(8)");
    }

    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 1;
    }

    /**
     * 类初始化过程
     * 	一个类要创建实例需要先加载并初始化该类
     * 	一个子类初始化需要先初始化父类
     * 	一个类初始化就是执行<clinit>()方法
     * 		<clinit>()方法由静态类变量显示赋值代码和静态代码块组成
     * 		类变量显示赋值代码和静态代码块代码从上到下顺序执行
     * 实例初始化过程
     *  实例初始化就是执行<init>()方法
     * 	<init>()方法由非静态类变量显示赋值代码和非静态代码块、对应构造器代码组成
     * 	非静态实例变量显示赋值代码和非静态代码块代码从上到下顺序执行，而对应构造器的代码最后执行
     * 	<init>方法的首行是super()或super(实参列表)，即对应父类的<init>方法
     * 方法重写
     * 	那些方法不可以被重写
     * 		final方法
     * 		静态方法
     * 		private等子类中不可见的方法
     * 	对象的多态性
     * 		子类如果重写了父类的方法，通过子类对象调用的一定是子类重写过的代码
     * 		非静态方法默认的调用对象是this
     * 		this对象在构造器或者说<init>方法中就是正在创建的对象
     * @param args
     */
    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }

}
