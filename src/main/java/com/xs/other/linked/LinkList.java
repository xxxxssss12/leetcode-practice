package com.xs.other.linked;

/**
 * 链表
 * @author xs
 * create time:2020-04-29 09:13:04
 */
public class LinkList<V> {
    private PointNode<V> root;

    public void print() {
        if (root == null) {
            return;
        }
        PointNode<V> point = root;
        StringBuilder result = new StringBuilder();
        do {
            result.append(point.getData() + "");
            point = point.getNext();
            if (point != null) {
                result.append(" -> ");
            }
        } while (point != null);
        System.out.println(result.toString());
    }

    /**
     * 链表反转-头插法
     */
    public void reverseHead() {
        if (root == null) {
            return;
        }

        //创建新的头结点
        PointNode<V> newHead = new PointNode<>();
        PointNode<V> cur = root;
        PointNode<V> next;
        while(cur!=null){
            //将指针先移至当前结点的下个结点，以便后面向后继续循环
            next = cur.getNext();
            //将新的头结点的下一个结点（即null）赋值给当前结点的下一个结点
            cur.setNext(newHead.getNext());
            //将当前节点赋值给新的头结点的下一个结点
            newHead.setNext(cur);
            //将当前节点向后移动
            cur = next;
        }
        root = newHead.getNext();
    }

    /**
     * 链表反转-三指针法
     */
    public void reverse3Point() {
        PointNode<V> current = root;   //这是用于遍历的指针
        PointNode<V> temp = null; //用于存储的指针
        PointNode<V> point = null; //用于交换的指针
        while(current != null){
            point = temp;
            temp = current;
            current = current.getNext();
            temp.setNext(point);
        }
        //得到反转后的Head指针
        root = temp;
    }

    /**
     * 链表反转-递归
     * @param node
     * @return
     */
    public PointNode<V> reverseDigui(PointNode<V> node){
        if(node == null || node.getNext() == null){
            return node;
        }
        //链表最后
        PointNode<V> prev = reverseDigui(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return prev;
    }
    public PointNode<V> getRoot() {
        return root;
    }

    public void setRoot(PointNode<V> root) {
        this.root = root;
    }
}
