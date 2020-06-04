package com.xs.medium.duplicatenum;

/**
 * @author xs
 * create time:2020-05-01 16:08:41
 */
public class Solution {
    public int findDuplicate(int[] nums) {
        BitMap bitMap = new BitMap(nums.length);
        for (int i=0; i<nums.length; i++) {
            if (bitMap.exist(nums[i])) {
                return nums[i];
            } else {
                bitMap.put(nums[i]);
            }
        }
        return 0;
    }

    class BitMap {
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
    }
}
