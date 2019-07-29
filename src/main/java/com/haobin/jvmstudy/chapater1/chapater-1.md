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

[使用例子说明](./ChapaterOne.java)：

(1) 对于静态字段来说，只有直接定义了静态字段的类才会被初始化
 
(2) 初始化类的时候会先去初始化该类的父类
         
