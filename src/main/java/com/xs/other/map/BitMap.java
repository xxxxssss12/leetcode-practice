package com.xs.other.map;

import com.xs.Utils;

/**
 * 位图数据结构实现
 *
 * @author xs
 * create time:2020-05-01 15:13:55
 */
public class BitMap {
    private byte[] data;

    private final byte[] location = new byte[] {
            1, 2, 4, 8, 16,32, 64, -128
    };
    public BitMap(int size) {
        data = new byte[(size-1) / 8 + 1];
    }

    public void put(int i) {
        // 定位到槽位
        int index = data.length - (i-1) / 8 - 1;
        // 定位到字节
        int bt = (i % 8 - 1);
        if (bt < 0) {
            bt += 8;
        }
        data[index] = (byte) (data[index] | location[bt]);
    }

    public boolean exist(int i) {
        // 定位到槽位
        int index = data.length - (i-1) / 8 - 1;
        // 定位到字节
        int bt = (i % 8 - 1);
        if (bt < 0) {
            bt += 8;
        }
        int isExist = data[index] & location[bt];
        return isExist != 0;
    }

    public void remove(int i) {
        // 定位到槽位
        int index = data.length - (i-1) / 8 - 1;
        // 定位到字节
        int bt = (i % 8 - 1);
        if (bt < 0) {
            bt += 8;
        }
        data[index] = (byte) (data[index] | (~location[bt]));
    }

    public static void main(String[] args) {
        BitMap b = new BitMap(16);
        b.put(16);
//        System.out.println(Utils.byteToBinaryStr(b.data[0]));
        System.out.println(b.exist(16));
        System.out.println(Utils.byteArrToBinaryStr(b.data));
    }
}
