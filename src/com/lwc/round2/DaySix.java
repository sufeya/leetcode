package com.lwc.round2;

import java.util.*;

public class DaySix {
    /**
     * 难度：简单
     * 题号：507
     * 时间：20211231
     * 对于一个 正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
     *
     * 给定一个 整数 n， 如果是完美数，返回 true，否则返回 false
     * 分析：也就是找到所有num的因数然后所有的因数之和为num时就表明该数为完美数，所以可以采用枚举法枚举根号num下的所有数
     * 同时如果根号num如果为整数时他枚举可能会重复使用
     * 解题：
     */
    public boolean checkPerfectNumber(int num) {
        if(num == 1){
            return false;
        }
        int d =(int)Math.sqrt(num);
        int sum =0;
        for(int i=2 ;i<=d ;i++){
            if(i*(num/i)==num){
                if(i==num/i){
                    sum =sum +i;
                }else{
                    sum = sum+ i+num/i;
                }
            }
        }
        sum++;
        if(sum == num){
            return true;
        }
        return false;
    }

    /**
     *时间：20220110
     * 难度：中等
     * 题号：306
     累加数 是一个字符串，组成它的数字可以形成累加序列。

     一个有效的 累加序列 必须 至少 包含 3 个数。除了最开始的两个数以外，字符串中的其他数都等于它之前两个数相加的和。

     给你一个只包含数字 '0'-'9' 的字符串，编写一个算法来判断给定输入是否是 累加数 。如果是，返回 true ；否则，返回 false 。

     说明：累加序列里的数 不会 以 0 开头，所以不会出现 1, 2, 03 或者 1, 02, 3 的情况。
     分析：该题应当采取回溯法对整个字符序列进行遍历生成，看完了别人的解析现在自己对别人解析的理解，可以将整个字符串当成一个
     子问题进行迭代解决，也就是找到三个数进行比较比较重复这三个过程即可，关键是怎么进行寻找确定这几个数，怎么在刚开始的时候确定
     第一个数这个就采用了回溯算法也就是每一个字符位进行判断然后在进行深度搜索如果符合则接着进行探索下面的单词，如果不符合则
     在增加一个进行判断，同时要对整个过程进行剪枝从而减少判断情况
     解题：
     */
    public boolean isAdditiveNumber(String num) {
        return dfs(num,0,0,0,0);
    }
    public boolean dfs(String num ,long first,int count,int index,long second){
        if (index >= num.length()) {
            return count > 2;
        }
        int len = num.length();
        long current =0;
        //从已经选取的下一个字符进行判断
        for(int i =index ;i < len ;i++){
            char c =num.charAt(i);
            //要出去以0为开头的数字,所有情况
            if(num.charAt(index) == '0' && i> index){
                return false;
            }
            //加上本轮所产生的数字
            current = current*10+c-'0';
            //如果现在已经产生了两个以上的数字则进行判断当前的数字是否符合要求
            if(count>=2){
              long sum =first +second;
              //对结果进行剪枝，如果sum要小于current则不必接着遍历
              if(sum<current){
                  return false;
              }
              //如果current要小于sum则还需要接着添加后面的数字扩大
              if(sum>current){
                  continue;
              }
            }
            //如果进行深度遍历的过程中其中的某一步不符合要求则直接返回
            if(dfs(num,second,count+1,i+1,current)){
                return true;
            }
        }
        return false;
    }

