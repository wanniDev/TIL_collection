package me.java.leetcode.ch01_recurrsion.ch01_00_principal_of_recursion;

import me.java.leetcode.ListNode;

public class P01_ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        // basecase
        if (head == null || head.next == null)
            return head;
        ListNode result = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return result;
    }
}
