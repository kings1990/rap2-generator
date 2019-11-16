<h1 align="center">为自动化而生</h1>


<div align="center"><img align="center" src="https://oscimg.oschina.net/oscnet/a964e875efa442570fe3a7cdfded0027183.jpg"/></div>

<div align="center">
	<span>
		<a href="https://travis-ci.com/kings1990/rap2-generator">
			<img src="https://travis-ci.com/kings1990/rap2-generator.svg?branch=master">
		</a>
	</span>
	<span >
		<a href="https://search.maven.org/search?q=g:io.github.kings1990%20AND%20a:rap2-generator">
			<img src="https://img.shields.io/maven-central/v/io.github.kings1990/rap2-generator.svg?style=flat-square"/>
		</a>
	</span>	
	<span >
		<a href="https://github.com/kings1990/rap2-generator">
			<img src="https://img.shields.io/badge/language-java-orange.svg"/>
		</a>
	</span>	
	<span>
		<a href="https://www.apache.org/licenses/LICENSE-2.0">
			<img src="https://img.shields.io/badge/license-Apache2-pink.svg"/>
		</a>	
	</span>
</div>

## 最新版本
```
<dependency>
    <groupId>io.github.kings1990</groupId>
    <artifactId>rap2-generator</artifactId>
    <version>1.0.5-RELEASE</version>
</dependency>
```

## 背景
前后端分离利用rap2的时候需要将java实体类的注释拷贝到rap2上,字段一多十分麻烦.本工具实现自动化将实体类解析导入rap2

## 功能
假如你有一个实体类,如下

```
@Data
public class KingsBankCard {
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 密码
     */
    private String password;
}
```
你需要将他写入rap2的响应内容参数,那么你就需要手动将*cardNo*和*password*字段的注释即rap2响应内容的简介拷贝进去,
本工具将解决这一部分的难题,实现简单配置自动导入

## 演示
基于[淘宝rap2](http://rap2.taobao.org/)的自动化导入.[演示->](https://oscimg.oschina.net/oscnet/99c83368fe39dc4733aa2e8e81676ec3ef9.jpg)

## Links

* [Home](https://github.com/kings1990/rap2-generator/wiki)

* [使用](https://github.com/kings1990/rap2-generator/wiki/使用)

* [自定义响应模板](https://github.com/kings1990/rap2-generator/wiki/自定义响应模板)

* [返回值配置示例](https://github.com/kings1990/rap2-generator/wiki/返回值配置示例)

* [常见问题](https://github.com/kings1990/rap2-generator/wiki/常见问题)

* [更新日志](https://github.com/kings1990/rap2-generator/wiki/更新日志)

## Features
* 无限级嵌套解析
* 泛型解析
* 自定义响应模板定制
* 兼容各种类型的属性注释
* 各种java类型解析

## Demo
仓库地址:[https://github.com/kings1990/rap2-generator-demo](https://github.com/kings1990/rap2-generator-demo)

淘宝rap2插件测试地址(请勿乱删数据)

```
地址:http://rap2.taobao.org/
仓库:rap2-generator-test
账号:rap2-generator@qq.com
密码:123456
```

## Contribut
请查看[CONTRIBUTING.md](https://github.com/kings1990/rap2-generator/blob/master/CONTRIBUTING.md)
我们非常欢迎你提交好的优质代码和建议
