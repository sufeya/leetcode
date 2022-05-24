package com.lwc.round3;

import java.util.*;

public class DayNine {

    /**
     * 题号：591
     * 难度：困难
     * 时间
     * 给定一个表示代码片段的字符串，你需要实现一个验证器来解析这段代码，并返回它是否合法。合法的代码片段需要遵守以下的所有规则：
     *
     * 代码必须被合法的闭合标签包围。否则，代码是无效的。
     * 闭合标签（不一定合法）要严格符合格式：<TAG_NAME>TAG_CONTENT</TAG_NAME>。其中，<TAG_NAME>是起始标签，</TAG_NAME>是结束标签。起始和结束标签中的 TAG_NAME 应当相同。当且仅当 TAG_NAME 和 TAG_CONTENT 都是合法的，闭合标签才是合法的。
     * 合法的 TAG_NAME 仅含有大写字母，长度在范围 [1,9] 之间。否则，该 TAG_NAME 是不合法的。
     * 合法的 TAG_CONTENT 可以包含其他合法的闭合标签，cdata （请参考规则7）和任意字符（注意参考规则1）除了不匹配的<、不匹配的起始和结束标签、不匹配的或带有不合法 TAG_NAME 的闭合标签。否则，TAG_CONTENT 是不合法的。
     * 一个起始标签，如果没有具有相同 TAG_NAME 的结束标签与之匹配，是不合法的。反之亦然。不过，你也需要考虑标签嵌套的问题。
     * 一个<，如果你找不到一个后续的>与之匹配，是不合法的。并且当你找到一个<或</时，所有直到下一个>的前的字符，都应当被解析为 TAG_NAME（不一定合法）。
     * cdata 有如下格式：<![CDATA[CDATA_CONTENT]]>。CDATA_CONTENT 的范围被定义成 <![CDATA[ 和后续的第一个 ]]>之间的字符。
     * CDATA_CONTENT 可以包含任意字符。cdata 的功能是阻止验证器解析CDATA_CONTENT，所以即使其中有一些字符可以被解析为标签（无论合法还是不合法），也应该将它们视为常规字符
     */
    public boolean isValid(String code) {
        //tag 用于存放匹配的标签
        Stack<String> tag = new Stack<>();
        int i =0,len = code.length();
        if(len ==1 ){
            return false;
        }
        //遍历整个字符串
        while(i<len){
            char c = code.charAt(i);

            //<表示为标签并且要判断是否有相对应的>与值匹配,判定其确定是起始标签
            //因为<标签一定不是最后一个字符所以不用考虑是否溢出的问题
            if(c == '<' && i+1<len && code.charAt(i+1) != '/' && code.charAt(i+1) != '!'){
                //找到下一个对应的>
                int temp = i+1;
                while(temp<len){
                    //找到第一个与之匹配的>标签
                   if(code.charAt(temp) == '>'){
                       break;
                   }
                   temp++;
                }
                //如果找不到与之匹配的标签则表示不合法,或者长度不合法
                int tagLen = temp -i-1;
                if(temp == len || !(tagLen>= 1 && tagLen <= 9)){
                    return false;
                }
                //找到了截取标签，并且判断其是否全是大写的,标签名是i到temp-1
                String sTag = code.substring(i+1,temp);
                //不全是大写的话直接返回false
                if(!isUpCase(sTag)){
                    return false;
                }
                //将标签入栈，以匹配下一个配对的标签
                tag.push(sTag);
                i = temp;
            }
            //判断结束标签
           else if(c == '<'  &&  i+1<len && code.charAt(i+1) == '/'){
                int temp = i+2;
                while(temp<len){
                    //找到第一个与之匹配的>标签
                    if(code.charAt(temp) == '>'){
                        break;
                    }
                    temp++;
                }
                int endTagLen =temp -i-2;
                if(temp == len || !(endTagLen>= 1 && endTagLen <= 9)){
                    return false;
                }
                String eTag = code.substring(i+2,temp);
                if(!isUpCase(eTag)){
                    return false;
                }
                //判断上一个标签是否与这一个标签相匹配
                if(tag.isEmpty() || !tag.peek().equals(eTag)){
                    return false;
                }
                //相匹配则直接进行出栈
                tag.pop();
                i=temp;
            }
            //判断是否是code
            else if(c == '<'  && i+1<len && code.charAt(i+1) == '!'){
                if(i+9<len && "<![CDATA[".equals(code.substring(i,i+9))){
                    //如果遇到这个标识符则说明栈中至少有一个标识符
                    if(tag.isEmpty()){
                        return false;
                    }
                    //找到code的结尾字符
                    int temp = i+9;
                    while(temp<len){
                        char tempC =code.charAt(temp);
                        if(tempC == ']' && "]]>".equals(code.substring(temp,temp+3))){
                            break;
                        }
                        temp++;
                    }
                    //如果code字符没有结尾则说明不合法
                    if(temp == len){
                        return false;
                    }
                    i=temp+2;
                }else{
                    return false;
                }
            }
            i++;
            if(tag.isEmpty() && i<len){
                return false;
            }
        }
        //如果栈中还存在标签则说明不符合条件
        if(!tag.isEmpty()){
            return false;
        }
        return true;
    }
    //判断是否全是大写字母
    public boolean isUpCase(String str){
        for(char c:str.toCharArray()){
            if(c>'Z' || c<'A'){
                return false;
            }
        }
        return true;
    }

