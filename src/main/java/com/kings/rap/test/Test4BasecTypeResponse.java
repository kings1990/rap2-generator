package com.kings.rap.test;

import com.kings.rap.config.*;
import com.kings.rap.core.KingsRap2;

public class Test4BasecTypeResponse {
    
    public static void main(String[] args) throws Exception{
        Summary.BodyOption bodyOption = Summary.BodyOption.FORM_DATA;
        String domainAndPortUrl = "http://101.37.66.104:8077";
        
        //rap2 cookie
        String sid = "c_IMAbZgZPFavzpFSxIK8BMmdQbXQUK2";
        String sig = "UML5gNS9BqnkwCKlF7Gu2XJU-RM";
        //接口itf参数
        int interfaceId = 283;
        //java类路径
        String javaDirPath = "/Users/wilson/develop/workspace-aux/my/kings-rap2-generator/src/main/java/com/kings/rap/demomodel/";
        //request和response类名 不带java
        String requestJavaClassname = "KingsQueryVo";
        String responseJavaClassname = "";

        
        Summary.RequestParamsType requestParamsType = Summary.RequestParamsType.QUERY_PARAMS;
        ResponseResultType responseResultType = ResponseResultType.Array;
        //返回不是对象需要关注
        ResponseResultData responseResultData = new ResponseResultData(ResponseResultData.ResponseResultDataType.String,"customerCode集合");
        
        
        ParseConfig parseConfig = new ParseConfig(domainAndPortUrl,sid,sig,interfaceId,javaDirPath,requestJavaClassname,responseJavaClassname,bodyOption,requestParamsType,responseResultType,responseResultData);
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
        
    }
}


