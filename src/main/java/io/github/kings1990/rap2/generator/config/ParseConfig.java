package io.github.kings1990.rap2.generator.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p class="detail">
 * 功能:解析配置类
 * </p>
 *
 * @author Kings
 * @version V1.0
 * @date 2019.11.02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseConfig extends ModuleConfig{
    /**
     * 域名端口
     */
    @Deprecated
    private String domainAndPortUrl;
    
    /**
     * 接口id
     */
    private Integer interfaceId;

    /**
     * 仓库id
     */
    private Integer repositoryId;

    /**
     * 解析java类包名
     */
    private String packageName;
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
     * 模块解析模板路径
     */
    private String moduleConfigPath;
    
}


