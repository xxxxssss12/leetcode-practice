# Java基础

## 1. Java中的几种基本数据类型是啥，各占用多少字节？

答：float=4字节, char=2字节, boolean=1字节, double=8字节, byte=1字节, short=2字节, int=4字节, long=8字节

## 2. String 能被继承吗？为什么？

答：不能。因为class上有final修饰符

## 3. String， Stringbuffer， StringBuilder 的区别。

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
编译器已经优化了a+b了，但是还是不太智能哦。(一个表达式就来一套StringBuilderxxx)

## 4. ArrayList 和 LinkedList 有什么区别。

ArrayList是数组结构，LinkedList是链表结构。
数组根据下标（index）查询快，直接从0开始算内存偏移量即可；但是插入删除慢，因为需要重新计算插入、删除后的偏移量；
链表对位置没有那么多讲究。插入很快（断开链接，将链接两端节点与新节点建立关系即可）；查询很慢（需要从头节点或尾节点循环一遍）。

## 5. 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当 new 的时候， 他们的执行顺序。

1. 父类静态成员和静态初始化块，按在代码中出现的顺序依次执行
2. 子类静态成员和静态初始化块，按在代码中出现的顺序依次执行
3. 父类实例成员和实例初始化块，按在代码中出现的顺序依次执行
4. 父类构造方法
5. 子类实例成员和实例初始化块，按在代码中出现的顺序依次执行
6. 子类构造方法

结论：对象初始化的顺序，先静态方法，再构造方法，每个又是先基类后子类。

## 6. 用过哪些 Map 类，都有什么区别，HashMap 是线程安全的吗,并发下使用的 Map 是什么，他们内部原理分别是什么，比如存储方式， hashcode，扩容， 默认容量等。

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

## 7. JAVA8 的 ConcurrentHashMap 为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计。
在ConcurrentHashMap中使用了一个包含16个锁的数组，每个锁保护所有散列桶的1/16，
其中第N个散列桶由第（N mod 16）个锁来保护。假设使用合理的散列算法使关键字能够均匀的分部，
那么这大约能使对锁的请求减少到越来的1/16。
也正是这项技术使得ConcurrentHashMap支持多达16个并发的写入线程。
带来的问题是，维护多个锁来实现独占访问将更加困难而且开销更加大。
<b color="red">
自己设计需要考虑。好难不会做哦
</b>
## 8. 有没有有顺序的 Map 实现类， 如果有， 他们是怎么保证有序的。
有 linkedHashMap;在维护hash表基础上，同时又维护了一个链表，已put顺序来往链表尾部插数据，链表可以保证有序。

## 9. 抽象类和接口的区别，类可以继承多个类么，接口可以继承多个接口么,类可以实现多个接口么。
* 抽象类中方法能实现，接口不能
* 类只能继承一个类
* 接口可以继承多个接口
* 类可以实现多个接口

## 10. 继承和聚合的区别在哪。
？？？

## 11. IO 模型有哪些， 讲讲你理解的 nio， 他和 bio， aio 的区别是啥，谈谈 reactor 模型。
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

## 12. 反射的原理，反射创建类实例的三种方式是什么。
* 反射定义：在运行状态中，对于任意一个类，都能够知道这个类的属性和方法；对于任意一个对象，都能够调用它的任何方法和属性。
* 作用：给定类的名字，就可以通过反射机制来获取类的所有信息，可以动态的创建对象和编译。
* 原理：其实A.class文件中就有这个类的一切信息。所以在类加载器加载对象的时候，就会将这些信息存在方法区中。

反射创建类实例的三种方式：
1. p.getClass();
2. Integer.class;
3. Class.forName("java.lang.Integer");

## 13. 反射中， Class.forName 和 ClassLoader 区别
Class.forName源码：
```java
Class<?> caller = Reflection.getCallerClass();
return forName0(className, true, ClassLoader.getClassLoader(caller), caller);
```
可看出，forName用的classLoader和正常类加载不太一样，是直接指定classLoader为Reflection.getCallerClass()的classLoader。
<br>
class.forName()前者除了将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块。
<br>
而classLoader只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去执行static块。

## 14. 描述动态代理的几种实现方式，分别说出相应的优缺点。
1. java自带Proxy，只能作用在接口上。但是可以很好的实现代理和业务逻辑解耦。
2. cglib代理。直接修改字节码文件，缺点是不能代理final class

动态代理缺陷：同类中不同方法之间相互调用，只有第一个方法能被代理。

