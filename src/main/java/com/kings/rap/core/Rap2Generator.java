package com.kings.rap.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kings.rap.config.*;
import com.kings.rap.model.Rap2Response;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p class="detail">
 * 功能:核心rap generator类
 * </p>
 *
 * @author Kings
 * @version V1.0
 * @date 2019.11.02
 */
public class Rap2Generator {
    
    private ParseConfig parseConfig;

    private ParseConfig getParseConfig() {
        return parseConfig;
    }

    public void setParseConfig(ParseConfig parseConfig) {
        this.parseConfig = parseConfig;
    }

    private static final String TARGET_URL = "/properties/update?itf=%d";
    private static final String ANNOTATION_EXP = "^(\\s+)\\*\\s+(.*)";
    private static final String FIELD_EXP = "\\s+private\\s+(.*)\\s+(\\w+);$";
    private static final String TYPE_NUMBER_EXP = "Integer|int|Short|short|Byte|byte|Long|long|BigDecimal|Float|float|Double|double";
    private static final String TYPE_STRING_EXP = "String|Date|LocalDate|LocalDateTime";
    private static final String TYPE_BOOLEAN_EXP = "Boolean|boolean";
    private static final String TYPE_ARRAY_EXP = "List(.*)|(.*)\\[]";
    private static final String BEGIN_PARSE_CLASS_EXP = "public\\s+class\\s+(\\w+(<\\w+>)?)\\s+(implements\\s+Serializable\\s+)?\\{";
    
    //KingsBankCard[]
    private static final String PARSE_ARRAY_TYPE_EXP = "(.*)\\[]";
    //List<KingsHobby>
    private static final String PARSE_LIST_TYPE_EXP = "(\\w+)<(.*)>";
    
    private static Pattern PATTERN_ANNOTATION = Pattern.compile(ANNOTATION_EXP);
    private static Pattern PATTERN_FIELD = Pattern.compile(FIELD_EXP);
    private static Pattern PATTERN_TYPE_NUMBER = Pattern.compile(TYPE_NUMBER_EXP);
    private static Pattern PATTERN_TYPE_STRING = Pattern.compile(TYPE_STRING_EXP);
    private static Pattern PATTERN_TYPE_BOOLEAN = Pattern.compile(TYPE_BOOLEAN_EXP);
    private static Pattern PATTERN_TYPE_ARRAY = Pattern.compile(TYPE_ARRAY_EXP);
    private static Pattern PATTERN_PARSE_ARRAY_TYPE = Pattern.compile(PARSE_ARRAY_TYPE_EXP);
    private static Pattern PATTERN_PARSE_LIST_TYPE = Pattern.compile(PARSE_LIST_TYPE_EXP);
    private static Pattern PATTERN_BEGIN_PARSE_CLASS = Pattern.compile(BEGIN_PARSE_CLASS_EXP);
    
    private static final String RESPONSE = "response";
    private static final String REQUEST = "request";
    
    //运算中可变
    private static int IDX = 1;
    
    //HTTP部分
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient CLIENT = new OkHttpClient();
    
