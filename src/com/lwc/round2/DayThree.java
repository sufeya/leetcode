package com.lwc.round2;

import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

public class DayThree {
    public static void main(String[] args) {
        DayThree three = new DayThree();
       three.findRadius2(new int[]{282475249,622650073,984943658,144108930,470211272,101027544,457850878,458777923},new int[]{823564440,115438165,784484492,74243042,114807987,137522503,441282327,16531729,823378840,143542612});
    }

    /**
     * 题号：851
     * 难度：中等
     * 时间：20211215
     * 有一组 n 个人作为实验对象，从 0 到 n - 1 编号，其中每个人都有不同数目的钱，以及不同程度的安静值（quietness）。为了方便起见，我们将编号为 x 的人简称为 "person x "。
     * <p>
     * 给你一个数组 richer ，其中 richer[i] = [ai, bi] 表示 person ai 比 person bi 更有钱。另给你一个整数数组 quiet ，其中 quiet[i] 是 person i 的安静值。richer 中所给出的数据 逻辑自恰（也就是说，在 person x 比 person y 更有钱的同时，不会出现 person y 比 person x 更有钱的情况 ）。
     * <p>
     * 现在，返回一个整数数组 answer 作为答案，其中 answer[x] = y 的前提是，在所有拥有的钱肯定不少于 person x 的人中，person y 是最安静的人（也就是安静值 quiet[y] 最小的人）。
     * 分析：对于answer[i]先找到所有比i有钱的人也就是遍历richer数组，找到所有的集合之后然后再在其中找到
     * quiter值最小的值即可
     * 解题：
     */
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int len = quiet.length;
        int[] answer = new int[len];
        int[][] graph = new int[len][len];
        Arrays.fill(answer,-1);
        //构建出一个富有图
        for (int i = 0; i < richer.length; i++) {
            graph[richer[i][1]][richer[i][0]] = 1;
        }
        //生成结果
        for (int i = 0; i < len; i++) {
           dfs(graph,i,quiet,answer);
        }
        return answer;
    }

    //对图进行深度优先遍历,其中i表示的是起始点
    public int dfs(int[][] graph, int i, int[] quiet,int[] ans) {
        //如果之前的有值的话可以直接使用之前的值
        if(ans[i] == -1 ){
            int len = graph.length;
            int minQuest = quiet[i];
            ans[i] = i;
            for (int k = 0; k < len; k++) {
                if (graph[i][k] != 0) {
                    int temp = dfs(graph, k, quiet ,ans);
                    if (quiet[k] <= quiet[temp] && quiet[k] < minQuest) {
                        ans[i] = k;
                        minQuest = quiet[k];
                    }
                    if (quiet[temp] <= quiet[k] && quiet[temp] < minQuest) {
                        ans[i] = temp;
                        minQuest = quiet[temp];
                    }
                }
            }
        }
        return ans[i];
    }
    /**
     * 题号：1610
     * 难度：困难
     * 时间：20211216
     * 给你一个点数组 points 和一个表示角度的整数 angle ，你的位置是 location ，其中 location = [posx, posy] 且 points[i] = [xi, yi] 都表示 X-Y 平面上的整数坐标。
     *
     * 最开始，你面向东方进行观测。你 不能 进行移动改变位置，但可以通过 自转 调整观测角度。换句话说，posx 和 posy 不能改变。你的视野范围的角度用 angle 表示， 这决定了你观测任意方向时可以多宽。设 d 为你逆时针自转旋转的度数，那么你的视野就是角度范围 [d - angle/2, d + angle/2] 所指示的那片区域。
     * 分析：其实自己原先是有点思路的只不过这个角度转化不太清楚，看完答案之后有了自己的理解，首先将所有的点以y=location
     * 解题：
     */
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        List<Double> polarAngel =new ArrayList<>();
        //记录与location位置相同的点
        int samePoint=0;
        //先将对应的点相对于location转化为极角
       for(List<Integer> list :points){
           int x =list.get(0)-location.get(0);
           int y =list.get(1) - location.get(1);
           if(x == 0 && y ==0){
              samePoint ++;
           }else{
               double tempPolar = Math.atan2(y,x);
               polarAngel.add(tempPolar+Math.PI);
           }
       }
        int m = polarAngel.size();
        for (int i = 0; i < m; ++i) {
            polarAngel.add(polarAngel.get(i) + 2 * Math.PI);
        }

       //对所生成的极角进行排序
       polarAngel.sort(new Comparator<Double>() {
           @Override
           public int compare(Double o1, Double o2) {
               return o1-o2 > 0 ? 1:-1;
           }
       });
        //跟据视角范围来对整个集合进行遍历从而查找到所能观察到点的最大值
        int i=0,j=0,maxPoint=0;
        double toDegree =(angle*Math.PI)/180;
        while(i < polarAngel.size() && j < polarAngel.size()){
            //这翻转180度的话也可以算入其中
            if( polarAngel.get(j) <= polarAngel.get(i)+toDegree){
                j++;
            }else{
                i=j;
            }
            maxPoint =Math.max(maxPoint,j-i+1);
        }
        //最后有可能到最后一个角度都是小于angel所以需要额外的比较
        return maxPoint+samePoint;
    }
    public int visiblePoints2(List<List<Integer>> points, int angle, List<Integer> location) {
        int sameCnt = 0;
        List<Double> polarDegrees = new ArrayList<>();
        int locationX = location.get(0);
        int locationY = location.get(1);
        for (int i = 0; i < points.size(); ++i) {
            int x = points.get(i).get(0);
            int y = points.get(i).get(1);
            if (x == locationX && y == locationY) {
                sameCnt++;
                continue;
            }
            Double degree = Math.atan2(y - locationY, x - locationX);
            polarDegrees.add(degree);
        }
        Collections.sort(polarDegrees);

        int m = polarDegrees.size();
        for (int i = 0; i < m; ++i) {
            polarDegrees.add(polarDegrees.get(i) + 2 * Math.PI);
        }

        int maxCnt = 0;
        int right = 0;
        double toDegree = angle * Math.PI / 180;
        for (int i = 0; i < m; ++i) {
            Double curr = polarDegrees.get(i) + toDegree;
            while (right < polarDegrees.size() && polarDegrees.get(right) <= curr) {
                right++;
            }
            maxCnt = Math.max(maxCnt, right - i);
        }
        return maxCnt + sameCnt;
    }
    /**
     * 难度：中等
     * 题号：419
     * 时间：20211218
     * 给你一个大小为 m x n 的矩阵 board 表示甲板，其中，每个单元格可以是一艘战舰 'X' 或者是一个空位 '.' ，返回在甲板 board 上放置的 战舰 的数量。
     *
     * 战舰 只能水平或者垂直放置在 board 上。换句话说，战舰只能按 1 x k（1 行，k 列）或 k x 1（k 行，1 列）的形状建造，其中 k 可以是任意大小。两艘战舰之间至少有一个水平或垂直的空位分隔 （即没有相邻的战舰）
     * 分析：战舰长度可以占一行或者占一个格子，且各个战舰之间没有相邻的战舰，他给的条件就是没有相邻的战舰所以
     * 解题：可以从上自下从左至右对整个数组进行遍历，每次遍历检查右边或者上边的格子是否右对应的字符如果有则说明不需要进行计数
     * 如果没有则说明需要进行计数，这样可以避免重复计数
     */
    public int countBattleships(char[][] board) {
        int m =board.length;
        int n =board[0].length;
        int res =0;
        for(int i =0 ; i<m ;i++){
            for(int j =0 ;j<n ;j++){
                //j和i均不为零时则可以进行判断
                if(j> 0 && i> 0 &&(board[i][j-1] == '.' && board[i-1][j] =='.'&& board[i][j] == 'X')){
                    res++;
                }
                if(i ==0 && j!=0 && board[i][j-1]=='.'&& board[i][j] == 'X'){
                    res++;
                }
                if(i !=0 && j==0 && board[i-1][j]=='.'&& board[i][j] == 'X'){
                    res++;
                }
                if(i == 0 && j == 0 && board[i][j] == 'X'){
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 题号：997
     * 难度：简单
     * 时间：20211219
     * 在一个小镇里，按从 1 到 n 为 n 个人进行编号。传言称，这些人中有一个是小镇上的秘密法官。
     *
     * 如果小镇的法官真的存在，那么：
     *
     * 小镇的法官不相信任何人。
     * 每个人（除了小镇法官外）都信任小镇的法官。
     * 只有一个人同时满足条件 1 和条件 2 。
     * 给定数组 trust，该数组由信任对 trust[i] = [a, b] 组成，表示编号为 a 的人信任编号为 b 的人。
     *
     * 如果小镇存在秘密法官并且可以确定他的身份，请返回该法官的编号。否则，返回 -1。
     */

    public int findJudge(int n, int[][] trust) {
        if(n == 1){
            return n;
        }
        Map<Integer,Integer> countTrust =new HashMap<>();
        //统计
        List<Integer> list =new ArrayList<>();
        //先跟据trust数组构建图
        int[][] graph =new int[n+1][n+1];
        for(int[] temp :trust){
            //矩阵的行表示人的标号graph[i][j]，其中如果有1的话表示的是i信任j
            graph[temp[0]][temp[1]] = 1;
        }
        //对graph进行遍历，先找到谁都不新人的人
        for(int i = 1; i < n+1; i++){
            int count =0 ;
            for( int j =1 ;j < n+1 ;j++){
                //统计j被多少人信任
                if(graph[i][j] == 1){
                    countTrust.put(j,countTrust.getOrDefault(j,0)+1);
                    count++;
                }
                if(count == 0 && j == n){
                    list.add(i);
                }
            }
        }
        for(Map.Entry<Integer,Integer> entry : countTrust.entrySet()){
            if(list.contains(entry.getKey()) && entry.getValue() == n-1){
                return  entry.getKey();
            }
        }

        return -1;
    }

    /**
     * 难度：中等
     * 时间：20211220
     * 题号：475
     * 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
     *
     * 在加热器的加热半径范围内的每个房屋都可以获得供暖。
     *
     * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
     *解题：
     * 分析：题目中给定了加热器的位置，跟据加热器的位置找到覆盖所有房屋的大致可以分成两种情况
     * 一种加热器位于房屋的两端进行加热
     * 另一种是加热器位于中间进行加热
     *
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        //记录没有房屋之间差距多少
        int begin =0 ,end =0;
        //表示加热器的半径长度
        int res =0,hLen = heaters.length, houseLen =houses.length;
        Arrays.sort(houses);
        Arrays.sort(heaters);
        //如果只有一个加热器并且房屋有两个以上的话那么直接计算两个端点的值即可,取最大值即可
        if(hLen == 1 && houseLen >1){
            return Math.max(heaters[0]-houses[0],houses[houseLen-1]-heaters[0]);
        }
        //如果房子和加热器都为一的话则直接端点相减即可
        if(houseLen ==1){
            res= 9999999;
            for(int i: heaters){
                if(i -houses[0]>=0){
                    res = Math.min(res,i-houses[0]);
                }else{
                    res =Math.min(res,houses[0]- i);
                }
            }
            return res;
        }
        //如果房子数量和加热器的数量都大于一
        //先只计算满足能覆盖所有两个加热器之间的房屋的最小半径的值，计算完之后在计算两个端点之间的值
        for(int i =1; i< hLen ;i++){
            //这里不能只是单纯的计算两个加热器之间的距离，而是要覆盖两个端点之间的所有房屋，所以应当做的是先找到两个加热点之间的房屋
            int tempInde =findIndex(houses,heaters[i-1]);
            int tempIndex2=findIndex(houses,heaters[i]);
            //如果不是找到的准确的位置则左端点需要向右偏移一位
            if(houses[tempInde] != heaters[i-1]){
                tempInde++;
            }

            //如果加热器之间没有房屋节点时
            if(tempInde<=tempIndex2){
                //如果之前跳过了很多节点则再进行 计算
                if(begin>0){
                    int k =i-begin;
                    int index =findIndex(houses,heaters[i-begin]);
                    int index2=findIndex(houses,heaters[i]);
                    res = Math.max(res, Math.max(heaters[i-begin]-houses[index],houses[index2]-heaters[i-1]));
                    begin=0;
                }

                //要覆盖两个索引之间的所有节点
                while(tempInde<tempIndex2){
                    if(houses[tempInde]-heaters[i-1]< heaters[i]- houses[tempIndex2]){
                        tempInde++;
                    }
                    if(houses[tempInde]-heaters[i-1]> heaters[i]- houses[tempIndex2]){
                        tempIndex2--;
                    }
                    if(houses[tempInde]-heaters[i-1]== heaters[i]- houses[tempIndex2]){
                        tempIndex2--;
                        tempInde++;
                    }
                }
                if(tempInde>tempIndex2){
                    tempInde--;
                    tempIndex2++;
                    res =Math.max(res,Math.max(houses[tempInde] - heaters[i-1],heaters[i] -houses[tempIndex2]));
                }else{
                    res =Math.max(res,houses[tempInde] - heaters[i-1]);
                }

            }else{
                begin++;
            }
        }
        //现在计算两个端点之间的
        res = Math.max(res,Math.max(heaters[0]-houses[0],houses[houseLen-1]-heaters[hLen-1]));
        return res;
    }

    /**
     * 上面程序自己编码以加热器为角度进行遍历整个程序非常麻烦，现在跟据答案的理解以房屋为角度进行遍历
     * 看完算法之后自己进行编程
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius2(int[] houses, int[] heaters) {
        //分别对两个数组进行排序
        Arrays.sort(heaters);
        Arrays.sort(houses);
        int res= 0;
        int houseLen =houses.length,heatLen=heaters.length;
        //以房屋为角度进行遍历利用二分法找到离房屋最近的两个加热器其中距离最小的便是加热器的最小半径
        for(int i=0; i<houseLen ;i++){
            //如果house[i]在加热器的左边则可以直接进行相减获得对应的半径位置
          if(houses[i]<=heaters[0]){
              res = Math.max(heaters[0]-houses[i],res);
          }
          //如果房子在最后一个加热器的最右边则也可以进行直接相减
          if(houses[i]>= heaters[heatLen-1]){
              res = Math.max(houses[i]- heaters[heatLen-1],res);
          }
          //如果房屋两边都具有加热器则找到离房子最近最大的下标索引，其所以加一就是大于该房子位置的最小距离的加热炉
            if(houses[i]>heaters[0] && houses[i]<heaters[heatLen-1]){
                int index=0;
               for(int k=0;k<heatLen;k++){
                   if(heaters[k]>houses[i]){
                       index=k-1;
                       break;
                   }
               }
                res = Math.max(res,Math.min(houses[i]-heaters[index],heaters[index+1] - houses[i]));
            }

        }
        return res;
    }
    public int findIndex(int[] houses,int aim){
        int left =0 ,right =houses.length-1;
        while(left < right){
            int mid =left+(right-left)/2;
            if(houses[mid]> aim){
                right = mid - 1;
            }else{
                left=mid+1;
            }
        }
        return left;
    }


}
