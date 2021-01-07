package com.shawntime.architect.notes.jvm.gc;

/**
 * -Xms100M -Xmx100M -XX:+PrintGCDetails
 *
 * 内存初始分配：
 *  heap：100M
 *      PSYoungGen：29M
 *          eden：25M
 *          form：2M
 *          to：2M
 *      ParOldGen：67M
 *      Metaspace：3.1M
 *
 * 运行结果：
 *
 * Heap
 *  PSYoungGen      total 29696K, used 23570K [0x00000000fdf00000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 25600K, 92% used [0x00000000fdf00000,0x00000000ff604a48,0x00000000ff800000)
 *   from space 4096K, 0% used [0x00000000ffc00000,0x00000000ffc00000,0x0000000100000000)
 *   to   space 4096K, 0% used [0x00000000ff800000,0x00000000ff800000,0x00000000ffc00000)
 *  ParOldGen       total 68608K, used 0K [0x00000000f9c00000, 0x00000000fdf00000, 0x00000000fdf00000)
 *   object space 68608K, 0% used [0x00000000f9c00000,0x00000000f9c00000,0x00000000fdf00000)
 *  Metaspace       used 3210K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
 *
 */
public class GcTest1 {

    public static void main(String[] args) {
        /**
         * 如果对象的大小<eden区大小，对象优先在eden区分配
         */
        byte[] allocation1 = new byte[20*1024*1024];
    }
}
