package com.kings.rap.test;

import com.alibaba.fastjson.JSONObject;
import com.kings.rap.config.ParseConfig;
import com.kings.rap.core.KingsRap2;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class TestPagination {
    public static void main(String[] args) throws Exception{
        File file = new File("src/main/resources/TestPagination.json");
        String jsonString = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        ParseConfig parseConfig = jsonObject.toJavaObject(ParseConfig.class);
        KingsRap2 kingsRap2 = new KingsRap2();
        kingsRap2.setParseConfig(parseConfig);
        kingsRap2.doRap2();
    }
}


