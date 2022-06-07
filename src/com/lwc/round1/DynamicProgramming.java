package com.lwc.round1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 该类旨在学习动态规划算法一下是动态规划算法的基本的步骤
 * 1.状态数组，用于确定决策树的走向记录状态值，避免冗余的计算从而降低时间复杂度
 * 2.初始化，对状态数组进行初始化操作确定边界条件的状态
 * 3.状态转移方程，将问题简化为规模更小的子问题从而对问题进行简化规划
 */
public class DynamicProgramming {

    /**
     * 题号：509
     * 难度：简单
     * 时间：20211023
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     *
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给你 n ，请计算 F(n) 。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：2
     * 输出：1
     * 解释：F(2) = F(1) + F(0) = 1 + 0 = 1
     * 示例 2：
     *
     * 输入：3
     * 输出：2
     * 解释：F(3) = F(2) + F(1) = 1 + 1 = 2
     * 示例 3：
     *
     * 输入：4
     * 输出：3
     * 解释：F(4) = F(3) + F(2) = 2 + 1 = 3
     *
     */
    public int fib(int n) {
        //可以使用这三个数代替整个状态转移方程
        int a=0,b=1,c=0;
        if(n ==0 ){
            return a;
        }
        if(n == 1){
            return b;
        }
        for(int j=0;j<n-1;j++){
            c=a+b;
            a=b;
            b=c;
        }
        return c;
    }
    /**
     * 题号：1137
     * 难度：简单
     * 时间：20211023
     * 泰波那契序列 Tn 定义如下： 
     *
     * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
     *
     * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：n = 4
     * 输出：4
     * 解释：
     * T_3 = 0 + 1 + 1 = 2
     * T_4 = 1 + 1 + 2 = 4
     * 示例 2：
     *
     * 输入：n = 25
     * 输出：1389537
     * 分析：该题时费波耶齐数列的变形而已，两数之和变成了三数之和
     */
    public int tribonacci(int n) {
        int a=0,b=1,c=1,d=0;
        if(n == 0){
            return a;
        }
        if(n==1 || n==2){
            return b;
        }
        for(int i=0;i<n-2;i++){
            d=a+b+c;
            a=b;
            b=c;
            c=d;
        }
        return c;
    }
    /**
     * 198. 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     *
     *
     * 示例 1：
     *
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 2：
     *
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     *分析：要清楚dp数组的状态表示的是什么意思,该题中dp数组表示的是表示的是当前偷到第i个房间时的最大值
     */
    public int rob2(int[] nums) {
        //边界条件
        if(nums.length ==1){
            return nums[0];
        }
        //状态数组
        int[] dp =new int[nums.length];
        //初始化状态数组，抢到第一家跟第二家的数字就就是数组本身
        dp[0]=nums[0];
        dp[1]=Math.max(nums[0],nums[1]);
        //对整个房间进行遍历从而确定最大值
        for(int i=2; i<nums.length;i++){
            //如果选择要选择第i号房间的话，那么只能便不可能选择i-1号房间，只能从i-2号房间开始考虑
            //但是有没有可能dp[i-1]比较的时候根本就没有选择room[i-1]那么其实dp[i]做选择的时候可以选择,所以有可能漏了
            //如果dp[i-1]时没有选择room[i-1]时那么dp[i-1]必定为dp[i-2]那么dp[i]的时候选择最大值时那么一定是要选择
            //第i号房间。所以不存在漏选room[i-1]号房间的情况。所以只需要考虑当前i号房间是否选择，选择的话只需要考虑
            //
            dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1]);
        }
        return dp[nums.length-1];
    }
    /**
     * 题号：213
     * 难度：中等
     * 时间：20211023
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     * 示例 2：
     *
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 3：
     *
     * 输入：nums = [0]
     * 输出：0
     * 分析：首先房子是围成一圈的所以不能同时偷第一个跟最后一个，状态转移的方程是什么，对于每个房子他可以选择偷或者不偷，如果
     * 偷了的话那么他只能选择相间的一个房间偷
     * .
     */
    public int rob(int[] nums) {
        if(nums.length ==1){
            return nums[0];
        }else if(nums.length ==2){
            return Math.max(nums[0],nums[1]);
        }
        //状态数组用于记录i个房子能偷的最大金额数
        int[] db =new int[nums.length];
        //对状态数组进行初始话
        db[0]=nums[0];
        db[1]=Math.max(nums[1],nums[0]);
        //对所有房间进行遍历,但是这个不能选中第num[length-1]个房间
        for(int i=2;i<nums.length-1;i++){
            db[i]=Math.max(db[i-2]+nums[i],db[i-1]);
        }
        //求出除第一个以外所能获取最大的金额数
        int a =nums[1],b=Math.max(nums[1],nums[2]),c=0;
        for(int i=3;i<nums.length;i++){
            c=Math.max(a+nums[i],b);
            a=b;
            b=c;
        }
        return Math.max(db[nums.length-2],b);
    }
    /**
     * 题号：64
     * 难度：中等
     * 时间：20211024
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     *  
     *
     * 示例 1：
     *
     *
     * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
     * 输出：7
     * 解释：因为路径 1→3→1→1→1 的总和最小。
     * 示例 2：
     *
     * 输入：grid = [[1,2,3],[4,5,6]]
     * 输出：12
     *

     */
    public int minPathSum(int[][] grid) {
        //动态规划的状态数组用于保存从左上角到该位置的最小路劲长度
        int[][] dp=new int[grid.length][grid[0].length];
        //初始化，两个边线上的所有路径都需要进行初始话，从左上角到该位置的路劲只有可能有一条
        //初始化横轴
       for(int i=0;i<grid[0].length;i++){
           if(i == 0){
               dp[0][i]=grid[0][i];
           }else{
               dp[0][i]=grid[0][i]+dp[0][i-1];
           }

       }
       //初始化纵轴
        for(int i=0 ;i<grid.length;i++){
            if(i == 0){
                dp[i][0]=grid[i][0];
            }else{
                dp[i][0]=grid[i][0]+dp[i-1][0];
            }

        }
        //通过由于到当前位置的路劲只有向右或者向下才可以到达所以其迭代公式应当为dp[i][j]=min(dp[i-1][j]+grid[i][j],dp[i][j-1]+grid[i][j])
        for(int i=1;i<grid.length;i++){
            for(int j=1;j<grid[0].length;j++){
                dp[i][j]=Math.min(dp[i-1][j]+grid[i][j],dp[i][j-1]+grid[i][j]);
            }
        }
        return dp[grid.length-1][grid[0].length-1];
    }
    /**
     * 题号：337
     * 难度：中等
     * 时间：20211026
     * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
     *
     * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
     *
     * 示例 1:
     *
     * 输入: [3,2,3,null,3,null,1]
     *
     *      3
     *     / \
     *    2   3
     *     \   \
     *      3   1
     *
     * 输出: 7
     * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
     * 示例 2:
     *
     * 输入: [3,4,5,1,3,null,1]
     *
     *      3
     *     / \
     *    4   5
     *   / \   \
     *  1   3   1
     *
     * 输出: 9
     * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
     *分析：两个直接相连的房子不能同一天被打劫，意味着如果偷了根节点那么便不能偷与其根节点直接相连
     * 的左右子节点只能偷其左右子节点的子节点，同时要注意如果选择了左节点同时也可以跟右边子节点的孩子相加
     *解题：该题的难点在于以什么样的形式去遍历该二叉树从而获得最大值，自己没有思路
     * 看完答案后解题理解：对于当前节点如果抢劫该节点时的最大值，和不抢该节点的最大值当抢劫该节点时其最大值所以不抢该节点的最大值
     * 应当为max(f(left),o(left))+max(f(right),o(right)),抢该该节点的最大值应当为root.val+o(right)+o(left)
     */

    public int rob(TreeNode root) {

        return Math.max(robTree(root)[0],robTree(root)[1]);
    }

    /**
     * res[0]表示的是抢该根节点的最大值
     * res[1]表示的是不抢该根节点的最大值
     * @return
     */
    public int[] robTree(TreeNode root){
        //当节点为空
        if(root ==null ){
            return new int[]{0,0};
        }
        int[] left=robTree(root.left);
        int[] right=robTree(root.right);
        //便是抢该根节点的钱的总数
        int rob =left[1]+ right[1]+root.val;
        //不抢根节点的获得的最大总钱数
        int noRob= Math.max(left[1],left[0])+Math.max(right[1],right[0]);
        return new int[]{rob,noRob};
    }
    /**
     * 题号：322
     * 难度：中
     * 时间：20211102
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     *
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     *
     * 你可以认为每种硬币的数量是无限的。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：coins = [1, 2, 5], amount = 11
     * 输出：3
     * 解释：11 = 5 + 5 + 1
     * 示例 2：
     *
     * 输入：coins = [2], amount = 3
     * 输出：-1
     * 示例 3：
     *
     * 输入：coins = [1], amount = 0
     * 输出：0
     * 示例 4：
     *
     * 输入：coins = [1], amount = 1
     * 输出：1
     * 示例 5：
     *
     * 输入：coins = [1], amount = 2
     * 输出：2
     *分析：题意是将amount的总额用coins中的数换成零散的的硬币，从而取得最小的硬币数,关键想明白其状态转移方程是什么
     * 然后要明白dp数组表达的意思是什么，对于每个数组中的硬币可以选也可以不选，如果选择该硬币的话，那么硬币数就是之前的硬币数
     * 加一，则额度就是amount=amount-coins[i],如果不选择该硬币硬币数跟额度则不发生改变，所以dp数组表达就是选择该硬币，或者不选择该
     * 硬币时的硬币数，
     * 但是还要表示的是最小的硬币数dp[i]=min(dp[i-1]+1+coninChange(coins,amount-conin[i]),coinsChange(conins,amount));
     * 可以重复使用
     *
     *解题：
     */
    public int coinChange(int[] coins, int amount) {

        if(amount ==0 ){
            return 0;
        }
        //至少有两个硬币时的处理方法
        //状态数组表示的是在当前额度是选择硬币的最少的数量
        int[] dp=new int[amount+1];
        //初始状态
        //首先对数组进行排序，在选择最小的硬币值赋予初值，当小于最小数组值时则表示在之下的所有金额数都不能进行表示
        Arrays.sort(coins);
        for(int j=1;j<=amount;j++){
            dp[j]=amount+1;
        }
        for(int i= coins[0];i<=amount;i++){
            for(int j=0;j<coins.length;j++){
                //要保证数组的最小值大于零
                if(i-coins[j]>=0){
                    //对于其中的硬币每个硬币都可以选或者不选，选的时候状态则进行转换，在选和不选之中选择最少数量的硬币,这里的比较同时也是跟
                    //最小值同时也是在筛选，也就是替换之前的比较结果，从而获得所有结果中的最小值
                    dp[i]=Math.min(dp[i-coins[j]]+1,dp[i]);
                }
            }
        }

        return dp[amount]>amount+1? dp[amount]:-1;
    }

