package com.lwc.round4;

import java.util.*;

public class DayOne {
    /**
     * 题号：436
     * 难度：中等
     * 时间：20220520
     * 给你一个区间数组 intervals ，其中 intervals[i] = [starti, endi] ，且每个 starti 都 不同 。
     *
     * 区间 i 的 右侧区间 可以记作区间 j ，并满足 startj >= endi ，且 startj 最小化 。
     *
     * 返回一个由每个区间 i 的 右侧区间 的最小起始位置组成的数组。如果某个区间 i 不存在对应的 右侧区间 ，则下标 i 处的值设为 -1
     */
    public int[] findRightInterval(int[][] intervals) {
        int len =intervals.length;
        int[] ans = new int[len];
        Map<Integer,Integer> map = new HashMap<>();
        //将起始点和序号放入到hash数组中
        for(int i =0 ;i<len;i++){
            map.put(intervals[i][0],i);
        }
        //对整个数组进行排序
        Comparator<int[]> comparator=new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        };
        Arrays.sort(intervals,comparator);
        //最后一个一定没有最右最小得区间
        ans[map.get(intervals[len-1][0])]=-1;
        //对排序之后得数组进行遍历
        for(int i=0;i<len-1;i++){
            int j=i;
            while(j<len && intervals[i][1]>intervals[j][0] ){
                j++;
            }
            if(j==len){
                ans[map.get(intervals[i][0])] =-1;
            }else{
                ans[map.get(intervals[i][0])]=map.get(intervals[j][0]);
            }

        }
        return ans;
    }

    /**
     *题号：
     */
    public int repeatedNTimes(int[] nums) {
        Map<Integer,Integer> map =new HashMap<>();
        for(int i :nums){
            map.put(i,map.getOrDefault(i,0)+1);
            if(map.getOrDefault(i,0) ==nums.length/2){
                return i;
            }
        }
        return 0;
    }

    /**
     * 题号：464
     * 难度：中等
     * 时间：20220522
     * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和 达到或超过  100 的玩家，即为胜者。
     *
     * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
     *
     * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
     *
     * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），若先出手的玩家是否能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
     *分析：对于两个玩家来说选择数字如果能赢则应当选择尽可能大的数字，如果当前任何数字不能使该玩家赢
     * 则应当选择尽可能小的数字，当满足最后的结果不管选择任何数字都是对方必输的状态时
     */
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        //dp用于记录深度遍历的结果以防止重复遍历其中dp[i][0]=-1时表示的时当选择数为i时先手人的胜负情况
        int[][] dp = new int[(1<<maxChoosableInteger)][2];
        if((maxChoosableInteger+1)*maxChoosableInteger/2<desiredTotal){
            return false;
        }
        int i =choose((1<<maxChoosableInteger)-1,0,desiredTotal,maxChoosableInteger,dp );

        boolean ans = i==-1 ? false:true;
        return ans;
    }
    public int  choose(int maxChoose,int count,int desiredTotal,int maxChoosableInteger,int[][]dp){

        if(dp[maxChoose][count%2] != 0){
            return dp[maxChoose][count%2];
        }
        int res =-1;
       for(int i=0;i<maxChoosableInteger;i++){
           //如果这个数已经被使用过了的话则跳过
           if(((maxChoose>>i)&1) != 1){
               continue;
           }
           //如果当前的数可以直接超过所需要的值时则说明当前对象可以直接取胜
           //为什么不选去最大值，因为这个是遍历所有的情况，最大值的情况后续会接着考虑
           if(desiredTotal<=i+1){
            res = -res;
            break;
           }
           //如果下一轮的选数的人会输的话则表明当前这轮的人会赢
           if(choose(maxChoose^(1<<i),count++,desiredTotal-i-1,maxChoosableInteger,dp) == -1 ){
               res = -res;
               break;
           }
       }
        dp[maxChoose][count%2] =res;
        return dp[maxChoose][count%2];
    }

    int n, t;
    int[][] f = new int[1 << 20][2];
    // 1 true / -1 false
    int dfs(int state, int tot, int k) {
        if (state == ((1 << n) - 1) && tot < t) return -1;
        if (f[state][k % 2] != 0) return f[state][k % 2];
        int hope = k % 2 == 0 ? 1 : -1;
        for (int i = 0; i < n; i++) {
            if (((state >> i) & 1) == 1) continue;
            if (tot + i + 1 >= t) return f[state][k % 2] = hope;
            if (dfs(state | (1 << i), tot + i + 1, k + 1) == hope) return f[state][k % 2] = hope;
        }
        return f[state][k % 2] = -hope;
    }
    public boolean canIWin2(int _n, int _t) {
        n = _n; t = _t;
        if (t == 0) return true;
        return dfs(0, 0, 0) == 1;
    }

    /**
     * 题号：675
     * 难度：困难
     * 时间：20220523
     * 你被请来给一个要举办高尔夫比赛的树林砍树。树林由一个 m x n 的矩阵表示， 在这个矩阵中：
     *
     * 0 表示障碍，无法触碰
     * 1 表示地面，可以行走
     * 比 1 大的数 表示有树的单元格，可以行走，数值表示树的高度
     * 每一步，你都可以向上、下、左、右四个方向之一移动一个单位，如果你站的地方有一棵树，那么你可以决定是否要砍倒它。
     *
     * 你需要按照树的高度从低向高砍掉所有的树，每砍过一颗树，该单元格的值变为 1（即变为地面）。
     *
     * 你将从 (0, 0) 点开始工作，返回你砍完所有树需要走的最小步数。 如果你无法砍完所有的树，返回 -1 。
     *
     * 可以保证的是，没有两棵树的高度是相同的，并且你至少需要砍倒一棵树。
     */
    boolean[][] visited ;
    int nLen=0,mLen=0;
    int[][] mod = new int[][]{{0,1},{1,0},{-1,0},{0,-1}};
    public int cutOffTree(List<List<Integer>> forest) {
         nLen = forest.size();
         mLen=forest.get(0).size();

        //先对整个森林进行处理，找出所有的数的数量，并且用优先队列将数保存在队列中
        int count=0;//保存数的数量
        //队列保留树中的坐标
        List<int[]> trees = new ArrayList<>();
        for(int i =0 ;i<nLen;i++){
            for(int j =0;j<mLen;j++){
                if(forest.get(i).get(j) >1){
                    trees.add(new int[]{i,j});
                }
            }
        }
        //对整个集合进行排序
        Collections.sort(trees, (a, b) -> forest.get(a[0]).get(a[1]) - forest.get(b[0]).get(b[1]));
        int x=0,y=0,ans =0;
        for(int[] pos :trees){
            int step =bfs(forest,x,y,pos[0],pos[1]);
            if(step == -1){
                return -1;
            }
            ans +=step;
            x=pos[0];
            y=pos[1];
        }
        return ans;
    }
    //广度优先遍历
    public int bfs(List<List<Integer>> forest,int x,int y,int nx,int ny){
        if(x==nx && ny ==y){
            return 0;
        }
        visited =new boolean[nLen][mLen];
        int step =0;
        Queue<int[]> queue =new ArrayDeque<>();
        queue.offer(new int[]{x,y});
        visited[x][y] = true;
        while(!queue.isEmpty()){
            int sz = queue.size();
            step++;
            for(int i =0 ;i<sz;i++){
                int[] temp = queue.poll();
                for(int[] dis: mod){
                    int kx=temp[0]+dis[0],ky=temp[1]+dis[1];
                    if(kx>=0&&kx<nLen && ky>=0&& ky<mLen && forest.get(kx).get(ky)>0 && !visited[kx][ky]){
                        if(kx==nx && ky== ny){
                            return step;
                        }
                        queue.offer(new int[]{kx,ky});
                        visited[kx][ky]=true;

                    }
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        DayOne one=new DayOne();
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(new Integer[]{1, 2, 3}));
        list.add(Arrays.asList(new Integer[]{0, 0, 4}));
        list.add(Arrays.asList(new Integer[]{7, 6, 5}));
        System.out.println(one.cutOffTree(list));
    }
}
