# kings-rap2-generator

## 0.背景
前后端分离利用rap2的时候需要将java实体类的注释拷贝到rap2上,字段一多十分麻烦,官方目前不支持将实体类解析导入.本工具实现自动化将实体类解析导入rap2

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
最新版本
```
<dependency>
    <groupId>io.github.kings1990</groupId>
    <artifactId>kings-rap2-generator</artifactId>
    <version>1.0.1-RELEASE</version>
</dependency>
```
如果遇到不支持lombok,请安装idea lombok插件

### 2.1 配置

> **domainAndPortUrl**  
rap2后端数据API服务器(F12随便更新一个文档就可见)


> **sid**  
rap2的cookie中的参数koa.sid


> **sig**  
rap2的cookie中的参数koa.sid.sig


> **interfaceId**  
rap2编辑api接口地址参数中的itf参数,如链接地址是```http:domain/organization/repository/editor?id=25&itf=285```,则interfaceId=285


> **packageName**  
解析的java类包路径,如```com.kings.rap.demomodel```,需要注意得把所有的实体类放在该目录下,因为要支持递归查找


> **requestJavaClassname**  
请求参数对应的类名,如**KingsQueryVo**


> **responseJavaClassname**  
响应参数对应的类名,如**KingsQueryVo**


> **bodyOption**  
body参数格式,支持4种格式:  
>>**FORM_DATA**  
**X_WWW_FORM_URLENCODED**  
**RAW**  
**BINARY**


> **requestParamsType**  
请求参数类型:只有2种格式:  
>>**BODY_PARAMS**:restful POST参数    
**QUERY_PARAMS**:restful GET参数



> **responseResultType**  
响应中result对应的类型,目前包含5种类型:  
>>**Object**:对象类型  
**Array**:数组集合类型  
**Number**:数字类型  
**Boolean**:布尔类型  
**String**:字符串类型


> **responseResultData**  
响应中result对应的值的具体类型,其包含2个子属性  
>>**responseResultDataType**:返回响应值的具体类型,目前包含4种类型:  
>>>**Object**:对象类型  
>>>**Number**:数字类型  
>>>**Boolean**:布尔类型  
>>>**String**:字符串类型
>>
>>**description**:返回响应值的具体类型描述,如果responseResultType是Object类型此参数也可以不设置  
   
> **responseConfigPath**  
自定义响应模板路径,第3节有详细配置

> demojson

```
{
  "domainAndPortUrl": "http://rap2api.taobao.org",
  "sid": "eDj92yxUtwYaj-K52UtsoD2tDWCAK62Z",
  "sig": "APDaceKunTDlPWdw0Solb6PiqeU",
  "interfaceId": 1349715,
  "packageName": "com.kings.rap2.test.model",
  "requestJavaClassname": "KingsQueryVo",
  "responseJavaClassname": "KingsHobby",
  "bodyOption": "FORM_DATA",
  "requestParamsType": "QUERY_PARAMS",
  "responseResultType": "Array",
  "responseResultData": {
    "responseResultDataType": "Object",
    "description": ""
  },
  "responseConfigPath": "my-responseTemplate.json"
}
```

### 2.2 执行
>a.使用json配置的形式执行(推荐)
```
import com.kings.rap.config.ParseConfig;
import com.kings.rap.core.KingsRap2;
import com.kings.rap.util.ParseConfigJsonUtil;

public class TestByJson {
    public static void main(String[] args) throws Exception{
        ParseConfig parseConfig = ParseConfigJsonUtil.parseByFile("TestByJson.json");
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
    }
}
```

>b.使用代码的形式执行(不推荐)  
```
import com.kings.rap.config.ParseConfig;
import com.kings.rap.config.ResponseResultData;
import com.kings.rap.config.ResponseResultType;
import com.kings.rap.config.Summary;
import com.kings.rap.core.KingsRap2;

public class Test4ModelWithAuthor {
    
    public static void main(String[] args) throws Exception{
        String domainAndPortUrl = "http://101.37.66.104:8077";
        //rap2 cookie
        String sid = "c_IMAbZgZPFavzpFSxIK8BMmdQbXQUK2";
        String sig = "UML5gNS9BqnkwCKlF7Gu2XJU-RM";
        //接口itf参数
        int interfaceId = 282;
        //java类路径
        String packageName = "com.kings.rap.demomodel";
        //request和response类名 不带java
        String requestJavaClassname = "ModelWithAuthor";
        String responseJavaClassname = "KingsQueryVo";

        Summary.BodyOption bodyOption = Summary.BodyOption.FORM_DATA;
        Summary.RequestParamsType requestParamsType = Summary.RequestParamsType.QUERY_PARAMS;
        ResponseResultType responseResultType = ResponseResultType.Array;
        ResponseResultData responseResultData = new ResponseResultData(ResponseResultData.ResponseResultDataType.Object,"KingsQueryVo");
        
        ParseConfig parseConfig = new ParseConfig(domainAndPortUrl,sid,sig,interfaceId,packageName,requestJavaClassname,responseJavaClassname,bodyOption,requestParamsType,responseResultType,responseResultData);
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
    }
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
2. 所有的解析类需要放在同一个目录下
3. 配置文件和自定义响应模板放在resource下,且是标准json

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
[https://github.com/kings1990/kings-rap2-generator-demo](https://github.com/kings1990/kings-rap2-generator-demo)

淘宝rap2(请勿乱删数据)
```
地址:http://rap2.taobao.org/
仓库:rap2-generator-test
账号:rap2-generator@qq.com
密码:123456
```
