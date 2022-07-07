package com.lwc.round4;

import com.lwc.round3.DaySeven;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/6/26 12:45
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DaySix {

    /**
     * 题号：1143
     * 难度：中等
     * 时间：20220627
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
     *
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     *
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
     * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int len1=text1.length(),len2=text2.length();
        //其中dp[i][j]表示的是text1(0-i)和text2(0-j)的最长公共子序列
        int[][] dp = new int[len1+1][len2+1];

        for(int i =1;i<=len1;i++){
            for(int j =1;j<=len2;j++){
                if(text1.charAt(i-1) == text2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;
                }else{
                    //当第i个字符和第j个字符不相同是则需要判断dp[i-1][j](可能使用j但是必然不适用i的值)
                    //dp[i][j-1](可能使用i但是必然不使用j的最大值)
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }
    /**
     * 题号：522
     * 难度：中等
     * 时间：20220627
     *给定字符串列表 strs ，返回其中 最长的特殊序列 。如果最长特殊序列不存在，返回 -1 。
     *
     * 特殊序列 定义如下：该序列为某字符串 独有的子序列（即不能是其他字符串的子序列）。
     *
     *  s 的 子序列可以通过删去字符串 s 中的某些字符实现。
     */
    HashMap<String,Integer> map = new HashMap<>();
    public int findLUSlength(String[] strs) {
        int ans =-1;
        for(String str :strs){
            dsf(str,0,new StringBuffer(),new TreeSet<String>());
        }
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            if(entry.getValue() == 1){
               ans = Math.max(entry.getKey().length(),ans);
            }
        }
        return ans;
    }
    //利用回溯枚举所有可能的子序列
    public void dsf(String str ,int i,StringBuffer sb,Set<String> set){
        if(i>str.length()){
            return;
        }
        String temp = sb.toString();
        if(!set.contains(temp) && sb.length()>0){
            set.add(temp);
            map.put(temp,map.getOrDefault(temp,0)+1);
        }
        for(int j =i;j<str.length();j++){
            sb.append(str.charAt(j));
            dsf(str,j+1,sb,set);
           sb.delete(sb.length()-1,sb.length());
        }
    }
    /**
     * 题号：324
     * 难度：中等
     * 时间：20220628
     * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
     *
     * 你可以假设所有输入数组都可以得到满足题目要求的结果。
     */
    public void wiggleSort(int[] nums) {
        int[] arr = nums.clone();

        int len = nums.length;
        if(len<3){
            Arrays.sort(nums);
            return;
        }

        //首先先将数组进行排序
        Arrays.sort(arr);
        //将数组分成两半
        int half=0;
        if(len%2 == 0){
            half=len/2;
        }else{
            half=len/2+1;
        }


        //将后半段数组进行反转
        int begin= half,end =len-1;
        while(end>begin){
            int temp = arr[begin];
            arr[begin]=arr[end];
            arr[end]=temp;
            end--;
            begin++;
        }
        //前半段也进行反转
        begin=0;
        end=half-1;
        while(end>begin){
            int temp = arr[begin];
            arr[begin]=arr[end];
            arr[end]=temp;
            end--;
            begin++;
        }
        for(int i =0,j=half,m=0;m<len;m++){
            if(m%2==0){
                nums[m]=arr[i];
                i++;
            }else{
                nums[m]=arr[j];
                j++;
            }


        }
    }
    //方法二快速选择，三路划分
    public int quickSelect(int l,int r,int pos,int[] nums){
        if(l == r){
            return nums[l];
        }
        int base = nums[l] ,i=l,j=r;

        while(j>i){
            while(j>i&&nums[j]>base){
                j--;
            }
           nums[i]= nums[j];
            while(i<j&&nums[i]<=base){
                i++;
            }
            nums[j]=nums[i];
        }
        nums[i]=base;
        int count = j-l+1;
        //如果左边的元素个数大于mid个数，则在左边找
        if(count>pos){
            return quickSelect(l,j-1,pos,nums);
        }else if(count<pos){
            return quickSelect(j+1,r,pos-count,nums);
        } else{
            return base;
        }
    }
   //三路划分
    public void threePartition(int mid ,int[] nums){
        int left =0,index =0 ,right=nums.length-1;
        while(right>index){
            if(nums[index]>mid){
                swap(nums[index],nums[right]);
                right--;
            }else if(nums[index]<mid){
                swap(nums[index],nums[left]);
                left++;
                index++;
            }else{
                index++;
            }
        }
    }
    public void swap(int i ,int j ){
        int temp = i;
        i=j;
        j=temp;
    }

    /**
     * 题号：1175
     * 难度：简单
     * 时间：20220630
     * 请你帮忙给从 1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上；你需要返回可能的方案总数。
     *
     * 让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数的乘积来表示。
     *
     * 由于答案可能会很大，所以请你返回答案 模 mod 10^9 + 7 之后的结果即可。
     */
    public int numPrimeArrangements(int n) {
        int mod =(int)1e9+7;
        long ansp =1,ansc=1;
        int countp=0,countc=0;
        for(int i =1;i<=n;i++){
            if(isPrime(i)){
                countp++;
              ansp=(ansp*countp);
              ansp%=mod;
            }else{
                countc++;
                ansc=ansc*countc;
                ansc%=mod;
            }
        }
        return (int)((ansp*ansc)%mod);
    }
    //判断是否是质数
    public boolean isPrime(int n){
        if(n == 1){
           return false;
        }
        int base = (int)Math.sqrt(n);
        for(int i =2;i<=base;i++){
            if(n%i == 0){
                return false;
            }
        }
        return true;
    }
    /**
     * 题号：241
     * 难度：中等
     * 时间：20220702
     * 给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，计算并返回所有可能组合的结果。你可以 按任意顺序 返回答案。
     *
     * 生成的测试用例满足其对应输出值符合 32 位整数范围，不同结果的数量不超过 104 。
     */

    public List<Integer> diffWaysToCompute(String expression) {
        return dfs(expression,0,expression.length());
    }
    public List<Integer> dfs(String str ,int begin,int end){
       String temp =  str.substring(begin,end);
       List<Integer> ans = new ArrayList<>();
       //纯数字
       if(!temp.contains("+") && !temp.contains("*") && !temp.contains("-")){
           ans.add(Integer.parseInt(temp));
           return ans;
       }
       //只有单个操作符
        if((temp.contains("+")&& temp.split("/+").length ==2)){
            String[] adds=temp.split("/+");
            ans.add(Integer.parseInt(adds[0])+Integer.parseInt(adds[1]));
            return ans;
        }
        if((temp.contains("*")&& temp.split("/*").length ==2)){
            String[] adds=temp.split("/-");
            ans.add(Integer.parseInt(adds[0])*Integer.parseInt(adds[1]));
            return ans;
        }
        if((temp.contains("-")&& temp.split("/-").length ==2)){
            String[] adds=temp.split("/-");
            ans.add(Integer.parseInt(adds[0])-Integer.parseInt(adds[1]));
            return ans;
        }

        for(int i = begin;i<end;i++){
            char c = str.charAt(i);
            if(c != '+' && c != '-' && c != '*'){
                continue;
            }
            List<Integer> temp1 = dfs(str,begin,i);
            List<Integer> temp2 =dfs(str,i+1,end);

            if(c == '+'){
                for(int j :temp1){
                    for(int k :temp2){
                        ans.add(j+k);
                    }
                }

            }else if(c == '*'){
                for(int j :temp1){
                    for(int k :temp2){
                        ans.add(j*k);
                    }
                }
            }else{
                for(int j :temp1){
                    for(int k :temp2){
                        ans.add(j-k);
                    }
                }
            }

        }
        return ans;
    }
    /**
     * 题号：871
     * 难度：困难
     * 时间：20220703
     * 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
     *
     * 沿途有加油站，每个 station[i] 代表一个加油站，它位于出发位置东面 station[i][0] 英里处，并且有 station[i][1] 升汽油。
     *
     * 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。它每行驶 1 英里就会用掉 1 升汽油。
     *
     * 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
     *
     * 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
     *
     * 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int len = stations.length;
        //其中dp[i]表示的是第i次加油所能达到的最大公里数
        int[] dp = new int[len+1];

        dp[0]=startFuel;
        //每到达第i个加油站时最多能加i+1次油
        for(int i =0;i<len;i++){
            //需要往前找到的是能到达第i个加油站所加的最多的油量
            for(int j=i+1;j>=0;j--){
                if(dp[j]>=stations[i][0]){
                    dp[j+1]=Math.max(dp[j+1],stations[i][1]+dp[j]);
                }
            }
        }
        int ans = -1;
        //找到能到达目的地的最小加油次数
        for(int j = len ;j>=0 ;j--){
            if(dp[j]>=target){
                ans =j;
            }
        }
        return ans;
    }
    //方法二优先队列
    public int minRefuelStops2(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b)->b-a);
        int ans = 0,len = stations.length,now=startFuel;
        for(int i =0 ;i<len ;i++ ){
            if(now>=stations[i][0]){
                queue.offer(stations[i][1]);
            }else{
                while(!queue.isEmpty() && now<stations[i][0]){
                    ans++;
                    now+=queue.poll();
                }

                if(now<stations[i][0] && queue.isEmpty()){
                    return -1;
                }
                queue.offer(stations[i][1]);
            }
        }
        while(!queue.isEmpty() && now<target){
            ans++;
            now+=queue.poll();
        }
        return now>=target? ans :-1;
    }

    /**
     * 题号：556
     * 难度：中等
     * 时间：20220704
     * 给你一个正整数 n ，请你找出符合条件的最小整数，其由重新排列 n 中存在的每位数字组成，并且其值大于 n 。如果不存在这样的正整数，则返回 -1 。
     *
     * 注意 ，返回的整数应当是一个 32 位整数 ，如果存在满足题意的答案，但不是 32 位整数 ，同样返回 -1 。
     */
    int basic=0,count = 0;
    long ans = Long.MAX_VALUE;
    public int nextGreaterElement(int n) {
        basic=n;
       Map<Integer,Integer> map =new HashMap<>();
        int temp = n;
        while(temp>0){
           map.put(temp%10,map.getOrDefault(temp%10,0)+1);
            temp/=10;
            count++;
        }
       dfs( map,0,0);
        return ans == Long.MAX_VALUE ? -1:(int)ans;
    }
    public void dfs(Map<Integer,Integer> map,int val,int tCount){
        if(count == tCount){
            if(val>basic){
                ans = Math.min(val,ans);
            }
            return;
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            int temp =(int)Math.pow(10,count-tCount-1);
           int i = (basic/temp)%10;
            int k = basic/temp;
            //进行剪枝
            if(entry.getValue() == 0 ||((basic/temp) == (val*10) && entry.getKey()<(basic/temp)%10)){
                continue;
            }
            map.put(entry.getKey(),entry.getValue()-1);
            dfs(map,val*10+entry.getKey(),tCount+1);
            map.put(entry.getKey(),entry.getValue()+1);
        }
    }
    //数学方法
    public int nextGreaterElement2(int n) {
        //获取数字的每一位
        List<Integer> list = new ArrayList<>();
        int temp = n;
        while(temp>0){
            list.add(temp%10);
            temp/=10;

        }
        //从后到前进行遍历找到第一个逆序的一对数
        //因为如果整个数都是逆序的话那么这个数必然是最大的
        int index =-1,size = list.size();
        for(int i = 0;i<size-1;i++){
           int first = list.get(i),second = list.get(i+1);
           //如果全是逆序的话
            if(i == size-2 && second >= first){
                return -1;
            }
            //找到第一个正序的位置
            if(second < first){
                index = i+1;
                break;
            }
        }
        //找到第一个正序的位置那么从i+1开始后面的都是逆序，从尾部找到第一个大于i位置的数进行交换
        //这样就可以保证增加的量最小
        int base = -1;
        for(int i = 0;i<index;i++){
            if(list.get(i)> list.get(index)){
                base = i;
                break;
            }
        }
        //再把这两个索引的数进行交换
        if(base != -1){
            swap(list,base,index);
        }
      /*  int tempVal = list.get(base);
        list.set(base,list.get(index));
        list.set(base,tempVal);*/
        //将index之后的数逆置，因为index之后的数一定为逆序，且逆序之后可以保证index之后组成的数最小
        //从而保证增量最小
        for(int i = 0,j=index-1;j>i;i++,j--){
            swap(list,i,j);
        }
        //组成答案
        long ans = 0;
        for(int i = size-1;i>=0;i--){
            ans = ans*10 + list.get(i);
        }
        if( ans >Integer.MAX_VALUE || ans == n){
            ans =-1;
        }
        return (int)ans;
    }
    //230412
    public void swap(List<Integer> list , int index1 ,int index2){
        int temp = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,temp);
    }
