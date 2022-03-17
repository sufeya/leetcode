package com.lwc.round1;

import java.util.*;

public class DayThree {
    public static void main(String[] args) {
       /* TreeNode treeNode =new TreeNode();
        treeNode.val=0;
        TreeNode treeNode1 =new TreeNode();
        treeNode1.val=1;
        TreeNode treeNode2 =new TreeNode();
        treeNode2.val=2;
        TreeNode treeNode3 =new TreeNode();
        treeNode3.val=3;
        TreeNode treeNode4 =new TreeNode();
        treeNode4.val=4;
        TreeNode treeNode5 =new TreeNode();
        treeNode5.val=5;
       treeNode2.right =treeNode4;
       treeNode2.left=treeNode1;
       treeNode1.left=treeNode;
       treeNode4.right=treeNode5;
       treeNode4.left=treeNode3;
        deleteNode(treeNode2,2);*/
        DayThree three = new DayThree();
        //  three.frequencySort("sttteeded");
        int[] nums = new int[]{1,1000000000};
        //three.quickSort(nums,nums.length-1,0);,

        System.out.println(three.minMoves(nums));
    }

    /**
     * 题号 450
     * 难度 中
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
     * <p>
     * 一般来说，删除节点可分为两个步骤：
     * <p>
     * 首先找到需要删除的节点；
     * 如果找到了，删除它。
     * 分析：对于删除二叉树的节点首先应当是找到该节点，考虑到二叉排序树的属性，可以定义两个方法，第一现根据二叉树找到该节点，找到之后跟据该节点的左右子树的大小ch
     * 重新构造该树
     */
    public static TreeNode deleteNode(TreeNode root, int key) {
        HashMap<String, TreeNode> keyRoots = findNode(root, key, null);

        //若找不到该节点，则不必删除
        if (keyRoots.size() <= 0) {
            return root;
        }
        //若存在该节点
        if (keyRoots.size() > 0) {
            TreeNode fRoot = keyRoots.get("child");
            TreeNode fPre = keyRoots.get("parent");
            //如果找到的节点是根节点时
            if (fPre == null) {
                //以右节点为根重新构建二叉树
                root = reBuildTree(fRoot.left, fRoot.right);
            }
            //如果不是根节点时
            if (fPre != null) {
                if (fPre.left == fRoot) {
                    fPre.left = reBuildTree(fRoot.left, fRoot.right);
                }
                if (fPre.right == fRoot) {
                    fPre.right = reBuildTree(fRoot.left, fRoot.right);
                }

            }
        }
        return root;
    }

    public static TreeNode reBuildTree(TreeNode root1, TreeNode root2) {
        //若两个节点均不为空则正常插入
        if (root1 != null && root2 != null) {
            //若其左子树不为空
            if (root2.left != null) {
                //若root2的左子树的值大于root1的值，则插入root2的左子树的右子树
                if (root1.val >= root2.left.val) {
                    root2.left.right = reBuildTree(root1, root2.left.right);
                }
                //若root2的左子树的值小于root1的值,则插入root2左子树的左子树
                if (root1.val < root2.left.val) {
                    root2.left.left = reBuildTree(root1, root2.left.left);
                }
            }
            //若要插入节点的左子树为空则直接将root1插入其左子树
            if (root2.left == null) {
                root2.left = root1;
            }
            return root2;
        }
        if (root1 == null && root2 != null) {
            return root2;
        }
        if (root1 != null && root2 == null) {
            return root1;
        }
        return null;
    }

    //寻找该目标节点
    public static HashMap<String, TreeNode> findNode(TreeNode root, int key, TreeNode pre) {
        HashMap<String, TreeNode> keyRoot = new HashMap<>();

        //如果该根节点为空时则直接返回空
        if (root == null) {
            return keyRoot;
        }
        //若该根节点的值要小于key的值，则在其右子树中进行查找
        if (root.val < key) {
            pre = root;
            keyRoot = findNode(root.right, key, pre);
        }
        //若该根节点的值要大于key的值，则在其左子树中进行查找
        if (root.val > key) {
            pre = root;
            keyRoot = findNode(root.left, key, pre);
        }
        if (root.val == key) {
            if (pre != null) {
                keyRoot.put("parent", pre);
            }
            if (root != null) {
                keyRoot.put("child", root);
            }
        }
        return keyRoot;
    }
    //在寻找目标节点的同时还要找到其父节点，这样才能对该节点进行删除

