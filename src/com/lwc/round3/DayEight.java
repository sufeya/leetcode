package com.lwc.round3;

import java.util.*;

public class DayEight {


    /**
     * 题号：868
     * 时间：20220425
     * 难度：简单
     * 给定一个正整数 n，找到并返回 n 的二进制表示中两个 相邻 1 之间的 最长距离 。如果不存在两个相邻的 1，返回 0 。
     * <p>
     * 如果只有 0 将两个 1 分隔开（可能不存在 0 ），则认为这两个 1 彼此 相邻 。两个 1 之间的距离是它们的二进制表示中位置的绝对差。例如，"1001" 中的两个 1 的距离为 3 。
     */
    public int binaryGap(int n) {
        int temp = n;
        int ans = 0, sum = 0;
        boolean flag = false;
        while (temp > 0) {
            int i = (temp & 1);
            //如果当前为1并且在该1之前依然存在一个1则统计结果
            if (i == 1 && flag) {
                sum++;
                ans = Math.max(ans, sum);
                sum = 0;
            } else if (i == 1 && !flag) {
                flag = true;
            } else if (i == 0 && flag) {
                sum++;
            }
            temp = temp >> 1;
        }
        return ans;
    }

    /**
     * 题号：883
     * 难度：简单
     * 时间：20220426
     * 在 n x n 的网格 grid 中，我们放置了一些与 x，y，z 三轴对齐的 1 x 1 x 1 立方体。
     * <p>
     * 每个值 v = grid[i][j] 表示 v 个正方体叠放在单元格 (i, j) 上。
     * <p>
     * 现在，我们查看这些立方体在 xy 、yz 和 zx 平面上的投影。
     * <p>
     * 投影 就像影子，将 三维 形体映射到一个 二维 平面上。从顶部、前面和侧面看立方体时，我们会看到“影子”。
     * <p>
     * 返回 所有三个投影的总面积 。
     */
    public int projectionArea(int[][] grid) {
        //可以一块一块的进行累加从而获取所有的面接
        int ans = 0;
        //计算x轴的投影对应的面积和
        for (int i = 0; i < grid.length; i++) {
            int temp = 0;
            for (int j : grid[i]) {
                temp = Math.max(temp, j);
            }
            ans += temp;
        }
        //计算y轴方向上的投影面积
        for (int i = 0; i < grid[0].length; i++) {
            int temp = 0;
            for (int j = 0; j < grid.length; j++) {
                temp = Math.max(temp, grid[j][i]);
            }
            ans += temp;
        }
        //计算俯视图的面积
        for (int i = 0; i < grid.length; i++) {
            int temp = 0;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    temp++;
                }
            }
            ans += temp;
        }
        return ans;
    }

    /**
     * 题号：417
     * 难度：中等
     * 时间：20220427
     * 有一个 m × n 的矩形岛屿，与 太平洋 和 大西洋 相邻。 “太平洋” 处于大陆的左边界和上边界，而 “大西洋” 处于大陆的右边界和下边界。
     * <p>
     * 这个岛被分割成一个由若干方形单元格组成的网格。给定一个 m x n 的整数矩阵 heights ， heights[r][c] 表示坐标 (r, c) 上单元格 高于海平面的高度 。
     * <p>
     * 岛上雨水较多，如果相邻单元格的高度 小于或等于 当前单元格的高度，雨水可以直接向北、南、东、西流向相邻单元格。水可以从海洋附近的任何单元格流入海洋。
     * <p>
     * 返回 网格坐标 result 的 2D列表 ，其中 result[i] = [ri, ci] 表示雨水可以从单元格 (ri, ci) 流向 太平洋和大西洋 。
     */

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int xlen = heights.length, ylen = heights[0].length;
        boolean[][] pacific = new boolean[xlen][ylen];
        boolean[][] alt = new boolean[xlen][ylen];
        //太平洋的两个边的某个点进行深度遍历
        for (int i = 0; i < ylen; i++) {
            dfs(i, 0, pacific, heights);
        }
        for (int i = 0; i < xlen; i++) {
            dfs(0, i, pacific, heights);
        }
        //从大西洋的源点进行出发遍历
        for (int i = 0; i < ylen; i++) {
            dfs(i, ylen - 1, alt, heights);
        }
        for (int i = 0; i < xlen; i++) {
            dfs(xlen - 1, i, alt, heights);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < xlen; i++) {
            for (int j = 0; j < ylen; j++) {
                if (alt[i][j] && pacific[i][j]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    res.add(list);
                }
            }
        }
        return res;
    }

    public void dfs(int x, int y, boolean[][] sea, int[][] heights) {
        int xlen = sea.length, ylen = sea[0].length;
        if (sea[x][y]) {
            return;
        }
        sea[x][y] = true;
        int[][] temp = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] pos : temp) {
            if (x + pos[0] < xlen && x + pos[0] >= 0 && y + pos[1] < ylen && y + pos[1] >= 0 && heights[x][y] <= heights[x + pos[0]][y + pos[1]]) {
                dfs(x + pos[0], y + pos[1], sea, heights);
            }
        }
    }
    /**
     * 题号：1305
     * 难度：中等
     * 时间：20220501
     *给你 root1 和 root2 这两棵二叉搜索树。请你返回一个列表，其中包含 两棵树 中的所有整数并按 升序 排序。.
     * 解题：可以将一个树的节点插入到另外一个树上然后在进行遍历
     */

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        TreeNode root = root1,root3 =root2;
        //如果两颗树均为空则直接返回
        if(root1 == null && root2 == null){
            return new ArrayList<>();
        }else{
            //如果其中某颗树为空的话，则把不空的树赋给root1进行遍历即可
            root= root1 == null ? root2:root1;
            root3= root1 == null ? root1:root2;
        }
        //利用栈先序遍历root2插入到root1为根节点的树中
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p =root3;
        while(!stack.isEmpty() || p != null){
            //先到达这个树的最左端节点
            while(p != null){
                stack.push(p);
                p=p.left;
            }
            //出栈遍历该节点
            p = stack.pop();
            //插入root1的树中
            insertNode(root,p.val);
            //遍历右子树
            p = p.right;
        }
        //然后再对root1进行中序遍历即可
        List<Integer> ans =new ArrayList<>();
        p=root;
        while(!stack.isEmpty() || p != null){
            //先到达这个树的最左端节点
            while(p != null){
                stack.push(p);
                p=p.left;
            }
            //出栈遍历该节点
            p = stack.pop();
            //插入root1的树中
            ans.add(p.val);
            //遍历右子树
            p = p.right;
        }
        return ans;
    }

    //将val的值插入到root的节点中
    public void insertNode(TreeNode root,int val){
        int rootVal = root.val;
        //如果大于根的值则插入右节点
        if(rootVal<val){
            //如果右节点为空则直接插入
            if(root.right == null){
                root.right =new TreeNode(val);
            }else{
                //不为空则递归插入
                insertNode(root.right,val);
            }
        }else{
            //小于等于该节点则插入左节点
            if(root.left == null){
                root.left = new TreeNode(val);
            }else{
                insertNode(root.left,val);
            }
        }
    }
    public void midTrack(TreeNode root2){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p =root2;
        while(!stack.isEmpty() || p != null){
            //先到达这个树的最左端节点
            while(p != null){
                stack.push(p);
                p=p.left;
            }
            //出栈遍历该节点
            p = stack.pop();
            //插入root1的树中
            System.out.println(p.val);
            //insertNode(root1,p.val);
            //遍历右子树
            p = p.right;
        }
    }
    public static void main(String[] args) {
        DayEight eight = new DayEight();
        TreeNode node = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(4);
        node.left=node1;
        node.right=node2;
        eight.insertNode(node,5);
        eight.insertNode(node,6);
        eight.insertNode(node,3);
        eight.insertNode(node,1);
        eight.midTrack(node);
       // System.out.println(eight.construct2(new int[][]{{0, 1}, {1, 0}}));

    }

    /**
     * 题号：427
     * 难度：中等
     * 时间：20220429
     * 给你一个 n * n 矩阵 grid ，矩阵由若干 0 和 1 组成。请你用四叉树表示该矩阵 grid 。
     * <p>
     * 你需要返回能表示矩阵的 四叉树 的根结点。
     * <p>
     * 注意，当 isLeaf 为 False 时，你可以把 True 或者 False 赋值给节点，两种值都会被判题机制 接受 。
     * <p>
     * 四叉树数据结构中，每个内部节点只有四个子节点。此外，每个节点都有两个属性：
     * <p>
     * val：储存叶子结点所代表的区域的值。1 对应 True，0 对应 False；
     * isLeaf: 当这个节点是一个叶子结点时为 True，如果它有 4 个子节点则为 False
     */


    public Node construct(int[][] grid) {
        Node node = process_grid(grid, 0,  0,grid.length, grid.length);
        return node;
    }

    Node process_grid(int[][] grid, int row_begin, int col_begin, int row_end, int col_end) {

        //不只有一单元格时进行操作

        //先判断范围内的所有元素是否都相同
        boolean flag = true;
        //取第一个元素作为标准
        for (int i = row_begin; i < row_end && flag; ++i) {
            for (int j = col_begin; j < col_end; ++j) {
                if (grid[row_begin][col_begin] != grid[i][j]) {
                    flag = false;
                    break;
                }
            }
        }
        //如果范围内所有的元素都相同，则返回叶子节点
        if (flag) {
            Node node = new Node(grid[row_begin][col_begin] == 1, true);
            return node;
        }

        //如果范围内的值不相同的话，则将该值分成四份进行处理
        Node ret = new Node(
                true,
                false,
                process_grid(grid, row_begin, col_begin, (row_begin + row_end) / 2, (col_begin + col_end) / 2),
                process_grid(grid, row_begin, (col_begin + col_end) / 2, (row_begin + row_end) / 2, col_end),
                process_grid(grid, (row_begin + row_end) / 2, col_begin, row_end, (col_begin + col_end) / 2),
                process_grid(grid, (row_begin + row_end) / 2, (col_begin + col_end) / 2, row_end, col_end)
        );
        return ret;
    }

    public Node construct2(int[][] grid) {
        Node node = dfs(grid, 0, 0, grid.length, grid.length);
        return node;
    }

    public Node dfs(int[][] grid, int r0, int c0, int r1, int c1) {
        boolean same = true;
        for (int i = r0; i < r1; ++i) {
            for (int j = c0; j < c1; ++j) {
                if (grid[i][j] != grid[r0][c0]) {
                    same = false;
                    break;
                }
            }
            if (!same) {
                break;
            }
        }

        if (same) {
            return new Node(grid[r0][c0] == 1, true);
        }

        Node ret = new Node(
                true,
                false,
                dfs(grid, r0, c0, (r0 + r1) / 2, (c0 + c1) / 2),
                dfs(grid, r0, (c0 + c1) / 2, (r0 + r1) / 2, c1),
                dfs(grid, (r0 + r1) / 2, c0, r1, (c0 + c1) / 2),
                dfs(grid, (r0 + r1) / 2, (c0 + c1) / 2, r1, c1)
        );
        return ret;
    }
}


/**
 * 题号：398
 * 难度：中等
 * 时间：20220425
 * 给定一个可能含有重复元素的整数数组，要求随机输出给定的数字的索引。 您可以假设给定的数字一定存在于数组中。
 * <p>
 * 注意：
 * 数组大小可能非常大。 使用太多额外空间的解决方案将不会通过测试。
 */
class Solution {
    Map<Integer, List<Integer>> map;
    Random random;

    public Solution(int[] nums) {
        map = new HashMap<>();
        //填充map
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = map.get(nums[i]);
            if (list == null) {
                list = new ArrayList<Integer>();
            }
            list.add(i);
            map.put(nums[i], list);
        }
    }

    public int pick(int target) {
        List<Integer> lst = map.get(target);
        if (lst == null || lst.size() == 0) {
            return -1;
        }
        random = new Random();
        return lst.get(random.nextInt(lst.size()));
    }
}

class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}