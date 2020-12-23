package com.shawntime.architect.notes.concurrency.threadlocal;

/**
 * ThreadLocalMap使用“开放寻址法”中最简单的“线性探测法”解决散列冲突问题
 */
public class MagicHashCode {
    /**
     * ThreadLocal中定义的hash魔数
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        // 初始化16
        hashCode(16);
        // 后续2倍扩容
        hashCode(32);
        // 后续4倍扩容
        hashCode(64);
    }

    /**
     *
     * @Description 寻找散列下标（对应数组小标）
     * @param length table长度
     * @since JDK1.8
     */
    private static void hashCode(Integer length){
        int hashCode = 0;
        for(int i=0;i<length;i++) {
            //每次递增HASH_INCREMENT
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            //求散列下标，算法公式
            System.out.print(hashCode & (length-1));
            System.out.print(" ");
        }
        System.out.println();
    }
}
