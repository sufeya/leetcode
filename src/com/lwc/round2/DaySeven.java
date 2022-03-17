package com.lwc.round2;

import java.util.*;

public class DaySeven {
    public static void main(String[] args) {
        DaySeven seven = new DaySeven();
        DetectSquares detectSquares  = new DetectSquares();
        detectSquares.add(new int[]{3,10});
        detectSquares.add(new int[]{11,2});
        detectSquares.add(new int[]{3,2});
        System.out.println(detectSquares.count(new int[]{11,10}));
        System.out.println(detectSquares.count(new int[]{14,8}));
        detectSquares.add(new int[]{11,10});
        System.out.println( detectSquares.count(new int[]{11,10}));
        System.out.println();
    }
    /**
     * 时间：20220120
     * 题号：2029
     * 难度：中等
     * Alice 和 Bob 再次设计了一款新的石子游戏。现有一行 n 个石子，每个石子都有一个关联的数字表示它的价值。给你一个整数数组 stones ，其中 stones[i] 是第 i 个石子的价值。
     *
     * Alice 和 Bob 轮流进行自己的回合，Alice 先手。每一回合，玩家需要从 stones 中移除任一石子。
     *
     * 如果玩家移除石子后，导致 所有已移除石子 的价值 总和 可以被 3 整除，那么该玩家就 输掉游戏 。
     * 如果不满足上一条，且移除后没有任何剩余的石子，那么 Bob 将会直接获胜（即便是在 Alice 的回合）。
     * 假设两位玩家均采用 最佳 决策。如果 Alice 获胜，返回 true ；如果 Bob 获胜，返回 false 。
     * 分析:
     * 解题：alice赢的情况为bob取下一块石头所取的值被三整除则alice胜出
     * bob赢的情况：1.alice取下一块石头其所取的值被三整除则bob胜出
     * 2.两个人轮流取石头取完所有石头之后均没有被三整除则bob胜出
     * 在整个取的过程中两个人都尽量取使得所取值不被三整除的石头的价值，即取最优解
     * 应当可以采用回溯算法进行选石头对选完该石头所剩余值被三整除的石头进行舍弃，当然如果当所有
     * 石头选取之后均能被三整除则可以直接判定另外一个人胜出
     */
    public boolean stoneGameIX(int[] stones) {
        Set<String> set =new HashSet<>();
        return chooseStone(set,0,0,stones);
    }
    //stone的价值用set进行封装方便进行移除，time表示选取的次数从而确定当前是谁在选取石头,sum表示当前
    //选取石头的剩余值
    public boolean chooseStone(Set<String> choosedStone,int time,int sum,int[] stones){
        boolean res = false;
        //当石头取完时则表明bob赢了
        if(choosedStone.size() == stones.length){
            return res;
        }
        //遍历set进行选取石头,如果当所有的石头选取之后,这里存在相同价值的石头
        for(int i =0 ;i<stones.length;i++){
            //如果选取该石头的值后剩余值不会被三整除则选取
            if((sum+stones[i])%3 != 0 && !choosedStone.contains(i+"_"+stones[i])){
                time++;
                sum+=stones[i];
                choosedStone.add(i+"_"+stones[i]);
                return chooseStone(choosedStone,time,sum,stones);
            }
        }
        //均会被3整除,则上面的循环便不会选取数，此时则需要进行另外的判断
        if((time+1) % 2 ==0){
            res= true;
        }
        return res;
    }
    //看完答案后的解题思路，采用博弈的角度对alice和bob的情况进行分析
    public boolean stoneGameIX2(int[] stones) {
        int[] count =new int[3];
        //计算其中0，1，2的个数
        for(int i : stones){
            count[i%3]++;
        }
        //如果0的个数为偶数的话那就相当于没有0
        if(count[0] % 2 == 0){
            //如果1，或者2中的某一个为零的话，那个a先手b只能拿相同的而后选择的值全1时为2，全2时选择的值为1
            //则要么a选择整除3的值，要么取完则b必胜
            if(count[1] ==0 || count[2]==0){
                return false;
            }else{
                //如果1，2均不为零则a只需选择少的那么一定时b先选择整除3的值比如说1的个数比2多，那么a，b的取的顺序一定为
                //2,2,1,2,1....b2一定先被取完然后b只能取1所以a必胜
                return true;
            }
        }else{
            //如果0的个数为奇数时就相当于只有一个换手的机会,如果1和2之间的差值小于等于2，则当a选择之后b立马进行换手之后a一定只能选择
            //与第一次选择相同的数
            if(Math.abs(count[2]-count[1])<=2){
                return false;
            }else{
                //如果小于两个之后
                return true;
            }
        }

    }
    /**
     * 题号：1332
     * 难度：简单
     * 时间：20220122
     * 给你一个字符串 s，它仅由字母 'a' 和 'b' 组成。每一次删除操作都可以从 s 中删除一个回文 子序列。
     *
     * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
     *
     * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
     *
     * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
     * 分析：由于整个字符串中只有两种字符，并且删除的是子序列所以可以跳过某些字符进行删除，要想删除的次数最少，则要进行保证
     * 每次删除的子序列都是整个字符串中的最长回文子序列所以可以从头开始进行扫描从而获取其最长回文子序列，由于整个字符串只有两个
     * 字符串所以可以很方便，由于所有子符串只有两个所以最多删除两次即全是a或者全部是b,要么就是整个就是一个回文字符串只需要删除
     * 一次即可
     * 解题：
     */
    public int removePalindromeSub(String s) {
        char[] chars = s.toCharArray();
        int i = 0, j =chars.length-1;
        while(j>i){
            if(chars[i] == chars[j]){
                i++;
                j--;
            }else{
                break;
            }
        }
        if(i >= j){
            return 1;
        }
        return 2;
    }
    /**
     * 题号：1688
     * 难度：简单
     * 时间：20220125
     * 给你一个整数 n ，表示比赛中的队伍数。比赛遵循一种独特的赛制：
     *
     * 如果当前队伍数是 偶数 ，那么每支队伍都会与另一支队伍配对。总共进行 n / 2 场比赛，且产生 n / 2 支队伍进入下一轮。
     * 如果当前队伍数为 奇数 ，那么将会随机轮空并晋级一支队伍，其余的队伍配对。总共进行 (n - 1) / 2 场比赛，且产生 (n - 1) / 2 + 1 支队伍进入下一轮。
     * 返回在比赛中进行的配对次数，直到决出获胜队伍为止。
     */
    public int numberOfMatches(int n) {
        int res =0;
        while(n>1){
            //如果是偶数
            if(n%2 == 0){
                res += n/2;
                n=n/2;
            }else{
                //如果是奇数
                res= res + (n-1)/2;
                n= (n-1)/2+1;
            }
        }
        return res;
    }
}

