package com.lwc.round4;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/7/15 10:33
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayEight {

    public Node intersect(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf) {
            //如果这个是true那么下面的都是true
            if (quadTree1.val) {
                return new Node(true, true);
            }
            return new Node(quadTree2.val, quadTree2.isLeaf, quadTree2.topLeft, quadTree2.topRight, quadTree2.bottomLeft, quadTree2.bottomRight);
        }
        if (quadTree2.isLeaf) {
            return intersect(quadTree2, quadTree1);
        }
        Node o1 = intersect(quadTree1.topLeft, quadTree2.topLeft);
        Node o2 = intersect(quadTree1.topRight, quadTree2.topRight);
        Node o3 = intersect(quadTree1.bottomLeft, quadTree2.bottomLeft);
        Node o4 = intersect(quadTree1.bottomRight, quadTree2.bottomRight);
        boolean flag = o1.isLeaf && o2.isLeaf && o3.isLeaf && o4.isLeaf;
        boolean isVal = (o1.val == o2.val && o2.val == o3.val && o3.val == o4.val);
        if (flag && isVal) {
            return new Node(o1.val, true);
        }
        return new Node(false, false, o1, o2, o3, o4);
    }

    /**
     * 题号：565
     * 难度：中等
     * 时间：20220718
     * 索引从0开始长度为N的数组A，包含0到N - 1的所有整数。找到最大的集合S并返回其大小，其中 S[i] = {A[i], A[A[i]], A[A[A[i]]], ... }且遵守以下的规则。
     * <p>
     * 假设选择索引为i的元素A[i]为S的第一个元素，S的下一个元素应该是A[A[i]]，之后是A[A[A[i]]]... 以此类推，不断添加直到S出现重复的元素。
     */
    int[] nums;
    int ans = 0;
    int[] visited;

    public int arrayNesting(int[] nums) {
        visited = new int[nums.length];
        this.nums = nums;
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 0) {
                dfs(0, nums[i]);
            }
        }
        return ans;

    }

    public void dfs(int count, int index) {
        if (visited[index] != 0) {
            ans = Math.max(ans, count);
            return;
        }
        visited[index] = 1;
        dfs(count + 1, nums[index]);
    }

    /**
     * 题号：814
     * 难度：中等
     * 时间：20220721
     * 给你二叉树的根结点 root ，此外树的每个结点的值要么是 0 ，要么是 1 。
     * <p>
     * 返回移除了所有不包含 1 的子树的原二叉树。
     * <p>
     * 节点 node 的子树为 node 本身加上所有 node 的后代
     */
    public TreeNode pruneTree(TreeNode root) {
        if(countOne(root) == 0){
            return null;
        }
        return root;
    }

    public int countOne(TreeNode root) {
        int count = 0;
        if (root.val == 1) {
            count++;
        }
        if (root.right != null) {
            int temp = countOne(root.right);
            if (temp == 0) {
                root.right = null;
            } else {
                count += temp;
            }
        }
        if (root.left != null) {
            int temp = countOne(root.left);
            if (temp == 0) {
                root.left = null;
            } else {
                count += temp;
            }
        }
        return count;
    }

    /**
     * 题号：749
     * 难度：困难
     * 时间：20220718
     * 病毒扩散得很快，现在你的任务是尽可能地通过安装防火墙来隔离病毒。
     * <p>
     * 假设世界由 m x n 的二维矩阵 isInfected 组成， isInfected[i][j] == 0 表示该区域未感染病毒，而  isInfected[i][j] == 1 表示该区域已感染病毒。可以在任意 2 个相邻单元之间的共享边界上安装一个防火墙（并且只有一个防火墙）。
     * <p>
     * 每天晚上，病毒会从被感染区域向相邻未感染区域扩散，除非被防火墙隔离。现由于资源有限，每天你只能安装一系列防火墙来隔离其中一个被病毒感染的区域（一个区域或连续的一片区域），且该感染区域对未感染区域的威胁最大且 保证唯一 。
     * <p>
     * 你需要努力使得最后有部分区域不被病毒感染，如果可以成功，那么返回需要使用的防火墙个数; 如果无法实现，则返回在世界被病毒全部感染时已安装的防火墙个数。
     */

    int[][] isInfected;
    int[][] visits;
    int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int m, n;
    List<Set<Integer>> zero;
    List<Integer> maxInfected;
    int allAffected;
    int maxBound = 0, maxSize;

    public int containVirus(int[][] isInfected) {
        this.isInfected = isInfected;
        int ans = 0;

        do {
            getCnt();
            if (allAffected == m * n) {
                break;
            }
            //将清除感染的全部变成2
            if (maxInfected != null) {
                for (int keyPair : maxInfected) {
                    int[] temp = genneratePos(keyPair);
                    allAffected--;
                    isInfected[temp[0]][temp[1]] = 2;
                }
            }

            //将没感染的全部变为1
            if (zero != null) {
                for (Set<Integer> temp : zero) {
                    for (int keyPair : temp) {
                        int[] temp2 = genneratePos(keyPair);
                        allAffected++;
                        isInfected[temp2[0]][temp2[1]] = 1;
                    }
                }
            }

            ans += maxBound;
        } while (maxBound > 0);
        return ans;
    }

    public void getCnt() {
        m = isInfected.length;
        n = isInfected[0].length;
        allAffected = 0;
        maxBound = 0;
        maxInfected = null;
        maxSize = 0;
        visits = new int[m][n];
        zero = new ArrayList<>();
        Set<Integer> maxNull = null;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visits[i][j] == 0 && isInfected[i][j] == 1) {
                    Set<Integer> temp2 = new HashSet<>();
                    List<Integer> infected = new ArrayList<>();
                    int temp = countVirus(i, j, temp2, infected);
                    if (maxSize < temp2.size()) {
                        maxSize = temp2.size();
                        if (maxNull != null) {
                            zero.add(maxNull);
                        }
                        maxNull = temp2;
                        maxInfected = infected;
                        maxBound = temp;
                    } else {
                        zero.add(temp2);
                    }
                }
            }
        }
    }

    public int getHashKey(int x, int y) {
        return (x << 16) ^ y;
    }

    public int[] genneratePos(int hash) {
        int x = (hash >> 16), y = hash & ((1 << 16) - 1);
        return new int[]{x, y};
    }

    public int countVirus(int x, int y, Set<Integer> blank, List<Integer> infected) {
        int count = 0;
        visits[x][y] = 1;
        allAffected++;
        infected.add(getHashKey(x, y));
        for (int[] temp : moves) {
            int newX = x + temp[0], newY = y + temp[1];
            int key = getHashKey(newX, newY);
            //只算y的即可
            if (temp[0] == 0) {
                if (newY >= 0 && newY < n && isInfected[newX][newY] == 0) {
                    if (!blank.contains(key)) {
                        blank.add(key);

                    }
                    count++;
                }
            } else {
                if (newX >= 0 && newX < m && isInfected[newX][newY] == 0) {
                    if (!blank.contains(key)) {
                        blank.add(key);
                    }
                    count++;
                }
            }
            if (newX >= 0 && newX < m && newY >= 0 && newY < n && isInfected[newX][newY] == 1 && visits[newX][newY] != 1) {
                count += countVirus(newX, newY, blank, infected);
            }
        }
        return count;
    }

    /*
    196626 3 18
     */
    public static void main(String[] args) {

        DayEight eight = new DayEight();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(0);
        TreeNode node4 = new TreeNode(1);
        node1.right = node2;
        node2.left = node3;
        node2.right = node4;
        MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
        myCalendarTwo.book(10, 20); // returns true
        myCalendarTwo.book(50, 60); // returns true
        myCalendarTwo.book(10, 40); // returns true
        myCalendarTwo.book(5, 15); // returns false
        myCalendarTwo.book(5, 10); // returns true
        myCalendarTwo.book(25, 55);


        System.out.println(eight.pruneTree(node1));
    }
}


