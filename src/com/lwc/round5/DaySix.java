package com.lwc.round5;

import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/10/15 21:28
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DaySix {
    /**
     * 题号：1141
     * 难度：中等
     * 时间：20221015
     * 给你一个数组 target 和一个整数 n。每次迭代，需要从  list = { 1 , 2 , 3 ..., n } 中依次读取一个数字。
     * <p>
     * 请使用下述操作来构建目标数组 target ：
     * <p>
     * "Push"：从 list 中读取一个新元素， 并将其推入数组中。
     * "Pop"：删除数组中的最后一个元素。
     * 如果目标数组构建完成，就停止读取更多元素。
     * 题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
     * <p>
     * 请返回构建目标数组所用的操作序列。如果存在多个可行方案，返回任一即可。
     *
     * @param target
     * @param n
     * @return
     */
    public List<String> buildArray(int[] target, int n) {

        String[] strs = new String[]{"Push", "Pop"};

        List<String> ans = new ArrayList<>();
        //利用双指针对数组进行遍历,point为指针
        int len = target.length, point = 1;
        for (int i = 0; i < len && point <= n; i++) {

            //当指针没有到达该元素时直接跳过，并且在ans中加入push ,pop
            while (point < target[i]) {
                ans.add(strs[0]);
                ans.add(strs[1]);
                point++;
            }
            //到达相等的元素时则直接加入
            ans.add(strs[0]);
            point++;
        }
        return ans;
    }

    /**
     * 题号：886
     * 难度：中等
     * 时间：20221016
     * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
     * <p>
     * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        Set<Integer> col1 = new HashSet<>();
        Set<Integer> col2 = new HashSet<>();
        for (int[] dislike : dislikes) {
            boolean res1 = col1.contains(dislike[0]);
            boolean res2 = col2.contains(dislike[0]);
            boolean res3 = col1.contains(dislike[1]);
            boolean res4 = col2.contains(dislike[1]);
            if (!res1 && !res4) {
                col1.add(dislike[1]);
                col2.add(dislike[0]);
            } else if (!res2 && !res3) {
                col2.add(dislike[1]);
                col1.add(dislike[0]);
            } else {
                return false;
            }
        }
        return true;
    }

    //上诉解答有误，现在使用并查集的方式来解答
    public boolean possibleBipartition2(int n, int[][] dislikes) {
        int[] father = new int[n + 1];
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        //初始话父节点，将所有的父节点都指向-1
        Arrays.fill(father, -1);

        //构建图
        for (int[] temp : dislikes) {
            graph[temp[0]].add(temp[1]);
            graph[temp[1]].add(temp[0]);
        }

        //遍历所有的集合
        for (int i = 1; i <= n; i++) {
            List<Integer> list = graph[i];

            //对于i不喜欢的一定在同一集合里面，因为只有两个集合
            for (int j = 0; j < list.size(); j++) {
                union(list.get(0), list.get(j), father);
                //判断合并了之后是否与不喜欢的人相连
                if (isConnected(i, list.get(0), father)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void union(int x, int y, int[] parent) {
        int px = findParent(x, parent), py = findParent(y, parent);
        if (px == py) {
            return;
        }
        //合并
        parent[x] = y;
    }

    //判断是否连接
    public boolean isConnected(int x, int y, int[] father) {
        int fx = findParent(x, father);
        int fy = findParent(y, father);
        if (fx == fy) {
            return true;
        }
        return false;
    }

    public int findParent(int x, int[] parent) {
        if (parent[x] < 0) {
            return x;
        }
        return findParent(parent[x], parent);
    }

    /**
     * 题号：904
     * 难度：中等
     * 时间：
     * 你正在探访一家农场，农场从左到右种植了一排果树。这些树用一个整数数组 fruits 表示，其中 fruits[i] 是第 i 棵树上的水果 种类 。
     * <p>
     * 你想要尽可能多地收集水果。然而，农场的主人设定了一些严格的规矩，你必须按照要求采摘水果：
     * <p>
     * 你只有 两个 篮子，并且每个篮子只能装 单一类型 的水果。每个篮子能够装的水果总量没有限制。
     * 你可以选择任意一棵树开始采摘，你必须从 每棵 树（包括开始采摘的树）上 恰好摘一个水果 。采摘的水果应当符合篮子中的水果类型。每采摘一次，你将会向右移动到下一棵树，并继续采摘。
     * 一旦你走到某棵树前，但水果不符合篮子的水果类型，那么就必须停止采摘。
     * 给你一个整数数组 fruits ，返回你可以收集的水果的 最大 数目。
     */
    public int totalFruit(int[] fruits) {
        int i = 0, j = 0, len = fruits.length, ans = 0;
        Map<Integer, Integer> map = new HashMap<>();

        //利用滑动窗口来判断窗口中种类数为二的最大窗口大小
        while (i < len && j < len) {
            map.put(fruits[j], map.getOrDefault(fruits[j], 0) + 1);
            while (map.size() > 2) {
                int temp = map.get(fruits[i]) - 1;
                if (temp == 0) {
                    map.remove(fruits[i]);
                } else {
                    map.put(fruits[i], temp);
                }
                i++;
            }
            ans = Math.max(j - i + 1, ans);
            j++;
        }
        return ans;
    }

    /**
     * 题号：902
     * 难度：困难
     * 时间：20221018
     * 给定一个按 非递减顺序 排列的数字数组 digits 。你可以用任意次数 digits[i] 来写的数字。例如，如果 digits = ['1','3','5']，我们可以写数字，如 '13', '551', 和 '1351315'。
     * <p>
     * 返回 可以生成的小于或等于给定整数 n 的正整数的个数 。
     */
    public int atMostNGivenDigitSet(String[] digits, int n) {
        //先计算出n的位数
        int count = 0, temp = n, ans = 0, length = digits.length;
        //用于保存除数出来得数，方便用于剪枝
        Stack<Integer> stack = new Stack<>();
        while (temp > 0) {
            stack.push(temp % 10);
            temp /= 10;
            count++;
        }
        int[] dit = new int[count];
        int j = 0;
        while (!stack.isEmpty() && j < count) {
            dit[j] = stack.pop();
            j++;
        }
        //如果只有一位数的话
        if (count == 1) {
            for (String str : digits) {
                int i = Integer.parseInt(str);
                if (i <= n) {
                    ans++;
                }
            }
            return ans;
        }

        //如果是多位数

        //先计算位数比n小的所有的个数
        int tempSum = length;
        for (int i = 1; i < count; i++) {
            ans += tempSum;
            tempSum *= length;
        }

        //再计算位数与n相同并且小于等于位数的值
        //利用广度优先遍历进行枚举
        Queue<Integer> queue = new ArrayDeque<>();
        //利用set对遍历进行剪枝
        Set<Integer> set = new HashSet<>();
        for (String str : digits) {
            int i = Integer.parseInt(str);
            if (i <= dit[0]) {
                queue.offer(i);
            }
        }
        int step = 1;
        while (!queue.isEmpty() && step<count) {
            int size = queue.size();
            //生成当前位数得值进行剪枝
            int val = 0;
            for (int k = 0; k <= step  && k < count; k++) {
                val = val * 10 + dit[k];
            }
            while (size > 0) {
                int target = queue.poll();
                for(String str : digits){
                    int i = Integer.parseInt(str);
                    int tet = target*10+i;
                    if(tet<= val && !set.contains(tet)){
                        queue.offer(tet);
                        set.add(tet);
                    }
                }
                size--;
            }
            step++;
        }
        ans += queue.size();
        return ans;
    }
    //上诉解法超时下面采用动态规划得解法进行解题
    public int atMostNGivenDigitSet2(String[] digits, int n) {
        //将数字转换为字符
        String nums = n+"";
        int len = nums.length(),len2 = digits.length;
        //其中dp[i][0]表示得是nums由0-i位组成得数字，小于该数字的个数， dp[i][1]表示的是与之相等的个数
        int[][] dp = new int[len][2];
        dp[0][1] = 1;
        for(int i = 1;i<len ;i++){
            for(int j = 0;j<len2;j++){
                //如果nums的第i个字符与digits[j]字符相同，则说明可以构成相等数字
                if(nums.charAt(i) == digits[j].charAt(0)){
                    dp[i][1] = dp[i-1][1];
                }else if(nums.charAt(i)<digits[j].charAt(0)){
                    //如果小于则可以跟前面相等的数组构成一个小于nums的数字
                    dp[i][0] += dp[i-1][1];
                }else{
                    break;
                }
            }
            if(i>1){
                //其中dp[i][0]表示的是digits中小于nums[i]的个数
                //len2表示的是单个字符的也包含在里面，因为就上面算dp[i][0]的算法，只有一位数字并没有包含在里面
                dp[i][0] = dp[i-1][0]*len2+dp[i][0]+len2;
            }
        }
        return 0;
    }

    /**
     * 题号：1235
     * 难度：困难
     * 时间：20221022
     * 这里有 n 份兼职工作，每份工作预计从 startTime[i] 开始到 endTime[i] 结束，报酬为 profit[i]。
     *
     * 给你一份兼职工作表，包含开始时间 startTime，结束时间 endTime 和预计报酬 profit 三个数组，请你计算并返回可以获得的最大报酬。
     *
     * 注意，时间上出现重叠的 2 份工作不能同时进行。
     *
     * 如果你选择的工作在时间 X 结束，那么你可以立刻进行在时间 X 开始的下一份工作。
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {

        int len = startTime.length;
        int[][] temp = new int[len][3];
        for(int i =0;i<len ;i++){
            temp[i] = new int[]{startTime[i],endTime[i],profit[i]};
        }
        Arrays.sort(temp,(a,b)->{return a[1]-b[1];});
        //排序
        //其中dp[i][0]表示不做当前任务取得的最大利益，dp[i][1]表示做当前任务
        //取得的最大利益
        int[]dp = new int[len+1];

        for(int i =1 ;i<=len;i++){
            //如果上一个任务的开始时间要比下一个任务的开始时间晚时
           int k =  binarySearch(temp,i-1,temp[i-1][0]);
           dp[i] = Math.max(dp[k]+temp[i-1][2],dp[i-1]);
        }
        return dp[len];
    }
    //二分查找，找到第一个结束时间要小于开始时间的索引
    public int binarySearch(int[][] jobs, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (jobs[mid][1] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 时间：20221026
     * 难度：困难
     * 题号：862
     *给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。如果不存在这样的 子数组 ，返回 -1 。
     *
     * 子数组 是数组中 连续 的一部分。
     */
    public int shortestSubarray(int[] nums, int k) {
        int len = nums.length;
        int[] pre = new int[len+1];
        int res = len+1;
        //前缀和
        for(int i = 0;i<len ;i++){
            pre[i+1] = pre[i]+nums[i];
        }
        //看完解答之后的理解，采用双端队列的方式进行解题
        Deque<Integer> queue = new ArrayDeque<>();
        for(int i =0;i<=len ;i++){
            int currSum = pre[i];
            //从队列头开始计算是否存在满足currSum - 队列头前缀大于等k
            //当满足时便可以直接将改队头出队，因为就算在i之后有元素减去当前队头元素大于等于k
            //其长度也会大于i减去当前的对头元素,所以队头元素可以直接出队
            while(!queue.isEmpty() && currSum - pre[queue.peekFirst()] >=k ){
                res = Math.min(res,i-queue.pollFirst());
            }
            //当队中存在大于当前前缀和的元素时，可以直接出队
            //因为大于当前的元素的前缀和后续前缀也将更难满足大于等k，就算是满足了，
            //相较于当前前缀和，其子数组长度也将更长，所以可以直接出队
            while(!queue.isEmpty() && currSum <= pre[queue.peekLast()]){
               queue.pollLast();
            }
            queue.offerLast(i);
        }
       return res == len+1 ? -1 : res ;
    }

    /**
     * 已知函数 signFunc(x) 将会根据 x 的正负返回特定值：
     *
     * 如果 x 是正数，返回 1 。
     * 如果 x 是负数，返回 -1 。
     * 如果 x 是等于 0 ，返回 0 。
     * 给你一个整数数组 nums 。令 product 为数组 nums 中所有元素值的乘积。
     *
     * 返回 signFunc(product) 。
     */
    public int arraySign(int[] nums) {
        int count = 0,res = 1;
        for(int i = 0;i<nums.length;i++){
            if(nums[i]<0){
                count++;
            }else if(nums[i] == 0){
                res = 0;
            }
        }
        if(res == 0){
            return 0;
        }else {
            return count%2 == 0 ? 1:-1;
        }
    }

    /**
     * 题号：784
     * 难度：中等
     * 时间：20221030
     * 给定一个字符串 s ，通过将字符串 s 中的每个字母转变大小写，我们可以获得一个新的字符串。
     *
     * 返回 所有可能得到的字符串集合 。以 任意顺序 返回输出。
     */
    public List<String> letterCasePermutation(String s) {
        char[] chars = s.toCharArray();
       List<Integer> list = new ArrayList<>();
        Set<String> res = new HashSet<>();
        for(int i = 0;i<chars.length ;i++){
            char c = chars[i];
            if((c>=65&& c<=90)||(c>=97 && c<=122)){
                list.add(i);
            }
        }
        for(int i = 1 ;i<=list.size();i++){
            dfs(res,chars,list,i,new HashSet<>());
        }
        List<String> r = new ArrayList<>();
        Iterator<String> iterator = res.iterator();
        while(iterator.hasNext()){
            r.add(iterator.next());
        }
        r.add(s);
        return r;
    }
    public char toUpOrLow(char chas){
        if(chas<=90){
            return (char) (chas+32);
        }else{
            return (char) (chas-32);
        }
    }
    public void dfs(Set<String> res ,char[] chars ,List<Integer> list ,int size,Set<Integer> set){
        if(set.size() == size){
            String str =new String(chars);
            if(!res.contains(str)){
                res.add(str);
            }
            return;
        }
        for(int i : list ){
            if(set.contains(i)){
                return;
            }
            set.add(i);
            chars[i] = toUpOrLow(chars[i]);
            dfs(res,chars,list,size,set);
            set.remove(i);
            chars[i] = toUpOrLow(chars[i]);
        }


    }
    public static void main(String[] args) {
        DaySix six = new DaySix();
        six.letterCasePermutation( "a1b2");
    }
}
/**
 * 难度：中等
 * 时间：20221021
 * 题号：901
 * 编写一个 StockSpanner 类，它收集某些股票的每日报价，并返回该股票当日价格的跨度。
 *
 * 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 *
 * 例如，如果未来7天股票的价格是 [100, 80, 60, 70, 60, 75, 85]，那么股票跨度将是 [1, 1, 1, 2, 1, 4, 6]。
 */

class StockSpanner {
    List<int[]> list;
    public StockSpanner() {
       list = new ArrayList<>();
    }

    public int next(int price) {
        if(list.size() == 0){
            list.add(new int[]{price,1});
            return 1;
        }
        int len = list.size()-1,ans = 1;
        while(len>=0){
            int[] temp = list.get(len);
            if(temp[0]<=price){
                len -=temp[1];
                ans+=temp[1];
            }else{
                break;
            }
        }
        list.add(new int[]{price,ans});
        return ans;
    }
}