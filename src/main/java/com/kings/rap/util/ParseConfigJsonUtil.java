package com.kings.rap.util;

import com.alibaba.fastjson.JSONObject;
import com.kings.rap.config.ParseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ParseConfigJsonUtil {
    public static ParseConfig parseByFile(String fileName) throws IOException {
        InputStream inputStream = ParseConfigJsonUtil.class.getResourceAsStream("/" + fileName);
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject.toJavaObject(ParseConfig.class);

    }

}


