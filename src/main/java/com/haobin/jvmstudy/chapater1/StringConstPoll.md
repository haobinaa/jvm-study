## 字符串常量池

### 1. 全局常量池

全局字符串池里的内容是在类加载完成，经过验证，准备阶段之后在堆中生成字符串对象实例，然后将该字符串对象实例的引用值存到string pool中（string pool中存的是引用值而不是具体的实例对象，具体的实例对象是在堆中开辟的一块空间存放的）

在HotSpot VM里实现的string pool功能的是一个StringTable类，它是一个哈希表，里面存的是驻留字符串(也就是我们常说的用双引号括起来的)的引用（而不是驻留字符串实例本身），也就是说在堆中的某些字符串实例被这个StringTable引用之后就等同被赋予了”驻留字符串”的身份。这个StringTable在每个HotSpot VM的实例只有一份，被所有的类共享


### 2. class文件常量池

class文件中除了包含类的版本、字段、方法、接口等描述信息外，还有一项信息就是常量池(constant pool table)，用于存放编译器生成的各种字面量(Literal)和符号引用(Symbolic References)

- 字面量就是我们所说的常量概念，如文本字符串、被声明为final的常量值等

- 符号引用是一组符号来描述所引用的目标, 一般包括下面三类常量:
    - 类和接口的全限定名
    - 字段的名称和描述符
    - 方法的名称和描述符
    
符号引用与直接引用区分一下，直接引用一般是指向方法区的本地指针，相对偏移量或是一个能间接定位到目标的句柄.简单的理解符号引用是描述类信息的文本形式,直接引用是能直接定位到目标的指针(jvm能直接使用),符号引用在使用的时候会被解析成直接引用

### 3. 运行时常量池

jvm在执行某个类的时候，必须经过加载、连接、初始化，而连接又包括验证、准备、解析三个阶段。而当类加载到内存中后，jvm就会将class常量池中的内容存放到运行时常量池中.

.运行时常量池是在类加载完成之后，将每个class常量池中的符号引用值转存到运行时常量池中，也就是说，每个class都有一个运行时常量池，类在解析之后，将符号引用替换成直接引用，与全局常量池中的引用值保持一致


### String 的使用方式

- 使用 ” ” 双引号创建 ： String s1 = “first”;
- 使用字符串连接符拼接 ： String s2=”se”+”cond”;
- 使用字符串加引用拼接 ： String s12=”first”+s2;
- 使用new String(“”)创建 ： String s3 = new String(“three”);
- 使用new String(“”)拼接 ： String s4 = new String(“fo”)+”ur”;
- 使用new String(“”)拼接 ： String s5 = new String(“fo”)+new String(“ur”);

这里存储的参考如下:

![](../../../../../../../images/string-pool.png)


## 参考资料
- [触摸Java常量池](https://www.cnblogs.com/iyangyuan/p/4631696.html)
- [java中几种常量池的区分](http://tangxman.github.io/2015/07/27/the-difference-of-java-string-pool/)
- [深入理解String#intern](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)
- [String 的理解](https://juejin.im/entry/5a4ed02a51882573541c29d5)