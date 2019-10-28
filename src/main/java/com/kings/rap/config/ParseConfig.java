package com.kings.rap.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseConfig {
    /**
     * 域名端口
     */
    private String domainAndPortUrl;
    /**
     * cookie sid
     */
    private String sid;
    /**
     * cookie sig
     */
    private String sig;

    public ParseConfig(String domainAndPortUrl, String sid, String sig, int interfaceId, String javaDirPath, String requestJavaClassname, String responseJavaClassname, Summary.BodyOption bodyOption, Summary.RequestParamsType requestParamsType, ResponseResultType responseResultType, ResponseResultData responseResultData) {
        this.domainAndPortUrl = domainAndPortUrl;
        this.sid = sid;
        this.sig = sig;
        this.interfaceId = interfaceId;
        this.javaDirPath = javaDirPath;
        this.requestJavaClassname = requestJavaClassname;
        this.responseJavaClassname = responseJavaClassname;
        this.bodyOption = bodyOption;
        this.requestParamsType = requestParamsType;
        this.responseResultType = responseResultType;
        this.responseResultData = responseResultData;
    }

    /**
     * 接口id
     */
    private int interfaceId;
    /**
     * 解析java类路径
     */
    private String javaDirPath;
    /**
     * 请求参数类名
     */
    private String requestJavaClassname;
    /**
     * 响应参数类名
     */
    private String responseJavaClassname;
    /**
     * Body类型:FORM_DATA
     */
    private Summary.BodyOption bodyOption;
    /**
     * 参数形式:BODY_PARAMS,QUERY_PARAMS
     */
    private Summary.RequestParamsType requestParamsType;
    /**
     * 响应result类型
     */
    private ResponseResultType responseResultType;
    /**
     * result结果类型
     */
    private ResponseResultData responseResultData;
    /**
     * 响应配置集合
     */
    private String responseConfigPath = "default";
    
}


