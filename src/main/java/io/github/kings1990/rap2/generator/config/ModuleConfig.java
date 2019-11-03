package io.github.kings1990.rap2.generator.config;

import lombok.Data;

@Data
public class ModuleConfig extends GlobalParseConfig{
    /**
     * 仓库id
     */
    private Integer repositoryId;

    /**
     * 模块id
     */
    private Integer mod;

    /**
     * 解析java类包名
     */
    private String packageName;

    
}


