# HashMap阅读(jdk1.8)

吐槽：1.8的一点都不好读，各种莫名其妙的变量名。

##### 1. putVal方法：

```java
/**
  * Implements Map.put and related methods
  *
  * @param hash hash for key
  * @param key the key
  * @param value the value to put
  * @param onlyIfAbsent if true, don't change existing value 只插入，不修改
  * @param evict if false, the table is in creation mode.  
  * @return previous value, or null if none
  */
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((p = tab[i = (n - 1) & hash]) == null)
          	// 如果hash后的位置上没有node，则直接放进去
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; 
          	K k;
            if (p.hash == hash 
                && ((k = p.key) == key || (key != null && key.equals(k))))
              	// key一致hash也一致则认为要插入的node是已有节点
                e = p;
            else if (p instanceof TreeNode)
              	// 如果p是红黑树的一个节点，则将当前节点也添加进这棵树
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
              	// hash冲突，遍历链表
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                      	// 下一个节点为空，则将当前节点放到链表尾部
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                          	// 如果链表长度>8则将hash表中这个位置上的链表转化为红黑树
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                      	// key一致则认为要插入的node是当前节点。
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold) // threshold默认12
            resize();	// 这个方法更恶心
        afterNodeInsertion(evict);	// HashMap中没有内容，先不管他了。
        return null;
    }
```

##### 2. resize方法

更恶心了。

```Java
/**
 * Initializes or doubles table size.  If null, allocates in
 * accord with initial capacity target held in field threshold.
 * Otherwise, because we are using power-of-two expansion, the
 * elements from each bin must either stay at same index, or move
 * with a power of two offset in the new table.
 *
 * @return the table
 */
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;	// oldCap为哈希表容量
    int oldThr = threshold;	// 默认16？
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) { //MAXIMUM_CAPACITY = 2^30， 一般不可能有这么大
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY) // 容量*2后 <2^30
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY; 	// table.length==0时，初始化成默认16
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); // threshold = 0.75*16=12
    }
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;	// 默认loadFactor=0.75f
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
	// 上面先不管了，反正一般情况就是将容量*2，触发resize的threshold*2。。
    if (oldTab != null) {
      	// 从1开始？
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                  	// 该节点后面没有节点了，直接放
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                  	// 如果e是一棵的根结点，则split（将这棵树拆成一大一小两棵树，具体也懒得看了。。）
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { 
                  	// 如果该节点后面还有节点，保持顺序?loHead,loTail,hiHead,hiTail都是啥？
                  	
                  	/**
                  	* 
比如oldCap=8,hash是3，11，19，27时，(e.hash & oldCap)的结果是0，8，0，8，这样3，19组成新的链表，index为3；而11，27组成新的链表，新分配的index为3+8；
                  	  */
                  	// 进行链表复制
                    // 方法比较特殊： 它并没有重新计算元素在数组中的位置
                    // 而是采用了 原始位置加原数组长度的方法计算得到位置
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                      	// 遍历
                        next = e.next;
                      	// 
                        if ((e.hash & oldCap) == 0) {
                          	// loTail是一个链表，以e为头。
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```
怎么看都不会有并发和数据丢失啊。多线程情况下只会出现resize执行多次的情况。为啥大家都说会有数据丢失的情况？