package com.kings.rap.demomodel;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * <p class="detail">
 * 功能:分页对象
 * </p>
 *
 * @param <KingsVo> the type parameter
 * @author Kings
 * @ClassName Pagination
 * @Version V1.0.
 * @date 2019.10.28 08:45:51
 */
@Data
public class Pagination<KingsVo> implements Serializable {
    
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
