package com.lwc.common;

/**
 * @author liuweichun
 * @date 2022/11/10 19:17
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

   public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
