package com.runx.ds;

/**
 * @author: kisszpy
 * @date: 2020/3/15
 */
public class LinkedList<E> {

    private int size ;


    private Node head;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(E e) {
        add(0,e);
    }

    public void addLast(E e) {
        add(size,e);
    }

    /**
     * 1->2->3->4->5
     * insert 4
     *
     * @param index
     * @param e
     */
    public void add(int index,E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("参数错误");
        }
        if (head == null) {
            head = new Node(e);
            size++;
            return;
        }
        Node node = new Node(e);
        Node temp = head;
        for (int i = 0; i < index -1; i++) {
            temp = temp.next;
        }
        node.next = temp.next;
        temp.next = node;
        size++;
    }

    /**
     * 1->2->3->4->5
     * insert 4
     * @param
     */
    public void remove(int index) {
        Node temp = head;
        for (int i = 0;i<index -1;i++) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        size--;
        // 先找到被删除元素之前的元素
        // temp.next = current.next;

    }

    public boolean contains(E e) {
        return get(e) == -1;
    }

    public int get(E e) {
        int index = 0;
        boolean flag = false;
        Node temp = head;
        while (temp!=null) {
            if (temp.data.equals(e)){
                flag = true;
                break;
            }
            temp = temp.next;
            index ++;
        }
        return flag ? index : -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node temp = head;
        while (temp != null) {
            builder.append(temp.data).append("->");
            temp = temp.next;
        }
        builder.append("NULL");
        return builder.toString();
    }

    private class Node {

        public Node(E data) {
            this.data = data;
        }
        /**
         * 数据域
         */
        private E data;
        /**
         * 指针域
         */
        private Node next;
    }
}
