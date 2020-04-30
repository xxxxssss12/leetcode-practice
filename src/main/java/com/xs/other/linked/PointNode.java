package com.xs.other.linked;

/**
 * 指针
 *
 * @author xs
 * create time:2020-04-29 09:13:36
 */
public class PointNode<V> {
    private V data;
    private PointNode<V> next;


    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public PointNode<V> getNext() {
        return next;
    }

    public void setNext(PointNode<V> next) {
        this.next = next;
    }
}
