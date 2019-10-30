package com.kings.rap.demomodel;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * <p class="detail">
 * 功能:分页对象
 * </p>
 *
 * @param <T> the type parameter
 * @author Kings
 */
@Data
public class PaginationKingsVo<T> implements Serializable {
    
    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 每页查询条数
     */
    private int pageSize;
    /**
     * 总条数
     */
    private int count;
    /**
     * 头
     */
    private Object header;
    /**
     * 记录data
     */
    private List<KingsVo> data;

    /**
     * 上一页
     */
    private int preIndex;
    /**
     * 下一页
     */
    private int nextIndex;
    /**
     * 总页数
     */
    private int pagesCount;
}
