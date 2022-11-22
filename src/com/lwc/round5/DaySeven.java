package com.lwc.round5;


import com.lwc.common.ListNode;

import java.util.*;

/**
 * @author liuweichun
 * @date 2022/11/1 17:20
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class DaySeven {
    /**
     * 题号：418
     * 难度：中等
     * 时间：20221101
     * 神奇字符串 s 仅由 '1' 和 '2' 组成，并需要遵守下面的规则：
     *
     * 神奇字符串 s 的神奇之处在于，串联字符串中 '1' 和 '2' 的连续出现次数可以生成该字符串。
     * s 的前几个元素是 s = "1221121221221121122……" 。如果将 s 中连续的若干 1 和 2 进行分组，可以得到 "1 22 11 2 1 22 1 22 11 2 11 22 ......" 。每组中 1 或者 2 的出现次数分别是 "1 2 2 1 1 2 1 2 2 1 2 2 ......" 。上面的出现次数正是 s 自身。
     *
     * 给你一个整数 n ，返回在神奇字符串 s 的前 n 个数字中 1 的数目。
     */
    public int magicalString(int n) {
        if(n<4){
            return 1;
        }
      int[] chars = new int[n];
        chars[0] = 1;
        chars[1] = 2;
        chars[2] = 2;
        int res = 1,j = 3,i=2;
        while(j<n){
            int  fill = chars[j-1] -1 == 0 ? 2:1;
         if(chars[i] == 1){
             chars[j] = fill;
             i++;
             j++;
             if(fill == 1){
                 res++;
             }
         }else{
            chars[j] = fill;
            if(j+1<n){
                chars[j+1] = fill;
                if(fill == 1){
                    res+=2;
                }
                j+=2;
                i++;
            }else{
                if(fill == 1){
                    res++;
                }
               i++;
               j++;
            }
         }
        }
        return res;
    }

    /**
     * 给你两个字符串数组 word1 和 word2 。如果两个数组表示的字符串相同，返回 true ；否则，返回 false 。
     *
     * 数组表示的字符串 是由数组中的所有元素 按顺序 连接形成的字符串。
     */
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder sb = new StringBuilder();
        for(String str : word1){
            sb.append(str);
        }
        StringBuilder sb2 = new StringBuilder();
        for(String str :word2){
            sb2.append(str);
        }
        if(sb2.toString().equals(sb.toString())){
            return true;
        }
        return false;
    }

    /**
     * 给你一个数组 towers 和一个整数 radius 。
     *
     * 数组  towers  中包含一些网络信号塔，其中 towers[i] = [xi, yi, qi] 表示第 i 个网络信号塔的坐标是 (xi, yi) 且信号强度参数为 qi 。所有坐标都是在  X-Y 坐标系内的 整数 坐标。两个坐标之间的距离用 欧几里得距离 计算。
     *
     * 整数 radius 表示一个塔 能到达 的 最远距离 。如果一个坐标跟塔的距离在 radius 以内，那么该塔的信号可以到达该坐标。在这个范围以外信号会很微弱，所以 radius 以外的距离该塔是 不能到达的 。
     *
     * 如果第 i 个塔能到达 (x, y) ，那么该塔在此处的信号为 ⌊qi / (1 + d)⌋ ，其中 d 是塔跟此坐标的距离。一个坐标的 信号强度 是所有 能到达 该坐标的塔的信号强度之和。
     *
     * 请你返回数组 [cx, cy] ，表示 信号强度 最大的 整数 坐标点 (cx, cy) 。如果有多个坐标网络信号一样大，请你返回字典序最小的 非负 坐标。
     */
    public int[] bestCoordinate(int[][] towers, int radius) {
        int xMax = -1,yMax = -1;
        int maxRes = -1;
        int resX = -1,resY = -1;
        for(int[] temp : towers){
            xMax = Math.max(temp[0],xMax);
            yMax = Math.max(temp[1],yMax);
        }
        for(int x = 0;x<=xMax;x++){
            for(int y = 0;y<=yMax;y++){
                int g = 0;
                for(int[] temp : towers){
                    double dis = Math.sqrt((x-temp[0])*(x-temp[0]) + (y-temp[1])*(y-temp[1]));
                    if(dis>radius){
                        continue;
                    }
                     g += Math.floor(temp[2]/(1+dis));
                }

                if(g>maxRes){
                    maxRes = g;
                    resX = x;
                    resY = y;
                }
            }
        }

        return new int[]{resX,resY};
    }
    public int[] bestCoordinate2(int[][] towers, int radius) {
        int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE;
        for (int[] tower : towers) {
            int x = tower[0], y = tower[1];
            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
        }
        int cx = 0, cy = 0;
        int maxQuality = 0;
        for (int x = 0; x <= xMax; x++) {
            for (int y = 0; y <= yMax; y++) {
                int[] coordinate = {x, y};
                int quality = 0;
                for (int[] tower : towers) {
                    int squaredDistance = getSquaredDistance(coordinate, tower);
                    if (squaredDistance <= radius * radius) {
                        double distance = Math.sqrt(squaredDistance);
                        quality += (int) Math.floor(tower[2] / (1 + distance));
                    }
                }
                if (quality > maxQuality) {
                    cx = x;
                    cy = y;
                    maxQuality = quality;
                }
            }
        }
        return new int[]{cx, cy};
    }
    public int getSquaredDistance(int[] coordinate, int[] tower) {
        return (tower[0] - coordinate[0]) * (tower[0] - coordinate[0]) + (tower[1] - coordinate[1]) * (tower[1] - coordinate[1]);
    }
    public int reachNumber(int target) {
        int aTarget = Math.abs(target);
        //通过解二次方程确定大于target的最小整数位置
        // (n+1)*n /2 = target  2*target = n^2 +n - 2*target  =0 -1 +1+8*target
        int n = (int)Math.ceil((Math.sqrt(1.0+8.0*aTarget)-1)/2) ;
        int delt = (n+1)*n/2 -aTarget;
        //如果超过的距离是偶数时，直接选择delt/2时往回走即可
        if(delt %2 == 0){
            return n;
        }
        //如果相差的距离是奇数的话
        //当当前位置是偶数,要使得delt为偶数，则向前再走一步即为奇数加上原来的delt为奇数
        //即为偶数
        if(n%2 == 0){
            return n+1;
        }
        //如果当前位置是奇数 ，要使得delt为偶数，则需要往前走两步，即是加上n+1 偶数，n+2偶数
        //之后delt即为偶数
        return n+2;

    }


    public String interpret(String command) {
        Map<String,String> map = new HashMap<>();
        map.put("G","G");
        map.put("()","o");
        map.put("(al)","al");
        StringBuilder sb = new StringBuilder();
        int len = command.length();
        for(int i=0,j=0;j<=len;j++){
            String str = command.substring(i,j);
            if(map.containsKey(str)){
                sb.append(map.get(str));
                i=j;
            }
        }
        return sb.toString();
    }
    public boolean parseBoolExpr(String expression) {
        //操作符栈
        Stack<Character> operator = new Stack<>();
        Stack<Character> operated = new Stack<>();
        //操作数栈
        for(int i =0;i<expression.length();i++){
            char c = expression.charAt(i);
            if(c == '|' || c == '&' || c == '!'){
                operator.push(c);
            }else if(c == ')'){
                char o = operator.pop();
                if(o != '!'){
                    if(!operated.isEmpty() && operated.peek() != '('){
                        char  res = operated.pop();
                        while(!operated.isEmpty() && operated.peek() != '('){
                            res = caculate(o,res,operated.pop());
                        }
                        if(!operated.isEmpty() && operated.peek() == '('){
                            operated.pop();
                            operated.push(res);
                        }
                    }
                }else{
                    char r = operated.pop();
                    char res;
                    if(r == 'f'){
                        res = 't';
                    }else{
                        res = 'f';
                    }
                    if(!operated.isEmpty() && operated.peek() == '('){
                        operated.pop();
                        operated.push(res);
                    }
                }

            }else{
                if(c != ','){
                    operated.push(c);
                }
            }
        }
        return operated.pop() == 't'? true:false;
    }
    public char caculate(char op,char c1,char c2){
        if(op == '&'){
            if(c1 == 't' && c2 == 't'){
                return 't';
            }else{
                return 'f';
            }
        }else {
            if(c1 == 't' || c2 == 't' ){
                return 't';
            }else{
                return 'f';
            }
        }
    }

    /**
     *给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tail = head,target = null,pre = null;
        int count = 1;
        while(tail.next != null){
            if(count == n){
               target = head;
            }
           tail = tail.next;
           count++;
           if(count>n){
               pre = target;
               target = target.next;
           }
        }
        if(target != null ){
            if(pre != null){
                pre.next = target.next;
            }else{
                head = target.next;
            }
        }else{
            head = head.next;
        }
        return head;
    }

    /**
     * 题号：864
     * 难度：困难
     * 时间：20221110
     * 给定一个二维网格 grid ，其中：
     *
     * '.' 代表一个空房间
     * '#' 代表一堵
     * '@' 是起点
     * 小写字母代表钥匙
     * 大写字母代表锁
     * 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
     *
     * 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母。换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
     *
     * 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
     *
     */
    public int shortestPathAllKeys(String[] grid) {
        int[][] move = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
        int n = grid.length,m = grid[0].length(),countKey = 0,step = 0,getKey = 0;
        Set<Integer> set = new HashSet();
        Set<Character> keys = new HashSet<>();
        int[] start = new int[2];
        //先遍历总共有多少把钥匙
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
             char c = grid[i].charAt(j);
             if(c>='a' && c<='j'){
                 countKey++;
             }
             if(c == '@'){
                 start[0]=i;
                 start[1]=j;
             }
            }
        }
        //广度优先遍历寻找路径，通过set进行剪枝
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);

        set.add(hashKey(start[0],start[1],m));
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size>0 && !queue.isEmpty()){
                int[] pos = queue.poll();
                for(int[] temp : move){
                    int nx = pos[0] + temp[0],ny = pos[1]+temp[1];
                    if(nx >= n || ny>= m || nx<0 || ny<0){
                        continue;
                    }
                    int hash = hashKey(nx,ny,m);
                    char c = grid[nx].charAt(ny);
                    //空房间且没有走过可以进去,是锁但是有钥匙可以进,钥匙可以进
                    if(!set.contains(hash)&& (c =='.' || (c <='F' && c>= 'A'&&keys.contains((char)(c+32))) || (c>= 'a' && c<= 'f' ))){
                        set.add(hash);
                        queue.offer(new int[]{nx,ny});
                        if(c>='a' && c <= 'f'){
                            keys.add(c);
                            getKey++;
                            //如果已经获取了所有的钥匙，直接返回步数
                            if(getKey == countKey){
                                return step+1;
                            }
                        }
                    }
                }
                size--;
            }
            step++;
        }
        return getKey == countKey ? step:-1;
    }
    public int hashKey(int n ,int m ,int size){
        return n*size+m;
    }
    //上述解法存在问题，首先广度遍历不同路径收集到所有的钥匙都算作一步，不能对步数进行累加
    //下述解法看完解答之后采用状态压缩的方式进行解题，，通过拿到钥匙之后改变状态从而可以
    //从拿到钥匙的位置进行返程,从而就可以达到路劲累加的效果，同时由该状态到达其他点也不会二次经过
    //某个房间，从而保证最短路径
    public int shortestPathAllKeys2(String[] grid){
        int[][] move = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

        int n = grid.length,m=grid[0].length(),keyCount = 0;
        Map<Character,Integer> map = new HashMap<>();
        int[] start = new int[2];
        //记录钥匙所在，并且找到开始节点
        for(int i = 0;i<n;i++){
            for(int j =0;j<m;j++){
                char c = grid[i].charAt(j);
                if(Character.isLowerCase(c)){
                    map.put(c,keyCount);
                    keyCount++;
                }
                if(c == '@'){
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
        //定义状态数组,其中dist[i][j][k]表示的是当位置为（i，j）时拿到几把钥匙所需要走的路程
        //后续是以（i，j）位置为起点去遍历其他路径 其中k二进制位中的第x位为一时表示的是的第x把钥匙
        //已经拿到
        int[][][] dist = new int[n][m][1<<map.size()];
        //初始化状态数组
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                Arrays.fill(dist[i][j],-1);
            }
        }
        //初始时将起点队
        dist[start[0]][start[1]][0] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{start[0],start[1],0});
        //广度优先遍历
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            for(int[] temp :move){
                int nx = state[0]+temp[0],ny=state[1]+temp[1];

                if(nx>=0&& nx<n && ny>=0&&ny<m && grid[nx].charAt(ny)!='#'&&dist[nx][ny][state[2]] == -1){
                    int mask = state[2];
                    char c = grid[nx].charAt(ny);
                    if (c == '.' || c == '@') {
                        dist[nx][ny][mask] = dist[state[0]][state[1]][mask]+1;
                        queue.offer(new int[]{nx, ny, mask});

                    } else if (Character.isLowerCase(c)) {
                       int idx = map.get(c);
                       int nMask = mask|(1<<idx);
                       if(nMask == (1<<map.size()) -1){
                            return dist[state[0]][state[1]][mask]+1;
                       }
                       dist[nx][ny][nMask] = dist[state[0]][state[1]][mask]+1;
                       queue.offer(new int[]{nx,ny,nMask});
                    }else{
                        //判断是否有钥匙,有钥匙则直接过去
                        int index = map.get(Character.toLowerCase(c));
                        if(((mask>>index)&1) == 1){
                            dist[nx][ny][mask] = dist[state[0]][state[1]][mask]+1;
                            queue.offer(new int[]{nx,ny,mask});
                        }
                    }
                }
            }

        }
        return -1;
    }

    /**
     * 题号：791
     * 难度：中等
     * 时间：20221113
     * 给定两个字符串 order 和 s 。order 的所有单词都是 唯一 的，并且以前按照一些自定义的顺序排序。
     *
     * 对 s 的字符进行置换，使其与排序的 order 相匹配。更具体地说，如果在 order 中的字符 x 出现字符 y 之前，那么在排列后的字符串中， x 也应该出现在 y 之前。
     *
     * 返回 满足这个性质的 s 的任意排列 。给定两个字符串 order 和 s 。order 的所有单词都是 唯一 的，并且以前按照一些自定义的顺序排序。
     *
     * 对 s 的字符进行置换，使其与排序的 order 相匹配。更具体地说，如果在 order 中的字符 x 出现字符 y 之前，那么在排列后的字符串中， x 也应该出现在 y 之前。
     *
     * 返回 满足这个性质的 s 的任意排列 。
     */
    public String customSortString(String order, String s) {
        //两个hash保存字符序
        Map<Character,Integer> sOrder = new HashMap<>();

        for(int i = 0;i<s.length();i++){
            char c = s.charAt(i);
           sOrder.put(c,sOrder.getOrDefault(c,0)+1);
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<order.length();i++){
            char c = order.charAt(i);
            int j = sOrder.getOrDefault(c,0);
            if(j>0){
                while(j>0){
                    sb.append(c);
                    j--;
                }
                sOrder.remove(c);
            }

        }
        //将剩下的直接加入
        for(Map.Entry<Character,Integer> entry :sOrder.entrySet()){
            int i = entry.getValue();
            while(i>0){
                sb.append(entry.getKey());
                i--;
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        DaySeven seven = new DaySeven();
        ListNode listNode = new ListNode(1);
       int i =  seven.shortestPathAllKeys2(new String[]{"b","A","a","@","B"});
        System.out.println(i);
        //@...a
        //.###A
        //b.BCc
    }
}

