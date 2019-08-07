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