## 15. 动态代理与 cglib 实现的区别
动态代理是一种"设计模式"。
cglib是实现这种设计模式的工具。
至于动态代理的两种实现方式已经在14描述了。

## 16. 为什么 CGlib 方式可以对接口实现代理。
创建一个继承实现类的子类，用asm库动态修改子类的代码，所以可以用传入的类引用执行代理类，所以可以对接口代理。

## 17. final 的用途
1. 被final修饰的类不可以被继承
2. 被final修饰的方法不可以被重写
3. 被final修饰的变量不可以被改变

## 18. 写出三种单例模式实现
no.1
```java
class A {
    private static final A instance = new A();
    private A() {}
    public static getInstance() {return instance;}
}
``` 
no.2
```java
class B {
    private B() {}
    private final volatile static B instance;
    public static getInstance() {
        if (instance == null) {
            synchronized (B.class) {
                if (instance == null) {
                    instance = new B();
                }
            }
        }
        return instance;
    }
}
```
no.3
```java
class C {
    private C() {}
    private static class LazyHolder {    
        private static final Singleton INSTANCE = new Singleton();    
    }    
    public static getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```

## 19.如何在父类中为子类自动完成所有的 hashcode 和 equals 实现？这么做有何优劣。
直接在父类中覆盖hashcode和equals呗。。
* 优：继承了这个类的子类的equals逻辑保持一致，满足特定业务需求
* 劣：这俩方法实现不好容易血崩

## 20. 请结合 OO 设计理念，谈谈访问修饰符 public、 private、 protected、 default 在应用设计中的作用。
面向对象七大原则：
1. 单一职责原则（Single Responsibility Principle）：每一个类应该专注于做一件事情。
2. 里氏替换原则（Liskov Substitution Principle）：超类存在的地方，子类是可以替换的。
3. 依赖倒置原则（Dependence Inversion Principle）：实现尽量依赖抽象，不依赖具体实现。
4. 接口隔离原则（Interface Segregation Principle）：应当为客户端提供尽可能小的单独的接口，而不是提供大的总的接口。
5. 迪米特法则（Law Of Demeter）：又叫最少知识原则，一个软件实体应当尽可能少的与其他实体发生相互作用。
6. 开闭原则（Open Close Principle）：面向扩展开放，面向修改关闭。
7. 组合/聚合复用原则（Composite/Aggregate Reuse Principle CARP）：尽量使用合成/聚合达到复用，尽量少用继承。原则： 一个类中有另一个类的对象。

* 对于1。。纯粹编码风格；
* 对于2。。interface，extends存在的价值
* 对于3。。interface
* 对于4。。private
* 对于5。。private/protect
* 对于6。。private?
* 对于7。。...

public大家都能访问；private只有自己能访问；protected只有子类能访问，default只有同一个包中的类能访问。
能根据实际情况进行很好的业务隔离，访问隔离。高内聚低耦合

## 21. 深拷贝和浅拷贝区别。
* 浅拷贝：仅仅拷贝引用
* 深拷贝：直接将引用对应的堆中的值copy一份，并将新引用指向该值

## 22. 数组和链表数据结构描述，各自的时间复杂度
* 数组一般在内存上连续，所以通过首位指针+下标*偏移量即可找到对应数据，list.get(i)的时间复杂度为O(1)；
但是插入的时候，需要将该下标之后的数全部往后偏移一位（给这个变量挪位置），才能保证在内存上依然连续，所以时间复杂度为O(n);
* 链表的每个节点在物理上不连续，只是前后有关联，所以在通过下标get数据的时候，需要从头节点开始遍历，时间复杂度为O(n)；
但是插入的时候，只需要重新建立插入节点前后节点关系即可，所以时间复杂度是O(1)。

## 23. error 和 exception 的区别， CheckedException， RuntimeException 的区别。
Error类和Exception类都继承自Throwable类。
异同：
* Exception：
1. 可以是可被控制(checked) 或不可控制的(unchecked)。
2. 表示一个由程序员导致的错误。
3. 应该在应用程序级被处理。

* Error：
1. 总是不可控制的(unchecked)。
2. 经常用来用于表示系统错误或低层资源的错误。
3. 如何可能的话，应该在系统级被捕捉。

