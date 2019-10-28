package com.kings.rap.demomodel;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class KingsVo {

    /**
     * 测试日期集合
     */
    private List<Date> testDateList;
    /**
     * 测试Id集合
     */
    private List<Integer> testIdList;
    /**
     * 测试Id数组
     */
    private Integer[] testIdArray;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 爱好
     */
    private List<KingsHobby> hobbyList;
    /**
     * 银行卡
     */
    private KingsBankCard[] cardArray;
    /**
     * 是否有趣
     */
    private Boolean funFlag;

    /**
     * 微信信息
     */
    private KingsWeChat kingsWeChat;
}


