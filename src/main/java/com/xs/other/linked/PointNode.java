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

    @Override
    public String toString() {
        int count = 0;
        PointNode<V> cursor = this;
        StringBuilder sb = new StringBuilder();
        do {
            count++;
            sb.append(cursor.getData());
            if (cursor.getNext() != null) {
                sb.append(" -> ");
            }
            cursor = cursor.next;
        } while(cursor != null && count < 100);
        return sb.toString();
    }
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