Java 提供了两种Exception 的模式，一种是执行的时候所产生的Exception (Runtime Exception)，另外一种则是受控制的Exception (Checked Exception)。
所有的Checked Exception 均从java.lang.Exception 继承而来，而Runtime Exception 则继承java.lang.RuntimeException 或java.lang.Error (实际上java.lang.RuntimeException 的上一层也是java.lang.Exception)。
Java认为Checked异常都是可以被处理（修复）的异常，所以Java程序必须显示处理Checked异常。如果程序没有处理Checked异常，该程序在编译时就会发生错误，无法通过编译。
Runtime异常无需显式声明抛出，如果程序需要捕捉Runtime异常，也可以使用try...catch块来捕捉Runtime异常。

## 24. 请列出 5 个运行时异常。
FileNotFoundException, NullPointException, IndexOutOfBoundsException, NumberFormatException, IOException

## 25.在自己的代码中，如果创建一个java.lang.String 类，这个类是否可以被类加载器加载？为什么
不能。双亲委派模型，有专门加载java.lang包的类加载器；有专门加载外部jar包的类加载器，加载外部类的类加载器加载java.lang会抛异常。

## 26. 说一说你对 java.lang.Object 对象中 hashCode 和 equals 方法的理解。在什么场景下需要重新实现这两个方法。
hashCode是Jvm为了快速找到一个对象而为它生成的哈希码。equals默认实现是比较两个对象的hashCode是否相等。
在需要自己实现自己的equals的逻辑的时候，需要重新实现这两个方法。

Java语言对设计equal()有五个必须遵循的要求。
1. 对称性。若 a.equal(b) 返回”true”, 则 b.equal(a) 也必须返回 “true”.
2. 反射性。a.equal(a) 必须返回”true”.
3. 传递性。若a.equal(b) 返回 “true”, 且 b.equal(c)返回 “true”, 则c.equal(a)必返回”true”.
4. 一致性。若a.equal(b) 返回”true”, 只要a, b内容不变，不管重复多少次a.equal(b)必须返回”true”.
5. 任何情况下，a.equals(null)，永远返回是“false”；a.equals(和a不同类型的对象)永远返回是“false”.

hashCode()的返回值和equals()的关系：
1. 如果a.equals(b)返回“true”，那么a和b的hashCode()必须相等。
2. 如果a.equals(b)返回“false”，那么a和b的hashCode()有可能相等，也有可能不等。

## 27. 在 jdk1.5 中，引入了泛型，泛型的存在是用来解决什么问题。
用来限制入参类型。可以保障数据安全，减少重复代码，将异常控制在编译期。

## 28. 这样的 a.hashcode() 有什么用，与 a.equals(b)有什么关系。
哪样啊

## 29. 有没有可能 2 个不相等的对象有相同的 hashcode。
可能啊，相当可能，尤其是自己实现的
比如：
```java
Integer a = new Integer(10);
Integer b = new Integer(10);
System.out.println(a.hashCode() == b.hashCode());
System.out.println(a == b);
```
输出为：<br>
true<br>
false<br>

## 30. Java 中的 HashSet 内部是如何工作的。
HashSet内部实际上就是复用HashMap。只是光拿key来用了。
所以原理可以参考hashMap。
通过对变量进行hash来指定变量存放的位置。
hash冲突则拉链；如果链表长度超过8，将该链表转为红黑树。
hash表内数据量超过容量的3/4则resize，将容量*2，并重新计算其中每个变量的hash值

## 31. 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
* 序列化：将对象的状态信息转换为可以存储或传输的形式的过程。
* 怎么序列化？实现 Serializable接口；然后使用一个输出流(如：FileOutputStream)来构造一个ObjectOutputStream(对象流)对象，
接着，使用ObjectOutputStream对象的writeObject(Object obj)方法就可以将参数为obj的对象写出(即保存其状态)，要恢复的话则用输入流。
* 为什么序列化？序列化与反序列化是让Java对象脱离Java运行环境的一种手段，可以有效的实现多平台之间的通信、对象持久化存储。
* 反序列化会遇到什么问题，如何解决。serialVersionUID随机生成问题。如果不给序列化对象赋值serialVersionUID，则其会随机生成。如果在序列化后修改类结构，会导致反序列化抛异常。解决：赋值serialVersionUID

## 32.  java8 的新特性。
自行百度。

# JVM知识
## 1. 什么情况下会发生栈内存溢出。
栈深度太大，一般是递归死循环。

## 2. JVM 的内存结构，Eden 和 Survivor 比例。