class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        topLeft = null;
        topRight = null;
        bottomLeft = null;
        bottomRight = null;
    }

    public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
}
/**
 * 题号：041
 * 难度：简单
 * 时间：20220716
 * 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算滑动窗口里所有数字的平均值。
 * <p>
 * 实现 MovingAverage 类：
 * <p>
 * MovingAverage(int size) 用窗口大小 size 初始化对象。
 * double next(int val) 成员函数 next 每次调用的时候都会往滑动窗口增加一个整数，请计算并返回数据流中最后 size 个值的移动平均值，即滑动窗口里所有数字的平均值。
 */

/**
 * 题号：558
 * 难度：中等
 * 时间：20220715
 *二进制矩阵中的所有元素不是 0 就是 1 。
 *
 * 给你两个四叉树，quadTree1 和 quadTree2。其中 quadTree1 表示一个 n * n 二进制矩阵，而 quadTree2 表示另一个 n * n 二进制矩阵。
 *
 * 请你返回一个表示 n * n 二进制矩阵的四叉树，它是 quadTree1 和 quadTree2 所表示的两个二进制矩阵进行 按位逻辑或运算 的结果。
 *
 * 注意，当 isLeaf 为 False 时，你可以把 True 或者 False 赋值给节点，两种值都会被判题机制 接受 。
 *
 * 四叉树数据结构中，每个内部节点只有四个子节点。此外，每个节点都有两个属性：
 */
