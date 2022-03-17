package com.lwc.round1;

public class DaySix {
    public static void main(String[] args) {
        DaySix six=new DaySix();
       /* Node node1 =new Node();
        node1.val=1;
        Node node2 =new Node();
        node2.val=2;
        Node node3 =new Node();
        node3.val=3;
        Node node4 =new Node();
        node4.val=4;
        Node node5 =new Node();
        node5.val=5;
        Node node6 =new Node();
        node6.val=6;
        Node node7 =new Node();
        node7.val=7;
        Node node8 =new Node();
        node8.val=8;
        Node node9 =new Node();
        node9.val=9;
        Node node10 =new Node();
        node10.val=10;
        Node node11 =new Node();
        node11.val=11;
        Node node12 =new Node();
        node12.val=12;
        node1.next=node2;
        node2.prev=node1;
        node2.next=node3;
        node3.prev=node2;
        //node3的子节点
        node3.child=node7;
        node7.next=node8;
        //node8的子节点
        node8.child=node11;
        node11.next=node12;
        node12.prev=node11;
        node8.prev=node7;
        node8.next=node9;
        node9.prev=node8;
        node9.next=node10;
        node10.prev=node9;
        //node3的邻接节点
        node3.next=node4;
        node4.prev=node3;
        node4.next=node5;
        node5.prev =node4;
        node5.next=node6;
        node6.prev=node5;
        six.flatten(node1);*/
        //six.minDistance("sea", "eat");
        System.out.println(six.numDecodings("7*9*3*6*3*0*5*4*9*7*3*7*1*8*3*2*0*0*6*"));
        //six.numDecodings2("7*9*3*6*3*0*5*4*9*7*3*7*1*8*3*2*0*0*6*");
    }
    /**
     * 题号 430
     * 难度 中等
     * 多级双向链表中，除了指向下一个节点和前一个节点指针之外，它还有一个子链表指针，可能指向单独的双向链表。这些子列表也可能会有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
     *
     * 给你位于列表第一级的头节点，请你扁平化列表，使所有结点出现在单级双链表中。
     *分析：该题类似于图的深度优先遍历，也就是先遍历完该节点的所有子节点然后再对其相邻节点进行遍历
     * 可以考虑使用队列来进行存储节点进行遍历即可,可以考虑用迭代法进行遍历，每一次只处理同一水平上的一条链，
     * 当有子链时可以通过递归处理子链从而返回结果
     * 解法：
     *  
     *

     * @param head
     * @return
     */
    public Node flatten(Node head) {
        //考虑边界情况
        if(head ==null ){
            return null;
        }
        Node result =null;
        Node j =head;
        Node m =null;
        //正常情况下
        while(j != null){
            Node node=new Node();
            node.val=j.val;
            if(result == null){
                result =node;
                m= node;
            }else{
                m.next=node;
                node.prev=m;
                m=node;
            }
            if(j.child !=null){
                m.next=flatten(j.child);
                m.next.prev=m;
                m=m.next;
                //到达子链的最后一个节点处
                while(m.next != null){
                    m=m.next;
                }
            }
            j=j.next;
        }

        return result;
    }
    /**
     * 题号 583
     * 难度 中等
     * 给定两个单词 word1 和 word2，找到使得 word1 和 word2 相同所需的最小步数，每步可以删除任意一个字符串中的一个字符。
     * 分析：两个字串相同，首先字串的长度一定相同，所以要进行删除的话肯定是对较长的的字符串进行删除，删除时肯定是对字符串1中出现了
     * 而2中并没有出现的进行删除或者相反，如果所有字符都出现于两个字符串之中则需要确定字符串出现的位置，则比较出现的次数从而确定需要删除的字母
     * 解法：采用动态规划法，因为每删除一个字符之后剩下来的两个字符串进行同样的操作，但是关键就是如何确定需要删除字符的位置
     * 该问题可以简化为找到一个字符使得左右两边的字符与字符串2相同
     */
    public int minDistance(String word1, String word2) {
        if(word1.equals("") ){
            return word2.length();
        }
        if(word2.equals("") ){
            return word1.length();
        }
       //其中temp[i][j] 表示word1以第i个字符为结尾，和word2以第j个字符位结尾达到相等所需要删除的最少的字符数
        int[][] temp=new int[word1.length()+1][word2.length()+1];
        //递归考虑该问题可以思考成当以temp[i][j]为temp[i-1][j] ,temp[i-1][j-1]和temp[i][j-1]中最小的值加上到temp[i][j]
        //所需要的删除的字符的最小数量
        //对状态数组进行初始化，因为temp[0][j]表示的word2以第j个字符串为结尾到达空字符串的需要删除的数量，同样temp[i][0]
        for(int j=0;j< word1.length()+1 ;j++){
            temp[j][0]=j;
        }
        for(int j=0;j< word2.length()+1 ;j++){
            temp[0][j]=j;
        }
        for( int i =1;i<word1.length()+1 ; i++){
            for (int j =1 ;j <word2.length()+1 ; j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    //如果第i个位置上的字符与第j个位置上的字符相同时，那么需要删除的字符个数就是temp[i-1][j-1]的个数
                    temp[i][j]=temp[i-1][j-1];
                }else{
                    //如果第i个字符上的字符不想同时那么就有可能是删除temp[i][j-1]+1和temp[i-1][j]+1 或者是temp[i-1][j-1]+2中的最小值
                    temp[i][j]=Math.min(temp[i][j-1]+1,Math.min(temp[i-1][j]+1,temp[i-1][j-1]+2));
                }
            }
        }

