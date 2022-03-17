package com.lwc.round2;

import java.util.*;

public class DayEight {
    public static void main(String[] args) {
        DayEight eight =new DayEight();
        //eight.findMinFibonacciNumbers(19);
        System.out.println(eight.numberOfGoodSubsets(new int[]{4,2,3,15}));
    }
    /**
     * 时间：20220203
     * 题号：1414
     * 难度：中等
     * 给你数字 k ，请你返回和为 k 的斐波那契数字的最少数目，其中，每个斐波那契数字都可以被使用多次。
     *
     * 斐波那契数字定义为：
     *
     * F1 = 1
     * F2 = 1
     * Fn = Fn-1 + Fn-2 ， 其中 n > 2 。
     * 数据保证对于给定的 k ，一定能找到可行解。
     */
    public int findMinFibonacciNumbers(int k) {
        int res =0;
        int len =45;
        int[] fab =new int[len];
        fab[0]=1;
        fab[1]=1;
        //生成费波耶齐数列
        for(int i=2;i<fab.length;i++){
            fab[i]=fab[i-1]+fab[i-2];
        }
        while(k>=1){
            //利用二分法查找小于等于k得最大数字
            int left =0 ,right =len-1;
            while(left<= right){
                int mid = left+((right-left)>>1);
                if(fab[mid]<=k){
                    left=mid+1;
                }else{
                    right =mid-1;
                }
            }
            k =k-fab[right];
            res++;
        }

        return res;
    }

