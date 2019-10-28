package com.kings.rap.test;

import com.kings.rap.config.ParseConfig;
import com.kings.rap.core.KingsRap2;
import com.kings.rap.util.ParseConfigJsonUtil;

public class TestCustomResponseTemplate {
    public static void main(String[] args) throws Exception{ 
        ParseConfig parseConfig = ParseConfigJsonUtil.parseByFile("TestCustomResponseTemplate.json");
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
    }
}


