package com.lwc.round2;

import java.util.*;

public class DayNine {
    public static void main(String[] args) {
        DayNine dayNine=new DayNine();
        //System.out.println((int)'1');
        System.out.println(dayNine.platesBetweenCandles("**|**|***|",new int[][]{{2,5},{5,9}}));
    }
    /**
     * 题号：537
     * 难度：中等
     * 时间：20220225
     * 复数 可以用字符串表示，遵循 "实部+虚部i" 的形式，并满足下述条件：
     *
     * 实部 是一个整数，取值范围是 [-100, 100]
     * 虚部 也是一个整数，取值范围是 [-100, 100]
     * i2 == -1
     * 给你两个字符串表示的复数 num1 和 num2 ，请你遵循复数表示形式，返回表示它们乘积的字符串。
     */
    public String complexNumberMultiply(String num1, String num2) {
        String[] arg1 =num1.split("\\+");
        String[] arg2 =num2.split("\\+");
        int param1 =Integer.parseInt(arg1[0]);
        int param2 =Integer.parseInt(arg2[0]);
        String vParam = arg1[1].substring(0,arg1[1].length()-1);
        String vParam2= arg2[1].substring(0,arg2[1].length()-1);
        int paramI1=Integer.parseInt(vParam);
        int paramI2 = Integer.parseInt(vParam2);
        int res=param1*param2+(-paramI1*paramI2);
        int resI=paramI1*param2+paramI2*param1;
        return res+"+"+resI+"i";
    }
    /**
     * 题号：1706
     * 难度：中等
     * 时间：20220225
     * 用一个大小为 m x n 的二维网格 grid 表示一个箱子。你有 n 颗球。箱子的顶部和底部都是开着的。
     *
     * 箱子中的每个单元格都有一个对角线挡板，跨过单元格的两个角，可以将球导向左侧或者右侧。
     *
     * 将球导向右侧的挡板跨过左上角和右下角，在网格中用 1 表示。
     * 将球导向左侧的挡板跨过右上角和左下角，在网格中用 -1 表示。
     * 在箱子每一列的顶端各放一颗球。每颗球都可能卡在箱子里或从底部掉出来。如果球恰好卡在两块挡板之间的 "V" 形图案，或者被一块挡导向到箱子的任意一侧边上，就会卡住。
     *
     * 返回一个大小为 n 的数组 answer ，其中 answer[i] 是球放在顶部的第 i 列后从底部掉出来的那一列对应的下标，如果球卡在盒子里，则返回 -1 。
     *
     */
    public int[] findBall(int[][] grid) {
        return null;
    }

    /**
     * 给你一个下标从 0 开始的整数数组 nums ，该数组的大小为 n ，请你计算 nums[j] - nums[i] 能求得的 最大差值 ，其中 0 <= i < j < n 且 nums[i] < nums[j] 。
     *
     * 返回 最大差值 。如果不存在满足要求的 i 和 j ，返回 -1 。
     */
    public int maximumDifference(int[] nums) {
        int maxDiff =0;
        for(int i =0 ;i< nums.length;i++){
            for(int j =i+1;j<nums.length;j++){
                if(maxDiff< nums[j]-nums[i]){
                    maxDiff=nums[j]-nums[i];
                }
            }
        }
        return maxDiff>0? maxDiff:-1;
    }
    /**
     * 给定一组正整数，相邻的整数之间将会进行浮点除法操作。例如， [2,3,4] -> 2 / 3 / 4 。
     *
     * 但是，你可以在任意位置添加任意数目的括号，来改变算数的优先级。你需要找出怎么添加括号，才能得到最大的结果，并且返回相应的字符串格式的表达式。你的表达式不应该含有冗余的括号。
     */
    public String optimalDivision(int[] nums) {
        return null;
    }

