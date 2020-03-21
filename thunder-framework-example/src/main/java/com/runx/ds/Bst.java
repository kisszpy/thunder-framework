package com.runx.ds;

/**
 * @author: kisszpy
 * @date: 2020/3/15
 */
public class Bst<E extends Comparable> {

    private Node root;

    private int size;


    public int size() {
        return size;
    }

    public void insert(Node node , E e) {

    }

    public void add(E e) {
        if (root == null) {
            root = new Node(e);
            size++;
            return;
        }else {
            add(root,e);
        }
    }

    private void add(Node node,E e) {
        if (e.equals(node.data)) {
            return;
        }
        if (e.compareTo(node.data) < 0 && node.left == null) {
            // left
            node.left = node;
            size++;
            return;
        } else if (e.compareTo(node.data) > 0 && node.right == null) {
            // right
            node.right = node;
            size++;
            return;
        }
        if (e.compareTo(node.data) < 0) {
            add(node.left,e);
        }else {
            add(node.right,e);
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
