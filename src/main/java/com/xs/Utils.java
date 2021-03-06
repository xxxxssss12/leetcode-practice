package com.xs;

import com.alibaba.fastjson.JSON;
import com.xs.other.map.BitMap;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by xs on 2019/4/12
 */
public class Utils {

    public static int[] createNums(int n, int bound) {
        return createNums(n, bound, false);
    }

    public static int[] createNums(int n, int bound, boolean isAbs) {
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            if (!isAbs) {
                arr[i] = ran().nextInt(bound) - (bound / 2);
            } else {
                arr[i] = ran().nextInt(bound);
            }
        }
        return arr;
    }

    private static Random ran() {
        return ThreadLocalRandom.current();
    }


    public static void println(int[] nums) {
        if (nums != null) {
            for (int i=0; i<nums.length; i++) {
                System.out.print(nums[i] + "\t\t");
            }
            System.out.println();
        }
    }

    public static ThreadPoolExecutor generatorExecutor(int threadNumbers, String threadGroupName) {
        return new ThreadPoolExecutor(threadNumbers, threadNumbers, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new MyThreadFactory(StringUtils.isEmpty(threadGroupName) ? "test" : threadGroupName), (r, executor) -> {
            System.out.println(Thread.currentThread().getName() + " abort！！！！");
                });
    }

    public static String byteToBinaryStr(byte data) {
        int value = 1 << 8 | Byte.toUnsignedInt(data);
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        return  bs.substring(1);
//        return Integer.toBinaryString(Byte.toUnsignedInt(data));
    }

    public static String byteArrToBinaryStr(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<arr.length; i++) {
            sb.append(Utils.byteToBinaryStr(arr[i]));
        }
        return sb.toString();
    }

    public static int random(int bound, boolean isAbs) {
        if (!isAbs) {
            return ran().nextInt(bound) - (bound / 2);
        } else {
            return ran().nextInt(bound);
        }
    }


    public static String null2Str(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }
}
