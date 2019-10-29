# 说明
此部分是针对解析配置文件中responseResultType、responseResultData、responseResultDataType部分帮助说明,参考下面各种返回值所需要的配置形式

# 返回数字
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": 1
  },
  "json类型参考": {
    "responseResultType": "Number",
    "responseResultData": {
      "responseResultDataType": "Number",
      "description": "客户id"
    }
  }
}
```

# 返回字符串
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": "hello"
  },
  "json类型参考": {
    "responseResultType": "String",
    "responseResultData": {
      "responseResultDataType": "String",
      "description": "客户code"
    }
  }
}
```

# 返回布尔
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": true
  },
  "json类型参考": {
    "responseResultType": "Boolean",
    "responseResultData": {
      "responseResultDataType": "Boolean",
      "description": "是否有效"
    }
  }
}
```

# 返回对象
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": {
      "id": "1",
      "name": "ws"
    }
  },
  "json类型参考": {
    "responseResultType": "Object",
    "responseResultData": {
      "responseResultDataType": "Object",
      "description": ""
    }
  }
}
```

# 返回集合or数组
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": [
      {
        "id": "1",
        "name": "ws1"
      },
      {
        "id": "2",
        "name": "ws2"
      }
    ]
  },
  "0.json类型参考(返回对象)": {
    "responseResultType": "Array",
    "responseResultData": {
      "responseResultDataType": "Object",
      "description": ""
    }
  },
  "1.json类型参考(数字集合)": {
    "responseResultType": "Array",
    "responseResultData": {
      "responseResultDataType": "Number",
      "description": "客户id集合"
    }
  },
  "2.json类型参考(布尔集合)": {
    "responseResultType": "Array",
    "responseResultData": {
      "responseResultDataType": "String",
      "description": "字符串集合"
    }
  },
  "3.json类型参考(布尔集合)": {
    "responseResultType": "Array",
    "responseResultData": {
      "responseResultDataType": "Boolean",
      "description": "是否有效集合"
    }
  }
}
```

# 返回分页
```
{
  "响应": {
    "msg": null,
    "retCode": null,
    "result": {
      "pageNo": null,
      "pageSize": null,
      "count": null,
      "header": {},
      "data": [
        {
          "id": null,
          "name": null
        }
      ],
      "preIndex": null,
      "nextIndex": null,
      "pagesCount": null
    }
  },
  "json类型参考": {
    "responseJavaClassname": "分页类指定data属性的具体泛型",
    "responseResultType": "Object",
    "responseResultData": {
      "responseResultDataType": "Object",
      "description": ""
    }
  }
}
```