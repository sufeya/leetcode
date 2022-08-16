package com.lwc.round4;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/8/3 20:36
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DayTen {
    /**
     * 题号：899
     * 难度：困难
     * 时间：20220803
     * 给定一个字符串 s 和一个整数 k 。你可以从 s 的前 k 个字母中选择一个，并把它加到字符串的末尾。
     *
     * 返回 在应用上述步骤的任意数量的移动后，字典上最小的字符串 。
     * 这题连思考都不会思考
     */
    public String orderlyQueue(String s, int k) {
        int lens = s.length();
        char[] chars = s.toCharArray();
        //如果窗口为一的那么只能穷举所有的可能能的情况判断哪个序列最小
        //正常情况是枚举所有的可能情况然后判断最小的序列值即可
      if(k == 1){
          int len = 0,i = 0 ,j = 1;
          while(i<lens && len<lens && j<lens){

              if(chars[(i+len)%lens] == chars[(j+len)%lens]){
                  len++;
              }else{
                  //当两个字符不相等是说明其中有一个并不复合最优的要求所以
                  //需要重新进行构造
                  if(chars[(i+len)%lens] >chars[(j+len)%lens]){
                      //如果是以(i,i+len-1)为开头的字符串不符合最优的要求,同时可以知道
                      //在相同的len为中取一个为开头一定不会由于j，j+len-1的字符串
                     i=i+len+1;
                  }else {
                      j=j+len+1;
                  }

                  //同时置长度为零
                  len=0;
                  if(i == j){
                      i++;
                  }
              }
          }
          i = Math.min(i, j);
          return s.substring(i) + s.substring(0, i);
      }else{
          //当窗口大于2时可以得到任意的一个序列,所以按照排序来得到的顺序序列时可以知道他时字典
          //序是最小的
          Arrays.sort(chars);
          return new String(chars);
      }
    }

    /**
     * 题号：761
     * 时间：20220808
     * 难度：困难
     *特殊的二进制序列是具有以下两个性质的二进制序列：
     *
     * 0 的数量与 1 的数量相等。
     * 二进制序列的每一个前缀码中 1 的数量要大于等于 0 的数量。
     * 给定一个特殊的二进制序列 S，以字符串形式表示。定义一个操作 为首先选择 S 的两个连续且非空的特殊的子串，然后将它们交换。（两个子串为连续的当且仅当第一个子串的最后一个字符恰好为第二个子串的第一个字符的前一个字符。)
     *
     * 在任意次数的操作之后，交换后的字符串按照字典序排列的最大的结果是什么？
     */
    public String makeLargestSpecial(String s) {
        return processStr(s);
    }
    public String processStr(String str){
        Stack<Character> stack = new Stack<>();
        int countOne =0;
        //逆序排列
        Map<Integer,Integer> map = new TreeMap<>();
        for(int i =0;i<str.length();i++){
            if(str.charAt(i) == '0'){
                if(!stack.isEmpty()){
                    stack.pop();
                    countOne++;
                }else{
                    if(countOne != 0){
                        map.put(i-2*countOne,countOne);
                        countOne=0;
                    }
                }
            }else{
                if(countOne != 0){
                    map.put(i-2*countOne,countOne);
                    countOne=0;
                    stack.clear();
                }
                stack.push(str.charAt(i));
            }
        }

        if(countOne != 0){
            map.put(str.length()-countOne*2,countOne);
        }
        StringBuffer sb = new StringBuffer();
        List<String> subStrs = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            subStrs.add(str.substring(entry.getKey(),entry.getKey()+2*entry.getValue()));
        }
        Collections.sort(subStrs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for(String subs : subStrs){
            sb.append(subs);
        }

       return sb.toString();
    }
    public String makeLargestSpecial2(String s) {
        if(s.length()<=2){
            return s;
        }
        List<String> list = new ArrayList<>();
        int cnt = 0,left=0;
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i) == '1'){
                cnt++;
            }else{
                cnt--;
                if(cnt == 0){
                    //递归时将该子串同样时字典序最大的字符串，这样将每个最大字典序的字符串拼接才可以得到
                    //最大字典序的总字符串
                   list.add('1'+makeLargestSpecial2(s.substring(left+1,i))+'0');
                   left=i+1;
                }
            }
        }
        Collections.sort(list,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        StringBuffer stringBuffer = new StringBuffer();
        for(String str : list){
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    /**
     * 题号：1413
     * 难度：简单
     * 时间：20220809
     * 给你一个整数数组 nums 。你可以选定任意的 正数 startValue 作为初始值。
     *
     * 你需要从左到右遍历 nums 数组，并将 startValue 依次累加上 nums 数组中的值。
     *
     * 请你在确保累加和始终大于等于 1 的前提下，选出一个最小的 正数 作为 startValue 。
     */
    public int minStartValue(int[] nums) {
        int minVal = 10001,sum =0;
        for(int i =0;i<nums.length ;i++){
            sum+=nums[i];
            minVal = Math.min(sum,minVal);
        }
        return minVal>0 ? minVal : Math.abs(minVal)+1;
    }

    /**
     * 题号：640
     * 难度：中等
     * 时间：20220810
     *求解一个给定的方程，将x以字符串 "x=#value" 的形式返回。该方程仅包含 '+' ， '-' 操作，变量 x 和其对应系数。
     *
     * 如果方程没有解，请返回 "No solution" 。如果方程有无限解，则返回 “Infinite solutions” 。
     *
     * 题目保证，如果方程中只有一个解，则 'x' 的值是一个整数。
     */
    public String solveEquation(String equation) {
        String[] ans = new String[]{"No solution","Infinite solutions"};
        String[] equations = equation.split("=");
        //x的系数与常数的系数
        int countX =0,countVal =0,flag =1;
        for(int j = 0;j<equations.length;j++){
            if(j>0){
                flag = -1;
            }
            String str = equations[j]+" ";
            int temp = 0,operate = 1;
            for(int i =0 ;i< str.length();i++){

                char c = str.charAt(i);
                if(c>='0' && c<='9'){
                    temp = temp*10+(c-'0');
                }else if(c == 'x'){
                    if(temp == 0){
                        if(!(i-1>=0 && str.charAt(i-1) == '0')){
                            countX+=operate*flag;
                        }
                    }else{
                        countX+= temp*operate*flag;
                    }
                    temp = 0;
                    operate=1;
                }else if(c == '-'|| c=='+'){
                    countVal += temp*operate*flag;
                    temp=0;
                    if(c=='-'){
                        operate = -1;
                    }else{
                        operate = 1;
                    }
                }else if(c==' '){
                    //最末尾结束未置零只能是数字
                    countVal += temp*operate*flag;
                }
            }
        }
        if(countX == 0 && countVal == 0){
            return ans[1];
        }else if(countX == 0 && countVal != 0){
            return ans[0];
        } else{
            return "x="+(countVal/countX)*-1;
        }
    }

    /**
     * 题号：1417
     * 难度：简单
     * 时间：20220811
     * 给你一个混合了数字和字母的字符串 s，其中的字母均为小写英文字母。
     *
     * 请你将该字符串重新格式化，使得任意两个相邻字符的类型都不同。也就是说，字母后面应该跟着数字，而数字后面应该跟着字母。
     *
     * 请你返回 重新格式化后 的字符串；如果无法按要求重新格式化，则返回一个 空字符串 。
     */
    public String reformat(String s) {
        List<Character> listc = new ArrayList<>();
        List<Character> lists = new ArrayList<>();
        for(int i =0 ;i< s.length();i++){
            char  c = s.charAt(i);
            if(c <= '9'&& c>='0'){
                listc.add(c);
            }else{
                lists.add(c);
            }
        }
        if(listc.size() == 0 || Math.abs(listc.size() - lists.size())>1){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<Math.max(listc.size(),lists.size());i++){
            if(listc.size()>lists.size()){
                sb.append(listc.get(i));
                if(i<lists.size()){
                    sb.append(lists.get(i));
                }
            }else{
                sb.append(lists.get(i));
                if(i<listc.size()){
                    sb.append(listc.get(i));
                }
            }

        }
        return sb.toString();
    }
    //双指针构造法
    public String reformat2(String s) {
        int sumDigtal = 0;
        for(int i = 0 ;i<s.length();i++){
            if(s.charAt(i)<='9' && s.charAt(i)>=0){
                sumDigtal++;
            }
        }
        if(Math.abs(s.length()-2*sumDigtal)>1){
            return "";
        }
        //大于零则字母多，小于零则数字多
        boolean flag = s.length() -2*sumDigtal<0;
        //多的放偶数位，少的放奇数位
        char[] cs = s.toCharArray();
        for(int i =0,j=1;i<cs.length;i+=2){
           if(Character.isDigit(cs[i]) != flag ){
               while(Character.isDigit(cs[j])!=flag){
                    j+=2;
               }
               char c = cs[i];
               cs[i]=cs[j];
               cs[j]=c;
           }
        }
        return  new String(cs);
    }

    /**
     * 题号：1282
     * 难度：中等
     * 时间：20220812
     * 有 n 个人被分成数量未知的组。每个人都被标记为一个从 0 到 n - 1 的唯一ID 。
     *
     * 给定一个整数数组 groupSizes ，其中 groupSizes[i] 是第 i 个人所在的组的大小。例如，如果 groupSizes[1] = 3 ，则第 1 个人必须位于大小为 3 的组中。
     *
     * 返回一个组列表，使每个人 i 都在一个大小为 groupSizes[i] 的组中。
     *
     * 每个人应该 恰好只 出现在 一个组 中，并且每个人必须在一个组中。如果有多个答案，返回其中 任何 一个。可以 保证 给定输入 至少有一个 有效的解。
     */
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<Integer,List<List<Integer>>> map = new HashMap<>();
        for(int i = 0;i<groupSizes.length;i++){
            List<List<Integer>> list =map.get(groupSizes[i]);
            if(list == null ){
                list = new ArrayList<>();
            }
            boolean flag = true;
            for(int j =0;j<list.size();j++){
                List<Integer> temp = list.get(j);
                if(temp.size()<groupSizes[i]){
                    temp.add(i);
                    flag=false;
                    break;
                }
            }
            //如果没有放在集合里面则需要新建一个新的集合放入map中
            if(flag){
                List<Integer> temp = new ArrayList<>();
                temp.add(i);
                list.add(temp);
            }
            map.put(groupSizes[i],list);
        }
        List<List<Integer>> ans =new ArrayList<>();
        for(Map.Entry<Integer,List<List<Integer>>> entry :map.entrySet()){
            for(List<Integer> temp :entry.getValue()){
                ans.add(temp);
            }
        }
        return ans;
    }

    /**
     * 题号：768
     * 难度：困难
     * 时间：20220814
     * 这个问题和“最多能完成排序的块”相似，但给定数组中的元素可以重复，输入数组最大长度为2000，其中的元素最大为10**8。
     *
     * arr是一个可能包含重复元素的整数数组，我们将这个数组分割成几个“块”，并将这些块分别进行排序。之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
     *
     * 我们最多能将数组分成多少块？
     */
    public int maxChunksToSorted(int[] arr) {
        //可以知道倒序就一定要加载一个模块里面
        //顺序就可以分多个模块
       Stack<Integer> stack = new Stack<>();
       for(int i =0;i<arr.length;i++){
           //如果大于栈顶的元素则说明要直接进行分块
           if(stack.isEmpty() || stack.peek()<arr[i]){
               stack.push(arr[i]);
           }else{
               //如果小于栈顶的元素则说明需要进行块融合，直到找到大于该元素的值
               //或者将所有元素合并为止
               int top  = stack.pop();
               while(!stack.isEmpty() && stack.peek()>=arr[i]){
                   stack.pop();
               }
               stack.push(top);
           }
       }
       return stack.size();
    }

    /**
     * 题号：1422
     * 难度：简单
     * 时间：20220814
     * 给你一个由若干 0 和 1 组成的字符串 s ，请你计算并返回将该字符串分割成两个 非空 子字符串（即 左 子字符串和 右 子字符串）所能获得的最大得分。
     *
     * 「分割字符串的得分」为 左 子字符串中 0 的数量加上 右 子字符串中 1 的数量。
     */
    public int maxScore(String s) {
        char[] cs = s.toCharArray();
        int countOne = 0;
        for (char c : cs){
            if(c == '1'){
                countOne++;
            }
        }
        int countTemp=0 ,ans =0;
        for(int i =0;i<cs.length-1;i++){
           if(cs[i] == '0' ){
               countTemp ++;
           }
           int temp= countOne-(i+1-countTemp);
          ans =Math.max(countTemp+(temp<0?0:temp),ans);
        }
        return ans;
    }
    public static void main(String[] args) {
        DayTen ten = new DayTen();
        System.out.println(ten.maxScore("011101"));;
    }
}
