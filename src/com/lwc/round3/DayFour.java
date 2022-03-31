package com.lwc.round3;

import java.util.*;

public class DayFour {

    /**
     * 题号：2028
     * 难度：中等
     * 时间：20220327
     * 现有一份 n + m 次投掷单个 六面 骰子的观测数据，骰子的每个面从 1 到 6 编号。观测数据中缺失了 n 份，你手上只拿到剩余 m 次投掷的数据。幸好你有之前计算过的这 n + m 次投掷数据的 平均值 。
     *
     * 给你一个长度为 m 的整数数组 rolls ，其中 rolls[i] 是第 i 次观测的值。同时给你两个整数 mean 和 n 。
     *
     * 返回一个长度为 n 的数组，包含所有缺失的观测数据，且满足这 n + m 次投掷的 平均值 是 mean 。如果存在多组符合要求的答案，只需要返回其中任意一组即可。如果不存在答案，返回一个空数组。
     *
     * k 个数字的 平均值 为这些数字求和后再除以 k 。
     *
     * 注意 mean 是一个整数，所以 n + m 次投掷的总和需要被 n + m 整除
     * 分析：该题可以通过回溯法进行穷举所有的可能性,回溯法需要进行重新学习
     */
    static boolean flag = true;
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int tempSum = 0;
        int sum = mean*(rolls.length+n);
        for(int i =0 ;i<rolls.length;i++){
            tempSum+=rolls[i];
        }
        int[] ans = new int[n];