/**
 * 难度：简单
 *
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 *
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 *
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：[7,1,5,3,6,4]4
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * 示例 2：
 *
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 *  
 *
 * 提示：
 *
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 * 分析：设置一个dp数组其中dp[i]表示的是第i天卖出所能所去的最大利益值第i天卖出所获得的最大利益应当为
 * dp[i]=max(dp[i-1]+price[i]-price[i-1],dp[i-1])(这个状态转移公式并不正确)，下面给出正确解释可以知道第i天卖出去所能
 * 获取的最大值应当是当前股价减去i-1天股价的最小值而i-1天股价的最小值与i-1天的dp[i-1]有关，所以可以给出状态转移方程
 * dp[i]=max(p[i]-p[i-1]+dp[i-1],dp[i])
 *
 * 设置一个二维数组其中p[i][j]表示的是i天买入j天卖出所获取的利润数，在这个二位数组中找到最大的利润数即可
 * 但是这样没有使用动态规划算法
 * 要使用动态规划算法首先就需要明白dp数组所表示的是什么意思这个很重要
 */
public int maxProfit(int[] prices) {
    //如果只有一天时则表示没有盈利
    if(prices.length ==1){
        return 0;
    }
    //对大于两天的股价进行处理
    //dp[i]表示的是第i天卖出去时所能获取的最大利润值
    int[] dp=new int[prices.length];
    //对dp数组进行初始话，也就是第二天的时候需要进行初始化
    dp[1]=prices[1]-prices[0]<0 ?0:prices[1]-prices[0] ;
    for(int i=1;i<prices.length;i++){
        dp[i]=Math.max(prices[i]-prices[i-1]+dp[i-1],dp[i]);
    }
    //他需要选择的是获取最大的利润值，所以这时还需要对数组进行排序
    Arrays.sort(dp);
    return dp[prices.length-1];
}
/**
 * 题号：122
 * 难度：中等
 * 时间：20211105
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 *
 * 输入: prices = [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * 示例 2:
 *
 * 输入: prices = [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * 示例 3:
 *
 * 输入: prices = [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 * 分析：该题与之前的题目不同之前的题目每次只有一次交易，现在一天可以进行多次交易然后取得所有交易股票的最大值,且一次只能拥有一笔股票
 * 其实也就是找到差价最大的两个点进行售卖如果股价一直上升的话就没有必要买当前一个股价大于后一个股价的再进行卖出，
 * 也就是说需要两个指针进行获取差值
 * 动态规划解法：要算出每天所持有的利益就必须明确在这天所持有股票的状态，如果在这天交易后是并没有持有股票的状态
 * 那么可以知道当天的利益值应当为在这天之前没有持有股票所获得的利益值，和在这天之前持有股票，然后在这天卖出去所持有的利益值
 * 状态转移方程应当为dp[i][0]=max(dp[i-1][0],dp[i-1][1]+price[i]);
 * 同理在今天持有股票的利益值的状态转移方程应该为dp[i][1]=max(dp[i-1][0]-price[i],dp[i-1][1])
 * 所以如果要确定状态转移方程，就必须要明白状态的变化，比如此处是股票持有状态的转变，每天对于股票的状态都会有可能是持有状态
 * 和未持有状态，要明白这两个状态的利益计算方式，这样就将问题简化了，在之前确定的状态上进行计算
 * 解题：
 */