* 方法区(类信息，常量，静态变量)，堆（对象存储位置）， 本地方法栈，栈（局部变量表，操作栈，etc），程序计数器；
* 堆中又分S0,S1(S0&S1统称Survivor区),eden,old,metaspace,ccs。
* 新生代大小=S0 + Eden, S0 : Eden 默认为1:8
* 新对象先放到eden；在minor collection过程中将部分活跃对象放到survivor区；survivor区gc时，S0区还ok的对象挪到S1区（内存上连续）,s0区整个清理干净，部分对象到old区。

## 3. JVM 内存为什么要分成新生代，老年代，持久代。新生代中为什么要分为 Eden 和 Survivor。
根据对象的存活周期不同将内存划分为新生代和老年代，存活周期短的为新生代，存活周期长的为老年代。<b>这样就可以根据每块内存的特点采用最适当的收集算法。</b> 
新生代的中每次垃圾收集中会发现有大批对象死区，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成收集。 
老年代中因为对象的存活率高，没有额外的控件对它进行分配担保，就必须使用“标记-清扫”或者“标记-整理”算法来进行回收。
<p>
新生代中的复制算法: 根据复制算法把新生代堆分为两份，一份为使用区，一份为存活区。 使用区：存活区=8：1每次新的对象都是在使用区创建，对使用区进行垃圾回收之后如果存活，就放入存活区。 
新生代使用区和存活区的比例是8：1，所以很有可能新生代执行了复制回收算法之后，存活区的内存不够。这个时候，存活区无法容纳的对象就会直接进入老年代。
</p>

## 4. JVM 中一次完整的 GC 流程是怎样的，对象如何晋升到老年代，说说你知道的几种主要的JVM参数。
eden -> Survivor -> old <br>
每个对象有个age计数器，出生时0，每活过一次gc就+1，age>=n(n默认15) 则晋升到老年代。
-Xmx, Xms, XXPermSize, XXMetaspaceSize..etc

## 5. 你知道哪几种垃圾收集器，各自的优缺点，重点讲下 cms 和 G1，包括原理，流程，优缺点。
7种垃圾收集器：Serial、ParNew、Parallel Scavenge、Serial Old、Parallel Old、CMS、G1；
* 新生代收集器：Serial、ParNew、Parallel Scavenge；
* 老年代收集器：Serial Old、Parallel Old、CMS；
* 整堆收集器：G1；

Parallel：多条垃圾收集线程并行工作，但此时用户线程仍然处于等待状态；
#### CMS收集器：并发标记清理（Concurrent Mark Sweep，CMS）收集器也称为并发低停顿收集器（Concurrent Low Pause Collector）或低延迟（low-latency）垃圾收集器
特点如下：

    针对老年代；
    基于"标记-清除"算法(不进行压缩操作，产生内存碎片)；            
    以获取最短回收停顿时间为目标；
    并发收集、低停顿；
    需要更多的内存（看后面的缺点）；
    是HotSpot在JDK1.5推出的第一款真正意义上的并发（Concurrent）收集器；
    第一次实现了让垃圾收集线程与用户线程（基本上）同时工作；

应用场景:与用户交互较多的场景，希望停顿时间短、响应速度快。<br>
CMS收集器运作过程：
1. 初始标记（CMS initial mark）：仅标记一下GC Roots能直接关联到的对象；速度很快，但需要"Stop The World"。
2. 并发标记（CMS concurrent mark）：进行GC Roots Tracing的过程；刚才产生的集合中标记出存活对象，应用程序也在运行，并不能保证可以标记出所有的存活对象。
3. 重新标记（CMS remark）：为了修正并发标记期间因用户程序继续运作而导致标记变动的那一部分对象的标记记录。需要"Stop The World"，且停顿时间比初始标记稍长，但远比并发标记短；采用多线程并行执行来提升效率。
4. 并发清除（CMS concurrent sweep）：回收所有的垃圾对象。

整个过程中耗时最长的并发标记和并发清除都可以与用户线程一起工作，所以总体上说，CMS收集器的内存回收过程与用户线程一起并发执行。<br>
CMS收集器3个明显的缺点：
1. 对CPU资源非常敏感，会造成吞吐量降低。
2. 无法处理浮动垃圾（并发清除时新产生的垃圾）,可能出现"Concurrent Mode Failure"失败，此时替补收集器Serail Old出场执行一次full gc（代价很大），所以CMSInitiatingOccupancyFraction不能设置得太大。
3. 产生大量内存碎片。产生大量不连续的内存碎片会导致分配大内存对象时，无法找到足够的连续内存，从而需要提前触发另一次Full GC动作。由于空间不再连续，CMS需要使用可用"空闲列表"内存分配方式，这比简单实用"碰撞指针"分配内存消耗大。