        return temp[word1.length()][word2.length()];
    }


/**
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
 * 注意:
 *
 * 0 < s1.length, s2.length <= 1000。
 * 所有字符串中的字符ASCII值在[97, 122]之间。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-ascii-delete-sum-for-two-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。n
 */

    /**
     * 题号 639
     * 难度 困难
     * 一条包含字母 A-Z 的消息通过以下的方式进行了编码：
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
     *
     * "AAJF" 对应分组 (1 1 10 6)
     * "KJF" 对应分组 (11 10 6)
     * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
     *
     * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，相当于解码该字符串可以表示的任何编码消息。
     *
     * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
     *
     * 由于答案数目可能非常大，返回对 109 + 7 取余 的结果。
     *
     * 分析：跟据题目中的问题可以大致理解为，1-26对应26个字母，单对这26个数字进行解码有两种解法，但是同时对数字的分割不同，也有不同的
     * 解读，但是分割时零不能出现在首位，同时当数字后带*号是表示的是以该数字为开头的所有可能的数字跟据以上原则解读字符串所有能解读的
     * 出来的种数
     * 解法：由于题目上标题是动态规划，所以考虑动态规划，需要找出状态转移方程，以及状态数组初试化条件，将该问题转化为求最值问题
     * 可以将字符的每个位对应状态数组，而最后的结果应当是前面所有组成编码的最大值,在之前的基础上新增加一个字符并不只是算该字符的编码
     * 种数，而是整个字符串重新组合的结果，且该字符串最多两个字符组合，排除零开头的双字符，和*号开头的数同时temp[i]和temp[i-1]的组合数之和
     *  
     */
    public int numDecodings(String s) {
        //当字符串为空串是只有一种解析
        if(s.equals("") || s.length()==0){
            return 1;
        }
        //先确定单字符的所有组合情况
        long[] temp=new long[s.length()];
        //而后对双字符进行整体计算
        if(s.charAt(0) == '*'){
            temp[0]=9;
        }else if(s.charAt(0) >'0' ||s.charAt(0)<=9){
            temp[0]=1;
        }else{
            temp[0]=0;
        }

        for(int i=1 ;i<s.length();i++){
            //如果第i个字符为*时考虑其所有的情况
            if(s.charAt(i) == '*' ){
                //先考虑单个字符解析的情况
                temp[i] += temp[i-1]*9;
                //再考虑双字符的情况
                if(s.charAt(i-1) == '*'){
                    //考虑当i-1与i个字符都为*时其解析情况
                    if(i == 1){
                        //当i等于1时总共有也就是只有前面两个字符就可以直接赋值为15
                        temp[i] += 15;
                    }else{
                        //解析情况为前面的i-2个字符解析情况乘以15
                        temp[i] += temp[i-2]*15;
                    }
                }else if(s.charAt(i-1) == '1'){
                    //如果此时i-1的字符为1则这时解析有九种可能11-19
                    if(i == 1){
                        temp[i] +=9;
                    }else{
                        temp[i] += temp[i-2]*9;
                    }
                }else if(s.charAt(i-1) == '2'){
                    //如果第i-1个字符为2时此时解析的字符有6种可能即21-26
                    if(i == 1){
                        temp[i] +=6;
                    }else{
                        temp[i] += temp[i-2]*6;
                    }
                }
                //如果是零以外的其他字符则不用考虑字符串解析
            }else if(s.charAt(i) >='0' && s.charAt(i)<= '6'  ){
                //如果第i个在0，6之间时先考虑单个字符的解析
                if(s.charAt(i) != '0'){
                    //因为0不可以进行编码
                    temp[i] +=temp[i-1]*1;
                }
                //考虑双字符解析
                if(s.charAt(i-1) == '*'){
                    //如果i-1为*是则i-1可以选择1或则2
                    if(i == 1){
                        temp[i] +=2;
                    }else{
                        temp[i] += temp[i-2]*2;
                    }
                }else if(s.charAt(i-1) >= '1' && s.charAt(i-1)<= '2'){
                    //若第i-1个字符在1到2之间时则只有一种解析方式
                    if(i == 1){
                        temp[i] +=1;
                    }else{
                        temp[i] += temp[i-2];
                    }
                }

            }else if(s.charAt(i) >= '7' && s.charAt(i) <= '9'){
                //如果第i个在7,9之间时先考虑单个字符的解析
                temp[i] +=temp[i-1]*1;
                //考虑双字符解析
                if(s.charAt(i-1) == '*' ||s.charAt(i-1) =='1' ){
                    //如果i-1为*是则i-1可以只能选择1,与i-1等于1的情况相同
                    if(i == 1){
                        temp[i] +=1;
                    }else{
                        temp[i] += temp[i-2]*1;
                    }
                }
            }
            temp[i] %= 1000000007;
        }

        return (int)temp[s.length()-1];
    }
    static final int MOD = 1000000007;

    public int numDecodings2(String s) {
        int n = s.length();
        // a = f[i-2], b = f[i-1], c = f[i]
        long a = 0, b = 1, c = 0;
        for (int i = 1; i <= n; ++i) {
            c = b * check1digit(s.charAt(i - 1)) % MOD;
            if (i > 1) {
                c = (c + a * check2digits(s.charAt(i - 2), s.charAt(i - 1))) % MOD;
            }
            a = b;
            b = c;
        }
        return (int) c;
    }

    public int check1digit(char ch) {
        if (ch == '0') {
            return 0;
        }
        return ch == '*' ? 9 : 1;
    }

    public int check2digits(char c0, char c1) {
        if (c0 == '*' && c1 == '*') {
            return 15;
        }
        if (c0 == '*') {
            return c1 <= '6' ? 2 : 1;
        }
        if (c1 == '*') {
            if (c0 == '1') {
                return 9;
            }
            if (c0 == '2') {
                return 6;
            }
            return 0;
        }
        return (c0 != '0' && (c0 - '0') * 10 + (c1 - '0') <= 26) ? 1 : 0;
    }


}
class Queue{
    private Node[] quque;
    private int QUQUE_SIZE;
    //队列的头尾指针
    int head=0,tear=0;
    //构造器创建队列
    public Queue(int size){
        this.QUQUE_SIZE=size;
        quque =new Node[size];
    }

    //判断队是否为空
    public boolean isEmpty(){
        if( head ==tear ){
            return true;
        }
        return  false;
    }
    public void inQuque(Node node){
        //队满时则直接返回
        if((tear +1) % QUQUE_SIZE == head){
            return ;
        }
        quque[(tear +1) % QUQUE_SIZE]=node;
        tear=(tear +1) % QUQUE_SIZE;
    }
    public Node deQuque(){
        if(tear == head){
            return null;
        }
       head= (head +1) % QUQUE_SIZE;
        return quque[head];

    }
}
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};