//堆排序算法，大根堆
    public void heapSort(int[] nums){
       int end = nums.length-1;
        heapBuild(nums,end);
       while(end>=0){
           //交换第一个跟最后一个数
           swap(nums,0,end);
           end--;
           heapify(nums,0,end);
       }
        System.out.println(nums);
    }
    public void heapBuild(int[] nums,int end){
        //堆排序,构建大根堆,end为最后一个需要建堆的节点，则其父节点就是(end-1)/2
        for(int i =(end-1)/2;i>=0;i--){
            heapify(nums,i,end);
        }
    }

    //建立大根堆进行排序
    public void heapify(int[] nums ,int root,int end){
        if(root>end){
            return;
        }
        //找到root对应的子节点
        int max= root,rightNode = root*2+1,leftNode = root*2+2;
        //找到最大的子节点
        if(rightNode <end+1 && nums[rightNode]>nums[max]){
            max = rightNode;
        }
        if(leftNode<end+1 && nums[leftNode]>nums[max]){
            max=leftNode;
        }
        //递归子节点进行建堆
        if(max != root ){
            swap(nums,max,root);
            heapify(nums,max,end);
        }
    }
    public void swap(int[] nums ,int index1,int index2){
        int temp = nums[index1];
        nums[index1]= nums[index2];
        nums[index2] =temp;
    }
    public static void main(String[] args) {
        DaySix six=new DaySix();
        int[] nums =   new int[]{1,4,3,4,1,2,1,3,1,3,2,3,3};
        six.heapSort(nums);
        System.out.println( );
    }
}
/**
 * 题号：710
 * 难度：困难
 * 时间：20220626
 * 给定一个整数 n 和一个 无重复 黑名单整数数组 blacklist 。设计一种算法，从 [0, n - 1] 范围内的任意整数中选取一个 未加入 黑名单 blacklist 的整数。任何在上述范围内且不在黑名单 blacklist 中的整数都应该有 同等的可能性 被返回。
 *
 * 优化你的算法，使它最小化调用语言 内置 随机函数的次数。
 *
 * 实现 Solution 类:
 */
