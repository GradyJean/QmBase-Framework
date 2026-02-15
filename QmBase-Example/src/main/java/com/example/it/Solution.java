package com.example.it;

import java.util.Arrays;

/**
 * 实现一个merge函数 ，功能是将两个有序数组 ，将它们合并成一个有序数组
 * 输入： [1,2,3,7,9]，[2,4,6]
 * 输出： [1,2,2,3,4,6,7,9]
 */
public class Solution {
    public int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        // 同时遍历两个数组
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        // 把剩下的 a 填进去
        while (i < a.length) {
            result[k++] = a[i++];
        }

        // 把剩下的 b 填进去
        while (j < b.length) {
            result[k++] = b[j++];
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr1 = {1, 2, 3, 7, 9};
        int[] arr2 = {2, 4, 6};
        int[] arr = solution.merge(arr1, arr2);
        System.out.println(Arrays.toString(arr));
    }
}