    /**
     * 题号：937
     * 难度：简单
     * 时间：20220503
     * 给你一个日志数组 logs。每条日志都是以空格分隔的字串，其第一个字为字母与数字混合的 标识符 。
     *
     * 有两种不同类型的日志：
     *
     * 字母日志：除标识符之外，所有字均由小写字母组成
     * 数字日志：除标识符之外，所有字均由数字组成
     * 请按下述规则将日志重新排序：
     *
     * 所有 字母日志 都排在 数字日志 之前。
     * 字母日志 在内容不同时，忽略标识符后，按内容字母顺序排序；在内容相同时，按标识符排序。
     * 数字日志 应该保留原来的相对顺序。
     * 返回日志的最终顺序。
     */
    public String[] reorderLogFiles(String[] logs) {
        int len = logs.length;
        String[] ans = new String[len];

        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1 = o1.split(" ",2);
                String[] str2 = o2.split(" ",2);
                if(str1[1].equals(str2[1])){
                   return sort(0,str1,str2);
                }else{
                  return sort(1,str1,str2);
                }
            }
        });

        for(int i = len-1,j=len-1 ;i>=0 ;i--){
           String[] strs =  logs[i].split(" ");
           //判断标识符之后是否是纯数字
            if(strs[1].matches("[0-9]+")){
                ans[j--]=logs[i];
            }else{
                //字母日志通过set进行排序
                set.add(logs[i]);
            }
        }
        //将set中的东西填入数组中
        int i =0;
        for(String str: set){
            ans[i++] = str;
        }
        return ans;
    }
    public int sort(int flag ,String[] arg1,String[] arg2){
        int i=0;
        char[] chars =arg1[flag].toCharArray();
        char[] chars1 =arg2[flag].toCharArray();
        while(i<chars.length && i<chars1.length){
            if(chars[i]<chars1[i]){
                return -1;
            }else if(chars[i]>chars1[i]){
                return 1;
            }else{
                i++;
            }
        }
        if(chars.length>i){
            return 1;
        }else{
            return -1;
        }
    }
    /**
     * 题号：1823
     * 难度：中等
     * 时间：20220504
     *
     * 共有 n 名小伙伴一起做游戏。小伙伴们围成一圈，按 顺时针顺序 从 1 到 n 编号。确切地说，从第 i 名小伙伴顺时针移动一位会到达第 (i+1) 名小伙伴的位置，其中 1 <= i < n ，从第 n 名小伙伴顺时针移动一位会回到第 1 名小伙伴的位置。
     *
     * 游戏遵循如下规则：
     *
     * 从第 1 名小伙伴所在位置 开始 。
     * 沿着顺时针方向数 k 名小伙伴，计数时需要 包含 起始时的那位小伙伴。逐个绕圈进行计数，一些小伙伴可能会被数过不止一次。
     * 你数到的最后一名小伙伴需要离开圈子，并视作输掉游戏。
     * 如果圈子中仍然有不止一名小伙伴，从刚刚输掉的小伙伴的 顺时针下一位 小伙伴 开始，回到步骤 2 继续执行。
     * 否则，圈子中最后一名小伙伴赢得游戏。
     * 给你参与游戏的小伙伴总数 n ，和一个整数 k ，返回游戏的获胜者。
     */
    public int findTheWinner(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for(int i =1 ;i<=n ;i++){
            list.add(i);
        }
        int i =1,index =0;
        while(list.size()>1){
           index = (index+k-1)%list.size();
           list.remove(index);
        }
        return list.get(0);
    }
    //通过递归把直线的表改为线性的表
    public int findTheWinner2(int n, int k) {
       if(n == 1){
           return 0;
       }
       return (findTheWinner(n-1,k)+k)%n+1;
    }
    //同时可以进行逆推
    public int findTheWinner3(int n, int k){
        //因为只有一个的时候必定是0
        int val = 0;
        //跟据地推可以知道f(n,k) =(f(n-1,k)+k)%n
        //因为0 1，2...k-2 k...n-1 这之间有n-1个数是在f(n,k)删除一个数后的结果，我们可以将前面的k-1个数移到后面
        //化成一个连续的序列也即k....n-1,0，1，2,....k-2后面加的k-1个数可以如果化成连续的可以变成
        //k.....n-1.n%n,(n+1)%n .....(n+k-2)%n,此时也就相当于((0......n-2)+k)%n,也即f(n,k)=(f(n-1,k)+k)%n
        //这里如果直接化成线性的1....k-1 k+1....n    k+1 ... n 1. ..k-1      k+1..... n 1+n ...n+k-1
         for(int i = 0 ;i<=n;i++){
             val =(val+k+1)%i;
         }
         return val;
    }

    /**
     * 题号：713
     * 难度：中等
     * 时间：20220505
     * 给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int ans = 0,temp =1;
        for(int left =0 ,right =0;right<nums.length;right++){
            temp*=nums[right];
            //缩小窗口将不合法的进行排除
            while(left<=right && temp>=k){
                temp/=nums[left++];
            }
            ans += right-left+1;
        }
        return ans;
    }

    /**
     * 题号：567
     * 难度：中等
     * 时间：20220505
     * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
     *
     * 换句话说，s1 的排列之一是 s2 的 子串 。
     * 分析：s1的排列也即在固定长的窗口内是否包含有s1的所有字符，如果是则说明包含了s1的排列
     */
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(),len2=s2.length();
        Map<Character,Integer> map = new HashMap<>();
        Map<Character,Integer> map1= new HashMap<>();
        //将s2的字符放入map1中
        for(int i =0 ;i<len1;i++){
            map1.put(s1.charAt(i),map1.getOrDefault(s1.charAt(i),0)+1);
        }
        for(int i =0 ,j =0;i<len2;i++){
            map.put(s2.charAt(i),map.getOrDefault(s2.charAt(i),0)+1);
            while(j+len1-1<i){
                //将最右端的字符去除
                char c =s2.charAt(j);
                int k= map.get(c)-1;
                if( k== 0){
                    map.remove(c);
                }else{
                    map.put(c,k);
                }
                j++;
            }
            boolean flag =true;
            //判断map中是否拥有s1中所有的字符
            for(Map.Entry<Character,Integer> entry : map1.entrySet()){
                if(entry.getValue() != map.get(entry.getKey())){
                    flag=false;
                    break;
                }
            }
            //移动窗口
            if(flag){
                return true;
            }
        }

        return  true;
    }
    /**
     * 题号：433
     * 难度：中等
     * 时间：20220507
     *基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。
     *
     * 假设我们需要调查从基因序列 start 变为 end 所发生的基因变化。一次基因变化就意味着这个基因序列中的一个字符发生了变化。
     *
     * 例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。
     * 另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。
     *
     * 给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使 start 变化为 end 所需的最少变化次数。如果无法完成此基因变化，返回 -1 。
     *
     * 注意：起始基因序列 start 默认是有效的，但是它并不一定会出现在基因库中。
     */
    public int minMutation(String start, String end, String[] bank) {
        char[] chars = new char[]{'A','C','G','T'};
        Set<String> set = new HashSet<>();
        for(String str : bank){
            set.add(str);
        }
        return findRes(start,end,set,chars);
    }
    public int findRes(String start ,String end,Set<String> set,char[] chars){
        if(start.equals(end)){
            return 0;
        }
        for(int i =0 ;i<start.length();i++){
            StringBuffer sb =new StringBuffer(start);
            for(char c:chars){
                if(sb.charAt(i) == c){
                    continue;
                }
                sb.deleteCharAt(i);
                sb.insert(i,c);
                String temp =sb.toString();
                //如果包含则可以进行深度遍历
                if(set.contains(temp)){
                    set.remove(temp);
                    int res =  findRes(temp,end,set,chars);
                    if(res == -1){
                       continue;
                    }else{
                        return res+1 ;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 题号：442
     * 难度：中等
     * 时间：20220508
     * 给你一个长度为 n 的整数数组 nums ，其中 nums 的所有整数都在范围 [1, n] 内，且每个整数出现 一次 或 两次 。请你找出所有出现 两次 的整数，并以数组形式返回。
     *
     * 你必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间的算法解决此问题。
     */
    public List<Integer> findDuplicates(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        List<Integer> ans = new ArrayList<>();
        for(int i :nums){
            if(map.getOrDefault(i,0)+1==2){
                ans.add(i);
            }else{
                map.put(i,map.getOrDefault(i,0)+1);
            }
        }
        return ans;
    }
    public List<Integer> findDuplicates2(int[] nums){
        List<Integer> ans = new ArrayList<>();
        for(int i = 0;i<nums.length;i++){
            int n = Math.abs(nums[i]);
            if(nums[n-1]<0){
                ans.add(n);
            }else{
                nums[n-1]= -nums[n-1];
            }
        }
        return ans;
    }
    /**
     * 题号：942
     * 难度：简单
     * 时间：20220509
     * 由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
     *
     * 如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I' 
     * 如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D' 
     * 给定一个字符串 s ，重构排列 perm 并返回它。如果有多个有效排列perm，则返回其中 任何一个 。
     */
    public int[] diStringMatch(String s) {
        int len =s.length();
        int[] ans = new int[len+1];
        int max =len ,min=0;
        for(int i =0;i<len ;i++){
            if(s.charAt(i) == 'I'){
                ans[i]=min;
                min++;
            }else{
                ans[i]=max;
                max--;
            }
        }
        ans[len] = max;
        return ans;
    }

    /**
     * 题号：1728
     * 难度：困难
     * 时间：20220510
     * 一只猫和一只老鼠在玩一个叫做猫和老鼠的游戏。
     *
     * 它们所处的环境设定是一个 rows x cols 的方格 grid ，其中每个格子可能是一堵墙、一块地板、一位玩家（猫或者老鼠）或者食物。
     *
     * 玩家由字符 'C' （代表猫）和 'M' （代表老鼠）表示。
     * 地板由字符 '.' 表示，玩家可以通过这个格子。
     * 墙用字符 '#' 表示，玩家不能通过这个格子。
     * 食物用字符 'F' 表示，玩家可以通过这个格子。
     * 字符 'C' ， 'M' 和 'F' 在 grid 中都只会出现一次。
     * 猫和老鼠按照如下规则移动：
     *
     * 老鼠 先移动 ，然后两名玩家轮流移动。
     * 每一次操作时，猫和老鼠可以跳到上下左右四个方向之一的格子，他们不能跳过墙也不能跳出 grid 。
     * catJump 和 mouseJump 是猫和老鼠分别跳一次能到达的最远距离，它们也可以跳小于最大距离的长度。
     * 它们可以停留在原地。
     * 老鼠可以跳跃过猫的位置。
     * 游戏有 4 种方式会结束：
     *
     * 如果猫跟老鼠处在相同的位置，那么猫获胜。
     * 如果猫先到达食物，那么猫获胜。
     * 如果老鼠先到达食物，那么老鼠获胜。
     * 如果老鼠不能在 1000 次操作以内到达食物，那么猫获胜。
     * 给你 rows x cols 的矩阵 grid 和两个整数 catJump 和 mouseJump ，双方都采取最优策略，如果老鼠获胜，那么请你返回 true ，否则返回 false 。
     */
    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        return false;
    }

    /**
     * 题号：913
     * 难度：困难
     * 时间：20220511
     * 两位玩家分别扮演猫和老鼠，在一张 无向 图上进行游戏，两人轮流行动。
     *
     * 图的形式是：graph[a] 是一个列表，由满足 ab 是图中的一条边的所有节点 b 组成。
     *
     * 老鼠从节点 1 开始，第一个出发；猫从节点 2 开始，第二个出发。在节点 0 处有一个洞。
     *
     * 在每个玩家的行动中，他们 必须 沿着图中与所在当前位置连通的一条边移动。例如，如果老鼠在节点 1 ，那么它必须移动到 graph[1] 中的任一节点。
     *
     * 此外，猫无法移动到洞中（节点 0）。
     *
     * 然后，游戏在出现以下三种情形之一时结束：
     *
     * 如果猫和老鼠出现在同一个节点，猫获胜。
     * 如果老鼠到达洞中，老鼠获胜。
     * 如果某一位置重复出现（即，玩家的位置和移动顺序都与上一次行动相同），游戏平局。
     * 给你一张图 graph ，并假设两位玩家都都以最佳状态参与游戏：
     *
     * 如果老鼠获胜，则返回 1；
     * 如果猫获胜，则返回 2；
     * 如果平局，则返回 0 。
     * 解释：该题目自己不会看完了好久的解析才勉强看懂
     */
    int DRAW= 0,MOUSE_WIN=1,CAT_WIN=2;
    int CAT_TURN=0,MOUSE_TURN=1;
    int[][] graph;
    //其中degree[i][j][cat]表示的是猫在i点老鼠在j点猫可能移动的度数
    int[][][] degree;
    //result[i][j][cat]表示的是猫在i点老鼠在j点猫先行的结果
    int[][][] result;
    public int catMouseGame(int[][] graph) {
        int len = graph.length;
        this.graph = graph;
        degree = new int[len][len][2];
        //默认都是平局，因为除了必赢必输之外只有平局
        result = new int[len][len][2];
        //队列用于保存那些已经确定结果的状态，用于逆推
        Queue<int[]> queue = new ArrayDeque<>();
        //初始化猫和老鼠在各个状态可能走的方向，且猫不可能走向0点
        for(int i =0;i<len ;i++){
            for(int j =0;j<len;j++){
                degree[i][j][CAT_TURN]= graph[i].length;
                degree[i][j][MOUSE_TURN]= graph[j].length;
            }
        }
        //因为猫不可能走向零点所以要将该度数减一
        //对于所有连向零点的节点都需要对猫减少一个度数,不管老鼠在那个位置
        for(int node : graph[0]){
            for(int  i =0 ;i<len;i++){
                degree[node][i][CAT_TURN]--;
            }
        }
        //初始化结果,只要老鼠的位置是在0则不管是谁操作都是老鼠赢了
        for(int i=1;i<len ;i++){
            result[i][0][CAT_TURN]=MOUSE_WIN;
            result[i][0][MOUSE_TURN]=MOUSE_WIN;
            queue.offer(new int[]{i,0,CAT_TURN});
            queue.offer(new int[]{i,0,MOUSE_TURN});
        }
        //只要老鼠跟猫的位置相同则不管是那个位置则都是老鼠必赢
        for(int i =1;i<len;i++){
            result[i][i][CAT_TURN] = CAT_WIN;
            result[i][i][MOUSE_TURN]= CAT_WIN;
            queue.offer(new int[]{i,i,CAT_TURN});
            queue.offer(new int[]{i,i,MOUSE_TURN});
        }
        //跟据已有的结果进行反推
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int catPos = state[0],mousePos = state[1],turn=state[2];
            int res = result[catPos][mousePos][turn];
            List<int[]> preStates = getPreState(catPos,mousePos,turn);
            for(int[] preState: preStates){
                int preResult = result[preState[0]][preState[1]][preState[2]];
                //如果前面的结果不是平局的话久不需要跟据当前的结果去逆推前面的结果
                if(preResult == DRAW){
                   //当前角色必赢的情况有上一轮是猫的移动，当前状态是猫赢，或者上一轮是老鼠的移动，当前赢是老鼠赢
                    boolean win = (preState[2] == CAT_TURN && res == CAT_WIN) || (preState[2] == MOUSE_TURN && res == MOUSE_TURN);
                    if(win){
                        result[preState[0]][preState[1]][preState[2]] =res;
                        //将该结果移入到确认结果的队列中
                        queue.offer(new int[]{preState[0],preState[1],preState[2]});
                    }else{
                        degree[preState[0]][preState[1]][preState[2]]--;
                        //如果当前一轮状态的下一轮都是必输的局面时，则表明前一轮只能是必输的
                        if(degree[preState[0]][preState[1]][preState[2]] == 0){
                            int loseResult = preState[2] == CAT_TURN ? MOUSE_WIN : CAT_WIN;
                            result[preState[0]][preState[1]][preState[2]] =loseResult;
                            queue.offer(new int[]{preState[0],preState[1],preState[2]});
                        }
                    }
                }
            }
        }

        return result[1][2][MOUSE_TURN];
    }
    public List<int[]> getPreState(int cat ,int mouse,int turn){
        List<int[]> preState = new ArrayList<>();
        //跟据当前的操作判断上一轮的操作是谁
        int preTurn = turn == MOUSE_TURN ? CAT_TURN:MOUSE_TURN;
        if(preTurn == MOUSE_TURN){
            for(int node : graph[mouse]){
                preState.add(new int[]{cat,node,MOUSE_TURN});
            }
        }else{
            for(int node :graph[cat]){
                if(node == 0){
                    continue;
                }
                preState.add(new int[]{node,mouse,CAT_TURN});
            }
        }
        return preState;
    }
    public static void main(String[] args) {
        DayNine nine=new DayNine();
        System.out.println(nine.findDuplicates2(new int[]{4,3,2,7,8,2,3,1}));
    }

}
