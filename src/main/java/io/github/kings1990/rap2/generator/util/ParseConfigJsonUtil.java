package io.github.kings1990.rap2.generator.util;

import com.alibaba.fastjson.JSONObject;
import io.github.kings1990.rap2.generator.config.ParseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * <p class="detail">
 * 功能:配置json解析类
 * </p>
 *
 * @author Kings
 * @version V1.0
 * @date 2019.11.02
 */
public class ParseConfigJsonUtil {
    /**
     * <p class="detail">
     * 功能:解析
     * </p>
     *
     * @param fileName :解析文件路径
     * @return parse config
     * @throws IOException the io exception
     * @author Kings
     * @date 2019.11.02
     */
    public static ParseConfig parseByJsonFile(String fileName) throws IOException {
        InputStream inputStream = ParseConfigJsonUtil.class.getResourceAsStream("/" + fileName);
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject.toJavaObject(ParseConfig.class);
    }

}