    public void generate() throws Exception {
        ParseConfig parseConfig = getParseConfig();
        
        //配置开始
        String sid = parseConfig.getSid();
        String sig = parseConfig.getSig();
        int interfaceId = parseConfig.getInterfaceId();
        
        String packageName = parseConfig.getPackageName();
        
        
        final String requestJavaClassname = parseConfig.getRequestJavaClassname();
        final String responseJavaClassname = parseConfig.getResponseJavaClassname();
        
        Summary.BodyOption bodyOption = parseConfig.getBodyOption();
        Summary.RequestParamsType requestParamsType = parseConfig.getRequestParamsType();
        ResponseResultType responseResultType = parseConfig.getResponseResultType();
        ResponseResultData responseResultData = parseConfig.getResponseResultData();
//      String domainAndPortUrl = parseConfig.getDomainAndPortUrl(); 1.0.2
        String responseConfigPath = parseConfig.getResponseConfigPath();

        //1.0.2新配置
        int repositoryId = parseConfig.getRepositoryId();
        String delosUrl = parseConfig.getDelosUrl();
        String doloresUrl = parseConfig.getDoloresUrl();
        //配置结束

        final String cookie = "koa.sid=" + sid + ";koa.sid.sig=" + sig;
        final String updateUrl = String.format(delosUrl+TARGET_URL,interfaceId);
        
        JSONArray jsonArray = new JSONArray();
        
        JSONArray all = new JSONArray();
        //处理request
        String parentId = "-1";
        JSONArray resultRequest = parse(jsonArray,requestParamsType.name(), REQUEST, interfaceId, parentId, packageName,requestJavaClassname,null);
        all.addAll(resultRequest);


        String responseType = responseResultData.getResponseResultDataType().getExp();
        String dealResponseType = dealType(responseType);
        //处理response
        //idx 重新记数
        IDX = 1;

        //自定义response json
        if("default".equals(responseConfigPath)){
            responseConfigPath = "default-responseTemplate.json";
        }

        InputStream inputStream = Rap2Generator.class.getResourceAsStream("/"+responseConfigPath);
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        List<ResponseTemplateConfig> responseConfigList = JSONArray.parseArray(jsonString,ResponseTemplateConfig.class);
        long resultCount = responseConfigList.stream().filter(ResponseTemplateConfig::isResultFlag).count();
        if(resultCount != 1){
            throw new Exception("自定义response有且必须包含1个resultFlag=true的配置");
        }

        for (ResponseTemplateConfig responseConfig : responseConfigList) {
            if(responseConfig.isResultFlag()){
                if(!"Object".equals(dealResponseType)){
                    //返回 结果(类型:注释) 如 结果(String:customerCode集合)
                    JSONObject jsonObjectResult = dealCommonParam(responseConfig.getProperty(), responseConfig.getDescription()+"("+dealResponseType+":"+responseResultData.getDescription()+")", responseResultType.getExp(), "-1", interfaceId, "response");
                    jsonArray.add(jsonObjectResult);
                } else {
                    //解析内部对象
                    JSONObject jsonObjectResult = dealCommonParam(responseConfig.getProperty(), responseConfig.getDescription(), responseResultType.getExp(), "-1", interfaceId, "response");
                    jsonArray.add(jsonObjectResult);
                }
            } else {
                JSONObject jsonObjectMsg = dealCommonParam(responseConfig.getProperty(), responseConfig.getDescription(), responseConfig.getType().getExp(), "-1", interfaceId, "response");
                jsonArray.add(jsonObjectMsg);
            }
            IDX++;
        }
        parentId = "memory-"+responseConfigList.size();
        
        JSONArray resultResponse = parse(jsonArray, requestParamsType.name(), RESPONSE, interfaceId, parentId, packageName,responseJavaClassname,responseResultData);
        all.addAll(resultResponse);

        JSONObject jsonObject = new JSONObject();
        Summary summary = new Summary(bodyOption,requestParamsType);
        jsonObject.put("properties",new JSONArray());
        jsonObject.put("summary",JSONObject.toJSON(summary));
        //清空历史
        post(updateUrl,jsonObject.toString(),cookie);
        jsonObject.put("properties",all);
        String result = post(updateUrl,jsonObject.toString(),cookie);
        
        Rap2Response rap2Response = JSONObject.parseObject(result, Rap2Response.class);
        if(rap2Response.getIsOk() != null && !rap2Response.getIsOk()){
            if("need login".equals(rap2Response.getErrMsg())){
                System.out.println("执行错误,cookie中sid和sig已过期，请重新登录拿取");    
            } else {
                System.out.println("执行错误:"+rap2Response.getErrMsg());
            }
            return;
        }
        String resultInfo;
        if(repositoryId != 0 && StringUtils.isNotBlank(doloresUrl)){
            resultInfo = String.format("执行成功,接口地址:%s/repository/editor?id=%d&itf=%d",doloresUrl,repositoryId,interfaceId);
        } else {
            resultInfo = "执行成功!";
        }
        
        System.out.println(resultInfo);
    }

