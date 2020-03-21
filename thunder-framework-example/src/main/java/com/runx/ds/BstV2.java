package com.runx.ds;

import java.util.Random;

/**
 * @author: kisszpy
 * @date: 2020/3/15
 */
public class BstV2<E extends Comparable> {

    private Node root;

    private int size;


    public int size() {
        return size;
    }

    public void createTree(Object[] items) {
        for(int i =0;i<items.length;i++) {
            add((E) items[i]);
        }
    }

    public static void main(String[] args) {
        BstV2<Integer> bstV2 = new BstV2<>();
        Object[] randomData = new Object[100];
        for (int i = 0;i<100;i++) {
            randomData[i] = new Random().nextInt(1000);
        }
        bstV2.createTree(randomData);
        System.out.println(bstV2.size());
    }

    public void add(E e) {
        root = add(root,e);
    }

    private Node add(Node node,E e) {

        if (node == null) {
            size++;
            return new Node(e);
        }else{
            if (e.compareTo(node.data) < 0 ) {
                node.left = add(node.left,e);
            }else {
                node.right = add(node.right,e);
            }
            return node;
        }
    }

    private class Node {

        private E data;

        private Node left;

        private Node right;

        public Node(E data) {
            left = null;
            right = null;
            this.data = data;
        }
    }
}
