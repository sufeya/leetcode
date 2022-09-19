package com.lwc.round5;

import com.lwc.round1.DayTow;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/9/13 10:52
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayTwo {
    public static void main(String[] args) {
        DayTwo two = new DayTwo();
        two.largestIsland2(new int[][]{{1,1},{1,0}});
    }
    /**
     * 题号：670
     * 难度：中等
     * 时间：20220913
     * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
     */
    public int maximumSwap(int num) {
        List<Integer> lis = new ArrayList<>();
        int temp = num;
        while(temp>0){
            lis.add(temp%10);
            temp/=10;
        }
        int[] nums = new int[lis.size()];
        for(int i = 0;i<lis.size();i++){
            nums[i] = lis.get(lis.size()-i-1);
        }
        lis.sort((a,b)->{return b-a;});
       int i =0,j=lis.size()-1;
       while(i<j){
           if(lis.get(i) == nums[i]){
               i++;
           }else{
              if(nums[j] == lis.get(i)){
                  int tem = nums[i];
                  nums[i]=nums[j];
                  nums[j]=tem;
                  break;
              }else{
                  j--;
              }
           }
       }
       int ans = 0;
       for(i=0;i<lis.size();i++){
           ans = ans*10+nums[i];
       }
        return ans;
    }
    /**
     * a/b>c/d -->ad>bc ad-bc>0
     * 题号：857
     * 难度：困难
     * 时间：20220913
     * 有 n 名工人。 给定两个数组 quality 和 wage ，其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
     *
     * 现在我们想雇佣 k 名工人组成一个工资组。在雇佣 一组 k 名工人时，我们必须按照下述规则向他们支付工资：
     *
     * 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
     * 工资组中的每名工人至少应当得到他们的最低期望工资。
     * 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额 。在实际答案的 10-5 以内的答案将被接受。。
     * 看完答案之后理解
     *
     *
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int len = quality.length;
        //将工资比例由大到小进行排列
        Integer[] sorted = new Integer[len];
        //这里设工作总量为totalg ，总工资为totalw  quality[i]/totalg *totalw>= wage[i]
        //(wage[i]/quality[i])*totalg<=totalw,当工作量一定时工资总量只与工资与工作质量比有关
        //对于k组中每一个，工作质量一定时工资只与k组中最大的工量比有关即Max(wage[i]/quality[i])*totalg
        //所需只需要枚举所有的k个中最大工量比与工作总量的乘积取最小值即可
        for(int i =0;i<len;i++){
            sorted[i]=i;
        }
        //工量比由小到大进行排列
        Arrays.sort(sorted,(a,b)->{return wage[a]*quality[b]-wage[b]*quality[a];});
        //在确定工量比一定时保证工作量最小，便可以确定工资最小，所以可以采用优先队列的方式
        //在确定最大工量比的同时保证最小工作量
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b)->b-a);
        //将前k-1个入队
        int totalq = 0;
        double res = 1e9;
        for(int i =0;i<k-1;i++){
            totalq+=quality[sorted[i]];
            queue.offer(quality[sorted[i]]);
        }
        for(int i = k-1;i<len;i++){
            int index = sorted[i];
            double totalw = 0;
            totalq += quality[index];
            queue.offer(quality[index]);
            totalw =(double)wage[index]/quality[index]*totalq;
            res = Math.min(res,totalw);
            totalq -= queue.poll();
        }
        return res;
    }
    /**
     * 题号:1619
     * 难度：简单
     * 时间：20220914
     * 给你一个整数数组 arr ，请你删除最小 5% 的数字和最大 5% 的数字后，剩余数字的平均值。
     *
     * 与 标准答案 误差在 10-5 的结果都被视为正确结果。
     * 20*0.05 1
     */
    public double trimMean(int[] arr) {
        int len = arr.length,sum=0;
        Arrays.sort(arr);
        for(int i = (int)(len*0.05);i<len-len*0.05;i++){
            sum+=arr[i];
        }
        return sum/(0.9*len);
    }
    /**
     * 题号：264
     * 难度：中等
     * 时间：20220914
     * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
     *
     * 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
     */
    //这个没有用重新搞过
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
      int pos1=0,pos2=0,pos3=0;
      dp[0]=1;
      for(int i = 1;i<n;i++){
          dp[i] = Math.min(Math.min(dp[pos1]*2,dp[pos2]*3),dp[pos3]*5);
          if(dp[pos1]*2 == dp[i]){
              pos1++;
          }
          if(dp[pos2]*3 == dp[i]){
              pos2++;
          }
          if(dp[pos3]*5 == dp[i]){
              pos3++;
          }
      }
       return dp[n-1];
    }
    //这个超时了不行
    int[] states ;
    Set<Integer> set;
    int press;
    public int flipLights(int n, int presses) {
        int state1 = 0,state2=(1<<n)-1,state3=(1<<n)-1,state4=(1<<n)-1;
        press = presses;
        set = new HashSet<>();
       for(int i =1;i<=n;i++){
           //偶数为全为关的状态
           if(i%2 == 0){
               state2 = state2^(1<<(i-1));
           }else{
               //奇数位全为零
              state3 = state3^(1<<(i-1));
           }
           //反转编号为 j = 3k + 1
           if((i-1)%3 == 0 ){
                state4 = state4^(1<<(i-1));
           }
       }
       // 110  000 010 101 011 0 2 5 3
        states = new int[]{state1,state2,state3,state4};
       dfsFlip(0,(1<<n)-1);
        return set.size();
    }
    public void dfsFlip(int count ,int n){
        if(count == press){
            set.add(n);
            return;
        }
        for(int i : states){
            dfsFlip(count+1,n^i);
        }
    }
    /**
     * 题号：827
     * 难度：困难
     * 时间：20220918
     * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
     *
     * 返回执行此操作后，grid 中最大的岛屿面积是多少？
     *
     * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
     */
    public int largestIsland(int[][] grid) {
        //仅有一次机会把一变成零
        int countOne = 1,len = grid.length,moves[][]={{1,0},{0,1},{-1,0},{0,-1}};
        //利用广度优先遍历对图进行遍历
        int ans = 0;
        //保存遍历的值
        Map<Integer,Integer> res = new HashMap<>();
        for(int i = 0;i<len ;i++){
            for(int j = 0 ;j<len;j++){
                if(grid[i][j]<=0){
                    continue;
                }
                Queue<int[]> queue = new ArrayDeque<>();
                int tag = -(i*len+j+1), count = 0;
                queue.offer(new int[]{i,j});
                grid[i][j] = tag;
                while(!queue.isEmpty()){
                    count++;
                    int[] pos = queue.poll();
                    for(int[] temp : moves){
                        int x = pos[0]+temp[0],y = pos[1]+temp[1];
                        if(x>=0 && x<len && y>=0 && y<len){
                            if(grid[x][y]>0){
                                grid[x][y] = tag;
                                queue.offer(new int[]{x,y});
                            }
                        }
                    }
                }
                ans = Math.max(ans,count);
                res.put(tag,count);
            }
        }
        //将为零的变为一然后进行累加
        for(int i = 0 ;i<len ;i++){
            for(int j =0;j<len ;j++){
                int countc = 1;
                if(grid[i][j] != 0 ){
                    continue;
                }
                Set<Integer> set = new HashSet<>();
                for(int[] temp : moves){
                    int x = i+temp[0],y=j+temp[1];
                    if(x<len && x>=0 &&y<len && y>=0 && !set.contains(grid[x][y])){
                        countc+=res.getOrDefault(grid[x][y],0);
                        set.add(grid[x][y]);
                    }
                }
                ans = Math.max(countc,ans);
            }
        }
        return ans;
    }
    //并查集方式进行使用
    int n;
    int[] parent,rank;

    public int findParent(int x){
        if(parent[x] != x){
            parent[x] = findParent(parent[x]);
        }
        return parent[x];
    }

    public void union(int x ,int y){
        int parentx = findParent(x);
        int parenty = findParent(y);
        if(parentx == parenty){
            return;
        }
        if(rank[parentx]>=rank[parenty]){
            parent[parenty] = parentx;
            rank[parentx] += rank[parenty];
        }else{
            union(y,x);
        }
    }
    public int largestIsland2(int[][] grid) {
        n = grid.length;
        parent = new int[n*n+1];
        rank = new int[n*n+1];
        int[][] moves={{1,0},{0,1},{-1,0},{0,-1}};
        for(int i =0;i<n*n+1;i++){
            parent[i] = i;
            rank[i]=1;
        }
        //将为一的联通块全部合并
        for(int i = 0 ;i<n;i++){
            for(int j =0 ;j<n ;j++){
                if(grid[i][j] == 0){
                    continue;
                }
                int parentTag = i*n+j+1;
                for(int[] temp : moves){
                    int x = i+temp[0],y = j+ temp[1];
                    if(x<n && x>=0 && y<n && y>=0 && grid[x][y] != 0){
                        union(parentTag,x*n+y+1);
                    }
                }
            }
        }
        //
        int ans = 0;
        for(int i = 0;i<n ;i++){
            for(int j = 0;j<n ;j++){
                if(grid[i][j] !=0){
                   int t =  findParent(i*n+j+1);
                    ans = Math.max(ans,rank[t]);
                }else{
                    Set<Integer> set = new HashSet<>();
                    int count = 1;
                    for(int[] temp : moves){
                        int x = i+ temp[0],y = j+ temp[1];
                        if(x>=0&&x<n &&y>=0 && y<n && grid[x][y] == 1){
                            int ta = findParent(x*n+y+1);
                            if(!set.contains(ta)){
                                count+=rank[ta];
                                set.add(ta);
                            }
                        }
                    }
                    ans = Math.max(ans ,count);
                }
            }
        }
        return ans;
    }

    /**
     * 题号：850
     * 难度：困难
     * 时间：20220916
     * 我们给出了一个（轴对齐的）二维矩形列表 rectangles 。 对于 rectangle[i] = [x1, y1, x2, y2]，其中（x1，y1）是矩形 i 左下角的坐标， (xi1, yi1) 是该矩形 左下角 的坐标， (xi2, yi2) 是该矩形 右上角 的坐标。
     *
     * 计算平面中所有 rectangles 所覆盖的 总面积 。任何被两个或多个矩形覆盖的区域应只计算 一次 。
     *
     * 返回 总面积 。因为答案可能太大，返回 109 + 7 的 模 。
     * 线段树动态开点
     */
    public int rectangleArea(int[][] rectangles) {
        int MOD = (int)1e9+7;
        List<Integer> baseX = new ArrayList<>();
        for(int[] rectangle : rectangles){
            baseX.add(rectangle[0]);
            baseX.add(rectangle[2]);
        }
        int ans = 0;
        Collections.sort(baseX,(a,b)->a-b);
        for(int i = 1;i<baseX.size();i++){
            int cur = baseX.get(i),pre = baseX.get(i-1),len = cur-pre;
            List<int[]> lines = new ArrayList<>();
            //扫描所有的四边形将在该区间的四边形加入
            for(int[] rectangle : rectangles){
                if(rectangle[0]<=pre && rectangle[2]>=cur){
                    lines.add(new int[]{rectangle[1],rectangle[3]});
                }
            }
            Collections.sort(lines,(a,b)->a[0]==b[0]?a[1]-b[1]:a[0]-b[0]);
            long sum =0;
            int top = -1,buttom= -1;
            for(int[] line :lines){
                if(line[0]>top){
                    sum+=top-buttom;
                    buttom=line[0];
                    top=line[1];
                }else if(line[1]>top){
                    top = line[1];
                }
            }
            sum+=top-buttom;
            ans +=(sum*len);
            ans = ans%MOD;
        }
        return ans;
    }
}
    class Range{
        class Node{
            Node leftNode;
            Node rightNode;
            int right;
            int left;
            int top;
            int buttom;
            long val;
            int lazy;
            public Node(int left,int right,int buttom,int top){

                this.left = left;
                this.right = right;
                this.buttom = buttom;
                this.top = top;
                val = 0;
                lazy = 0;
            }
        }
        Node root = new Node(0,(int)1e9,-1,-1);
        public void update(Node root,int begin ,int end,int top,int buttom){
            if(root.left>=begin&&root.right<=end){
                root.lazy++;
                if(root.buttom == -1){
                    root.buttom = buttom;
                }
                if(root.top == -1){
                    root.top = top;
                }
                //如果全部覆盖了原始的正方形
                if(root.top<=top && root.buttom>=buttom){
                    root.val = (root.left-root.right)*(top-buttom);
                    root.top = top;
                    root.buttom = buttom;
                }else if(root.top>top && root.buttom>buttom){
                    //底部低于原正方形
                    root.val = root.val+(root.buttom-buttom)*(root.left-root.right);
                    root.buttom = buttom;
                }else if(root.buttom<buttom && root.top<top){
                    //头部高于原正方形
                    root.val = root.val +(top - root.top)* (root.left-root.right);
                    root.top = top;
                }
              return;
            }
            int mid = root.left+(root.right-root.left>>1);
            //创建下层节点
            create(root);
            //将懒标记下推
            pushDown(root);
            if(mid>=begin){
                update(root.leftNode,begin,end,top,buttom);
            }
            if(mid<end){
                update(root.rightNode,begin,end,top,buttom);
            }
            pushUp();
        }
        //将懒标记下推
        public void pushDown(Node root){

        }
        //将记录向上更新
        public void pushUp(){

        }
        public void create(Node root){
            int mid = root.left+(root.right-root.left>>1);
            if(root.leftNode == null){
                root.leftNode = new Node(root.left,mid,root.buttom,root.top);
            }
            if(root.rightNode == null){
                root.rightNode = new Node(mid+1,root.right,root.buttom,root.top);
            }
        }
    }