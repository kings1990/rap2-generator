package com.kings.rap.config;

/**
 * <p class="detail">
 * 功能:返回值result的类型
 * </p>
 *
 * @author Kings
 */
public enum  ResponseResultType {
    /**
     * Object类型
     */
    Object("XXOO"),
    /**
     * 数组和集合类型
     */
    Array("List"),
    /**
     * 数字类型
     */
    Number("Integer"),
    /**
     * 布尔类型
     */
    Boolean("Boolean"),
    /**
     * 字符串类型
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