总体来看，与Parallel Old垃圾收集器相比，CMS减少了执行老年代垃圾收集时应用暂停的时间；
但却增加了新生代垃圾收集时应用暂停的时间、降低了吞吐量而且需要占用更大的堆空间；

#### G1收集器：JDK7-u4才推出商用的收集器
##### 特点：
1. 并行与并发：能充分利用多CPU、多核环境下的硬件优势，可以并行来缩短"Stop The World"停顿时间，也可以并发让垃圾收集与用户程序同时进行。
2. 分代收集，收集范围包括新生代和老年代。能独立管理整个GC堆（新生代和老年代），而不需要与其他收集器搭配；能够采用不同方式处理不同时期的对象；
虽然保留分代概念，但Java堆的内存布局有很大差别：将整个堆划分为多个大小相等的独立区域（Region），新生代和老年代不再是物理隔离，它们都是一部分Region（不需要连续）的集合。
更多G1内存布局信息请参考：《垃圾收集调优指南》 9节：http://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/g1_gc.html#garbage_first_garbage_collection
3. 结合多种垃圾收集算法，空间整合，不产生碎片：从整体看，是基于标记-整理算法；从局部（两个Region间）看，是基于复制算法。
这是一种类似火车算法的实现；都不会产生内存碎片，有利于长时间运行；
4. 可预测的停顿：低停顿的同时实现高吞吐量。G1除了追求低停顿处，还能建立可预测的停顿时间模型：可以明确指定M毫秒时间片内，垃圾收集消耗的时间不超过N毫秒。

##### 应用场景：
面向服务端应用，针对具有大内存、多处理器的机器；最主要的应用是为需要低GC延迟，并具有大堆的应用程序提供解决方案；
如：在堆大小约6GB或更大时，可预测的暂停时间可以低于0.5秒；
用来替换掉JDK1.5中的CMS收集器。在下面的情况时，使用G1可能比CMS好：
1. 超过50％的Java堆被活动数据占用；
2. 对象分配频率或年代提升频率变化很大；
3. GC停顿时间过长（长于0.5至1秒）。

##### G1收集器运作过程:不计算维护Remembered Set的操作，可以分为4个步骤（与CMS较为相似）。
1. 初始标记（Initial Marking）:仅标记一下GC Roots能直接关联到的对象，
且修改TAMS（Next Top at Mark Start）,让下一阶段并发运行时，用户程序能在正确可用的Region中创建新对象。
需要"Stop The World"，但速度很快。
2. 并发标记（Concurrent Marking）：进行GC Roots Tracing的过程。
刚才产生的集合中标记出存活对象，
耗时较长，但应用程序也在运行，
并不能保证可以标记出所有的存活对象。
3. 最终标记（Final Marking）：为了修正并发标记期间因用户程序继续运作而导致标记变动的那一部分对象的标记记录。
上一阶段对象的变化记录在线程的Remembered Set Log。
这里把Remembered Set Log合并到Remembered Set中。
需要"Stop The World"，且停顿时间比初始标记稍长，但远比并发标记短；采用多线程并行执行来提升效率。
4. 筛选回收（Live Data Counting and Evacuation）：
首先排序各个Region的回收价值和成本，
然后根据用户期望的GC停顿时间来制定回收计划，
最后按计划回收一些价值高的Region中垃圾对象。
回收时采用"复制"算法，从一个或多个Region复制存活对象到堆上的另一个空的Region，并且在此过程中压缩和释放内存。
可以并发进行，降低停顿时间，并增加吞吐量。

## 6. 垃圾回收算法的实现原理。
任何一种垃圾回收算法一般要做2件基本的事情：（1）发现无用信息对象；（2）回收被无用对象占用的内存空间，使该空间可被程序再次使用。
#### 1. 引用计数法: 
堆中每个对象实例都有一个引用计数。当一个对象被创建时，且将该对象实例分配给一个变量，该变量计数设置为1。
当任何其它变量被赋值为这个对象的引用时，计数加1（a = b,则b引用的对象实例的计数器+1），但当一个对象实例的某个引用超过了生命周期或者被设置为一个新值时，对象实例的引用计数器减1。
任何引用计数器为0的对象实例可以被当作垃圾收集。当一个对象实例被垃圾收集时，它引用的任何对象实例的引用计数器减1。<br>
缺陷：如果ab相互引用，但是都没有其他对象引用他们，理论上是需要回收他俩的。但是因为引用计数不为0，则他俩都无法回收