    /**
     * 题号 451
     * 难度 中等
     * 给定一个字符串，请将字符串里的字符按照出现的频率降序排列。
     * <p>
     * 分析：统计字符出现的次数，可以通过设置一个hashMap用于存储字符，以字符内容为键以出现的次数为值进行统计，统计完之后
     * ，定义一个新的数据类型对数据进行存储，然后对其进行排序，然后跟据数值大小可对字符进行重复输出从而获取
     */
    public String frequencySort(String s) {
        //定义一个hashMap对字符进行存储并统计
        Map<String, Integer> map = new HashMap<>();
        char[] charArray = s.toCharArray();
        //遍历整个字符数组对字符进行存储
        for (char cha : charArray) {
            map.put(cha + "", map.getOrDefault(cha + "", 0) + 1);
        }
        //创建一个集合，存储字符出现的次数
        List<Node> order = new ArrayList<>();
        int i = 0;
        //将map中的数据取出,存入node中然后存入list中，对list进行排序
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Node node = new Node();
            node.s = entry.getKey();
            node.i = entry.getValue();
            order.add(node);
        }
        //这里不一定需要node节点进行存储可以通过键的值对键进行排序，排序的时候获取map中对应的值进行比较就可以
        //获得所需要的节点顺序
        //List<String> characters =new ArrayList<String>(map.keySet());
        order.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.i <= o2.i)
                    return 1;
                return -1;
            }
        });
        //排完序之后就跟据排序的结果生成新的字符串
        StringBuilder sb = new StringBuilder();
        for (Node node : order) {
            for (int j = 0; j < node.i; j++) {
                sb.append(node.s);
            }
        }
        String str = sb.toString();
        return str;

    }

    class Node {
        public Node() {

        }

        public String s;
        public Integer i;
    }

    /**
     * 题目 453
     * 难度 简单
     * 给定一个长度为 n 的 非空 整数数组，每次操作将会使 n - 1 个元素增加 1。找出让数组所有元素相等的最小操作次数。
     * <p>
     * 分析 ：每次对n-1个元素加一之后都对其进行排序，至于最后所有的是否相等可以根据首字符跟尾字符是否相等进行判断
     * 解后感： 这个问题我的思考还是太过死板，感觉没有进行太多对问题的思考然后就进行解题，以至于对问题的解法仅限于一步一步的
     * 进行累加，答案却有很多种
     * 数学法：移动的次数其实就相当于所有元素到最小值次数的累加，也就是sum(A)(数组所有值的累加) -min*n(n为数组的长度)
     * 动态规划法：
     */
    public int minMoves(int[] nums) {
        int times = 0;
        quickSort(0, nums.length-1, nums);
        /*while (nums[0] != nums[nums.length - 1]) {
            //每次增加最大最小元素之间的差值，以便减少循环次数
            int diff =nums[nums.length-1] -nums[0];
            for (int i = 0; i <= nums.length - 2; i++) {
                nums[i] = nums[i] + diff;
            }
            //先对数组进行排序
            quickSort(0, nums.length-1, nums);
            times+= diff;
        }*/
        /**
         * 由于每次为n-1个元素增加完最大最小值之间的差值之后，n-2个元素就变成了原先的最大值所以以此
         * 顺序进行递推即可,所以所有的次数就（倒序）等于前一个元素与后一个元素之间的差值
         */
        for(int i=nums.length-1;i>0;i--){
            times += nums[i]-nums[0];
        }
        return times;
    }
    //对数组进行快速排序
    public void quickSort(int low, int high, int[] nums) {
        if (low >= high) {
            return;
        }
        int pre = nums[low];
        int i = low, j = high;
        while (i < j) {
            while (i < j && nums[j] >= pre) {
                j--;
            }
            nums[i] = nums[j];

            while (i < j && nums[i] <= pre) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i] = pre;
        quickSort(low, i - 1, nums);
        quickSort(i + 1, high, nums);

    }

    /**
     * Mike, Ellie, Rohan, Fatma四个人参加自行车比赛，
     * 分别获得1-4名，他们每个人骑不同颜色的自行车（红，紫，蓝，绿）。
     * 基于以下线索，谁获得了第二名？Ellie骑紫色的车，Rohan没有骑绿色的车。
     * Mike和Ellie不是第一，也不是最后。骑蓝色车的人领先Fatma。骑绿色车的人领先骑紫色车的人。
     */
    public void competeSpeach() {
        //设置一个二维数组表示四个人骑车的情况,设置红为0紫为1蓝为2绿为3
        String[] colors = new String[]{"red", "purper", "blue", "green"};
        String[] orders = new String[]{"1", "2", "3", "4"};
        String[] names = new String[]{"Mike", "Ellie", "Rohan", "Fatma"};
        List<HashMap<String, String>> peoples = new ArrayList<>();
        for (String color : colors) {
            for (String order : orders) {
                for (String name : names) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", name);
                    map.put("color", color);
                    map.put("order", order);
                    peoples.add(map);
                }
            }
        }

        Iterator<HashMap<String, String>> iterator = peoples.iterator();

        while (iterator.hasNext()) {
            HashMap<String, String> people = iterator.next();
            if (people.get("name").equals("Ellie") && !(people.get("color").equals("purper")) || !(people.get("name").equals("Ellie")) && people.get("color").equals("purper")) {
                iterator.remove();
            } else {
                if (people.get("name").equals("Rohan") && people.get("color").equals("green")) {
                    iterator.remove();
                } else {
                    if (people.get("name").equals("Ellie") && (people.get("order").equals("1") || people.get("order").equals("4"))) {
                        iterator.remove();
                    } else {
                        if (people.get("name").equals("Mike") && (people.get("order").equals("1") || people.get("order").equals("4"))) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        for (int i = peoples.size() - 1; i > 0; i--) {
            HashMap<String, String> people = peoples.get(i);
            for (int j = peoples.size() - 1; j > 0; j--) {
                HashMap<String, String> people2 = peoples.get(j);
                if (people.get("color").equals("blue")) {
                    if ((people2.get("name").equals("Fatma") && (Integer.parseInt(people.get("order")) >= Integer.parseInt(people2.get("order"))))) {
                        peoples.remove(people);
                    }
                }
                if (people.get("name").equals("Fatma")) {
                    if ((people2.get("color").equals("blue")) && (Integer.parseInt(people.get("order")) <= Integer.parseInt(people2.get("order")))) {
                        peoples.remove(people);
                    }
                }

                if (people.get("color").equals("green")) {
                    if (people2.get("color").equals("purper") && Integer.parseInt(people.get("order")) >= Integer.parseInt(people2.get("order"))) {
                        people.remove(people);
                    }
                }
                if (people.get("color").equals("purper")) {
                    if (people2.get("color").equals("green") && Integer.parseInt(people.get("order")) <= Integer.parseInt(people2.get("order"))) {
                        people.remove(people);
                    }
                }
            }
        }

        System.out.println(peoples);

    }


}
