package com.runx;

/**
 * @author: kisszpy
 * @date: 2020/3/17
 */
public interface List<E> {

    boolean isEmpty();

    int size();

    void add(int index , E e);

    void addFirst(E e);

    void addLast(E e);

    void remove(E e);

    E get(int index);

    void set(int index, E e);
}