    private JSONArray parse(JSONArray jsonArray,String  requestParamsType ,String scope, int interfaceId, String parentId, String packageName,String className,ResponseResultData responseResultData) throws Exception {
        //包目录
        String packageNameDir = packageName.replaceAll("\\.","/");
        String javaDirPath = System.getProperty("user.dir")+"/src/main/java/"+ packageNameDir +"/";;
        
        
        List<String> annotationList = new ArrayList<>();
        List<String> fieldList = new ArrayList<>();
        List<String> fieldTypeList = new ArrayList<>();
        //时间格式
        Map<String,String> dayFormatMap = new HashMap<>();
        //response为对象进行解析
        if(responseResultData == null ||  "Object".equals(dealType(responseResultData.getResponseResultDataType().getExp()))) {
            String path = javaDirPath + className + ".java";
            File tempFile = new File(path);
            FileReader fileReader = new FileReader(tempFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            boolean beginFlag = false;
            while ((s = bufferedReader.readLine()) != null) {
                Matcher matcherField = PATTERN_FIELD.matcher(s);
                Matcher matcherAnnotation = PATTERN_ANNOTATION.matcher(s);
                Matcher matcherBeginParseClass = PATTERN_BEGIN_PARSE_CLASS.matcher(s);
                //读取到类名开始解析
                if (matcherBeginParseClass.matches()) {
                    beginFlag = true;
                }
                if (beginFlag) {
                    //注释部分
                    if (matcherAnnotation.matches()) {
                        annotationList.add(matcherAnnotation.group(2));
                    }
                    //字段部分
                    if (matcherField.matches()) {
                        String filedType = matcherField.group(1);
                        String fieldName = matcherField.group(2);
                        fieldTypeList.add(filedType);
                        fieldList.add(fieldName);
                        if(judgeDayType(filedType)){
                            String dayPattern = getDayPattern(packageName+"."+className, fieldName);
                            dayFormatMap.put(fieldName,dayPattern);
                        }
                    }
                }
            }

            for (int i = 0; i < fieldList.size(); i++) {
                String fieldString = fieldList.get(i);
                String annotationString = annotationList.get(i);
                String type = fieldTypeList.get(i);
                if(judgeDayType(type)){
                    String dayPattern = dayFormatMap.get(fieldString);
                    annotationString = annotationString + String.format("[格式:%s]",dayPattern);
                }
                
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", fieldString);
                jsonObject.put("description", annotationString);
                String dealType = dealType(type);
                jsonObject.put("type", dealType);
                int pos = Summary.RequestParamsType.QUERY_PARAMS.name().equals(requestParamsType) ? 2 : 3;
                jsonObject.put("pos", pos);
                String idStr = scope + "-" + IDX;
                jsonObject.put("id", idStr);
                jsonObject.put("parentId", parentId);
                jsonObject.put("memory", "false");
                jsonObject.put("interfaceId", interfaceId);
                jsonObject.put("scope", scope);
                IDX++;
                //"!Object".equals(type)：字段本来就是Object的不予处理
                if ("Array".equals(dealType) || ("Object".equals(dealType)) && !"Object".equals(type)) {
                    String fileType;
                    Matcher matcherArray = PATTERN_PARSE_ARRAY_TYPE.matcher(type);
                    Matcher matcherList = PATTERN_PARSE_LIST_TYPE.matcher(type);
                    //数组
                    if (matcherArray.matches()) {
                        fileType = matcherArray.group(1);
                        //集合    
                    } else if (matcherList.matches()) {
                        fileType = matcherList.group(2);
                        //对象    
                    } else {
                        fileType = type;
                    }
                    String filedType = dealType(fileType);
                    if (!"Object".equals(filedType)) {
                        jsonObject.put("description", String.format(annotationString + "(%s)", filedType));
                    }
                    jsonArray.add(jsonObject);
                    //只有对象才会递归解析
                    if ("Object".equals(filedType)) {
                        parse(jsonArray, requestParamsType, scope, interfaceId, idStr, packageName, fileType, null);
                    }
                } else {
                    jsonArray.add(jsonObject);
                }
            }
        }
            return jsonArray;
    }

    /**
     * <p class="detail">
     * 功能:判断字段类型是否为时间类型
     * </p>
     *
     * @param fieldType :
     * @return boolean
     * @author Kings
     * @date 2019.11.02
     */
    private boolean judgeDayType(String fieldType){
        return "Date".equals(fieldType) | "LocalDate".equals(fieldType) | "LocalDateTime".equals(fieldType); 
    }

    /**
     * <p class="detail">
     * 功能:判断字段类型是否为时间类型,若字段写了多个注解则优先级为DateTimeFormat(spring)>JSONField(fastjson)>JsonFormat(jackson),异常就返回null
     * </p>
     *
     * @param className :类名
     * @param fieldName :字段名
     * @return boolean
     * @author Kings
     * @date 2019.11.02
     */
    private String getDayPattern(String className, String fieldName){
        try {
            Class<?> parseClass = Class.forName(className);
            DateTimeFormat dateTimeFormat = parseClass.getDeclaredField(fieldName).getAnnotation(DateTimeFormat.class);
            if(dateTimeFormat != null){
                return dateTimeFormat.pattern();
            }
            JSONField jsonField = parseClass.getDeclaredField(fieldName).getAnnotation(JSONField.class);
            if(jsonField != null){
                return jsonField.format();
            }
            JsonFormat jsonFormat = parseClass.getDeclaredField(fieldName).getAnnotation(JsonFormat.class);
            if(jsonFormat != null){
                return jsonFormat.pattern();
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    private String dealType(String fieldType) {
        String result;
        if (PATTERN_TYPE_NUMBER.matcher(fieldType).matches()) {
            result = "Number";
        } else if (PATTERN_TYPE_STRING.matcher(fieldType).matches()) {
            result = "String";
        } else if (PATTERN_TYPE_BOOLEAN.matcher(fieldType).matches()) {
            result = "Boolean";
        } else if (PATTERN_TYPE_ARRAY.matcher(fieldType).matches()) {
            result = "Array";
        } else {
            result = "Object";
        }
        return result;
    }

    private JSONObject dealCommonParam(String fieldString, String annotationString, String type, String parentId, int interfaceId, String scope) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", fieldString);
        jsonObject.put("description", annotationString);
        String dealType = dealType(type);
        jsonObject.put("type", dealType);
        jsonObject.put("pos", 3);
        String idStr = "memory-" + IDX;
        jsonObject.put("id", idStr);
        jsonObject.put("parentId", parentId);
        jsonObject.put("memory", "false");
        jsonObject.put("interfaceId", interfaceId);
        jsonObject.put("scope", scope);
        return jsonObject;
    }

    private static String post(String url, String json,String cookie) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Cookie", cookie)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    private static String get(String url,String cookie) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Cookie", cookie)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }
}


