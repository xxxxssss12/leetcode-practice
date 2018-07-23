# Java基础
1. Java中的几种基本数据类型是啥，各占用多少字节？
<br>
答：float=4字节, char=2字节, boolean=1字节, double=8字节, byte=1字节, short=2字节, int=4字节, long=8字节
</br>
2. String 能被继承吗？为什么？
<br>
答：不能。因为class上有final修饰符
</br>
3. String， Stringbuffer， StringBuilder 的区别。
<br>
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
</br>
4. ArrayList 和 LinkedList 有什么区别。
<br>
ArrayList是数组结构，LinkedList是链表结构。
数组根据下标（index）查询快，直接从0开始算内存偏移量即可；但是插入删除慢，因为需要重新计算插入、删除后的偏移量；
链表对位置没有那么多讲究。插入很快（断开链接，将链接两端节点与新节点建立关系即可）；查询很慢（需要从头节点或尾节点循环一遍）。
</br>
5. 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当 new 的时候， 他们的执行顺序。
<br>
```
1． 父类静态成员和静态初始化块 ，按在代码中出现的顺序依次执行
2． 子类静态成员和静态初始化块 ，按在代码中出现的顺序依次执行
3． 父类实例成员和实例初始化块 ，按在代码中出现的顺序依次执行
4． 父类构造方法
5． 子类实例成员和实例初始化块 ，按在代码中出现的顺序依次执行
6． 子类构造方法
```
结论：对象初始化的顺序，先静态方法，再构造方法，每个又是先基类后子类。
</br>
6. 用过哪些 Map 类，都有什么区别，HashMap 是线程安全的吗,并发下使用的 Map 是什么，他们内部原理分别是什么，比如存储方式， hashcode，扩容， 默认容量等。