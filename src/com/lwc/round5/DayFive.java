package com.lwc.round5;


import javafx.util.Pair;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/10/8 16:55
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayFive {
    /**
     * 题号：870
     * 难度：中等
     * 时间：20221008
     * 给定两个大小相等的数组 nums1 和 nums2，nums1 相对于 nums2 的优势可以用满足 nums1[i] > nums2[i] 的索引 i 的数目来描述。
     * <p>
     * 返回 nums1 的任意排列，使其相对于 nums2 的优势最大化。
     */
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int len = nums1.length;
        int[] ans = new int[len];
        //排序nums1
        Arrays.sort(nums1);
        List<Integer> remin = new ArrayList<>();

        //遍历nums2
        for (int i = 0; i < len; i++) {
            //找到最小大于nums2[i]的值并且填入
            int k = 0;
            //跳过之前使用过的值,并且找到第一个大于nums2[i]的值
            while (k < len && (nums1[k] < 0 || nums1[k] <= nums2[i])) {
                k++;
            }
            if (k < len) {
                ans[i] = nums1[k];
                nums1[k] = -1;
            } else {
                remin.add(i);
            }
        }

        //填充剩余的数字
        for (int i : remin) {
            //找到最小大于nums2[i]的值并且填入
            int k = 0;
            //跳过之前使用过的值,并且找到第一个大于nums2[i]的值
            while (k < len && nums1[k] < 0) {
                k++;
            }
            if (k < len) {
                ans[i] = nums1[k];
                nums1[k] = -1;
            }
        }
        return ans;
    }

    //题解给出的答案
    public int[] advantageCount2(int[] nums1, int[] nums2) {
        int len = nums1.length;

        //将两个数组的索引按照元素大小进行排序
        Integer[] ids = new Integer[len], ids2 = new Integer[len];
        //填充两个数组
        for (int i = 0; i < len; i++) {
            ids[i] = i;
            ids2[i] = i;
        }
        Arrays.sort(ids, (a, b) -> nums1[a] - nums1[b]);
        Arrays.sort(ids2, (a, b) -> nums2[a] - nums2[b]);

        //定义答案数组，并且定义双指针进行遍历
        int[] ans = new int[len];
        int right = len - 1, left = 0;

        //按照排序的顺序遍历数组一进行优势点排序
        for (int i = 0; i < len; i++) {
            //如果当前的nums1首个元素大于nums2首个元素，则说明nums1的所有元素大于该首个元素
            //当元素差距最小时收益最高，所以直接填充答案即可
            if (nums1[ids[i]] > nums2[ids2[left]]) {
                ans[ids2[left]] = nums1[ids[i]];
                left++;
            } else {
                //如果nums首个元素小于nums2首个元素则说明nums1的首个元素要小于nums2的所有值
                //则说明该元素没有意义，又因为我们时顺序遍历nums2元素，所以剩下的元素就是右边的元素
                //所以可以直接填充到右边
                ans[ids2[right]] = nums1[ids[i]];
                right--;
            }
        }
        return ans;
    }

    //周赛题目
    public int hardestWorker(int n, int[][] logs) {
        int ans = logs[0][0], len = logs.length;
        if (len == 1) {
            return ans;
        }
        int maxTask = logs[0][1];
        for (int i = 1; i < len; i++) {
            int temp = logs[i][1] - logs[i - 1][1];
            if (temp > maxTask) {
                maxTask = temp;
                ans = logs[i][0];
            } else if (temp == maxTask) {
                ans = Math.min(logs[i][0], ans);
            }
        }
        return ans;
    }

    public int[] findArray(int[] pref) {
        int len = pref.length;
        int[] ans = new int[len];
        ans[0] = pref[0];

        //当长度为一时直接返回答案即可
        if (len == 1) {
            return ans;
        }
        //长度不为一时
        for (int i = 1; i < len; i++) {
            //a^b = c =>  b = c^a  因为 异或相同为零相异为一  0 ^ 0 = 1  逆推  1^0 = 0  ,
            // 0^1 = 1 1^1 =0  0^1 =0  , 1^1 = 0
            ans[i] = pref[i - 1] ^ pref[i];
        }
        return ans;
    }

    public String robotWithString(String s) {
        Stack<Character> stack = new Stack();
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        //小写字符的数量进行计数
        int[] sort = new int[26];
        for (int i = 0; i < len; i++) {
            sort[s.charAt(i) - 'a']++;
        }

        //索引指向字符个数不为零的位置
        int index = 0;
        while (sort[index] == 0) {
            index++;
        }
        for (int i = 0; i < len && index < 26; i++) {
            char c = s.charAt(i);
            while (!stack.isEmpty() && (stack.peek() - 'a') <= index) {
                sb.append(stack.pop());
            }
            stack.push(c);
            sort[c - 'a']--;
            while (index < 26 && sort[index] == 0) {
                index++;
            }
        }
        //直接将栈中剩余的元素出栈即可
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    //广度遍历超时了
    public int numberOfPaths(int[][] grid, int k) {
        int ans = 0, mod = (int) 1e9 + 7;

        int n = grid.length, m = grid[0].length;
        if (n == m && n == 1) {
            if (grid[0][0] % k == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        //移动的距离
        int[][] moves = new int[][]{{1, 0}, {0, 1}};
        Queue<Pair<int[], Integer>> queue = new ArrayDeque();
        queue.offer(new Pair<>(new int[]{0, 0}, grid[0][0] % k));
        while (!queue.isEmpty()) {
            int size = queue.size();
            //每一步遍历下一步的所有可能性
            while (size > 0) {
                Pair<int[], Integer> pair = queue.poll();
                int[] pos = pair.getKey();
                int val = pair.getValue();
                for (int[] move : moves) {
                    int x = pos[0] + move[0], y = pos[1] + move[1];
                    if (x == n - 1 && y == m - 1) {
                        if ((val + grid[x][y]) % k == 0) {
                            ans++;
                            ans %= mod;
                        }
                    } else if (x < n && y < m) {
                        int temp = (val + grid[x][y]) % k;
                        queue.offer(new Pair<>(new int[]{x, y}, temp));
                    }
                }
                size--;
            }
        }
        return ans;
    }

    //动态规划做法
    public int numberOfPaths2(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length, mod = (int) 1e9 + 7;
        ;
        int[][][] dp = new int[n + 1][m + 1][k];
        dp[0][0][grid[0][0] % k] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int v = 0; v < k; v++) {
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        dp[i][j][(v + grid[i][j]) % k] = (dp[i - 1][j][v] + dp[i][j - 1][v]) % mod;
                    } else if (i - 1 >= 0) {
                        dp[i][j][(v + grid[i][j]) % k] = dp[i - 1][j][v];
                    } else if (j - 1 >= 0) {
                        dp[i][j][(v + grid[i][j]) % k] = dp[i][j - 1][v];
                    }
                }
            }
        }
        return dp[n - 1][m - 1][0];
    }

    /**
     * 难度：困难
     * 时间：20221010
     我们有两个长度相等且不为空的整型数组 nums1 和 nums2 。在一次操作中，我们可以交换 nums1[i] 和 nums2[i]的元素。

     例如，如果 nums1 = [1,2,3,8] ， nums2 =[5,6,7,4] ，你可以交换 i = 3 处的元素，得到 nums1 =[1,2,3,4] 和 nums2 =[5,6,7,8] 。
     返回 使 nums1 和 nums2 严格递增 所需操作的最小次数 。

     数组 arr 严格递增 且  arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1] 。
     自己写完全没有思路看的别人的思路的解题如下
     */
    public int minSwap(int[] nums1, int[] nums2) {
        int len = nums1.length;
        //对于每个位置我们只有两个状态一个是交换一个是不交换
        //可以知道当nums1[i]>nums1[i-1]并且nums2[i]>nums2[i-1]两个要么都交换要么都不交换
        //dp[i][1] = dp[i-1][1]+1 ,dp[i][0] = dp[i-1][0]
        //当nums1[i]>nums2[i-1]并且nums2[i]>nums1[i-1]时 ，则dp[i][1] = dp[i-1][0]+1 dp[i][0]=dp[i-1][1]

        int[][] dp = new int[len][2];

        //初始化
        for(int i = 1;i<len;i++){
            dp[i][0] = len+5;
            dp[i][1] = len+5;
        }
        dp[0][1] = 1;
        for(int i =1;i<len;i++){
            if(nums1[i]>nums1[i-1]&&nums2[i]>nums2[i-1]){
                dp[i][1] = dp[i-1][1]+1;
                dp[i][0] = dp[i-1][0];
            }
            if(nums1[i]>nums2[i-1]&&nums2[i]>nums1[i-1]){
                dp[i][1] = Math.min(dp[i][1],dp[i-1][0]+1);
                dp[i][0]=Math.min(dp[i][0],dp[i-1][1]);
            }
        }
        return Math.min(dp[len-1][1],dp[len-1][0]);
    }
    public int minSwap2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] f = new int[n][2];
        for (int i = 1; i < n; i++) f[i][0] = f[i][1] = n + 10;
        f[0][1] = 1;
        for (int i = 1; i < n; i++) {
            if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                f[i][0] = f[i - 1][0];
                f[i][1] = f[i - 1][1] + 1;
            }
            if (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]) {
                f[i][0] = Math.min(f[i][0], f[i - 1][1]);
                f[i][1] = Math.min(f[i][1], f[i - 1][0] + 1);
            }
        }
        return Math.min(f[n - 1][0], f[n - 1][1]);
    }


    public static void main(String[] args) {
        DayFive five = new DayFive();
       five.minSwap(new int[]{1,3,5,4},new int[]{1,2,3,7});
    }
}

