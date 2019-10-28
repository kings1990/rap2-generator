package com.kings.rap.config;

/**
 * <p class="detail">
 * 功能:返回值result的类型
 * </p>
 *
 * @author Kings
 * @ClassName ResponseResultType
 * @Version V1.0.
 * @date 2019.10.26 14:03:55
 */
public enum  ResponseResultType {
    /**
     * Object response result type.
     */
    Object("XXOO"),
    /**
     * Array response result type.
     */
    Array("List"),
    /**
     * Number response result type.
     */
    Number("Integer"),
    /**
     * Boolean response result type.
     */
    Boolean("Boolean"),
    /**
     * String response result type.
     */
    String("String");

    /**
     * Exp.
     */
    private String exp;

    /**
     * Instantiates a new Response result type.
     *
     * @param exp the exp
     */
    ResponseResultType(String exp) {
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