class MovingAverage {
    int size;
    Queue<Integer> queue;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.size = size;
        queue = new ArrayDeque<>();
    }

    public double next(int val) {
        if (queue.size() >= size) {
            queue.poll();
        }
        queue.offer(val);
        int sum = 0;
        Iterator<Integer> integers = queue.iterator();
        while (integers.hasNext()) {
            sum += integers.next();
        }
        return (double) sum / queue.size();
    }
}
/*
题号：731
难度：中等
时间：20220721
* 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。

MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为，  start <= x < end。

当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。

每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。

请按照以下步骤调用MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)

* */
class MyCalendarTwo {
    int MAX_LEN = (int) 1e9+1;
    Node root;
    class Node{
        int left,right,count,lazy;
        Node leftNode,rightNode;
        public Node(int left ,int right){
           this.left=left;
           this.right=right;
            count=0;
            lazy=0;
        }
    }
    //线段树动态开点
    public MyCalendarTwo() {
        root = new Node(0,MAX_LEN);
    }
    //查询
    public int qryNode(Node root ,int left ,int right){
        int begin = root.left,end = root.right;
        //如果要查询的范围大于当前节点范围直接返回
        if(left<=begin && right>=end){
            return root.count;
        }
        //如果没有则需要下下层接着查询,先创建节点
        createNode(root);
        //将懒标记下推
        pushDown(root);
        //计算中间节点
        int mid = begin +(end-begin>>1);
        int count = 0;
        if(mid>=left){
          count = Math.max(count , qryNode(root.leftNode,left,right));
        }
        if(mid<right){
           count = Math.max(count,qryNode(root.rightNode,left,right));
        }
        return count;
    }
    public void updateNode(Node root ,int left ,int right){
        int begin = root.left,end = root.right;
        if(left<=begin && right>=end){
            root.count++;
            //这里懒标记也应当进行累加，因为可能多次在一个地方进行更新，导致懒标记没有下推，只有往下更新或者
            //往下查时懒标记才会向下推
            root.lazy++;
            return;
        }
        //创建节点
        createNode(root);
        //将懒标记下推
        pushDown(root);
        int mid = begin +(end-begin>>1);
        if(mid>=left){
            updateNode(root.leftNode,left,right);
        }
        if(mid<right){
            updateNode(root.rightNode,left,right);
        }
        //将更新得数据上推
        pushUp(root);
    }
    public void pushDown(Node root){
        //懒标记不能直接进行覆盖，因为当一个节点被更新时，存在该节点下面的子节点同样有懒标记
        //如果直接进行覆盖则会丢失掉子节点的懒标记值，从而会丢失一次变化，导致错误
        root.leftNode.lazy= root.lazy;
        root.rightNode.lazy=root.lazy;
        root.leftNode.count+=root.lazy;
        root.rightNode.count+=root.lazy;
        root.lazy=0;
    }
    public void pushUp(Node root){
        root.count=Math.max(root.leftNode.count,root.rightNode.count);
    }
    public void createNode(Node root){
        int mid = root.left +(root.right-root.left>>1);
        if(root.leftNode == null){
            root.leftNode = new Node(root.left,mid);
        }
        if(root.rightNode == null){
            root.rightNode = new Node(mid+1,root.right);
        }
    }
    public boolean book(int start, int end) {
        int temp = qryNode(root,start,end-1);
        if(temp>=2){
            return false;
        }
        updateNode(root,start,end-1);
        return true;
    }
}

