# 如何贡献你的代码
很高兴你正在阅读这篇文章，因为我们需要志愿者开发人员来帮助完善这个项目不足之处，丰富功能，使得插件更加强大！

以下是一些重要的资源

* 联系作者qq： `963987632`
* 测试rap2地址：[rap2地址](http://rap2.taobao.org/repository)
* 账号/密码:**rap2-generator@qq.com/123456**

# 测试
测试demo地址：[https://github.com/kings1990/kings-rap2-generator-demo](https://github.com/kings1990/kings-rap2-generator-demo)，请发起测试代码的pull request，测试形式以junit的形式执行。

提交代码需要新增一个rap2仓库，并且命名好仓库名称，请在新增的仓库里面做测试。

# 提交更改
请发送一个[pull request](https://github.com/kings1990/kings-rap2-generator/pulls)，并附上你所做的事情的清晰列表。当你发送一个拉请求，最好是带上测试程序。我们总是可以使用更多的测试覆盖率。请遵循我们的编码约定(如下)，并确保所有提交都是原子性的(每次提交一个特性)。
请申明你是在哪个版本的基础上改的

始终为您的提交编写一个清晰的日志消息。单行消息适用于小的更改，但是较大的更改应该如下所示:

```
$ git commit -m "版本:x.x.x 摘要:xxx
>
> 描述变化及其影响的段落"
```

# 代码规范
开始阅读我们的代码，你就会掌握它的窍门。

* 使用4个空格缩减代替tab
* 使用标准的驼峰命名，静态变量使用大写下划线形式
* 一些标准的参数使用枚举去实现
* 请使用lombok而非代码setter/getter
* 核心逻辑出必须加上代码注释
