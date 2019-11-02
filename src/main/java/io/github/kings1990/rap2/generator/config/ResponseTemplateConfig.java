package io.github.kings1990.rap2.generator.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p class="detail">
 * 功能:自定义响应模板
 * </p>
 *
 * @author Kings
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateConfig {
    /**
     * 属性
     */
    private String property;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private ResponseResultData.ResponseResultDataType type;
    /**
     * 是否为结果
     */
    private boolean resultFlag;
}


