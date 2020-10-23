# ddd-biz-parent
ddd公共框架（附标准测试工程）

## ddd-biz-parent框架

### biz-platform-framework
该Module是整个框架的核心，里面的主要功能和Package如下：
```
org.ddd.biz.platform
└── framework
        ├── command \\ 提供了实现CQRS的命令总线
        ├── constants
        ├── converter  \\ 提供实体对象之间的自动转换
        ├── dao \\ DO基类
        ├── ddd \\ 领域核心对象
        ├── i18n
        ├── mybatis  \\ mybatis的拦截器，用于自动写入表的一些公共属性
        ├── security
        ├── threadpool \\ 提供一个默认的线程池
```

### biz-platform-common
该module定义了标准的Result、errorCode、Exception、Command等公共对象，二方库会依赖该module

### biz-platform-org.ddd.biz.platform.test
测试框架