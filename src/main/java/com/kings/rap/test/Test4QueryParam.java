package com.kings.rap.test;

import com.kings.rap.config.*;
import com.kings.rap.core.KingsRap2;

public class Test4QueryParam {
    
    public static void main(String[] args) throws Exception{
        String domainAndPortUrl = "http://101.37.66.104:8077";
        //rap2 cookie
        String sid = "c_IMAbZgZPFavzpFSxIK8BMmdQbXQUK2";
        String sig = "UML5gNS9BqnkwCKlF7Gu2XJU-RM";
        //接口itf参数
        int interfaceId = 281;
        //java类路径
        String packageName = "com.kings.rap.demomodel";
        //request和response类名 不带java
        String requestJavaClassname = "KingsQueryVo";
        String responseJavaClassname = "KingsQueryVo";

        Summary.BodyOption bodyOption = Summary.BodyOption.FORM_DATA;
        Summary.RequestParamsType requestParamsType = Summary.RequestParamsType.QUERY_PARAMS;
        ResponseResultType responseResultType = ResponseResultType.Object;
        ResponseResultData responseResultData = new ResponseResultData(ResponseResultData.ResponseResultDataType.Object,"KingsQueryVo");
        
        
        ParseConfig parseConfig = new ParseConfig(domainAndPortUrl,sid,sig,interfaceId,packageName,requestJavaClassname,responseJavaClassname,bodyOption,requestParamsType,responseResultType,responseResultData);
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
        
    }
}