    /**
     * 时间：20220111
     * 题号：1036
     * 难度：困难
     * 在一个 106 x 106 的网格中，每个网格上方格的坐标为 (x, y) 。
     *
     * 现在从源方格 source = [sx, sy] 开始出发，意图赶往目标方格 target = [tx, ty] 。数组 blocked 是封锁的方格列表，其中每个 blocked[i] = [xi, yi] 表示坐标为 (xi, yi) 的方格是禁止通行的。
     *
     * 每次移动，都可以走到网格中在四个方向上相邻的方格，只要该方格 不 在给出的封锁列表 blocked 上。同时，不允许走出网格。
     *
     * 只有在可以通过一系列的移动从源方格 source 到达目标方格 target 时才返回 true。否则，返回 false。
     * 分析：可以采用递归的手段对问题进行解决可以把每一个当成一个源点进行寻找路径比如说下一步可能有四个方向那么便以下某个可行的
     * 方向作为起始节点为源点进行遍历，同时要设置递归终止条件
     * 1：超出范围的直接返回false
     * 2:达到节点的直接返回true
     * 解题：
     */
    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        if(source[0] == target[0] && source[1] == target[1]){
            return true;
        }
        if(source[0]>1000000 || source[1]> 1000000 || source[0]<0 || source[1]<0){
            return false;
        }
        int[] tag =new int[4];
        for(int[] block:blocked){
            if(block[0] == source[0]+1 && block[1] ==source[1]){
                tag[0]=1;
            }
            if(block[0] == source[0] && block[1] ==source[1]+1){
                tag[1]=1;
            }
            if(block[0] == source[0]-1 && block[1] ==source[1]){
                tag[2]=1;
            }
            if(block[0] == source[0] && block[1] ==source[1]-1){
                tag[3]=1;
            }
        }
        if(tag[0] !=1){
            return isEscapePossible(blocked,new int[]{source[0]+1,source[1]},target);
        }
        if(tag[1] !=1){
            return isEscapePossible(blocked,new int[]{source[0],source[1]+1},target);
        }
        if(tag[2] !=1){
            return isEscapePossible(blocked,new int[]{source[0]-1,source[1]},target);
        }
        if(tag[3] !=1){
            return isEscapePossible(blocked,new int[]{source[0],source[1]-1},target);
        }
        return false;
    }

    /**
     *
     * @param blocked
     * @param source
     * @param target
     * @return
     * 跟据答案解析可以知道如果通过广度优先遍历对整个表格进行遍历的的话那么其时间远远会超过其限制，由题目中
     * 的条件我们可以知道障碍物的数量远远小于可以没有障碍物的数量不妨设障碍物的数量为n当如果要用n个节点将
     * 源点或者目标点进行包围的话那么能包围的最大面积的数量为n*(n-1)/2个单位数量如果从源点出发能够访问到的
     * 格子数量要大于其能包围的最大面积那么目标点和源点之间便是可达的，如果小于该限制那么说明该源点/目标点
     * 已经被包围，在进行查找过程中，我们可以对其间访问的点进行离散化（也就是进行hash化从而降低时间复杂度）
     */
    int ArraySize =1000000;
    public boolean isEscapePossible2(int[][] blocked, int[] source, int[] target) {

        int size = blocked.length;
        int maxSize = (size+2)*(size+1)/2;
        Set<String> blocks =new HashSet<>();
        //将阻碍节点离散化
        for(int[] block : blocked){
            blocks.add(block[0]+"_"+block[1]);
        }

        return bfs(source,maxSize,blocks,target) && bfs(target,maxSize,blocks,source);
    }

    //对整个源点进行广度优先遍历
    public boolean bfs(int[] orgin,int maxSize,Set block,int[] target){
        //以源点为范围找出周围的四个点
        int[][] bounds =new int[][]{{0,1},{1,0},{-1,0},{0,-1}};
        //广度遍历队列,利用循环队列
        Queue<String> queue =new ArrayDeque<>();
        //将源点进行hash化
        queue.offer(orgin[0]+"_"+orgin[1]);
        //存储被访问的节点
        Set<String> visited =new HashSet<>();
        while(!queue.isEmpty() && visited.size() <= maxSize){
            //出队
            String temp = queue.poll();
            String[] tempPoint=temp.split("_");
            long x = Long.parseLong(tempPoint[0]);
            long y = Long.parseLong(tempPoint[1]);

            //如果访问过，则进入下一轮遍历
            if(visited.contains(temp)){
                continue;
            }
            //如果这个超过边界，或小于零
            if(x>=ArraySize || y>=ArraySize ||x<0 || y<0 || block.contains(temp)){
               continue;
            }
            //如果达到源点，或者达到起始点则返回
            if( x == target[0] && y == target[1]){
                return true;
            }
            visited.add(temp);
            for(int[] bound : bounds){
                long tempX =x+bound[0];
                long tempY =y+bound[1];
                queue.offer(tempX+"_"+tempY);
            }
        }
        return visited.size()>maxSize;
    }

    /**
     * 时间：20220114
     * 难度：中等
     * 题号：373
     * 给定两个以升序排列的整数数组 nums1 和 nums2 , 以及一个整数 k 。
     *
     * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
     *
     * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
     * 分析：在两个数组中找到相加和最小的k对数，可以采用双重循环进行暴力解题，其中数字可以重复使用，
     * 但是其数字对不可以重复，可以在上一个的基础上进行修改，首先获取上一次键值对所对应的两个数字将其中一个数字
     * 采用二分查找法进行替换，可能有两种情况替换第一个和替换第二个，当列表中没有元素时则分别找到两个数组元素中
     * 最小的即可
     * 解题：
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length-1;
        int len2 = nums2.length-1;
        Set<String> set =new HashSet<>();
        //其中dp[i]表示的是能查找到的第i小的数对
        String[] dp =new String[k];
        String[] indexDp =new String[k];
        //对dp数组进行初始话
        dp[0]=nums1[0]+"_"+nums2[0];
        set.add("0_0");
        indexDp[0]="0_0";
        List<List<Integer>> res =new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(nums1[0]);
        list.add(nums2[0]);
        res.add(list);
        for(int i =1;i<k;i++){
            String str2 =indexDp[i-1];
            String[] temp =str2.split("_");
            int first =Integer.parseInt(temp[0]);
            int second = Integer.parseInt(temp[1]);
            //这里找到下标在加一可能会导致下标越界

            int trueIndex1 =first+1>len1 ? first:first+1;
            int trueIndex2 =second+1>len2 ? second:second+1;
            if(set.contains(trueIndex1+"_"+second) && !set.contains(first+"_"+trueIndex2)) {
                List<Integer> tempList =new ArrayList<>();
                tempList.add(nums1[first]);
                tempList.add(nums2[trueIndex2]);
                indexDp[i]=first+"_"+trueIndex2;
                set.add(first+"_"+trueIndex2);
                res.add(tempList);
            }else if(!set.contains(trueIndex1+"_"+second) && set.contains(first+"_"+trueIndex2)){
                List<Integer> tempList =new ArrayList<>();
                tempList.add(nums1[trueIndex1]);
                tempList.add(nums2[second]);
                indexDp[i]=trueIndex1+"_"+second;
                set.add(trueIndex1+"_"+second);
                res.add(tempList);
            }else if(!set.contains(trueIndex1+"_"+second) && !set.contains(first+"_"+trueIndex2)){
                if(nums1[trueIndex1]+nums2[second]>nums2[trueIndex2]+nums1[first] ){
                    List<Integer> tempList =new ArrayList<>();
                    tempList.add(nums1[first]);
                    tempList.add(nums2[trueIndex2]);
                    indexDp[i]=first+"_"+trueIndex2;
                    set.add(first+"_"+trueIndex2);
                    res.add(tempList);
                }else if(nums1[trueIndex1]+nums2[second] <= nums2[trueIndex2]+nums1[first] ){
                    List<Integer> tempList =new ArrayList<>();
                    tempList.add(nums1[trueIndex1]);
                    tempList.add(nums2[second]);
                    indexDp[i]=trueIndex1+"_"+second;
                    set.add(trueIndex1+"_"+second);
                    res.add(tempList);
                }
            }else{
                //如果两个均包含
                dp[i]=trueIndex1+"_"+second;
            }

        }
        return res;
    }
    //二分查找
    public int findIndex(int[] nums ,int target){
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int num = nums[mid];
            if (num == target) {
                if(mid == 0 || nums[mid-1] !=target){
                    return mid;
                }else{
                    high=mid-1;
                }
            } else if (num > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 看完别人解析后自己的理解，首先对两个有序序列其中所有数对的大小必然在nums1[0]+nums2[0]到nums1[len1-1]+nums2[len2-1]的有序数轴之间
     * 所以我们需要找到的是整个数对中第k小的数，所以可以使用二分查找的方法进行，使用一个计数值对数对进行计数当小于mid值是则计数值相应的增加
     * 在找到第k小的值pairSum之后因为真正存在的数值对是小于等于pairSum的，所以应当先找到所有的小于pairSum的数，但是有可能等于pairSum的数
     * 与小于pairSum的数之和数量可能大于k个
     */
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int right = nums1[len1-1]+nums2[len2-1],left = nums1[0]+nums2[0];
        int pairSum =right;
        List<List<Integer>> res =new ArrayList<>();

        //利用二分法查找第k小的数
        while(right>=left){
            int mid = left+((right-left)>>1);
            int countK=0;
           int start = 0;
           int end = len2-1;
           while(start <len1 && end>=0){
               if(nums1[start]+nums2[end]<=mid){
                   countK=countK+end+1;
                   start++;
               }else{
                   end--;
               }
           }
           if(countK>k){
               pairSum=mid;
               right=mid-1;
           }else{
               left =mid+1;
           }
        }
        //找到小于pairSum的所有数对
        int m =len2-1;
        for(int i =0 ;i<len1;i++){
            while(m>=0 && nums1[i]+nums2[m]>=pairSum){
                m--;
            }
            for(int j =m ; j>=0 && k>0 ;j--,k--){
                List<Integer> temp =new ArrayList<>();
                temp.add(nums1[i]);
                temp.add(nums2[j]);
                res.add(temp);
            }
        }
       m =len2-1;
        for (int i =0 ;i<len1 && k>0 ;i++){
             while(m >= 0 && nums1[i] + nums2[m] > pairSum){
                 m--;
             }

             for(int j =i ;k>0 && j>=0 && nums1[j]+nums2[m]== pairSum ;k--,j--){
                 List<Integer> temp =new ArrayList<>();
                 temp.add(nums1[j]);
                 temp.add(nums2[m]);
                 res.add(temp);
             }
        }
        return res;
    }
    public List<List<Integer>> kSmallestPairs3(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;

        /*二分查找第 k 小的数对和的大小*/
        int left = nums1[0] + nums2[0];
        int right = nums1[m - 1] + nums2[n - 1];
        int pairSum = right;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            long cnt = 0;
            int start = 0;
            int end = n - 1;
            while (start < m && end >= 0) {
                if (nums1[start] + nums2[end] > mid) {
                    end--;
                } else {
                    cnt += end + 1;
                    start++;
                }
            }
            if (cnt < k) {
                left = mid + 1;
            } else {
                pairSum = mid;
                right = mid - 1;
            }
        }

        List<List<Integer>> ans = new ArrayList<>();
        int pos = n - 1;
        /*找到小于目标值 pairSum 的数对*/
        for (int i = 0; i < m; i++) {
            while (pos >= 0 && nums1[i] + nums2[pos] >= pairSum) {
                pos--;
            }
            for (int j = 0; j <= pos && k > 0; j++, k--) {
                List<Integer> list = new ArrayList<>();
                list.add(nums1[i]);
                list.add(nums2[j]);
                ans.add(list);
            }
        }

        /*找到等于目标值 pairSum 的数对*/
        pos = n - 1;
        for (int i = 0; i < m && k > 0; i++) {
            while (pos >= 0 && nums1[i] + nums2[pos] > pairSum) {
                pos--;
            }
            for (int j = i; k > 0 && j >= 0 && nums1[j] + nums2[pos] == pairSum; j--, k--) {
                List<Integer> list = new ArrayList<>();
                list.add(nums1[j]);
                list.add(nums2[pos]);
                ans.add(list);
            }
        }
        return ans;
    }

    /**
     * 题号：219
     * 难度：简单
     * 时间：2022.01.19
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int len = nums.length;

        for(int i =0 ;i<len-1 ;i++){
            for(int j = i+1 ; j<len ;j++){
                if(nums[i] == nums[j] && j-i <= k){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        int len = nums.length;
        Set<Integer> set =new HashSet<>();
        for(int i = 0 ;i<len ;i++){
            if(i > k){
                set.remove(nums[i-k-1]);
            }
            if(set.contains(nums[i])){
                return true;
            }else{
                set.add(nums[i]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        DaySix six=new DaySix();
        int[] temp =new int[]{5,8,12,60,81,93};
        System.out.println(six.containsNearbyDuplicate2(new int[]{1,2,3,1,2,3}, 2));
    }
}