#### 2. 根搜索，标记清除算法
根搜索算法是从离散数学中的图论引入的，程序把所有的引用关系看作一张图，从一个节点GC ROOT开始，
寻找对应的引用节点，找到这个节点以后，继续寻找这个节点的引用节点，当所有的引用节点寻找完毕之后，
剩余的节点则被认为是没有被引用到的节点，即无用的节点。<br>
java中可作为GC Root的对象有：
1. 虚拟机栈中引用的对象（本地变量表）
2. 方法区中静态属性引用的对象
3. 方法区中常量引用的对象
4. 本地方法栈中引用的对象（Native对象）
缺陷：只回收会产生很多碎片。

#### 3. 标记清除加强版-压缩收集算法
设置两个区from和to，每次gc将from区有效的对象copy到to区(压缩)，然后将from区整个清空。
然后两个区调转角色，to变from，from变to，在下次gc时重复这个操作。

## 7. 当出现了内存溢出，你怎么排错。
借助工具jstat/jstack/jmap。
内存溢出一般现象是cpu使用率很高，服务假死，请求进不来，不打印log。这种情况一般是发生了频繁full gc，而且fullgc之后old区仍然不足以放下对象。
通过jstat -gcutil <pid> 可以确认是否是这种情况。<br>
然后通过jmap -heap <pid> 可以看出内存区域大小和当前使用情况；<br>
通过jstack <pid>可以看当前线程运行情况，排查因为死锁导致的资源不释放<br>

## 8. JVM 内存模型的相关知识了解多少， 比如重排序， 内存屏障， happen-before，主内存，工作内存等。
太宽泛了

## 9. 简单说说你了解的类加载器，可以打破双亲委派么，怎么打破。
* 启动（Bootstrap）类加载器：负责将放置在<JAVA_HOME>\lib目录中的，或者被-Xbootclasspath参数所指定路径中的，并且是虚拟机能识别的(仅按照文件名识别，如rt.jar，名字不符合的类库即使放置在lib目录中也不会被加载)类库加载到虚拟机内存中。启动类加载器无法被Java程序直接使用。
* 扩展（Extension）类加载器：负责加载<JAVA_HOME>\lib\ext目录中的，或者被java.ext.dirs系统变量所指定的路径中的所有类库
* 系统（System）类加载器=应用程序类加载器：负责加载用户类路径上所指定的类库，开发者可以直接使用这个类加载器，如果应用程序中没有自定义过自己的类加载器，一般情况下这个就是程序中默认的类加载器。

双亲委派模型的好处：让越“基础”的类由越“高级”的类加载器来加载，保证足够安全。比如用户如果定义一个java.lang.Object然后自行加载了，就会造成混乱。
<br>
Java 程序中基本有一个共识：OSGI对类加载器的使用时值得学习的，弄懂了OSGI的实现，就可以算是掌握了类加载器的精髓。
<br>
打破原因：基础类需要用用户的代码--jndi
osgi：为了实现热插拔，热部署，模块化，意思是添加一个功能或减去一个功能不用重启，只需要把这模块连同类加载器一起换掉就实现了代码的热替换。

## 14. 请解释如下 jvm 参数的含义：
### -server
由于服务器的CPU、内存和硬盘都比客户端机器强大，所以程序部署后，都应该以server模式启动，获取较好的性能；
<br>
server:启动慢，编译更完全，编译器是自适应编译器，效率高，针对服务端应用优化，在服务器环境中最大化程序执行速度而设计。
<br>
client:快速启动，内存占用少，编译快，针对桌面应用程序优化，为在客户端环境中减少启动时间而优化；

### -Xms512m -Xmx512m
堆最小内存512，最大内存512

### -Xss1024K
栈

### -XX:PermSize=256m -XX:MaxPermSize=512m
永久代初始大小256m，最大512m。java8已经去掉了，换成了metaSpace。

### -XX:MaxTenuringThreshold=20
经历过多少次gc移到老年代。

### -XX:CMSInitiatingOccupancyFraction=80
设定CMS在对内存占用率达到80%的时候开始GC

### -XX:+UseCMSInitiatingOccupancyOnly
只使用设定的回收阈值(上面指定的70%),如果不指定,JVM仅在第一次使用设定值,后续则自动调整。

# 开源框架知识
## 简单讲讲 tomcat 结构，以及其类加载器流程，线程模型等。