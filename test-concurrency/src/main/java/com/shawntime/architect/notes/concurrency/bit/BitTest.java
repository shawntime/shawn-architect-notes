package com.shawntime.architect.notes.concurrency.bit;

public class BitTest {

    public static void main(String[] args) {
// 1010
System.out.println("10 = " + Integer.toBinaryString(10));

// 0110
System.out.println("6 = " + Integer.toBinaryString(6));

// 按位与: 1&1=1 1&0 = 0 0&1=0 0&0=0
// 0010
System.out.println("10 & 6 = " + Integer.toBinaryString(10 & 6));

// 按位或：1&1=1 1&0=1 0&1=1 0&0=0
// 1110
System.out.println("10 | 6 = " + Integer.toBinaryString(10 | 6));

// 按为异或：1&1=0 1&0=1 0&1=1 0&0=0
// 1100
System.out.println("10 ^ 6 = " + Integer.toBinaryString(10 ^ 6));


// 左移
int a = 10;
// 1010 <<2 = 0010 1000
// 40 = 10 * 2 ^ 2
System.out.println("a << 2 = " + (a << 2));
// 0010 1000
System.out.println("a << 2 = " + Integer.toBinaryString((a << 2)));

// 右移
int b = 101;
// 0110 0101
System.out.println("b = " + Integer.toBinaryString(101));
// 101 / 2^2 = 25
System.out.println("b >> 2 = " + (b >> 2));
// 0001 1001
System.out.println("b >> 2 = " + Integer.toBinaryString((b >> 2)));

/**
 * 如果取模的分母是2的N次方，则等价于 &上2的N次方-1
 * b % 4 = b & (2 ^2 - 1)
 * b = 101
 *   0110 0101
 * & 0000 0011
 * ---------------
 *   0000 0001
 *
 *   b = 111 % 16 = 15
 *
 *   0110 1111
 *   0000 1111
 *   ------------
 *   0000 1111
 *
 *   原理：某个数A，对2^n取模，则余数最大值为2^n-1，
 *   当按位与上2^n-1时，高位的都被舍弃，只留下了2^n-1中满足条件的余数
 *
 */
        // 1
        System.out.println("b % 4 = " + (b % 4) + " = " + (b & (4 - 1)));

        b = 111;
        // 15
        System.out.println("b % 16 = " + (b % 16) + " = " + (b & (16 - 1)));

    }
}
