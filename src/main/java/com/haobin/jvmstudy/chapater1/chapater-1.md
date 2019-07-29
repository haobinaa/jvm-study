### 第一部分 类的加载过程


#### 1. 加载时机

java class 只有在java程序首次主动使用的时候才会被被 jvm 加载

主动使用的情况：

1. 创建类的实例(`new class`)
2. 访问某个类或接口的静态变量(`getstatic`)或对该静态变量赋值(`putstatic`)
3. 调用类对静态方法(`invokestatic`)
4. 反射调用(`Class.forName("com.test.Test")`)
5. 初始化一个类的子类
6. jvm启动的时候被标记为启动类

[使用例子说明](./ClassLoad.java)：

(1) 对于静态字段来说，只有直接定义了静态字段的类才会被初始化
 
(2) 初始化类的时候会先去初始化该类的父类
         
> -XX:+TraceClassLoading 该参数表示追踪类的加载过程

#### 2. 常量的本质

1. 常量在编译阶段会存入调用这个常量方法所在类的常量池中
本质上，调用类并没有主动使用调用常量的类，所以不会触发加载定义常量所在的类的初始化。常量在编译后，其实与定义常量所在的类就没有任何关系了。
类似这种:`public static final String str = "hello"`


2. 如果常量的值在编译期无法确定出来(运行期才能确定的), 运行期间还是会触发定义常量所在类的初始化, 类似这种`public static final String str1 = UUID.randomUUID().toString()`



[使用例子说明](./ConstLoad.java)

[常量池参考文章](https://www.cnblogs.com/iyangyuan/p/4631696.html)