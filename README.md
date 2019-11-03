# rap2-generator

## 最新版本
```
<dependency>
    <groupId>io.github.kings1990</groupId>
    <artifactId>rap2-generator</artifactId>
    <version>1.0.4-RELEASE</version>
</dependency>
```

## 0.背景
前后端分离利用rap2的时候需要将java实体类的注释拷贝到rap2上,字段一多十分麻烦.本工具实现自动化将实体类解析导入rap2

## 1.功能
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

## 2.使用

### 2.0 maven引入
最新版本([search.maven.org](https://search.maven.org/search?q=rap2-generator)搜索坐标rap2-generator)
```
<dependency>
    <groupId>io.github.kings1990</groupId>
    <artifactId>rap2-generator</artifactId>
    <version>版本号</version>
</dependency>
```
如果遇到不支持lombok,请安装idea lombok插件

### 2.1 配置

#### 2.1.0 配置介绍
目前配置分为三部分

* 全局配置
* 模块配置
* 接口配置

`全局配置`:必须在resource目录下有一个globalConfig.json文件,包含了`delosUrl`、`doloresUrl`、`sid`、`sig`、`responseConfigPath`属性.
因为sid、sig会过期,将这一分抽离出来可以更加方便执行程序即使过期也无碍,只需要改全局配置

`模块配置`:包含了`repositoryId`、`packageName`、`mod`属性,将同一个包下面的配置设置成一个模块配置,如用户模块和订单模块两者包名不一致,
可以将两个模块分别设置一个公共的模块配置.这个配置是为了让接口配置更加专注于接口本身需要的一些属性,同时也使得模块和rap2仓库的概念
更加清晰.

`接口配置`:接口中所需要的一些必要属性

#### 2.1.1 详细参数
全局配置
> **delosUrl**`必传`  
rap2后端数据API服务器地址,如:```http://rap2api.taobao.org```

> **doloresUrl**`必传`  
rap2前端静态资源,如:```http://rap2.taobao.org```

> **sid**`必传`  
rap2的cookie中的参数koa.sid

> **sig**`必传`  
rap2的cookie中的参数koa.sid.sig

模块配置
> **repositoryId**`非必传`  
接口仓库id,值为接口链接中的id,即如果链接连接是```http://rap2.taobao.org/repository/editor?id=235211&itf=1349709```,
则repositoryId=235211,加了此参数,执行程序会返回链接,不传的话执行程序不会返回接口地址

> **mod**`非必传`  
rap2仓库下模块的模块id,点击rap2横向模块取mod参数值,不传的话执行程序不会返回接口地址

> **packageName**`必传`  
解析的java类包路径,如```com.kings.rap.demomodel```,需要注意得把所有的实体类放在该目录下,因为要支持递归查找

接口配置
> **interfaceId**`必传`  
rap2编辑api接口地址参数中的itf参数,如链接地址是```http:domain/organization/repository/editor?id=25&itf=285```,则interfaceId=285

> **requestJavaClassname**`必传`  
请求参数对应的类名,如**KingsQueryVo**

> **responseJavaClassname**`必传`  
响应参数对应的类名,如**KingsQueryVo**

> **bodyOption**`必传`  
body参数格式,支持4种格式:  
>>**FORM_DATA**  
**X_WWW_FORM_URLENCODED**  
**RAW**  
**BINARY**

> **requestParamsType**`必传`  
请求参数类型:只有2种格式:  
>>**BODY_PARAMS**:restful POST参数    
**QUERY_PARAMS**:restful GET参数

> **responseResultType**`必传`  
响应中result对应的类型,目前包含5种类型:  
>>**Object**:对象类型  
**Array**:数组集合类型  
**Number**:数字类型  
**Boolean**:布尔类型  
**String**:字符串类型

> **responseResultData**`必传`  
响应中result对应的值的具体类型,其包含2个子属性  
>>**responseResultDataType**:返回响应值的具体类型,目前包含4种类型:  
>>>**Object**:对象类型  
>>>**Number**:数字类型  
>>>**Boolean**:布尔类型  
>>>**String**:字符串类型
>>
>>**description**:`非必传`  
返回响应值的具体类型描述,如果responseResultType是Object类型此参数也可以不设置  
   
> **responseConfigPath**`非必传`  
自定义响应模板路径,第3节有详细配置  

> **moduleConfigPath**`必传`  
模块配置文件路径

注意:`responseResultType`、`responseResultData属性可参考`[json参数帮助向导.md](https://github.com/kings1990/rap2-generator/blob/master/json参数帮助向导.md)

#### 2.1.2 demo配置

全局配置globalConfig.json
```  
{
  "delosUrl": "http://rap2api.taobao.org",
  "doloresUrl": "http://rap2.taobao.org",
  "sid": "Mt-pC-FunYdi4eXjfvhCVopIENTNChZ1",
  "sig": "We21o7E1Re5wyOQ7mTGUwoDFXJM",
  "responseConfigPath": "my-responseTemplate.json"
}
```

模块配置(orderCommon.json)
```
{
  "repositoryId":235482,
  "packageName": "io.github.kings1990.rap2.generator.test.order"
}
```

接口配置
```
{
  "interfaceId": 1352831,
  "requestJavaClassname": "Order",
  "responseJavaClassname": "",
  "bodyOption": "FORM_DATA",
  "requestParamsType": "BODY_PARAMS",
  "responseResultType": "Number",
  "responseResultData": {
    "responseResultDataType": "Number",
    "description": "[1-新建成功 0-新建失败]"
  },
  "moduleConfigPath": "/订单/订单模块/orderCommon.json"
}
```

样例模板
![模板](https://oscimg.oschina.net/oscnet/2230aad922d69fbf16835496f014517b734.jpg)
建议:接口json结构最好以`仓库/模块/接口名.json`这么命名,跟rap2里面的结构保持一致,理由是有利于查找和定位

### 2.2 执行
>a.使用json配置的形式执行(推荐)
```
@Test
public void testCustomResponseTemplate() throws Exception {
    ParseConfig parseConfig = ParseConfigJsonUtil.parseByJsonFile("自定义响应模板.json");
    Rap2Generator rap2Generator = new Rap2Generator();
    rap2Generator.setParseConfig(parseConfig);
    rap2Generator.generate();
}
```

## 3.自定义响应模板
响应模板默认为如下格式
```
{
  "retCode": 200,
  "msg": "xxx",
  "result":{}
}
```
如果需要返回其他格式的则需要使用自定义响应模板,分为2步

1.首先需要配置在**2.1 配置**介绍中加入**responseConfigPath**属性,值为自定义响应模板文件名,文件必须放在resource目录下,内容必须是json形式

2.参考一下**demoCustomResponseTemplate.json**配置,json约束:  
**property**:字段值(必传)  
**description**:字段描述(必传)  
**type**:字段类型(非结果字段必传) 类型约束:参考2.1章节中**responseResultType**  
**resultFlag**:结果字段标记 约束:有且必须只有1个"resultFlag": true的字段配置


> demoCustomResponseTemplate.json
```
[
  {
    "property": "code",
    "description": "状态码",
    "type": "String"
  },
  {
    "property": "message",
    "description": "状态码",
    "type": "Number"
  },
  {
    "property": "url",
    "description": "链接地址",
    "type": "String"
  },
  {
    "property": "result",
    "description": "结果",
    "resultFlag": true
  }
]
```

## 4.注意事项
1. 针对类里面包含泛型的需要指明泛型,否则不支持解析
2. 不支持不同package下的类解析,解析的时候将实体类放到同一个指定的package下
3. 配置文件和自定义响应模板放在resource下,且是标准json
4. `sid`和`sig`可能会过期,如果程序中说需要重新登录,那就再登录一下并覆盖这2个参数

### 4.0 Eclpise用户
eclipse用户参考此文[https://blog.csdn.net/wangxiaotongfan/article/details/82660523](https://blog.csdn.net/wangxiaotongfan/article/details/82660523),必须要修改.classpath的xml文件
```
<classpathentry kind="src" path="src/main/java"/>
<classpathentry kind="src" path="src/main/resources"/>
<classpathentry kind="src" path="src/test/java"/>
<classpathentry kind="src" path="src/test/resources"/>
``` 

## 5.演示
基于[淘宝rap2](http://rap2.taobao.org/)的自动化导入,请看演示.mp4

## 6.demo小程序
[https://github.com/kings1990/rap2-generator-demo](https://github.com/kings1990/rap2-generator-demo)

淘宝rap2插件测试地址(请勿乱删数据)
```
地址:http://rap2.taobao.org/
仓库:rap2-generator-test
账号:rap2-generator@qq.com
密码:123456
```

## 7.如何贡献代码
请查看[CONTRIBUTING.md](https://github.com/kings1990/rap2-generator/blob/master/CONTRIBUTING.md)
我们非常欢迎你提交好的优质代码和建议