//贪心算法解答
public int maxProfit2(int[] prices) {
    //i表示的是指向前面的降价的基准
    int i=0,maxPro=0, j=1;
    for(;j<prices.length;j++){
        //如果第j天的价格相对于下降了，则选择前一天的相比于i天没下降的加个卖出
        //并且将i的位置更新
        if(prices[j]<prices[j-1]){
            maxPro+=prices[j-1]-prices[i];
            i=j;
        }
    }
    //股价可能在中间某一段到尾一直上升状态，所以这里需要另外进行处理
    if(i<prices.length){
        maxPro+=prices[j-1]-prices[i];
    }

    return maxPro;
}
//动态规划解答
public int maxProfit3(int[] prices) {
    //表示dp[i][0]表示的是第i天不持有股票时的最大值，dp[i][1]表示的时第i天持有股票的利益最大值
    int[][] dp=new int[prices.length][2];
    //对dp数组进行初始化
    dp[0][0]=0;
    dp[0][1]=-prices[0];

    //对每天的股价进行动态更新比较
    for(int i =1 ;i<prices.length;i++){
        dp[i][1]=Math.max(dp[i-1][0]-prices[i],dp[i-1][1]);
        dp[i][0]=Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
    }

    return dp[prices.length-1][0];
}
/**
 * 题号：123
 * 难度：困难
 * 时间：20211105
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 示例 1:
 *
 * 输入：prices = [3,3,5,0,0,3,1,4]
 * 输出：6
 * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 *      随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 * 示例 2：
 *
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。  
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。  
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * 示例 3：
 *
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
 * 示例 4：
 *
 * 输入：prices = [1]
 * 输出：0
 * 分析：该题与之前的题目差别在于只能有两次交易的机会，也就是说当超过两次时就不能在接着进行交易了，所以该题目的关键在于
 * 如何选择这两次交易。该题没有思路
 * 看完答案所提供的状态之后自己的理解：首先明确这个问题的状态转换，首先这个问题的状态：
 * 1.可能是一次都没有进行交易
 * 2.进行了一次买
 * 3.完成了第一次交易
 * 4.进行了第二次买
 * 5.完成了第二次交易
 * 所以现在需要思考的是如果知道了第i-1天的这四个状态，如果跟据第i-1天的状态推出第i天的状态
 * dp[i][0]=max(-price[i],dp[i-1][0])进行了第一次买操作
 * dp[i][1]=max(dp[i-1][0]+price[i],dp[i-1][1]) 完成了第一交易
 * dp[i][2]=max(dp[i-1][1]-price[i],dp[i-1][2]) 完成了第二次买的利润
 * dp[i][3]=max(dp[i-1][2]+price[i],dp[i-1][3]) 完成了第三次交易后的状态转化方程
 * 并且如果没有完成第一次交易就不可能完成第二次交易，这个必须要控制
 *最后结果应当时第一次的利润加上第二次的利润的所有值
 * 所以动态规划的重点应该是找到解决问题的状态变化才能正确的找到状态转移方程，就像该题中的状态转移有第一次交易买第一次交易卖
 * 第二次交易买第二次交易卖的状态转化，当明确了这几个状态的转化问题也就迎刃而解了

 * 解题：
 */