    /**
     * 题号：1748
     * 难度：简单
     * 时间：20220206
     * 给你一个整数数组 nums 。数组中唯一元素是那些只出现 恰好一次 的元素。
     *
     * 请你返回 nums 中唯一元素的 和 。
     * @param nums
     * @return
     */
    public int sumOfUnique(int[] nums) {
        int res = 0;
        Map<Integer,Integer> map =new HashMap<>();
        for(int i : nums){

            map.put(i,map.getOrDefault(i,0)+1);
            //减去计算两次的数字，超过两次的数字就不进行计算，以防重复计算
            if(map.getOrDefault(i,0)==2){
                res -=i;
            }else if(map.getOrDefault(i,0)<=1){
                res+=i;
            }
        }
        return res;
    }
    /**
     * 题号：969
     * 难度：中等
     * 时间：20220219
     * 给你一个整数数组 arr ，请使用 煎饼翻转 完成对数组的排序。
     *
     * 一次煎饼翻转的执行过程如下：
     *
     * 选择一个整数 k ，1 <= k <= arr.length
     * 反转子数组 arr[0...k-1]（下标从 0 开始）
     * 例如，arr = [3,2,1,4] ，选择 k = 3 进行一次煎饼翻转，反转子数组 [3,2,1] ，得到 arr = [1,2,3,4] 。
     *
     * 以数组形式返回能使 arr 有序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * arr.length 范围内的有效答案都将被判断为正确。
     *分析：该题主要在于找到翻转的位置以及如何对整个位置进行翻转，首先找到整个数组最大的数的索引将该位置翻转一遍到首位置，之后
     * 将整个数组进行翻转，将最大的数反转到尾位置去掉尾位数字进行同样的操作即可
     */
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> res =new ArrayList<>();
        findMax(arr,arr.length-1,res);
        return res;
    }
    public void findMax(int[] arr ,int index,List<Integer> result){
        //还要判断整个数组到当前位置是否是有序的如果是有序的就不进行接下来的操作
        boolean flag = false;
        for(int i =0 ;i<index;i++){
            if(arr[i]>arr[i+1]){
                flag = true;
            }
        }
        if(index>=0 && flag){
            int maxIndex = -1,maxVal=-1;
            //找到索引范围内最大的数字，并且记录其索引
            for(int i =0 ;i<=index ;i++){
                if(maxVal < arr[i]){
                    maxVal=arr[i];
                    maxIndex=i;
                }
            }
            //对整个数组进行翻转
            reverse(arr,maxIndex);
            result.add(maxIndex+1);
            reverse(arr,index);
            result.add(index+1);
            findMax(arr,index-1,result);
        }
    }
    //将整个数组从0到index的位置进行翻转
    public void reverse(int[] arr ,int index){
        int[] temp =new int[index+1];
        for(int i = index ; i>=0;i--){
            temp[index-i]=arr[i];
        }
        for(int i =0 ;i<=index;i++){
            arr[i]=temp[i];
        }
    }

    /**
     * 题号：717
     * 难度：简单
     * 时间：2022.02.20
     * 有两种特殊字符：
     *
     * 第一种字符可以用一个比特 0 来表示
     * 第二种字符可以用两个比特(10 或 11)来表示、
     * 给定一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一位字符，则返回 true 。
     *
     */
    public boolean isOneBitCharacter(int[] bits) {
          int i =0;
          while(i<bits.length-1){
              i+=bits[i]+1;
          }
            return  i == bits.length-1;
    }

    /**
     * 题号：1219
     * 难度：中等
     * 时间：20220206
     * 你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 m * n 的网格 grid 进行了标注。每个单元格中的整数就表示这一单元格中的黄金数量；如果该单元格是空的，那么就是 0。
     *
     * 为了使收益最大化，矿工需要按以下规则来开采黄金：
     *
     * 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
     * 矿工每次可以从当前位置向上下左右四个方向走。
     * 每个单元格只能被开采（进入）一次。
     * 不得开采（进入）黄金数目为 0 的单元格。
     * 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。
     */
    public int getMaximumGold(int[][] grid) {
        return 0;
    }
    /**
     * 时间：20220221
     * 难度：中等
     * 题号：838
     * n 张多米诺骨牌排成一行，将每张多米诺骨牌垂直竖立。在开始时，同时把一些多米诺骨牌向左或向右推。
     *
     * 每过一秒，倒向左边的多米诺骨牌会推动其左侧相邻的多米诺骨牌。同样地，倒向右边的多米诺骨牌也会推动竖立在其右侧的相邻多米诺骨牌。
     *
     * 如果一张垂直竖立的多米诺骨牌的两侧同时有多米诺骨牌倒下时，由于受力平衡， 该骨牌仍然保持不变。
     *
     * 就这个问题而言，我们会认为一张正在倒下的多米诺骨牌不会对其它正在倒下或已经倒下的多米诺骨牌施加额外的力。
     *
     * 给你一个字符串 dominoes 表示这一行多米诺骨牌的初始状态，其中：
     *
     * dominoes[i] = 'L'，表示第 i 张多米诺骨牌被推向左侧，
     * dominoes[i] = 'R'，表示第 i 张多米诺骨牌被推向右侧，
     * dominoes[i] = '.'，表示没有推动第 i 张多米诺骨牌。
     * 返回表示最终状态的字符串。
     * 分析：看完解析后的理解如下整个多米诺骨牌的过程类似一个广度优先搜索，当初始受力时，跟据初始受力节点向两边进行
     * 扩散受力，当遍历完初始受力节点再
     * 解题：
     */
    public String pushDominoes(String dominoes) {
        //将字符串转化为字符数组
        char[] c = dominoes.toCharArray();
        //记录影响的时间
        int[] time =new int[dominoes.length()];
        Arrays.fill(time,-1);
        //记录每个点相对应所受的力的情况
        List<Character>[] forceList =new List[dominoes.length()];
        Queue<Integer> queue =new ArrayDeque<>();
        for(int i =0 ;i<dominoes.length();i++){
            List force =new ArrayList<Character>();
            forceList[i]=force;
            if(dominoes.charAt(i)!='.'){
                time[i] = 0;
                queue.offer(i);
                force.add(dominoes.charAt(i));
            }
        }
        char[] res = new char[dominoes.length()];
        Arrays.fill(res,'.');
        while(!queue.isEmpty()){
            int i = queue.poll();
            //只考虑受一个力的情况，因为受两个力则表示所受力平衡则不需要考虑这个节点
            if(forceList[i].size() ==1){
                char len = forceList[i].get(0);
                //如果当前是向左倒的话那个这个力将会影响其左边的模块
                int n = len == 'L' ? i-1:i+1;
                res[i]=len;
                //如果是初始节点的话那么就设置其受力状态
                if(n>=0 && n<dominoes.length() ){
                    if(time[n] == -1){
                        time[n] = time[i]+1;
                        forceList[n].add(len);
                        queue.add(n);
                    }else if(time[n] == time[i]+1){
                        forceList[n].add(len);
                    }

                }
            }
        }
        return new String(res);
    }
    /**
     * 题号：917
     * 难度：简单
     * 时间：20220223
     * 给你一个字符串 s ，根据下述规则反转字符串：
     *
     * 所有非英文字母保留在原有位置。
     * 所有英文字母（小写或大写）位置反转。
     * 返回反转后的 s 。
     */
    public String reverseOnlyLetters(String s) {
        int i =0, j =s.length()-1;
        char[] c =s.toCharArray();
        while(j>i){
            if(!((c[i]<='n' && c[i]>='a')||(c[i]>='A'&&c[i]<='Z'))){
                i++;
            }
            if(!((c[j]<='z' && c[j]>='a')||(c[j]>='A'&&c[j]<='Z'))){
                j--;
            }
            if(((c[i]<='z' && c[i]>='a')||(c[i]>='A'&&c[i]<='Z')) && ((c[j]<='z' && c[j]>='a')||(c[j]>='A'&&c[j]<='Z'))){
                char temp = c[i];
                c[i]=c[j];
                c[j]= temp;
                j--;
                i++;
            }

        }
        return new String(c);
    }
    /**
     * 时间：20220204
     * 难度：困难
     * 题号：1994
     *给你一个整数数组 nums 。如果 nums 的一个子集中，所有元素的乘积可以表示为一个或多个 互不相同的质数 的乘积，那么我们称它为 好子集 。
     *
     * 比方说，如果 nums = [1, 2, 3, 4] ：
     * [2, 3] ，[1, 2, 3] 和 [1, 3] 是 好 子集，乘积分别为 6 = 2*3 ，6 = 2*3 和 3 = 3 。
     * [1, 4] 和 [4] 不是 好 子集，因为乘积分别为 4 = 2*2 和 4 = 2*2 。
     * 请你返回 nums 中不同的 好 子集的数目对 109 + 7 取余 的结果。
     *
     * nums 中的 子集 是通过删除 nums 中一些（可能一个都不删除，也可能全部都删除）元素后剩余元素组成的数组。如果两个子集删除的下标不同，那么它们被视为不同的子集。
     * 分析：经过看了一下午的别人的解析，大概知道了该题怎么去解决，但是依然有个地方不太明白，就是
     */
    public int numberOfGoodSubsets(int[] nums) {
        int mod=(int)1e9+7;
        //30以内的质数是有限个的,且30以内的质数均在子集中只出现一次
        int[] prime ={2,3,5,7,11,13,17,19,23,29};
        //用于计算其中出现每个数字出现次数
        int[] cnt =new int[31];
        for(int i :nums){
            cnt[i]++;
        }

        //1000 0000 00 表示使用了prime[0]，其中dp[2^9]表示的是使用了2，之后所有好子集的个数
        int[] dp =new int[1<<10];
        dp[0]=1;
        //如果要判断该子集是否是好子集首先其中的数不能包含平方项系数
        for(int i =2 ;i<=30 ;i++){
            //进行试除，如果出现了平方次质数则表示该数不能加入好子集中
            int state =0;
            //该标记符表示的这个数是否能被一个质数的平方整除
            boolean flag =false;
            for(int j =0;j<prime.length;j++){
                if(i%(prime[j]*prime[j])==0){
                    flag=true;
                    break;
                }else if(i% prime[j] == 0){
                    state |= (1<<j);
                }
            }
            if(flag){
                continue;
            }
            //使用动态规划进行，好子集数累加
            for(int prev=(1<<10)-1;prev>=0;prev--){
                //确保prev不包含i所用的质数集
                if((prev & state) !=0){
                    continue;
                }
                //将i加入到好子集中所有情况进行累加
                dp[prev|state] =(dp[prev|state] +cnt[i]*dp[prev])%mod;
            }
        }
        long ans = 0;
        // 统计所有非空集的方案数
        for (int i = 1; i < (1<<10); i++){
            ans = (ans + dp[i]) % mod;
        }
        // 在此基础上，考虑每个 1 选择与否对答案的影响
        for (int i = 0; i < cnt[1]; i++){
            ans = ans * 2 % mod;
        }
        return (int)ans;
    }


}
