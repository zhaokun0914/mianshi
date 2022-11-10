package com.atguigu.mianshi._02_Season._02_CAS;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
class User {

    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

/**
 * 原子引用
 * @author Kavin
 * @date 2022/11/10 22:46
 */
@Slf4j
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        // 创建原子引用包装类
        AtomicReference<User> atomicReference = new AtomicReference<>();

        // 现在主物理内存的共享变量，为z3
        atomicReference.set(z3);

        // 比较并交换，如果现在主物理内存的值为z3，那么交换成l4
        log.info(atomicReference.compareAndSet(z3, li4) + "\t|" + atomicReference.get());

        // 比较并交换，现在主物理内存的值是l4了，但是预期为z3，因此交换失败
        log.info(atomicReference.compareAndSet(z3, li4) + "\t|" + atomicReference.get());
    }

}
