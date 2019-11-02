package io.github.kings1990.rap2.generator.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p class="detail">
 * 功能:参数形式
 * </p>
 *
 * @author Kings
 * @version V1.0
 * @date 2019.11.02
 */
@Data
@AllArgsConstructor
public class Summary {
    /**
     * body传参形式
     */
    private BodyOption bodyOption;
    /**
     * 请求参数类型
     */
    private RequestParamsType requestParamsType;

    /**
     * <p class="detail">
     * 功能:body传参形式
     * </p>
     *
     * @author Kings
     * @version V1.0
     * @date 2019.11.02
     */
    public enum  BodyOption {
        /**
         * FORM_DATA
         */
        FORM_DATA,
        /**
         * X_WWW_FORM_URLENCODED
         */
        X_WWW_FORM_URLENCODED,
        /**
         * RAW
         */
        RAW,
        /**
         * BINARY
         */
        BINARY
    }

    /**
     * <p class="detail">
     * 功能:入参形式
     * </p>
     *
     * @author Kings
     * @version V1.0
     * @date 2019.11.02
     */
    public enum RequestParamsType {
        /**
         * body形式
         */
        BODY_PARAMS,
        /**
         * query形式
         */
        QUERY_PARAMS
    }
}