    /**
     * 时间：20220302
     * 难度：困难
     * 题号：564
     * 给定一个表示整数的字符串 n ，返回与它最近的回文整数（不包括自身）。如果不止一个，返回较小的那个。
     *
     * “最近的”定义为两个整数差的绝对值最小。
     * 分析：该题目需要分情况进行讨论
     * 一.当n的长度为一时仅需将n的值减一即可
     * 二.当n的长度不为一时，先要判断整个字符是否是回文数字
     * 1.如果是回文数字：偶数的话将中间两位分别减一，奇数的话则将中间一位进行减一，同时需要注意的是1开头跟9开头的要考虑退位的9和进位的1
     * 所以在整个构造过程中跟据位数都留9..9和10...01的备选项进行选择
     * 2.如果不是回文字符串：则需要跟据字符串的长度的奇偶分成两部分，答案由前半部分进行反转所得到如果构造之后的数字大于原来的中间数字，
     * 如果是奇数位则中间的数加1小于则中间的数减一，如果是偶数的话则中间两位加一或者减一，当然得排除99 和11还有00
     * 解题：
     *
     */
    public String nearestPalindromic(String n) {
        int len = n.length();
        //n对应的数值
        long val = Long.parseLong(n);
        //如果长度为一的话直接返回数值减一对应的字符串
        if(len ==1 ){
            return String.valueOf(val-1);
        }
        //当长度大于二时,定义长度为10的数组用于选择备选项
        List<Long> res =new ArrayList<Long>();
        //对于长度大于等于二的数一定有两个备选项，一个位数减一全9，一个位数加一的100...01
       //全9的备选项
        res.add((long)Math.pow(10,len-1)-1);
        res.add((long)Math.pow(10,len)+1);
      //跟据是否是回文字符串进行分类处理
        if(isBack(n)){
            //如果是回文字符串，则先判断奇偶
            if(len%2==0){
                //如果为偶数，则中间两位减一或者加一
                if(n.charAt(len/2) !='0'){
                   long temp = val - (long)Math.pow(10,len/2)-(long)Math.pow(10,len/2-1);
                    res.add(temp);
                }
                if(n.charAt(len/2) !='9'){
                    long temp = val + (long)Math.pow(10,len/2)+(long)Math.pow(10,len/2-1);
                    res.add(temp);
                }

            }else{
                //如果为奇数位的长度，则中间的那一位加一或者减一
                if(n.charAt(len/2) != '0'){
                    long temp = val - (long)Math.pow(10,len/2);
                    res.add(temp);
                }
                if(n.charAt(len/2) != '9'){
                    long temp = val + (long)Math.pow(10,len/2);
                    res.add(temp);
                }
            }

        }else{
            //如果不是回文字符串时
            //如果为偶数时，则直接截取一半，末尾加减一进行字符串翻转即可,同样要注意0和9
            char[] temp = n.toCharArray();
            for(int i =0 ;i<len/2;i++){
                temp[len-i-1]=temp[i];
            }
            long lVal = Long.parseLong(new String(temp));
            res.add(lVal);
            String str =String.valueOf(lVal);
            //"1095445901"
            if(str.charAt(len/2) !='0'){
                if(len %2 == 0){
                    long temp2=lVal - (long)Math.pow(10,len/2)-(long)Math.pow(10,len/2-1);
                    res.add(temp2);
                }else{
                    long temp2 = lVal - (long)Math.pow(10,len/2);
                    res.add(temp2);
                }
            }
            if(str.charAt(len/2) !='9'){
                if(len %2 == 0){
                    long temp2=lVal + (long)Math.pow(10,len/2)+(long)Math.pow(10,len/2-1);
                    res.add(temp2);
                }else{
                    long temp2 = lVal + (long)Math.pow(10,len/2);
                    res.add(temp2);
                }
            }
        }
        //处理预选结果集
        res.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                //o2离val的值更近则排在前面
                if(Math.abs(o1-val)>Math.abs(o2-val)){
                    return 1;
                }else if(Math.abs(o1-val)==Math.abs(o2-val)){
                    //距离相等，小的在前面
                    return (int)(o1-o2);
                }else{
                    return -1;
                }
            }
        });
        return String.valueOf(res.get(0));
    }
    //判断是否是回文字符串
    public boolean isBack(String str){
        int len = str.length();
        int i=0,j=len-1;
        while(j>i){
            if(str.charAt(i) != str.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    /**
     * 时间：20220303
     * 难度：简单
     * 题号：258
     * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果
     */
    public int addDigits(int num) {
        String sb =String.valueOf(num);
        while(sb.length() != 1){
            int len =sb.length();
            int temp=0;
            for(int i =0 ;i<len;i++){
                temp+=Integer.parseInt(sb.charAt(i)+"");
            }
            sb=String.valueOf(temp);
        }
        return Integer.parseInt(sb);
    }
    public int addDigits2(int num) {
        int res=num;
        while(res >= 10){
            res=0;
            while(num>0){
                res +=num-(num/10)*10;
                num=num/10;
            }
            num=res;
        }
       return res;
    }
    /**
     * 时间：20220304
     * 题号：2104
     * 难度：中等
     * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
     * 返回 nums 中 所有 子数组范围的 和 。
     *
     * 子数组是数组中一个连续 非空 的元素序列。
     * 分析：可以采用滑动窗口对整个数组进行截取，然后对子数组获取最大最小值从而获取之间的差值，最后求和即可
     */
    public long subArrayRanges(int[] nums) {
        long res=0;
        int len = nums.length;
        //跟据长度对整个数组进行截取
        for(int i =0;i<len;i++){
            int maxVal = nums[i];
            int minVal = nums[i];
            for(int j =i+1;j<len;j++){
                maxVal=Math.max(nums[j],maxVal);
                minVal=Math.min(nums[j],minVal);
                res+=maxVal-minVal;
            }
        }
        return res;
    }
    //单调栈做法
    public long subArrayRanges2(int[] nums) {
        int n = nums.length;
        int[] minLeft = new int[n];
        int[] minRight = new int[n];
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];
        Deque<Integer> minStack = new ArrayDeque<Integer>();
        Deque<Integer> maxStack = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++) {
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);

            // 如果 nums[maxStack.peek()] == nums[i], 那么根据定义，
            // nums[maxStack.peek()] 逻辑上小于 nums[i]，因为 maxStack.peek() < i
            while (!maxStack.isEmpty() && nums[maxStack.peek()] <= nums[i]) {
                maxStack.pop();
            }
            maxLeft[i] = maxStack.isEmpty() ? -1 : maxStack.peek();
            maxStack.push(i);
        }
        minStack.clear();
        maxStack.clear();
        for (int i = n - 1; i >= 0; i--) {
            // 如果 nums[minStack.peek()] == nums[i], 那么根据定义，
            // nums[minStack.peek()] 逻辑上大于 nums[i]，因为 minStack.peek() > i
            while (!minStack.isEmpty() && nums[minStack.peek()] >= nums[i]) {
                minStack.pop();
            }
            minRight[i] = minStack.isEmpty() ? n : minStack.peek();
            minStack.push(i);

            while (!maxStack.isEmpty() && nums[maxStack.peek()] < nums[i]) {
                maxStack.pop();
            }
            maxRight[i] = maxStack.isEmpty() ? n : maxStack.peek();
            maxStack.push(i);
        }

        long sumMax = 0, sumMin = 0;
        for (int i = 0; i < n; i++) {
            sumMax += (long) (maxRight[i] - i) * (i - maxLeft[i]) * nums[i];
            sumMin += (long) (minRight[i] - i) * (i - minLeft[i]) * nums[i];
        }
        return sumMax - sumMin;
    }
    /**
     * 时间：20220305
     * 难度：简单
     * 题号：521
     * 给你两个字符串 a 和 b，请返回 这两个字符串中 最长的特殊序列  的长度。如果不存在，则返回 -1 。
     *
     * 「最长特殊序列」 定义如下：该序列为 某字符串独有的最长子序列（即不能是其他字符串的子序列） 。
     *
     * 字符串 s 的子序列是在从 s 中删除任意数量的字符后可以获得的字符串。
     *
     * 例如，"abc" 是 "aebdc" 的子序列，因为删除 "aebdc" 中斜体加粗的字符可以得到 "abc" 。 "aebdc" 的子序列还包括 "aebdc" 、 "aeb" 和 "" (空字符串)。
     */
    public int findLUSlength(String a, String b) {
        int len1 =a.length(),len2=b.length();
        return a.equals(b) ? -1:(len1>len2 ? len1:len2);
    }
    /**
     * 题号：2100
     * 难度：中等
     * 时间：20220306
     * 你和一群强盗准备打劫银行。给你一个下标从 0 开始的整数数组 security ，其中 security[i] 是第 i 天执勤警卫的数量。日子从 0 开始编号。同时给你一个整数 time 。
     *
     * 如果第 i 天满足以下所有条件，我们称它为一个适合打劫银行的日子：
     *
     * 第 i 天前和后都分别至少有 time 天。
     * 第 i 天前连续 time 天警卫数目都是非递增的。
     * 第 i 天后连续 time 天警卫数目都是非递减的。
     * 更正式的，第 i 天是一个合适打劫银行的日子当且仅当：security[i - time] >= security[i - time + 1] >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
     *
     * 请你返回一个数组，包含 所有 适合打劫银行的日子（下标从 0 开始）。返回的日子可以 任意 顺序排列。
     */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        List<Integer> res =new ArrayList<>();
        int len = security.length;
        //用于存放第i天前非递增的天数
        int[] before =new int[len];
        //用于存放第i天之后非递减的天数
        int[] after =new int[len];
        //首先找到第i天之前的非递增的天数，判断如下，如果se[i]>=se[i-1]则before[i]=before[i-1]+1
        for(int i =1 ;i<len ;i++){
            if(security[i]<=security[i-1]){
                before[i]=before[i-1]+1;
            }
        }
        //处理after的天数
        for(int i =len -2 ;i>=0;i--){
            if(security[i]<=security[i+1]){
                after[i]=after[i+1]+1;
            }
        }
        for(int i = 0;i<len ;i++){
            if(before[i]>=time && after[i]>=time){
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 时间：20220307
     * 难度：简单
     *
     * 给定一个整数 num，将其转化为 7 进制，并以字符串形式输出。
     * @param num
     * @return
     */
    public String convertToBase7(int num) {
        int base =7;
        int time =0;
        StringBuilder ans =new StringBuilder();
        if(num<0){
            ans.append("-");
        }else if(num == 0){
            return "0";
        }
        //除去符号
        num=Math.abs(num);
        //计算出最高次数
        while(num /(int)Math.pow(base,time)>0){
            time++;
        }
        time--;
        while(num>=0 && time >=0){
            int i = num/(int)Math.pow(base,time);
            ans.append(i);
            num=num-i*(int)Math.pow(base,time);
            time--;
        }
        return ans.toString();
}
    /**
     * 给你一个长桌子，桌子上盘子和蜡烛排成一列。给你一个下标从 0 开始的字符串 s ，它只包含字符 '*' 和 '|' ，其中 '*' 表示一个 盘子 ，'|' 表示一支 蜡烛 。
     *
     * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] 表示 子字符串 s[lefti...righti] （包含左右端点的字符）。对于每个查询，你需要找到 子字符串中 在 两支蜡烛之间 的盘子的 数目 。如果一个盘子在 子字符串中 左边和右边 都 至少有一支蜡烛，那么这个盘子满足在 两支蜡烛之间 。
     *
     * 比方说，s = "||**||**|*" ，查询 [3, 8] ，表示的是子字符串 "*||**|" 。子字符串中在两支蜡烛之间的盘子数目为 2 ，子字符串中右边两个盘子在它们左边和右边 都 至少有一支蜡烛。
     * 请你返回一个整数数组 answer ，其中 answer[i] 是第 i 个查询的答案。
     */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int len =s.length();
        int qLen =queries.length;
        //用于处理字符串将字符串处理成前缀和的形式
        int[] paseString = new int[len];
        int[] res =new int[qLen];
        int[] right =new int[len];
        int r=0;
        for(int i =0;i<len;i++){
            if(s.charAt(i) == '|'){
                right[i]=i;
                r=i;
            }else{
                right[i]=r;
            }
        }
        int[] left =new int[len];
        int l = len-1;
        for(int i = len-1 ;i>=0 ;i--){
            if(s.charAt(i) =='|'){
                left[i]=i;
                l=i;
            }else{
                left[i]=l;
            }
        }
        if(s.charAt(0) == '|'){
            paseString[0]=1;
        }
        for(int i =1 ;i<len;i++){
            //将柱子看成1，将盘子看成0
            if(s.charAt(i) ==  '|'){
                paseString[i]=paseString[i-1]+1;
            }else{
                paseString[i]=paseString[i-1];
            }
        }
        //先查找到最左边和最右边的蜡烛索引
        for(int i =0 ; i<qLen;i++){
            int rIndex= queries[i][1],lIndex=queries[i][0];
            if(right[rIndex]<=left[lIndex]){
                res[i]=0;
            }else{
               int diff= paseString[right[rIndex]] -paseString[left[lIndex]];
               res[i]=right[rIndex]-left[lIndex]-diff;
            }
        }
        return res;
    }
}
