package com.kings.rap.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p class="detail">
 * 功能:返回响应值的具体类型
 * </p>
 *
 * @author Kings
 * @ClassName ResponseResultData
 * @Version V1.0.
 * @date 2019.10.26 14:03:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResultData {
    /**
     * Response result data type.
     */
    private ResponseResultDataType responseResultDataType;
    /**
     * Description.
     */
    private String description;

    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @author Kings
     * @ClassName ResponseResultDataType
     * @Version V1.0.
     * @date 2019.10.26 14:03:32
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


