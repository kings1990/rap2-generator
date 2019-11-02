package io.github.kings1990.rap2.generator.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p class="detail">
 * 功能:返回响应值的具体类型
 * </p>
 *
 * @author Kings
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResultData {
    /**
     * 响应结果枚举
     */
    private ResponseResultDataType responseResultDataType;
    /**
     * 响应结果描述
     */
    private String description;

    /**
     * <p class="detail">
     * 功能:响应结果枚举
     * </p>
     *
     * @author Kings
     */
    public enum ResponseResultDataType {
        /**
         * Object response result data type.
         */
        Object("XXOO"),
        /**
         * Number response result data type.
         */
        Number("Integer"),
        /**
         * Boolean response result data type.
         */
        Boolean("Boolean"),
        /**
         * String response result data type.
         */
        String("String");
        
        

        /**
         * Exp.
         */
        private String exp;

        /**
         * Instantiates a new Response result data type.
         *
         * @param exp the exp
         */
        ResponseResultDataType(String exp) {
            this.exp = exp;
        }

        /**
         * Gets exp.
         *
         * @return the exp
         */
        public String getExp() {
            return exp;
        }
    }
    
}