/**
 * 题号：2013
 * 难度：中等
 * 时间：20220126
 * 给你一个在 X-Y 平面上的点构成的数据流。设计一个满足下述要求的算法：
 *
 * 添加 一个在数据流中的新点到某个数据结构中。可以添加 重复 的点，并会视作不同的点进行处理。
 * 给你一个查询点，请你从数据结构中选出三个点，使这三个点和查询点一同构成一个 面积为正 的 轴对齐正方形 ，统计 满足该要求的方案数目。
 * 轴对齐正方形 是一个正方形，除四条边长度相同外，还满足每条边都与 x-轴 或 y-轴 平行或垂直。
 *
 * 实现 DetectSquares 类：
 *
 * DetectSquares() 使用空数据结构初始化对象
 * void add(int[] point) 向数据结构添加一个新的点 point = [x, y]
 * int count(int[] point) 统计按上述方式与点 point = [x, y] 共同构造 轴对齐正方形 的方案数。
 *  分析：跟据题目需求可以知道题目是的count是跟据给定的点搜索能成为正方形的四个点且每个点都可以进行重复
 * 也就是所四个点重复的数量相乘得到这四个点所构成的四方形所以
 * 解题：
 */
class DetectSquares {
    Map<Integer, Map<Integer, Integer>> cnt;

    public DetectSquares() {
        cnt = new HashMap<Integer, Map<Integer, Integer>>();
    }

    public void add(int[] point) {
        int x = point[0], y = point[1];
        cnt.putIfAbsent(y, new HashMap<Integer, Integer>());
        Map<Integer, Integer> yCnt = cnt.get(y);
        yCnt.put(x, yCnt.getOrDefault(x, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0;
        int x = point[0], y = point[1];
        if (!cnt.containsKey(y)) {
            return 0;
        }
        Map<Integer, Integer> yCnt = cnt.get(y);
        Set<Map.Entry<Integer, Map<Integer, Integer>>> entries = cnt.entrySet();
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : entries) {
            int col = entry.getKey();
            Map<Integer, Integer> colCnt = entry.getValue();
            if (col != y) {
                // 根据对称性，这里可以不用取绝对值
                int d = col - y;
                res += colCnt.getOrDefault(x, 0) * yCnt.getOrDefault(x + d, 0) * colCnt.getOrDefault(x + d, 0);
                res += colCnt.getOrDefault(x, 0) * yCnt.getOrDefault(x - d, 0) * colCnt.getOrDefault(x - d, 0);
            }
        }
        return res;
    }
}

