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
public class GlobalParseConfig {
    /**
     * rap2后端数据API服务器地址
     */
    private String delosUrl;

    /**
     * rap2前端静态资源
     */
    private String doloresUrl;

    /**
     * cookie sid
     */
    private String sid;
    /**
     * cookie sig
     */
    private String sig;

    /**
     * 响应配置集合
     */
    private String responseConfigPath = "default";

}