        return ans;
    }

    public int[] missingRolls2(int[] rolls, int mean, int n) {
        int sum = mean *(rolls.length +n);
        //减去原来有的数据
        for(int i :rolls){
            sum-=i;
        }
        //因为取值在1到6之间所以对应的数据就在n到6n之间
        if(sum<n || sum> 6*n){
            return new int[0];
        }
        //将整个数分成n份，在后在加上剩余的部分就可以
        int base = sum/n,remain=sum%n;
        int[] ans = new int[n];
        for(int i = 0;i<n;i++){
            //要加上remain份1才可以与之前的数相同
            ans[i] = base +(remain>i?1:0);
        }
        return ans;
    }
    /**
     *  题号：693
     *  难度：简单
     *  时间：20220328
     * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
     */
    public boolean hasAlternatingBits(int n) {
        int temp = n;
        int j =(temp&1),i=((temp>>1)&1);
        temp = (temp>>1);
        while(temp>0){
            //取最后一个数字
           if(i == j){
               return false;
           }else{
               j=i;
               i=((temp>>1)&1);
               temp = (temp>>1);
           }
        }
        if(i != j ){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 题号：2024
     * 难度：中等
     * 时间：20220329
     * 一位老师正在出一场由 n 道判断题构成的考试，每道题的答案为 true （用 'T' 表示）或者 false （用 'F' 表示）。老师想增加学生对自己做出答案的不确定性，方法是 最大化 有 连续相同 结果的题数。（也就是连续出现 true 或者连续出现 false）。
     *
     * 给你一个字符串 answerKey ，其中 answerKey[i] 是第 i 个问题的正确结果。除此以外，还给你一个整数 k ，表示你能进行以下操作的最多次数：
     *
     * 每次操作中，将问题的正确答案改为 'T' 或者 'F' （也就是将 answerKey[i] 改为 'T' 或者 'F' ）。
     * 请你返回在不超过 k 次操作的情况下，最大 连续 'T' 或者 'F' 的数目。
     * 解题：该题自己本身不会写看完答案后理解了答案才知道怎么写了，本题采用了滑动窗口算法进行计算
     * 将题目转化为找整个字符串中包含少于k个T或F的最大窗口数，然后两进行比较求其最大值便时所求答案，具体解法如下
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        return Math.max(getMaxHatch(answerKey,k,'T'),getMaxHatch(answerKey,k,'F'));
    }
    public int getMaxHatch(String answerKey,int k ,char c){
        int ans = 0;
        int len = answerKey.length();
        for(int left =0 ,right =0,count =0;right<len;right++){
            //计算当前窗口包含字符c的个数
            if(answerKey.charAt(right) == c){
                count++;
            }
            //如果当中count大于k的话则需要减少窗口大小，从而减少包含字符c的个数
            while(count >k){
                //如果left对应的字符是c时则count减少
                if(answerKey.charAt(left) == c){
                    count--;
                }
                //当count>k时一直要减少窗口的大小
                left++;
            }
            //计算符合条件的窗口大小,并且保留最大窗口值
           ans = Math.max(ans,right-left+1);
        }
        return ans;
    }
    /**
     * 题号：1004
     * 难度：中等
     * 时间：20220329
     * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
     * 该题目与上述题目类型一致用于训练自己对滑动窗口的理解
     */
    public int longestOnes(int[] nums, int k) {
        int ans = 0;
        int len = nums.length;
        for(int left =0 ,count =0 ,right =0 ;right< len ;right++){
            if(nums[right] == 0 ){
                count++;
            }
            while(count>k){
                if(nums[left] == 0){
                    count--;
                }
                left++;
            }
           ans = Math.max(ans,right-left+1);
        }
        return ans;
    }
    /**
     * 题号：1606
     * 难度：困难
     * 时间：20220330
     *你有 k 个服务器，编号为 0 到 k-1 ，它们可以同时处理多个请求组。每个服务器有无穷的计算能力但是 不能同时处理超过一个请求 。请求分配到服务器的规则如下：
     *
     * 第 i （序号从 0 开始）个请求到达。
     * 如果所有服务器都已被占据，那么该请求被舍弃（完全不处理）。
     * 如果第 (i % k) 个服务器空闲，那么对应服务器会处理该请求。
     * 否则，将请求安排给下一个空闲的服务器（服务器构成一个环，必要的话可能从第 0 个服务器开始继续找下一个空闲的服务器）。比方说，如果第 i 个服务器在忙，那么会查看第 (i+1) 个服务器，第 (i+2) 个服务器等等。
     * 给你一个 严格递增 的正整数数组 arrival ，表示第 i 个任务的到达时间，和另一个数组 load ，其中 load[i] 表示第 i 个请求的工作量（也就是服务器完成它所需要的时间）。你的任务是找到 最繁忙的服务器 。最繁忙定义为一个服务器处理的请求数是所有服务器里最多的。
     *
     * 请你返回包含所有 最繁忙服务器 序号的列表，你可以以任意顺序返回这个列表。
     * 分析：该题目可以采用优先队列进行解题，每次都使用最早结束的时间来进行处理最近的一个任务
     * 即可
     */
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        List<Integer> ans = new ArrayList<>();
        int len = arrival.length;
        //保存整个数组中的所有的服务数量
        int[] counts = new int[k];
        //定义一个优先队列其中元素是一个二维数组a[0]表示的是编号a[1]表示的是任务结束时间
        Queue<int[]> queue = new PriorityQueue<int[]>((a,b)->{return a[1]-b[1];});
        //定义一个treeset排序集合，用于存放空闲服务器，并且找到最接近i%k的服务器
        TreeSet<Integer> free = new TreeSet<>();
        //初始时所有的服务器均是空闲的
        for(int i =0 ;i<k;i++){
            free.add(i);
        }
        //用于比较最大的任务数量
        int maxCount = 0;
        for(int i =0;i<len ;i++){
            //判断优先队列中时间最小的元素值，如果没有超过任务的到达时间则加到空闲集合中去
            while(!queue.isEmpty() && queue.peek()[1]<=arrival[i]){
                free.add(queue.poll()[0]);
            }
            //如果没有空闲的服务器则放弃该服务
            if(free.size()==0){
                continue;
            }
            //找到可以进行服务的服务器
           Integer server =  free.ceiling(i%k);
            //如果找不到i%k的服务器则需要重新从0开始进行查找可以服务的服务器
            if(server == null){
                server = free.ceiling(0);
            }
            //对应服务器服务的数量增加
            counts[server]++;
            if(counts[server]>maxCount){
                ans.clear();
                ans.add(server);
                maxCount=counts[server];
            }else if(counts[server] == maxCount){
                ans.add(server);
            }
            //将其从空闲集合中移除
            free.remove(server);
            //加入到优先队列中
            queue.add(new int[]{server,arrival[i]+load[i]});

        }
        return ans;
    }
    /**
     * 题号：728
     * 难度：简单
     * 时间：20220331
     * 自除数 是指可以被它包含的每一位数整除的数。
     *
     * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
     * 自除数 不允许包含 0 。
     *
     * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right] 内所有的 自除数
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        for(int i = left ;i<=right;i++){
            String str = i+"";
            char[] chars = str.toCharArray();
            int count = 0;
            for(char c :chars){
                int j=Integer.parseInt(c+"");
                if(j != 0){
                    if(i%j == 0){
                        count++;
                        if(count==chars.length){
                            ans.add(i);
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 求出大于或等于 N 的最小回文素数。
     *
     * 回顾一下，如果一个数大于 1，且其因数只有 1 和它自身，那么这个数是素数。
     *
     * 例如，2，3，5，7，11 以及 13 是素数。
     *
     * 回顾一下，如果一个数从左往右读与从右往左读是一样的，那么这个数是回文数。
     *
     * 例如，12321 是回文数。
     */
    public int primePalindrome(int n) {
        double MAX_VAL = 2*1e9;
        int temp = n+1;
        while(temp<MAX_VAL){
            if(isBack(temp) && isPrime(temp)){
                return temp;
            }else{
                temp++;
            }
        }
        return 0;
    }
    public boolean isPrime(int num){
        if(num == 1){
            return false;
        }
        int tmep =(int) Math.sqrt(num);
        for(int i =2;i<=tmep ;i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }
    public boolean isBack(int num){
        String str = num+"";
        char[] chars =str.toCharArray();
        int j=0,i=chars.length-1;
        while(i>j){
            if(chars[i] == chars[j]){
                i--;
                j++;
            }else{
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        DayFour four =new DayFour();
        System.out.println(four.primePalindrome(9989900));
    }
}