class Solution {
    //前缀和
    int[] sum;
    List<int[]> pair;
    int size,n;
    Random random;
    public Solution(int n, int[] blacklist) {
        int len = blacklist.length;
        Arrays.sort(blacklist);
        this.n=n;
        sum=new int[len+2];
         if(blacklist.length !=0){
             //将有效的的选择空间进行离散化
             pair=new ArrayList<>();
             if(blacklist[0]>0){
                 size++;
                pair.add(new int[]{0,blacklist[0]-1});
             }
             for(int i = 1;i<len;i++){
                 if(blacklist[i-1]+1<=blacklist[i]-1){
                     size++;
                     pair.add(new int[]{blacklist[i-1]+1,blacklist[i]-1});
                 }
             }
             if(blacklist[len-1]<n-1){
                 size++;
                 pair.add(new int[]{blacklist[len-1]+1,n-1});
             }

             sum[0]=pair.get(0)[1]-pair.get(0)[0]+1;
             //计算前缀和
             for(int i =1;i<pair.size();i++){
                 sum[i]=sum[i-1]+pair.get(i)[1]-pair.get(i)[0]+1;
             }
         }

        random =new Random();
    }

    public int pick() {
        if(pair == null){
            return random.nextInt(n);
        }
        int temp = random.nextInt(sum[size-1])+1;
       int index = binaryFind(temp);
        int[] ans = pair.get(index);

      return random.nextInt(ans[1]-ans[0]+1)+ans[0];
    }
    public int binaryFind(int val){
        int left =0,right=size-1;
        while(right>left){
            int mid = left+right>>1;
            if(sum[mid]<val){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        return left;
    }


}
/**
 * 题号：535
 * 难度：中等
 * 时间：20220629
 * TinyURL 是一种 URL 简化服务， 比如：当你输入一个 URL https://leetcode.com/problems/design-tinyurl 时，它将返回一个简化的URL http://tinyurl.com/4e9iAk 。请你设计一个类来加密与解密 TinyURL 。
 *
 * 加密和解密算法如何设计和运作是没有限制的，你只需要保证一个 URL 可以被加密成一个 TinyURL ，并且这个 TinyURL 可以用解密方法恢复成原本的 URL 。
 *
 * 实现 Solution 类：
 */
class Codec {

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        return null;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return null;
    }
}
