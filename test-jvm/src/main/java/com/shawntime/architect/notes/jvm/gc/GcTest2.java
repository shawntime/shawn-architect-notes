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
 *  ParOldGen       total 68608K, used 20480K [0x00000000f9c00000, 0x00000000fdf00000, 0x00000000fdf00000)
 *   object space 68608K, 29% used [0x00000000f9c00000,0x00000000fb000010,0x00000000fdf00000)
 *  Metaspace       used 3229K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
 *
 */
public class GcTest2 {

    public static void main(String[] args) {
        /**
         * 如果对象的大小<eden区大小，对象优先在eden区分配
         */
        byte[] allocation1 = new byte[20*1024*1024];
        /**
         * 创建allocation2，由于eden区已经占有92%，无法存放allocation2的大小，则会触发一次Minor GC，
         * 由于allocation1为20M大小，而Survior区分别只有2M无法存放，虚拟机只要把新生代eden区的allocation1转移到老年代中
         * allocation2存到在eden区
         */
        byte[] allocation2 = new byte[20*1024*1024];
    }
}
