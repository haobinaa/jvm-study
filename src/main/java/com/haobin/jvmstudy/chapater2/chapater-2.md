### 第二部分 类加载器

#### 类的加载和加载器原理

1. 类的加载
- 类加载的最终结果是内存中的Class对象
- Class对象封装了类在方法区的数据结构，并提供了访问方法区数据结构的接口

2. 类加载器类型
- BootStrap(根, 加载的是`JRE/rt.jar`)
- Extension(扩展类加载, 加载的是`JRE/lib/ext/*.jar`)
- AppClassLoader(应用类加载器, 加载的是`classpath`)

3. 类加载器加载类
- 类加载器并不需要等到某个类被首次"主动使用"才加载它(这里要与初始化分开理解)
- 类加载器加载类，使用的是一种双亲委托机制， 会先让父类加载器去加载， 加载不到再自己加载。类加载器的顺序就是`BootStrap->Extension->AppClassLoader->用户自定义类加载器`

>所以结合上一点，假如我自定义一个类加载器`loader`,去加载我项目中的某个类，其实这个类并不是这个`loader`去加载的，因为在classpath下，最终还是会由`AppClassLoader`去加载

4. 类加载器与类的初始化

`ClassLoader.loadClass()`并不会导致类的初始化， `Class.forName`这种反射调用会导致类的初始化

[代码例子点击这里](./ClassLoaderTest.java)


#### 获取类加载器

获取类加载器的方式:
```
// 获取 AppClassLoader
ClassLoader.getSystemClassLoader();

// 返回当前线程的上下文加载器， 由线程的创建者提供
Thread.currentThread().getContextClassLoader()

// 获取这个类的 ClassLoader
claszz.getClassLoader()
```

#### classLoader执行顺序

`ClassLoader`的`loadClass()`执行顺序：
1. `findLoadedClass(String)` 检查是否被加载过
2. 调用父类加载器的`loadClass`， 父类返回null，则调用自身`loadClass`
3. `findClass`去寻找class文件

#### 自定义类加载器

[代码示例](./CustomClassLoader.java)

使用restartClassLoader 加载的时候 threadlocal 的值取不到， 因为static 变量的threadlocal 每次都被重新加载了


#### 类命名空间

- 每个类加载器都有自己命名空间，由该类加载器及所有父类加载器加载的类组成的
- 同一个命名空间中，不会出现类的完整名字(含包名)完全相同的两个类
- 不同类命名空间中可能会出现类完全相同的两个类

结合上述自定义的`CustomCLassLoader`, 有如下:
``` 
ClassLoader loader1 = new CustomerClassLoader("loader1");
ClassLoader loader2 = new CustomerClassLoader(loader1, "loader2");
ClassLoader loader3 = new CustomerClassLoader("loader3");
```
3个classloader都是自定义的， 并且loader1是loader2的父类。 如果都去加载一个类`com.java.test.class`(非classpath下，所以不是AppClassLoader去加载，而是走自定义类加载器)，如下:
```
String class = "com.java.test";
loader1.loadCLass(class);
loader2.loadCLass(class);
loader3.loadCLass(class);
```

这里这个类会被加载两次， 原因是:
1. loader1会加载这个类， 因为是在非classpath下
2. loader2去加载的时候，因为loader1是loader2的父类，所以在同一个命名空间下已经被加载过类，就不会在加载
3. loader3和loader1并非在一个命名空间下，会去重新加载这个类


#### 类的卸载

> 当`Sample`类被加载、链接、初始化后，它的生命周期就开始了，当代表`Sample`类的CLass对象不在被引用时，CLass对象就会结束生命周期，方法区的数据也会被卸载，从而结束`Sample`类的生命周期

这里需要注意：
>java虚拟机自带的类加载器(bootstrap,extension,app)加载的类，始终不会被卸载。因为Java虚拟机本身会一直引用这些类加载器，这些类加载器会始终引用它们所加载的class对象。但是用户自定义的类加载器加载的类是可以被卸载的