public int maxProfit4(int[] prices) {
    //dp[i][0]表示的是第一买操作时获取的最大利润值，同理dp[i][1],dp[i][2],dp[i][3],均对应上述的各个状态
    int[][] dp=new int[prices.length][4];
    //dp数组初始化，为零时不可能完成第一次交易，也不可能完成第二次交易
    for(int j=0 ;j<prices.length;j++){
        Arrays.fill(dp[j],-9999);
    }

    dp[0][0]=-prices[0];
    for(int i=1;i<prices.length;i++){
        //进行第一次买操作
        dp[i][0]=Math.max(-prices[i],dp[i-1][0]);
        //进行第一次卖操作
        dp[i][1]=Math.max(dp[i-1][0]+prices[i],dp[i-1][1]);
        //只有当前一天完成了第一次交易之后今天才可以进行第二次交易
        //第二次买
        dp[i][2]=Math.max(dp[i-1][1]-prices[i],dp[i-1][2]);
        //第二次卖，这里可能会出现第二次没有买然后就直接卖的情况
        dp[i][3]=Math.max(dp[i-1][2]+prices[i],dp[i-1][3]);

    }
    //可能是完成第一次交易就获得了最大值
    return Math.max(dp[prices.length-1][3],dp[prices.length-1][1]) >0 ?Math.max(dp[prices.length-1][3],dp[prices.length-1][1]):0;
}
/**
 * 题号：188
 * 难度：困难
 * 时间：20211105
 * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 * 示例 2：
 *
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 *  分析：该题关键在于分析其状态转换可以参考上一题的状态转换公式可以得到其状态转移方程
 * 1.第一次买操作时
 * 2.第一次卖操作状态
 * 3.第二次买操作状态
 * 4.第二次卖操作的状态
 *.
 * .
 * 2k-1.第k次买操作的状态
 * 2K.第k次卖操作的状态
 * 同时对应的dp[i]的状态应当为
 * dp[i][0]=max(-price[i],dp[i-1][0])
 * dp[i][1]=max(dp[i-1][0]-price[i],dp[i-1][1])
 * ......
 * dp[i][k]=max(dp[i-1][k-1]-price[i],dp[i-1][k])
 * 解题：
 */
    public int maxProfit(int k, int[] prices) {
        if(prices.length ==0 || k== 0){
            return 0;
        }
        //定义状态数组
        int[][] dp =new int[prices.length][2*k];
        //对数组进行初始化
        for(int j=0 ;j<prices.length;j++){
            Arrays.fill(dp[j],-9999);
        }
        dp[0][0]=-prices[0];
        for(int j=1 ;j<prices.length;j++){
            dp[j][0]=Math.max(-prices[j],dp[j-1][0]);
            for(int i=1;i<2*k;i++){
                if(i%2 ==0){
                    //偶数次表示的是买
                    dp[j][i]=Math.max(dp[j-1][i-1]-prices[j],dp[j-1][i]);
                }else{
                    //奇数次表示的是卖出时的利润
                    dp[j][i]=Math.max(dp[j-1][i-1]+prices[j],dp[j-1][i]);
                }

            }
        }
        //寻找所有交易中的最大致
        int maxPro=0;
        for(int i =0 ;i<2*k ;i++){
            if(i%2 != 0){
                maxPro=Math.max(dp[prices.length-1][i],maxPro);
            }
        }
        return maxPro;
    }
    /**
     * 题号：309
     * 难度：中
     * 时间：20211112
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
     *
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     *
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 示例:
     *
     * 输入: [1,2,3,0,2]
     * 输出: 3
     * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
     * 分析：分析股票的状态转化买入状态，卖出状态，冷冻期状态，总共三种状态遍历股票数组一定要明确这三个状态，买入时要确定
     * 不在冷却期，买了之后就不能再次买入。且冷冻期为一天，其实冷冻期可以考虑是隔一天进行交易，就是确定前一天有没有进行交易
     * 如果进行了卖出操作则不能进行买入
     * 解题：设置db数组为表示股票在每天的最大利益其中dp[i][0]表示持有股票，dp[i][1]表示没有持有股票
     * 状态转移方程应为:第i天买入的状态 (可能是前面i-1天买入，可能是第i天进行买入的,也可能前一天是冷冻期)dp[i][0]=max(dp[i-1][0],dp[i-2][1]-price[i])
     * 第i天卖出的状态 (买入存在冷冻期而卖出并不存在，所以只需要考虑第i天卖出的利润，或者i天之前卖出的最大利润即可)dp[i][1]=max(dp[i-1][1]+price[i],dp[i-1][0])
     */
    public int maxProfit5(int[] prices) {
        if(prices.length <=1){
            return 0;
        }
        //dp数组表示的是每天的状态
        int[][] dp=new int[prices.length][2];
        //对dp数组进行初始化
        dp[0][0]=-prices[0];
        dp[1][0]=Math.max(dp[0][0],-prices[1]);
        dp[1][1]=Math.max(prices[1]-prices[0],dp[0][1]);
        for(int i =2 ;i<prices.length;i++){
            dp[i][0]=Math.max(dp[i-1][0],dp[i-2][1]-prices[i]);
            dp[i][1]=Math.max(dp[i-1][0]+prices[i],dp[i-1][1]);
        }
        //这里可能存在小于零的情况，所以一定要进行判断
        return dp[prices.length-1][1] >0 ? dp[prices.length-1][1] :0;
    }
    //编辑距离题目
    /**
     * 题号：516
     * 难度：中等
     * 时间:20211116
     * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
     *
     * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "bbbab"
     * 输出：4
     * 解释：一个可能的最长回文子序列为 "bbbb" 。
     * 示例 2：
     *
     * 输入：s = "cbbd"
     * 输出：2
     * 解释：一个可能的最长回文子序列为 "bb" 。
     *分析：不改变字符串的顺序删除某个或者多个字符找出剩下字符的最长回文子序列，同样可以设置二维数组用于表示的是以改字符为
     * 开头的字符串是到哪里是回文子序列例如dp[i][j]表示的是以i到j最长回文子序列的长度同样跟据原先所做的最长回文序列可以得到
     * 以下状态如果第i个字符与第j个字符相同时则dp[i][j]=dp[i+1][j-1]+2,如果不相同时则dp[i][j]=max(dp[i+1][j],dp[i][j-1])
     * 不相同时就要考虑将i或者第j个字符加入到子串中的得到的最大值
     *解题：
     */
    public int longestPalindromeSubseq(String s) {
        //表示由i到j是否是回文字符串
        int dp[][] =new int[s.length()][s.length()];
        //对dp数组进行初始化,其中单个字符一定是回文字符串且长度为1
        for(int i=0;i<s.length();i++){
            dp[i][i]=1;
        }
        //其中双循环只需要遍历上三角矩阵即可，上三角与下三角意思差不多,跟据迭代公式可以知道dp[i][j]是跟据下一行的数据
        //所以可以确定遍历顺序是从前往后进行遍历
        for(int i=s.length()-1;i>=0;i--){
            for(int j=i+1; j<s.length();j++){
                if(s.charAt(i) == s.charAt(j) ){
                    dp[i][j]=dp[i+1][j-1]+2;
                }else{
                    dp[i][j]=Math.max(dp[i][j-1],dp[i+1][j]);
                }
            }
        }
        return dp[0][s.length()-1];
    }

    /**
     * 题号：72
     * 难度：困难
     * 时间：20211116
     * @param word1
     * @param word2
     * @return
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
     *
     * 你可以对一个单词进行如下三种操作：
     *
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     *  
     *
     * 示例 1：
     *
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     * 示例 2：
     *
     * 输入：word1 = "intention", word2 = "execution"
     * 输出：5
     * 解释：
     * intention -> inention (删除 't')
     * inention -> enention (将 'i' 替换为 'e')
     * enention -> exention (将 'n' 替换为 'x')
     * exention -> exection (将 'n' 替换为 'c')
     * exection -> execution (插入 'u')
     *  
     *
     * 提示：
     *
     * 0 <= word1.length, word2.length <= 500
     * word1 和 word2 由小写英文字母组成
     * 分析：看解析之前不会解题，以下是看完解析之后的解题思路，设置一个二维dp数组其中dp[i][j]表示的是
     * word1以第i-1个字符为结尾的字符到word2以j-1个字符为结尾的最小编辑距离，下面来对字符的状态进行分析
     * 我们可以假设dp[i][j]以前的数值都已经确定接下来是要用之前的数据来表示dp[i][j]
     * 可以进行如下分析如果word1的第i个字符与word2的第j个字符相同则可以知道dp[i][j]=dp[i-1][j-1]
     * 如果不相同的话可以考虑对两个字符进行替换删除以及增加，我们知道一个字符增加一个字符其实也可以相当于
     * 另外一个字符减少一个字符所以有以下状态转移方程对于删除：如果是删除word1中的第i-1个字符则
     * dp[i][j]=dp[i-1][j]+1,同理如果是删除word2中的第j-1个字符则可以表示为dp[i][j]=dp[i][j-1]+1
     * 对于修改则是修改两个word中的某一个字符从而达到相同的情况所以我们只需要考虑dp[i-1][j-1]然后在进行一步
     * 修改操作即可所以最后的结果就是dp[i][j]=dp[i-1][j-1]+1
     * 所以最后我们的出的解题思路是一定要名确定dp数组表达的意义这样才可以真正明白动态规划的解法
     * 解题：
     */
    public int minDistance(String word1, String word2) {
        //定义dp数组，其中dp[i][j]表达的是word1以第i-1个字符为结尾的字符串到以第j-1个字符为结尾的字符串
        //所需要的最小编辑距离
        int[][] dp=new int[word1.length()+1][word2.length()+1];
        //对dp数组进行初始化我们确定的是dp[0]这一行的值应当为word2的长度，dp[][0]这一列的值应当为word1的长度
        //所以对其优化应当如下
        for(int i=0;i<word2.length()+1;i++){
            dp[0][i]=i;
        }
        for(int i=0;i<word1.length()+1;i++){
            dp[i][0]=i;
        }
        //经过之前两步之后我们应当确定遍历的顺序，跟据状态转移方程可以知道dp[i][j]应当是依赖于上一次的所以遍历
        //顺序就是从低位到高位进行遍历
        for(int i =1 ;i<word1.length()+1;i++){
            for(int j =1; j<word2.length()+1;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] =dp[i-1][j-1];
                }else{
                    dp[i][j]=Math.min(dp[i-1][j-1]+1,Math.min(dp[i][j-1]+1,dp[i-1][j]+1));
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * 题号：712
     * 难度：中
     * 时间：20211116
     * 给定两个字符串s1, s2，找到使两个字符串相等所需删除字符的ASCII值的最小和。
     *
     * 示例 1:
     *
     * 输入: s1 = "sea", s2 = "eat"
     * 输出: 231
     * 解释: 在 "sea" 中删除 "s" 并将 "s" 的值(115)加入总和。
     * 在 "eat" 中删除 "t" 并将 116 加入总和。
     * 结束时，两个字符串相等，115 + 116 = 231 就是符合条件的最小和。
     * 示例 2:
     *
     * 输入: s1 = "delete", s2 = "leet"
     * 输出: 403
     * 解释: 在 "delete" 中删除 "dee" 字符串变成 "let"，
     * 将 100[d]+101[e]+101[e] 加入总和。在 "leet" 中删除 "e" 将 101[e] 加入总和。
     * 结束时，两个字符串都等于 "let"，结果即为 100+101+101+101 = 403 。
     * 如果改为将两个字符串转换为 "lee" 或 "eet"，我们会得到 433 或 417 的结果，比答案更大。
     *  分析：该题与之前的题目类似，首先定义dp数组其中dp[i][j]表示的是由word1到word2所需要删除字符的最小
     *  asci码值，假设dp[i][j]之前的数据都已经确定则可以确定dp[i][j]=min(dp[i-1][j]+charAt(i),dp[i][j-1]+charAt(j))
     * 还有一种可能就是两个字符都需要删除到怕dp[i][j]=dp[i-1][j-1]+charAt(i)+charAt(j)
     * 解题：
     */
    public int minimumDeleteSum(String s1, String s2) {
        //设置dp数组其中dp[i][j]表示的是以i-1结尾的字符到j-1结尾的字符所需要删除的最小ascii码值
        int[][] dp=new int[s1.length()+1][s2.length()+1];
        //对dp数组进行初始化操作
        for(int i=1 ;i<s1.length()+1;i++){
            dp[i][0]=dp[i-1][0]+Integer.valueOf(s1.charAt(i-1));
        }
        for(int i=1 ;i<s2.length()+1;i++){
            dp[0][i]=dp[0][i-1]+Integer.valueOf(s2.charAt(i-1));
        }
        //对两字符串进行遍历,
        for(int j=1;j<s1.length()+1;j++){
            for(int i=1;i<s2.length()+1;i++){
                if(s1.charAt(j-1)==s2.charAt(i-1)){
                    dp[j][i]=dp[j-1][i-1];
                }else{
                    dp[j][i]=Math.min(dp[j-1][i]+s1.charAt(j-1),dp[j][i-1]+s2.charAt(i-1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    /**
     * 题号：300
     * 难度：中
     * 时间：20211116
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     *
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     *
     *  
     * 示例 1：
     *
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * 示例 2：
     *
     * 输入：nums = [0,1,0,3,2,3]
     * 输出：4
     * 示例 3：
     *
     * 输入：nums = [7,7,7,7,7,7,7]
     * 输出：1
     * 分析：怎么确定状态转移方程以及怎么确定动态数组的意义，假设已知前i个字符的最长的递增子序列长度怎么判断第i+1个递增子序列
     * 与i之间的关系，需要将这个问题转化为一个子问题进行解决,看完答案之后确定的解题思路如下因为前i个数字中存在从某个字符开始
     * 便不是递增的序列，所以并不能只是判断第i与第i+1之间的大小关系来判断递增子序列的长度，应当判断前i的子序列中与i+1比较大小
     * 后的最大的子序列的值所以状态转移方程应当如下max(dp[i],dp[j]+1),其中j属于0-i
     * 解题：
     */
    public int lengthOfLIS(int[] nums) {
        //其中dp[i]表示的前i个序列的最长递增子序列
        int[] dp =new int[nums.length];
        //对数组进行初始化，因为前i个的初始长度就为1
        Arrays.fill(dp,1);
        int res =0;
        for(int i=0; i<nums.length ;i++){
            for(int j=0 ;j<nums.length;j++){
                if(nums[i] > nums[j]){
                    dp[i]= Math.max(dp[i],dp[j]+1);
                }
            }
            if(dp[i]>res){
                res=dp[i];
            }
        }

        return res;
    }
    /**
     * 上述算法改进，可以利用贪心算法的思想对该状态数组进行改进，可以知道当最长子序列的最末尾元素越小时我们就越容易碰到
     * 大于该末尾元素的数，这样就可以使得这个递增子序列的长度最长，这样dp数组的定义就变成了dp[i]表示的是长度为i的最长
     * 递增子序列的最小值。接下来就要证明这个dp数组是递增的，假设dp[j]<dp[i] 且j>i,跟据上述对dp数组的定义长度为j的最长递增
     * 子序列最小的尾元素为dp[j],长度为i的最长递增子序列的最小尾元素为dp[i],由于 j>i 且描述的均为同一数组所以我们可以得到
     * dp[j]>dp[i]必然成立，所以dp数组一定是单调递增的，所以目前所需要做的就是遍历整个nums数组更新最长递增子序列的最小的末尾元素
     * 而整个最nums数组的最大长度就是整个dp数组有值的长度
     */
    public int lengthOfLIS2(int[] nums) {
        //其中dp[i]表示的是长度为i的最长子序列的最小尾元素
        int[] dp =new int[nums.length];
        //对dp数组进行初始化
        dp[0]=nums[0];
        //记录数组的长度返回结果值
        int len =0;
        for(int i= 0 ;i<nums.length; i++){
            //如果nums[i]的值大于dp[len]则直接将nums的值直接添加到dp数组中
            if(nums[i]>dp[len]){
                dp[len+1]=nums[i];
                len++;
            }else{
                //如果小于则找到dp数组中的dp[j-1]<num[i]<dp[j]将dp[j]的位置进行取代，其中这个查找可以使用二分法进行查找
                int j=findPosition(dp,nums[i],len,0);
                dp[j]=nums[i];
            }
        }
        return len+1;
    }
    //有可能找不到
    public int findPosition(int[] arr,int j,int high,int low){
        int res=0;
        if(high > low){
            int mid =(high+low)/2;
            if(arr[mid] == j){
                res= mid;
            }else if(arr[mid]>j){
                res= findPosition(arr,j,mid,low);
            }else if(arr[mid]<j){
                res= findPosition(arr,j,high,mid+1);
            }
        }else{
            return high;
        }
       return res;
    }

    /**
     * 题号：647
     * 难度：中
     * 时间：20211201
     * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
     *
     * 回文字符串 是正着读和倒过来读一样的字符串。
     *
     * 子字符串 是字符串中的由连续字符组成的一个序列。
     *
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "abc"
     * 输出：3
     * 解释：三个回文子串: "a", "b", "c"
     * 示例 2：
     *
     * 输入：s = "aaa"
     * 输出：6
     * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
     *分析：该题如之前的回文字串一样，不过是解题的答案不一样而已，该题同样可以设置一个二维数组该二维数组中的dp[i][j]
     * 表示的是由i到j的字符串是否是回文子序列，同时该状态转移方程为 如果char[i]==char[j]那么dp[i][j]=dp[i+1][j-1]
     * 同时该二维数组只使用上三角矩阵，最后再统计其中所有是回文子序列的之和便可以得出最后的数量
     *解题：
     */
    public int countSubstrings(String s) {
        //定义状态数组其中dp[i][j]表示的是由i到j是否是回文子序列
        boolean[][] dp =new boolean[s.length()][s.length()];
        //用于统计结果
        int result =0;
        //初始化状态数组，其中对角线上的元素都是回文子序列
        for(int i = 0 ; i<s.length() ;i++){
            result++;
            dp[i][i] = true;
        }
        //遍历整个字符串的所有位置
        for(int i = s.length() ; i>=0;i--){
            for(int j =i+1; j<s.length(); j++){
                if(s.charAt(i) == s.charAt(j) && (j-i<2 || dp[i+1][j-1])){
                    dp[i][j]=true;
                    result++;
                }else{
                    dp[i][j] =false;
                }
            }
        }
        return result;
    }

    /**
     * 题号：673
     * 难度：中
     * 时间：20211203
     * 给定一个未排序的整数数组，找到最长递增子序列的个数。
     *
     * 示例 1:
     *
     * 输入: [1,3,5,4,7]
     * 输出: 2
     * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
     * 示例 2:
     *
     * 输入: [2,2,2,2,2]
     * 输出: 5
     * 解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
     * 注意: 给定的数组长度不超过 2000 并且结果一定是32位有符号整数。
     *分析：设置dp[i]数组表示的是nums[i]的最长递增子序列的长度，假设已知dp[i-1]的大小现在跟据dp[i-1]计算dp[i]
     * 计算dp[i]应当是由（0-i）之间的最大值也即dp[j]+1
     *解题：
     * @param nums
     * @return
     */

    public int findNumberOfLIS(int[] nums) {
        int[] dp=new int[nums.length];
        int[] num =new int[nums.length];
        int maxLen =0,res=0;
        for(int i =0 ;i<nums.length;i++){
            //对dp数组进行初始化
            dp[i]=1;
            num[i]=1;
            for(int j=0 ;j<i ;j++){
                //如果nums[i]>nums[j]就选择j作为迭代
                if(nums[i]>nums[j]){
                    //比较获得最大值
                    if(dp[i] < dp[j]+1){
                        dp[i]=dp[j]+1;
                        //重置num[i]的计数
                        num[i]=num[j];
                    }else if(dp[i] == dp[j]+1){
                        //如果相等则对num[i]进行累加
                        num[i]++;
                    }
                }

            }
            //累计其他位置的最长子序列的长度
            if(dp[i]>maxLen){
                maxLen=dp[i];
                res=num[i];
            }else if(dp[i] == maxLen){
                res += num[i];
            }
        }
        return res;
    }
    /**
     * 题号：730
     * 难度：困难
     * 时间：20211206
     * 给定一个字符串 S，找出 S 中不同的非空回文子序列个数，并返回该数字与 10^9 + 7 的模。
     *
     * 通过从 S 中删除 0 个或多个字符来获得子序列。
     *
     * 如果一个字符序列与它反转后的字符序列一致，那么它是回文字符序列。
     *
     * 如果对于某个  i，A_i != B_i，那么 A_1, A_2, ... 和 B_1, B_2, ... 这两个字符序列是不同的。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：
     * S = 'bccb'
     * 输出：6
     * 解释：
     * 6 个不同的非空回文子字符序列分别为：'b', 'c', 'bb', 'cc', 'bcb', 'bccb'。
     * 注意：'bcb' 虽然出现两次但仅计数一次。
     * 示例 2：
     *
     * 输入：
     * S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
     * 输出：104860361
     * 解释：
     * 共有 3104860382 个不同的非空回文子序列，对 10^9 + 7 取模为 104860361。
     * 分析：该问题自己没有思路，一下是看完解析所得的思路，跟据大神们的思路整理得到一下方法
     * 首先定义二维状态数组dp其中dp[i][j]表示的是由i到j字符串的所有回文子序列所以得到以下状态转移方程
     * 如果s[i]==s[j] 则考虑在i+1到j-1中是否有与s[i]相同的字符如果i+1到j-1中没有与s[i]相同的字符
     * 那么dp[i][j]=2*dp[i+1][j-1]+2-------其中2*dp[i+1][j-1]表示的是dp[i+1][j-1]不加上边界上两相同字符的
     * 回文子序列数，再加上一个dp[i+1][j-1]表示的是加上两边界后所得的所有回文子序列，+2是单个s[i]组成的回文子序列
     * 和两个s[i]组成的回文子序列
     * 如果i+1到j-1中只有一个与s[i]相同的回文子序列则状态转移方程为dp[i][i]=2*dp[i+1][j-1]+1,因为单个s[i]构成的
     * 回文子序列在s[i+1][j-1]中已经计算了所以只需要加一即可
     * 对于有两个以上的与s[i]相同的字符则状态数组为 dp[i][j]=2*dp[i+1][j-1]-dp[l+1][k-1]其中l表示的是由i+1找到
     * 第一个s[i]相同的字符的索引，k表示的是由s[j]找到的第一个与s[j]相同的索引这里不需要+2因为dp[i+1][j-1]中已经计算过了
     * 且要减去dp[l+1][k-1]因为2*dp[i+1][j-1]中对边界上拼上两个s[j]时重复计算了所以需要减去
     * 如果s[i]!=s[j]则说明将两个字符同时加入不能得到回文序列所以两边需要单独计算所以状态转移方程为
     * dp[i][j]=dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1]这里减去dp[i+1][j-1]因为前面两个中有重复计算
     * ------
     * 解题：
     */
    public int countPalindromicSubsequences(String s) {

        int mod=1000000007;
        //定义状态数组其中dp[i][j]表示的是由i到j之间的回文子序列的个数,且dp只使用上三角矩阵
        int[][] dp=new int[s.length()][s.length()];
        //初始化数组，因为遍历肯定遍历不到dp[s.len][s.len],所以要对其进行初始话
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
        }
        //确定遍历顺序因为dp[i][j]使用到下标i+1和j-1，所以i由下到上，j由左到右进行遍历
        for(int i =s.length()-2 ;i >=0 ;i--){
            for(int j =i+1 ;j<s.length();j++){
                if(s.charAt(i) == s.charAt(j)){
                    int begin = i+1;
                    int end = j-1;
                    while(begin<=j-1 && s.charAt(begin)!=s.charAt(i)){
                        begin++;
                    }

                    while(i+1<=end && s.charAt(end)!=s.charAt(i)){
                        end--;
                    }
                    //如果只有一个相同的字符
                    if(end == begin){
                        dp[i][j]= (2*dp[i+1][j-1]+1)%mod ;
                    }else if(end > begin){
                        dp[i][j] =(2*dp[i+1][j-1] -dp[begin+1][end-1])%mod;
                    }else{
                        //没有相同的字符
                        dp[i][j]=(2*dp[i+1][j-1]+2)%mod;
                    }
                }else{
                    dp[i][j]=(dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1])%mod;
                }
            }
        }
        return dp[0][s.length()-1];
    }
    /**
     * 题号：689
     * 难度：困难
     * 时间：20211208
     * 给你一个整数数组 nums 和一个整数 k ，找出三个长度为 k 、互不重叠、且 3 * k 项的和最大的子数组，并返回这三个子数组。
     *
     * 以下标的数组形式返回结果，数组中的每一项分别指示每个子数组的起始位置（下标从 0 开始）。如果有多个结果，返回字典序最小的一个。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,1,2,6,7,5,1], k = 2
     * 输出：[0,3,5]
     * 解释：子数组 [1, 2], [2, 6], [7, 5] 对应的起始下标为 [0, 3, 5]。
     * 也可以取 [2, 1], 但是结果 [1, 3, 5] 在字典序上更大。
     * 示例 2：
     *
     * 输入：nums = [1,2,1,2,1,2,1,2,1], k = 2
     * 输出：[0,2,4]
     * 分析：在值相同的情况下是选择下标更小的数字，分析每次选择的状态转移
     * 第一次进行选择dp[i][0]=max(dp[i-3][0],sum(i))
     * 第二次进行选择dp[i][1]=max(dp[i-3][1],dp[i-3][0]+sum(i))
     * 第三次进行选择dp[i][2]=max(dp[i-3][2],dp[i-3][1]+sum(i))
     * 但是要找出选择元素相应的下标
     * 解题：
     */
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        //其中dp[i][0]表示的是长度为i的数组第一次选择下标时所得到的最大值
        int[][] dp=new int[nums.length][3];
        //用于保存选择结果的下标
        int[][][] result =new int[nums.length][3][3];
        //对状态数组进行初始化，因为前三个下标不可能循环的到
        dp[0][0]=sum(nums,0,k);
        result[0][0][0]=0;
       for(int i=1 ;i<k;i++){
           int sum =sum(nums,i,k);
           if(dp[i-1][0]>=sum){
               dp[i][0] = dp[i-1][0];
               result[i][0][0] =result[i-1][0][0];
           }else{
               dp[i][0]=sum;
               result[i][0][0] =i;
           }
        }
        //由于dp是要使用前面的dp所以遍历方向是自前向后
        for(int i =k ;i<nums.length-k+1;i++){
            int sum =sum(nums,i,k);
            //第一次选择并不需要跳k个位置
            if(dp[i-1][0]>=sum){
                dp[i][0] =dp[i-1][0];
                result[i][0][0]=result[i-1][0][0];
            }else{
                dp[i][0] =sum;
                result[i][0][0]=i;
            }
            //第二次选择时进行状态转移
            if(dp[i-1][1]>=sum+dp[i-k][0]){
                dp[i][1] =dp[i-1][1];
                result[i][1][0]=result[i-1][1][0];
                result[i][1][1]=result[i-1][1][1];
            }else{
                dp[i][1] =dp[i-k][0]+sum;
                result[i][1][0]=result[i-k][0][0];
                result[i][1][1]=i;
            }
            //第三次状态转移
            if(dp[i-1][2]>=dp[i-k][1]+sum){
                dp[i][2] =dp[i-1][2];
                result[i][2][0]=result[i-1][2][0];
                result[i][2][1]=result[i-1][2][1];
                result[i][2][2]=result[i-1][2][2];
            }else{
                dp[i][2] =dp[i-k][1]+sum;
                result[i][2][0]=result[i-k][1][0];
                result[i][2][1]=result[i-k][1][1];
                result[i][2][2]=i;
            }

        }
        return result[nums.length-k][2];
    }
    //计算从下标k开始的length个数的值
    public int sum(int[] nums ,int k,int length){
        int res =0;
       for(int i= k;i<k+length;i++){
           res += nums[i];
       }
       return res;
    }
    /**
     * 树型dp问题
     * 有一棵二叉苹果树，如果树枝有分叉，一定是分两叉，即没有只有一个儿子的节点。这棵树共 NN 个节点，标号 11 至 NN，树根编号一定为 11。
     *
     * 我们用一根树枝两端连接的节点编号描述一根树枝的位置。一棵有四根树枝的苹果树，因为树枝太多了，需要剪枝。但是一些树枝上长有苹果，给定需要保留的树枝数量，求最多能留住多少苹果。
     */
    public int solution(){
        return 0;

    }
    /**
     * 题号：887
     * 难度：困难
     * 时间：20220417
     * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
     *
     * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
     *
     * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
     *
     * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
     */
    public int superEggDrop(int k, int n) {
        //dp[i][j]表示的时第i个鸡蛋时j层楼所用的最少操作数
        int[][] dp =new int[n+1][k+1];
        //因为我们不确定是那层楼层是鸡蛋会碎的楼层所以所有的楼层都有可能，所以我们需要涵盖所有的情况来进行判断
        //不妨设我们在x层丢下鸡蛋，如果丢下第i颗鸡蛋碎了那么我们可以确定的是鸡蛋少了一个而且楼层f必定是在该层之下
        //也就是dp[i-1][x-1],如果第x层丢下的第i颗鸡蛋没有碎那么鸡蛋的个数也没有变，而且我们确定楼层f必定在n-f之间
        //用状态表示也就是dp[i][n-x],因为我们要确定所有的情况都要包含在内以确保正确性，所以我们需要取得是这两个数之间
        //的最大值也就是max(dp[i-1][x-1],dp[i][n-x])


        return 0;
    }
    /**
     * 题号：96
     * 难度：中等
     * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
     */
    public int numTrees(int n) {
        //dp[i]表示节点个数为i个时所拥有的二叉树的种数
        int[] dp = new int[n+1];
        //初始化动态数组
        dp[1]=1;
        for(int i=2;i<dp.length;i++){
            //首先对于单支树
            dp[i]=2*dp[i-1];
            //其次对于有左右子树的树
            for(int j=1;j<i-1;j++){
                dp[i] += dp[j]*dp[i-1-j];
            }
        }
        return dp[n];
    }

    /**
     * 题号：95
     * 难度：中等
     * 时间：20220517
     * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
     */
    public List<TreeNode> generateTrees(int n) {
        if(n <1){
           return null;
        }
        List<TreeNode> ans = buildTree(1,n);
       return ans;
    }
    public List<TreeNode> buildTree(int start , int end ){
        List<TreeNode> ans = new ArrayList<>();
        if(end == start){
            ans.add(new TreeNode(end));
            return ans;
        }else if(end <start ){
            ans.add(null);
            return ans;
        }
        //枚举所有的根节点
        for(int i =start;i<=end ;i++){
            List<TreeNode> leftNodes =buildTree(start,i-1);
            List<TreeNode> rightNodes =buildTree(i+1,end);
            for(TreeNode leftNode:leftNodes){
                for(TreeNode rightNode:rightNodes){
                    TreeNode node =new TreeNode(i);
                    node.left=leftNode;
                    node.right=rightNode;
                    ans .add(node);
                }
            }
        }
     return ans;

    }
    /**
     * 题号：131
     * 难度：中等
     * 时间：20220524
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     *
     * 回文串 是正着读和反着读都一样的字符串。
     */
    int len;
    boolean[][] dp;
    List<List<String>> res = new ArrayList<>();
    List<String> ans = new ArrayList<>();
    public List<List<String>> partition(String s) {
        len = s.length();
        //设置二维数组其中dp[i][j]表示的是由i到j组成的字符串是回文字符串
        dp = new boolean[len][len];
        //对dp数组进行初始化可以确定一个字符的一定是回文字符串
        for(int i =0;i<len;i++){
            dp[i][i]=true;
        }
        for(int i =len-1 ;i>=0;i--){
            for(int j =i+1;j<len;j++){
                if(dp[i][j]){
                    continue;
                }
                if(j-1>=0 && i+1<len && i+1<j-1){
                    dp[i][j]=((s.charAt(j) == s.charAt(i)) && dp[i+1][j-1]);
                }else if(j-1>=0 && i+1<len && j-1<=i+1){
                    dp[i][j]=(s.charAt(j) == s.charAt(i))&&true;
                }
            }
        }
        backTrace(s,0);
        return res;
    }
    //回溯法进行遍历所有的可能
    public void backTrace(String s ,int pos){
        if(pos == len){
            res.add(new ArrayList<>(ans));
            return;
        }
        for(int i =pos ;i<len ;i++){
            if(dp[pos][i]){
                ans.add(s.substring(pos,i+1));
                backTrace(s,i+1);
                ans.remove(ans.size()-1);
            }
        }
    }

    /**
     * 题号：45
     * 难度：中等
     * 时间：20220605
     *给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     *
     * 假设你总是可以到达数组的最后一个位置。
     */
    public int jump(int[] nums) {
        int len =nums.length;
        //其中dp[i]表示的是由位置i到末位的移动步数
        int[] dp =new int[len];
        Arrays.fill(dp,10001);
        dp[len-1] =0;
        for(int i =len -2;i>=0;i--){
            int temp = nums[i];
            for(int j =1;j<=temp && j+i<=len-1;j++){
                dp[i]=Math.min(dp[i],dp[i+j]+1);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        dp.jump(new int[]{2,3,1,1,4});
    }
}

