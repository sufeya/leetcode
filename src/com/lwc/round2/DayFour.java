package com.lwc.round2;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DayFour {
    /**
     * 给你一个字符串 date ，按 YYYY-MM-DD 格式表示一个 现行公元纪年法 日期。请你计算并返回该日期是当年的第几天。
     *
     * 通常情况下，我们认为 1 月 1 日是每年的第 1 天，1 月 2 日是每年的第 2 天，依此类推。每个月的天数与现行公元纪年法（格里高利历）一致。
     */
    public int dayOfYear(String date) {
        int[] monthDays= new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int day =0;
       String[] dates = date.split("-");
       int year =Integer.parseInt(dates[0]);
       int month = Integer.parseInt(dates[1]);
       int days = Integer.parseInt(dates[2]);
       for(int i =0 ;i<month-1;i++){
           day=day+monthDays[i];
       }
       //判断输入的年份是否是闰年，是的话2月份就有29天
       if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
           if(month>2){
               day++;
           }

       }
        return day+days;
    }

    /**
     * 题号：686
     * 难度：中等
     * 时间：20211222
     * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
     *
     * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
     * 分析：首先需要判断a中字符是否包含了b中所有的字符，如果没有包含则表明不能通过重叠字符获得相应的子串序列
     * 包含了，则可以分为两种情况，一种是a的长度大于b的长度，那么有且只有可能a跟据两次重叠就可以使得b为a
     * 的重叠子串，另一种是a的长度小于b的长度那么应当先将字符a重叠到大于b的长度然后再进行判断
     * 解题：
     */
    public int repeatedStringMatch(String a, String b) {
        String str =a;
        //记录重叠的次数
        int res =0;
        //先判断b中是否有a没有的字符
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i<a.length();i++){
            map.put(a.charAt(i)+"",map.getOrDefault(a.charAt(i)+"",0)+1);
        }
        for(int i =0 ;i< b.length();i++){
            if(map.get(b.charAt(i)+"") == null){
                return -1;
            }
        }
        //判断两个字符串的长度,如果a的长度小于b的长度则先将字符a进行增加直到大于b为止
        if(a.length()<b.length()){
            while(str.length()<b.length()){
                str=str+a;
                res++;
            }
        }
        //增加完长度之后再进行字符串匹配
        int i =0 ,k=0,j=0,strLen=b.length(),tempLen = str.length();
        while(k<str.length() && j<strLen && k<=2*tempLen){
          if(str.charAt(k)==b.charAt(j)){
              k++;
              j++;
              if(k >= str.length() && j<strLen){
                  str=str+a;
              }
          }else{
              j=0;
              i++;
              k=i;
          }
        }
        if(j != b.length()){
            return -1;
        }
        return str.length()/a.length();
    }
    public int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) {
            return 0;
        }
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }


    //kmp字符串匹配算法实现
    public int kmp(String a ,String b,int m){
        if(a.equals("") && b.equals("")){
            return 0;
        }
        if(a.equals("") && !b.equals("")){
            return -1;
        }
        if(!a.equals("") && b.equals("")){
            return 0;
        }
        //获取next数组
        int[] next =generateNext(b);
        int j =0;
        //对原始字符串进行匹配
       for(int i =m ;i< a.length() ;i++){
           if(a.charAt(i) == b.charAt(j)){
               j++;
           }else{
               if(next[j] == -1){
                   j=0;
               }else{
                   j=next[j];
                   i--;
               }
           }
           if(j == b.length()){
               return i-b.length()+1;
           }
       }
       return  -1;
    }
    //生成next数组
    public int[] generateNext(String pattern){
        char[] ch =pattern.toCharArray();
        int patternLen =pattern.length();
        //定义next数组的长度
        int[] next =new int[patternLen];
        int len =0 ,j=1;
        while(j<patternLen){
            //如果j指向字符与len指向的字符是否是相等的，如果相等则next[j]=len
            if(ch[len] == ch[j]){
                len++;
                next[j]=len;
                j++;
            }else{
                /**
                 * 我们不妨设next[i]=k,其定义为s[0....k-1] == s[i-k+1...i],同时我们根据next[i-1]的定义其应当为s[0....next[i-1]-1]==s[i-next[i-1].....i-1]
                 * 我们将next[i]的定义的右域向左收缩一个单位得到s[0.....k-2]==s[i-k+1....i-1],不妨设next[i-1]-1<k-2即next[i-1]+1<=k=next[i]也即next[i-1]到k之间
                 * 有两个或两个以上的字符，然而i到i-1之间只存在一个字符，所以必然有next[i-1]+1>=next[i]。
                 * 跟据next[i-1]的定义有s[0....next[i-1]-1] == s[i-next[i-1],i-1]所以如果s[next[i-1]]==s[i]那么next[i]=next[i-1]+1
                 * 这样的话我们求解next[i]就等价于求解最大的j的值使得s[0...j-1]=s[i-j,i-1]如果此时s[j]==s[i]那么就有s[0...j]==s[i-j...i]此时可以的出next[i]=j+1
                 * 毫无疑问当j取得next[i-1]时可以取得最大值即s[next[i-1]]==s[i],当s[next[i-1]]!=s[i]时，跟据上面的推论next[i-1]>=next[i]（这里去掉+1是因为s[next[i-1]]!=s[i]）
                 *设k-1=j那么next[i-1]>j于是我们取s[0...next[i-1]-1]中长度为j的子串所以必有s[next[i-1]-j...next[i-1]-1]==s[i-j.....i-1]，当s[next[i-1]]!=s[i]
                 * 时我们需要找到最大的j的值使得 s[0....j-1] == s[i-j...i-1]也就相当于求s[0....j-1]==s[next[i-1]-j...next[i-1]-1]==s[i-j.....i-1]所以如果
                 * s[next[i-1]]=s[i]那么就可以得到next[i]=next[i-1]
                 */
               if(len>0){
                   len=next[len-1];
               }else{
                   next[j]=0;
                   j++;
               }
            }
        }
        //将整个字符向后移一位
        for(int i =next.length-1;i>0;i--){
            next[i]=next[i-1];
        }
        next[0]=-1;
        return next;
    }
    public int[] getNext(String pattern){
        // 先求出字符串的前缀表
        char[] charArr = pattern.toCharArray();
        int[] next = new int[charArr.length];
        // 因为字符串的第一个元素没有前后缀，所以共有最大字符串长度为0
        next[0] = 0;
        int len = 0;
        int i = 1;
        /*
            i   str          next[i]
            0   A            0
            1   AB           0
            2   ABA          1
            3   ABAB         2
            4   ABABC        0
            5   ABABCA       1
            6   ABABCAB      2
            7   ABABCABA     3
            8   ABABCABAA    1
        */
        while (i < charArr.length){
            // 1.举例：比如这次进来的字符串是上面的AB,此时上一次的共有字符串长度是len=0(因为上一次就一个A字符，没有共有字符串，当然是0)，
            // 要想判断这次共有字符串长度会不会加1，只需要判断这次的字符串AB比上次字符串A多出来的字符(也就是B)是不是和上次共有字符串长度位置上的字符相等
            // 也就是charArr[1(i)] == charArr[0(len)]?，这里是不等，所以不能加1
            // 2.比如这次进来的是ABA，上一次是AB，那么多出来的这个A和上次AB的共有字符串长度位置(len=0)上的字符是否相等，显然charArr[0] == charArr[2]，所以len++;
            // 3.再比如：这次进来的是ABAB,上一次是ABA,上一次的共有字符串长度是len=1，判断这次多出来的字符B是不是和charArr[1]相等，显然相等，len++;
            if(charArr[i] == charArr[len]){
                len++;
                next[i] = len;
                i++;
            }else{
                // 如果不相等，说明这次多出来的这个字符并没有让共有字符串的长度加1，而且，可能还会直接影响到上一次的共有字符串长度
                // 这里的做法是：因为多出来一个字符，而且charArr[i] != charArr[len]，那这次已经不能拿上一次共有字符串位置上的字符来做比较了，必须拿上上一次的结果
                // 比如：这次进来的是ABABC,上一次是ABAB，上一次共有字符串是AB,len=2,那charArr[2(len)]是A，和这次的多出来的C已经不一样了，那上次的len已经不能作为判断依据了，
                // 必须拿上上一次的len,于是i不变，也就是说下一轮循环还是ABABC，但是len要拿上上一轮的长度，也就是AB这个字符串共有字符串的长度值，len=1，
                // 此时charArr[1(len)]是B，还是和C不相同，说明这次的len还是不能作为判断，于是i继续不变，下一轮还是ABABC，len拿A的共有字符串长度值，len=0，
                // 此时charArr[0(len)]是A，还是和C不相同，说明这次的len还是不能作为判断，理论上还得去那更早一次的len值，但是这时候有个临界情况，因为已经拿到第一次进来的len了，
                // len拿不到更早一次的值了，或者说到这已经没有共有字符串了，说明这次加多出来的字符C。彻底让这个字符串ABABC没有了共有字符串，也就是len=0，可以放心的将这一轮字符串
                // 的共有字符串长度设为0了，这轮len值设置完毕，i++，进行下一轮设置
                if(len > 0){
                    len = next[len-1];
                }else{
                    next[i] = len;
                    i++;
                }
            }
        }
        // 到此，前缀表已经设置完毕，但是有个问题，就是next[0]和next[1]的位置一直都是0，为了后续使用的方便，需要将""和只有一个字符的字符串共有前缀区别开，
        // 而且，对共有字符串来说，前缀表的最后一项就是字符串本身的共有字符串长度，这个在实际使用的时候没有意义，所以直接将整个前缀表往后平移一位，空出来的
        // next[0]赋值为-1
        for (int j = next.length  -1; j > 0; j--) {
            next[j] = next[j-1];
        }
        next[0] = -1;
//        for (int m = 0; m < next.length; m++) {
//            System.out.print(next[m] + "");
//        }
        return next;
    }

    /**
     * 题号：1044
     * 难度：困难
     * 时间：20211223
     * 给你一个字符串 s ，考虑其所有 重复子串 ：即，s 的连续子串，在 s 中出现 2 次或更多次。这些出现之间可能存在重叠。
     *
     * 返回 任意一个 可能具有最长长度的重复子串。如果 s 不含重复子串，那么答案为 "" 。
     *
     * 分析：可以通过滑动窗口创建相对应的字符子串，然后跟据该子串对原字符串进行匹配，如果找到则记录其下标然后对其开头下标进行截取
     * 创建新的原始字符串在进行匹配，这样可以避免重复匹配，同时也可以计算出其正确的个数
     * 解题：
     */
    public String longestDupSubstring(String s) {
        int maxRes=0;
        String res = "";
        int sLen =s.length();
        //计算子字符串的数量
        Map<String,Integer> countStr =new HashMap<>();
        //遍历整个字符串获取子串进行匹配，在匹配的过程中，同时要进行截取原字符串重新进行匹配
        for(int i =0;i<sLen;i++){
            for(int j =i+1;j<sLen;j++){
                String str =s.substring(i,j);
                //通过kmp算法找到对应的子串在字符串中的位置
                int index = kmp(s,str,0);
                while(index < sLen){
                    //这里单词划分存在重复计算
                    countStr.put(str,countStr.getOrDefault(str,0)+1);
                    index=kmp(s,str,index+1);
                    if(index == -1){
                        break;
                    }
                }
               if(countStr.get(str) !=null && countStr.get(str)>=2 && maxRes<(j-i+1)){
                   maxRes =j-i+1;
                   res=str;
               }
            }
        }
        return res;
    }

    /**
     * 看完大佬的答案之后的理解，首先对字符串进行处理从而进行前缀和处理这样的话更方便的得到子串的的hash值从而进行hash计数
     * 从而将时间复杂度从c*n到c+n从而达到线性
     * 因为子串长度在[1-n]从而可以对长度进行二分查找
     * @param s
     * @return
     */
    public String longestDupSubstring2(String s) {
        int sLen = s.length();
        //选取质数，用于编码字符串hash
        int P =1313;
        //将字符串转化为前缀值的形式进行保存，从而更好的计算出子串的值
        long[] hashString =new long[s.length()+10];
        //次方数组，更方便计算子串的值
        long[] p =new long[s.length()+10];
        p[0]=1;
        String ans ="";
        //对字符串进行前缀和求取
        for(int i = 0; i<s.length();i++){
            hashString[i+1] = hashString[i]*P +s.charAt(i);
            p[i+1]=P*p[i];
        }
        int l =0,r= sLen;
        //通过二分查找子串的长度进行查找
        while(l<r){
            int mid = l +(r-l)/2;
            String temp =checkString(hashString,p,s,mid);
            //如果存在长度为mid的子串，则可以去查找长度更大的子串
            if(temp.length() !=0){
                l=mid;
            }else{
                //如果不存在长度为mid的子串则应当查找长度更小的子串
                r=mid-1;
            }
            if(ans.length()<temp.length()){
                ans= temp;
            }
        }
        return ans;
    }
    String check(long[] hashString,long[] p,String s, int len) {
        int n = s.length();
        Set<Long> set = new HashSet<>();
        for (int i = 1; i + len - 1 <= n; i++) {
            int j = i + len - 1;
            long cur = hashString[j] - hashString[i - 1] * p[j - i + 1];
            if (set.contains(cur)) return s.substring(i - 1, j);
            set.add(cur);
        }
        return "";
    }


    //对原串进行搜索加入hash，找到长度为len的子串
    public String checkString(long[] hashString,long[] p,String str,int len){
        Map<Long,Integer> map = new HashMap<>();
        //对字符数组进行遍历找到长度为len的所有子串
       for(int i=1 ;i<str.length()-len+1;i++){
           int j =i+len-1;
           //计算出长度为len的子串的hash码值
           long hash = hashString[j] - hashString[i-1]*p[j-i+1];
           Integer temp =map.get(hash);
           if(temp !=null && temp>1){
               return str.substring(i-1,j);
           }
           map.put(hash,map.getOrDefault(hash,0)+1);
        }
        return "";
    }

    public static void main(String[] args) {
        DayFour four=new DayFour();
        //System.out.println(four.kmp("nana","ana"));
        System.out.println(four.longestDupSubstring2("banana"));
    }
}
