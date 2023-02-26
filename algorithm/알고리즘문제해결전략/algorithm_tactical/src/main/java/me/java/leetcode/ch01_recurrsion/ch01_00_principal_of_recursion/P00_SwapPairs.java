package me.java.leetcode.ch01_recurrsion.ch01_00_principal_of_recursion;

import me.java.leetcode.ListNode;

public class P00_SwapPairs {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode first = head;
        ListNode second = head.next;

        first.next = swapPairs(second.next);
        second.next = first;

        return second;
    }
}
