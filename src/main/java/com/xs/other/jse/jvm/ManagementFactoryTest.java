package com.xs.other.jse.jvm;

import com.alibaba.fastjson.JSON;

import java.lang.management.*;

/**
 * @author xs
 * create time:2020-05-17 11:03
 **/
public class ManagementFactoryTest {
    public static void main(String[] args) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        System.out.println(Thread.getAllStackTraces().keySet().size());
        int nbRunning = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState()==Thread.State.RUNNABLE) nbRunning++;
        }
        System.out.println(nbRunning);
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("已使用堆内存:" + heapMemoryUsage.getUsed());
        System.out.println("最大堆内存:" + heapMemoryUsage.getMax());
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        System.out.println("已使用非堆内存:" + nonHeapMemoryUsage.getUsed());
        System.out.println("最大非堆内存:" + nonHeapMemoryUsage.getMax());

        System.gc();

        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            long count = gc.getCollectionCount();
            long time = gc.getCollectionTime();
            String name = gc.getName();
            System.out.println(String.format("%s..%s: %s times %s ms",
                    JSON.toJSONString(gc.getMemoryPoolNames()), name, count, time));
        }

    }
}
