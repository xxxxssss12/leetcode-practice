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
编译器已经优化了a+b了，但是还是不太智能哦。(一个表达式就来一套StringBuilderxxx)

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
* 反射定义：在运行状态中，对于任意一个类，都能够知道这个类的属性和方法；对于任意一个对象，都能够调用它的任何方法和属性。
* 作用：给定类的名字，就可以通过反射机制来获取类的所有信息，可以动态的创建对象和编译。
* 原理：其实A.class文件中就有这个类的一切信息。所以在类加载器加载对象的时候，就会将这些信息存在方法区中。

反射创建类实例的三种方式：
1. p.getClass();
2. Integer.class;
3. Class.forName("java.lang.Integer");

### 13. 反射中， Class.forName 和 ClassLoader 区别
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

### 14. 描述动态代理的几种实现方式，分别说出相应的优缺点。
1. java自带Proxy，只能作用在接口上。但是可以很好的实现代理和业务逻辑解耦。
2. cglib代理。直接修改字节码文件，缺点是不能代理final class

动态代理缺陷：同类中不同方法之间相互调用，只有第一个方法能被代理。

### 15. 动态代理与 cglib 实现的区别
动态代理是一种"设计模式"。
cglib是实现这种设计模式的工具。
至于动态代理的两种实现方式已经在14描述了。

### 16. 为什么 CGlib 方式可以对接口实现代理。
创建一个继承实现类的子类，用asm库动态修改子类的代码，所以可以用传入的类引用执行代理类，所以可以对接口代理。

### 17. final 的用途
1. 被final修饰的类不可以被继承
2. 被final修饰的方法不可以被重写
3. 被final修饰的变量不可以被改变

### 18. 写出三种单例模式实现
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

### 19.如何在父类中为子类自动完成所有的 hashcode 和 equals 实现？这么做有何优劣。
直接在父类中覆盖hashcode和equals呗。。
* 优：继承了这个类的子类的equals逻辑保持一致，满足特定业务需求
* 劣：这俩方法实现不好容易血崩

### 20. 请结合 OO 设计理念，谈谈访问修饰符 public、 private、 protected、 default 在应用设计中的作用。
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

### 21. 深拷贝和浅拷贝区别。
* 浅拷贝：仅仅拷贝引用
* 深拷贝：直接将引用对应的堆中的值copy一份，并将新引用指向该值

### 22. 数组和链表数据结构描述，各自的时间复杂度
* 数组一般在内存上连续，所以通过首位指针+下标*偏移量即可找到对应数据，list.get(i)的时间复杂度为O(1)；
但是插入的时候，需要将该下标之后的数全部往后偏移一位（给这个变量挪位置），才能保证在内存上依然连续，所以时间复杂度为O(n);
* 链表的每个节点在物理上不连续，只是前后有关联，所以在通过下标get数据的时候，需要从头节点开始遍历，时间复杂度为O(n)；
但是插入的时候，只需要重新建立插入节点前后节点关系即可，所以时间复杂度是O(1)。

### 23. error 和 exception 的区别， CheckedException， RuntimeException 的区别。
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

### 24. 请列出 5 个运行时异常。
FileNotFoundException, NullPointException, IndexOutOfBoundsException, NumberFormatException, IOException

### 25.在自己的代码中，如果创建一个java.lang.String 类，这个类是否可以被类加载器加载？为什么
不能。双亲委派模型，有专门加载java.lang包的类加载器；有专门加载外部jar包的类加载器，加载外部类的类加载器加载java.lang会抛异常。

### 26. 说一说你对 java.lang.Object 对象中 hashCode 和 equals 方法的理解。在什么场景下需要重新实现这两个方法。
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

### 27. 在 jdk1.5 中，引入了泛型，泛型的存在是用来解决什么问题。
用来限制入参类型。可以保障数据安全，减少重复代码，将异常控制在编译期。

### 28. 这样的 a.hashcode() 有什么用，与 a.equals(b)有什么关系。
哪样啊

### 29. 有没有可能 2 个不相等的对象有相同的 hashcode。
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

### 30. Java 中的 HashSet 内部是如何工作的。
HashSet内部实际上就是复用HashMap。只是光拿key来用了。
所以原理可以参考hashMap。
通过对变量进行hash来指定变量存放的位置。
hash冲突则拉链；如果链表长度超过8，将该链表转为红黑树。
hash表内数据量超过容量的3/4则resize，将容量*2，并重新计算其中每个变量的hash值

### 31. 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
* 序列化：将对象的状态信息转换为可以存储或传输的形式的过程。
* 怎么序列化？实现 Serializable接口；然后使用一个输出流(如：FileOutputStream)来构造一个ObjectOutputStream(对象流)对象，
接着，使用ObjectOutputStream对象的writeObject(Object obj)方法就可以将参数为obj的对象写出(即保存其状态)，要恢复的话则用输入流。
* 为什么序列化？序列化与反序列化是让Java对象脱离Java运行环境的一种手段，可以有效的实现多平台之间的通信、对象持久化存储。
* 反序列化会遇到什么问题，如何解决。serialVersionUID随机生成问题。如果不给序列化对象赋值serialVersionUID，则其会随机生成。如果在序列化后修改类结构，会导致反序列化抛异常。解决：赋值serialVersionUID

### 32.  java8 的新特性。
自行百度。

# JVM知识