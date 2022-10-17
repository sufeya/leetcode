package com.lwc.round5;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/10/15 21:28
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DaySix {
    /**
     * 题号：1141
     * 难度：中等
     * 时间：20221015
     * 给你一个数组 target 和一个整数 n。每次迭代，需要从  list = { 1 , 2 , 3 ..., n } 中依次读取一个数字。
     * <p>
     * 请使用下述操作来构建目标数组 target ：
     * <p>
     * "Push"：从 list 中读取一个新元素， 并将其推入数组中。
     * "Pop"：删除数组中的最后一个元素。
     * 如果目标数组构建完成，就停止读取更多元素。
     * 题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
     * <p>
     * 请返回构建目标数组所用的操作序列。如果存在多个可行方案，返回任一即可。
     *
     * @param target
     * @param n
     * @return
     */
    public List<String> buildArray(int[] target, int n) {

        String[] strs = new String[]{"Push", "Pop"};

        List<String> ans = new ArrayList<>();
        //利用双指针对数组进行遍历,point为指针
        int len = target.length, point = 1;
        for (int i = 0; i < len && point <= n; i++) {

            //当指针没有到达该元素时直接跳过，并且在ans中加入push ,pop
            while (point < target[i]) {
                ans.add(strs[0]);
                ans.add(strs[1]);
                point++;
            }
            //到达相等的元素时则直接加入
            ans.add(strs[0]);
            point++;
        }
        return ans;
    }

    /**
     * 题号：886
     * 难度：中等
     * 时间：20221016
     * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
     * <p>
     * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        Set<Integer> col1 = new HashSet<>();
        Set<Integer> col2 = new HashSet<>();
        for (int[] dislike : dislikes) {
            boolean res1 = col1.contains(dislike[0]);
            boolean res2 = col2.contains(dislike[0]);
            boolean res3 = col1.contains(dislike[1]);
            boolean res4 = col2.contains(dislike[1]);
            if (!res1 && !res4) {
                col1.add(dislike[1]);
                col2.add(dislike[0]);
            } else if (!res2 && !res3) {
                col2.add(dislike[1]);
                col1.add(dislike[0]);
            } else {
                return false;
            }
        }
        return true;
    }

    //上诉解答有误，现在使用并查集的方式来解答
    public boolean possibleBipartition2(int n, int[][] dislikes) {
        int[] father = new int[n + 1];
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        //初始话父节点，将所有的父节点都指向-1
        Arrays.fill(father, -1);

        //构建图
        for (int[] temp : dislikes) {
            graph[temp[0]].add(temp[1]);
            graph[temp[1]].add(temp[0]);
        }

        //遍历所有的集合
        for (int i = 1; i <= n; i++) {
            List<Integer> list = graph[i];

            //对于i不喜欢的一定在同一集合里面，因为只有两个集合
            for (int j = 0; j < list.size(); j++) {
                union(list.get(0), list.get(j), father);
                //判断合并了之后是否与不喜欢的人相连
                if (isConnected(i, list.get(0), father)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void union(int x, int y, int[] parent) {
        int px = findParent(x, parent), py = findParent(y, parent);
        if (px == py) {
            return;
        }
        //合并
        parent[x] = y;
    }

    //判断是否连接
    public boolean isConnected(int x, int y, int[] father) {
        int fx = findParent(x, father);
        int fy = findParent(y, father);
        if (fx == fy) {
            return true;
        }
        return false;
    }

    public int findParent(int x, int[] parent) {
        if (parent[x] < 0) {
            return x;
        }
        return findParent(parent[x], parent);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        DaySix six = new DaySix();
        six.possibleBipartition2(1, new int[][]{});
    }
}
