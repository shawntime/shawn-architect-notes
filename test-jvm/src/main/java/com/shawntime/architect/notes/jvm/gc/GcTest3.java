package com.shawntime.architect.notes.jvm.gc;

/**
 * -Xms100M -Xmx100M -XX:+PrintGCDetails
 *
 * 内存初始分配：
 *  heap：100M
 *      PSYoungGen：29M
 *          eden：25M
 *          from：2M
 *          to：2M
 *      ParOldGen：67M
 *      Metaspace：3.1M
 *
 * 运行结果：
 *
 * [GC (Allocation Failure) --[PSYoungGen: 23058K->23058K(29696K)] 84498K->84506K(98304K), 0.0009387 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 23058K->21107K(29696K)] [ParOldGen: 61448K->61441K(68608K)] 84506K->82548K(98304K), [Metaspace: 3223K->3223K(1056768K)], 0.0064699 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * [GC (Allocation Failure) --[PSYoungGen: 21107K->21107K(29696K)] 82548K->82548K(98304K), 0.0010788 secs] [Times: user=0.09 sys=0.00, real=0.00 secs]
 * [Full GC (Allocation Failure) [PSYoungGen: 21107K->21089K(29696K)] [ParOldGen: 61441K->61441K(68608K)] 82548K->82530K(98304K), [Metaspace: 3223K->3223K(1056768K)], 0.0035676 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at com.shawntime.architect.notes.jvm.gc.GcTest3.main(GcTest3.java:48)
 * Heap
 *  PSYoungGen      total 29696K, used 21857K [0x00000000fdf00000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 25600K, 85% used [0x00000000fdf00000,0x00000000ff4586c0,0x00000000ff800000)
 *   from space 4096K, 0% used [0x00000000ffc00000,0x00000000ffc00000,0x0000000100000000)
 *   to   space 4096K, 0% used [0x00000000ff800000,0x00000000ff800000,0x00000000ffc00000)
 *  ParOldGen       total 68608K, used 61441K [0x00000000f9c00000, 0x00000000fdf00000, 0x00000000fdf00000)
 *   object space 68608K, 89% used [0x00000000f9c00000,0x00000000fd800508,0x00000000fdf00000)
 *  Metaspace       used 3255K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 353K, capacity 388K, committed 512K, reserved 1048576K
 *
 */
public class GcTest3 {

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

        byte[] allocation3 = new byte[20*1024*1024];

        byte[] allocation4 = new byte[20*1024*1024];

        /**
         * 由于堆空间67M，分配完4个对象后已经达到了86%，再分配对象无法存放，触发full GC，full GC 无法回收，内存溢出
         */
        byte[] allocation5 = new byte[20*1024*1024];
    }
}
