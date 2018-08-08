# Java基础

### 1. Java中的几种基本数据类型是啥，各占用多少字节？

答：float=4字节, char=2字节, boolean=1字节, double=8字节, byte=1字节, short=2字节, int=4字节, long=8字节

### 2. String 能被继承吗？为什么？

答：不能。因为class上有final修饰符

### 3. String， Stringbuffer， StringBuilder 的区别。

答：String对象是常量。Stringbuilder和StringBuffer是可变的。String单独赋值的时候会在常量池中寻找常量，存在即返回指向该常量的引用，否则新建一个常量并返回引用。
StringBuilder和StringBuffer适合大量字符串拼接，在拼接过程中（.append方法调用）不会新建String常量，在toString时才会新建。而StringBuilder线程不安全，StringBuffer是线程安全的。
java8环境下在进行字符串拼接的时候，执行：
```java
String a = "aaa";
String b = "bbb";
String c = a + b;
```
反编译能看到：
```
16: new           #7                  // class java/lang/StringBuilder
19: dup
20: invokespecial #8                  // Method java/lang/StringBuilder."<init>":()V
23: aload_2
24: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
27: aload_3
28: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
31: invokevirtual #10                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
34: astore        4
```
可知进行字符串拼接时编译器实际上new了一个StringBuilder对象，执行append之后toString。而toString是一个new的动作。
```java
String a = "aaa";
String b = "bbb";
String c = a + b;
String d = a + b;
System.out.println(c == d); // false
```
编译器已经优化了a+b了，但是还是不太智能哦。

### 4. ArrayList 和 LinkedList 有什么区别。

ArrayList是数组结构，LinkedList是链表结构。
数组根据下标（index）查询快，直接从0开始算内存偏移量即可；但是插入删除慢，因为需要重新计算插入、删除后的偏移量；
链表对位置没有那么多讲究。插入很快（断开链接，将链接两端节点与新节点建立关系即可）；查询很慢（需要从头节点或尾节点循环一遍）。

### 5. 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当 new 的时候， 他们的执行顺序。

1. 父类静态成员和静态初始化块，按在代码中出现的顺序依次执行
2. 子类静态成员和静态初始化块，按在代码中出现的顺序依次执行
3. 父类实例成员和实例初始化块，按在代码中出现的顺序依次执行
4. 父类构造方法
5. 子类实例成员和实例初始化块，按在代码中出现的顺序依次执行
6. 子类构造方法

结论：对象初始化的顺序，先静态方法，再构造方法，每个又是先基类后子类。

### 6. 用过哪些 Map 类，都有什么区别，HashMap 是线程安全的吗,并发下使用的 Map 是什么，他们内部原理分别是什么，比如存储方式， hashcode，扩容， 默认容量等。

HashMap,LinkedHashMap,TreeMap。
* HashMap数据结构为哈希表，查询很快，但是无序；线程不安全
* LinkedHashMap维护两个数据结构，一个链表一个哈希表，链表保证遍历时能维持插入顺序。线程不安全
* TreeMap数据结构为红黑树，是一种平衡二叉树，能保证插入数据有序（特定顺序），并且在查某一个数据的性能不会太差。

并发下使用的是concurrentHashMap。线程安全在jdk6/7中的实现方式是分段锁。jdk8中的实现方式是CAS。

详解：(http://www.importnew.com/22007.html)
#### 1.1.设计思路
ConcurrentHashMap采用了分段锁的设计，只有在同一个分段内才存在竞态关系，不同的分段锁之间没有锁竞争。
相比于对整个Map加锁的设计，分段锁大大的提高了高并发环境下的处理能力。
但同时，由于不是对整个Map加锁，导致一些需要扫描整个Map的方法（如size(), containsValue()）需要使用特殊的实现，
另外一些方法（如clear()）甚至放弃了对一致性的要求。

ConcurrentHashMap中的分段锁称为Segment，它即类似于HashMap（JDK7与JDK8中HashMap的实现）的结构，即内部拥有一个Entry数组，数组中的每个元素又是一个链表；
同时又是一个ReentrantLock（Segment继承了ReentrantLock）。
ConcurrentHashMap中的HashEntry相对于HashMap中的Entry有一定的差异性：HashEntry中的value以及next都被volatile修饰，这样在多线程读写过程中能够保持它们的可见性，代码如下：

```
static final class HashEntry<K,V> {
        final int hash;
        final K key;
        volatile V value;
        volatile HashEntry<K,V> next;
```
#### 1.2.并发度（Concurrency Level）
并发度可以理解为程序运行时能够同时更新ConccurentHashMap且不产生锁竞争的最大线程数，
实际上就是ConcurrentHashMap中的分段锁个数，即Segment[]的数组长度。
ConcurrentHashMap默认的并发度为16，但用户也可以在构造函数中设置并发度。当用户设置并发度时，
ConcurrentHashMap会使用大于等于该值的最小2幂指数作为实际并发度（假如用户设置并发度为17，实际并发度则为32）。
运行时通过将key的高n位（n = 32 – segmentShift）和并发度减1（segmentMask）做位与运算定位到所在的Segment。
segmentShift与segmentMask都是在构造过程中根据concurrency level被相应的计算出来。

如果并发度设置的过小，会带来严重的锁竞争问题；如果并发度设置的过大，
原本位于同一个Segment内的访问会扩散到不同的Segment中，CPU cache命中率会下降，从而引起程序性能下降。
（文档的说法是根据你并发的线程数量决定，太多会导性能降低）

#### 未完待续

### 7. JAVA8 的 ConcurrentHashMap 为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计。
在ConcurrentHashMap中使用了一个包含16个锁的数组，每个锁保护所有散列桶的1/16，
其中第N个散列桶由第（N mod 16）个锁来保护。假设使用合理的散列算法使关键字能够均匀的分部，
那么这大约能使对锁的请求减少到越来的1/16。
也正是这项技术使得ConcurrentHashMap支持多达16个并发的写入线程。
带来的问题是，维护多个锁来实现独占访问将更加困难而且开销更加大。
<b color="red">
自己设计需要考虑。好难不会做哦
</b>
### 8. 有没有有顺序的 Map 实现类， 如果有， 他们是怎么保证有序的。
有 linkedHashMap;在维护hash表基础上，同时又维护了一个链表，已put顺序来往链表尾部插数据，链表可以保证有序。

### 9. 抽象类和接口的区别，类可以继承多个类么，接口可以继承多个接口么,类可以实现多个接口么。
* 抽象类中方法能实现，接口不能
* 类只能继承一个类
* 接口可以继承多个接口
* 类可以实现多个接口

### 10. 继承和聚合的区别在哪。
？？？

### 11. IO 模型有哪些， 讲讲你理解的 nio， 他和 bio， aio 的区别是啥，谈谈 reactor 模型。
linux IO模型：五种。
* blocking IO（阻塞IO）
* nonblocking IO（非阻塞IO）
* IO multiplexing（多路复用IO）
* signal driven IO（信号驱动式IO）
* asynchronous IO（异步IO）
<p>
nio:多路复用 一个selector管理多个channel。要使用Selector，得向Selector注册Channel，然后调用它的select()方法。
这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，事件的例子有如新连接进来，数据接收等。
</p>
<p>
reactor模型：个人认为和发布-订阅、观察者模式类似。
服务A告诉观察者我要监听这个端口的读事件。
观察者发现读事件发生时，将读到数据放到buffer中，通知服务A。
响应式嘛。
</p>

### 12. 反射的原理，反射创建类实例的三种方式是什么。
