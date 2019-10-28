package com.kings.rap.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kings.rap.config.*;
import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KingsRap2 {
    
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
    private static final String TYPE_STRING_EXP = "String|Date";
    private static final String TYPE_BOOLEAN_EXP = "Boolean|boolean";
    private static final String TYPE_ARRAY_EXP = "List(.*)|(.*)\\[]";
    private static final String BEGIN_PARSE_CLASS_EXP = "public\\s+class\\s+(\\w+(<\\w+>)?)\\s+(implements\\s+Serializable\\s+)?\\{";
    
    //KingsBankCard[]
    private static final String PARSE_ARRAY_TYPE_EXP = "(.*)\\[]";
    //List<KingsHobby>
    private static final String PARSE_LIST_TYPE_EXP = "(\\w+)<(.*)>";
    
    private static Pattern patternAnnotation = Pattern.compile(ANNOTATION_EXP);
    private static Pattern patternField = Pattern.compile(FIELD_EXP);
    private static Pattern patternTypeNumber = Pattern.compile(TYPE_NUMBER_EXP);
    private static Pattern patternTypeString = Pattern.compile(TYPE_STRING_EXP);
    private static Pattern patternTypeBoolean = Pattern.compile(TYPE_BOOLEAN_EXP);
    private static Pattern patternTypeArray = Pattern.compile(TYPE_ARRAY_EXP);
    private static Pattern patternParseArrayType = Pattern.compile(PARSE_ARRAY_TYPE_EXP);
    private static Pattern patternParseListType = Pattern.compile(PARSE_LIST_TYPE_EXP);
    private static Pattern patternBeginParseClass = Pattern.compile(BEGIN_PARSE_CLASS_EXP);
    
    private static final String RESPONSE = "response";
    private static final String REQUEST = "request";
    
    //运算中可变
    private static int idx = 1;
    
    //HTTP部分
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    
    public void doRap2() throws Exception {
        ParseConfig parseConfig = getParseConfig();
        
        //配置
        String sid = parseConfig.getSid();
        String sig = parseConfig.getSig();
        int interfaceId = parseConfig.getInterfaceId();
        String javaDirPath = parseConfig.getJavaDirPath();
        if(!javaDirPath.endsWith("/")){
            javaDirPath = javaDirPath+"/";
        }
        
        final String requestJavaClassname = parseConfig.getRequestJavaClassname();
        final String responseJavaClassname = parseConfig.getResponseJavaClassname();
        
        Summary.BodyOption bodyOption = parseConfig.getBodyOption();
        Summary.RequestParamsType requestParamsType = parseConfig.getRequestParamsType();
        ResponseResultType responseResultType = parseConfig.getResponseResultType();
        ResponseResultData responseResultData = parseConfig.getResponseResultData();
        String domainAndPortUrl = parseConfig.getDomainAndPortUrl();
        String responseConfigPath = parseConfig.getResponseConfigPath();
        //配置

        final String cookie = "koa.sid=" + sid + ";koa.sid.sig=" + sig;
        final String updateUrl = String.format(domainAndPortUrl+TARGET_URL,interfaceId);
        
        JSONArray jsonArray = new JSONArray();
        
        JSONArray all = new JSONArray();
        //处理request
        String parentId = "-1";
        JSONArray resultRequest = parse(jsonArray,requestParamsType.name(), REQUEST, interfaceId, 1, parentId, javaDirPath,requestJavaClassname,null);
        all.addAll(resultRequest);


        String responseType = responseResultData.getResponseResultDataType().getExp();
        String dealResponseType = dealType(responseType);
        //处理response
        //idx 重新记数
        idx = 1;

        //自定义response json
        if("default".equals(responseConfigPath)){
            responseConfigPath = "default-responseTemplate.json";
        }

        InputStream inputStream = KingsRap2.class.getResourceAsStream("/"+responseConfigPath);
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        
//        File file = new File(responseConfigPath);
//        String jsonString = FileUtils.readFileToString(file);
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
            idx++;
        }
        parentId = "memory-"+responseConfigList.size();
        
        
//         
//        JSONObject jsonObjectMsg = dealCommonParam("msg", "消息", "String", "-1", interfaceId, "response");
//        jsonArray.add(jsonObjectMsg);
//        idx++;
//        JSONObject jsonObjectCode = dealCommonParam("retCode", "状态码", "Integer", "-1", interfaceId, "response");
//        jsonArray.add(jsonObjectCode);
//        idx++;
//        if(!"Object".equals(dealResponseType)){
//            //返回 结果(类型:注释) 如 结果(String:customerCode集合)
//            JSONObject jsonObjectResult = dealCommonParam("result", "结果("+dealResponseType+":"+responseResultData.getDescription()+")", responseResultType.getExp(), "-1", interfaceId, "response");
//            jsonArray.add(jsonObjectResult);
//        } else {
//            //解析内部对象
//            JSONObject jsonObjectResult = dealCommonParam("result", "结果", responseResultType.getExp(), "-1", interfaceId, "response");
//            jsonArray.add(jsonObjectResult);
//        }
//        idx++;
//        parentId = "memory-3";
        
        
        JSONArray resultResponse = parse(jsonArray, requestParamsType.name(), RESPONSE, interfaceId, 1, parentId, javaDirPath,responseJavaClassname,responseResultData);
        all.addAll(resultResponse);
//        System.out.println(all);
        JSONObject jsonObject = new JSONObject();
        Summary summary = new Summary(bodyOption.name(),requestParamsType.name());
        jsonObject.put("properties",new JSONArray());
        jsonObject.put("summary",JSONObject.toJSON(summary));
        //清空历史
        post(updateUrl,jsonObject.toString(),cookie);
        jsonObject.put("properties",all);
        String result = post(updateUrl,jsonObject.toString(),cookie);
    }

    private JSONArray parse(JSONArray jsonArray,String  requestParamsType ,String scope, int interfaceId, int id, String parentId, String javaDirPath,String className,ResponseResultData responseResultData) throws Exception {
        List<String> annotationList = new ArrayList<>();
        List<String> fieldList = new ArrayList<>();
        List<String> fieldTypeList = new ArrayList<>();
        //response为对象进行解析
        if(responseResultData == null ||  "Object".equals(dealType(responseResultData.getResponseResultDataType().getExp()))) {
            String path = javaDirPath + className + ".java";
            File tempFile = new File(path);
            FileReader fileReader = new FileReader(tempFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            boolean beginFlag = false;
            while ((s = bufferedReader.readLine()) != null) {
                Matcher matcherField = patternField.matcher(s);
                Matcher matcherAnnotation = patternAnnotation.matcher(s);
                Matcher matcherBeginParseClass = patternBeginParseClass.matcher(s);
                //读取到类名开始解析
                if (matcherBeginParseClass.matches()) {
                    beginFlag = true;
                }
                if (beginFlag) {
                    if (matcherAnnotation.matches()) {
                        annotationList.add(matcherAnnotation.group(2));
                    }
                    if (matcherField.matches()) {
                        fieldTypeList.add(matcherField.group(1));
                        fieldList.add(matcherField.group(2));
                    }
                }
            }


            for (int i = 0; i < fieldList.size(); i++) {
                String fieldString = fieldList.get(i);
                String annotationString = annotationList.get(i);
                String type = fieldTypeList.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", fieldString);
                jsonObject.put("description", annotationString);
                String dealType = dealType(type);
                jsonObject.put("type", dealType);
                int pos = Summary.RequestParamsType.QUERY_PARAMS.name().equals(requestParamsType) ? 2 : 3;
                jsonObject.put("pos", pos);
                String idStr = scope + "-" + idx;
                jsonObject.put("id", idStr);
                jsonObject.put("parentId", parentId);
                jsonObject.put("memory", "false");
                jsonObject.put("interfaceId", interfaceId);
                jsonObject.put("scope", scope);
                idx++;
                //"!Object".equals(type)：字段本来就是Object的不予处理
                if ("Array".equals(dealType) || ("Object".equals(dealType)) && !"Object".equals(type)) {
                    String fileType;
                    Matcher matcherArray = patternParseArrayType.matcher(type);
                    Matcher matcherList = patternParseListType.matcher(type);
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
                        parse(jsonArray, requestParamsType, scope, interfaceId, idx, idStr, javaDirPath, fileType, null);
                    }
                } else {
                    jsonArray.add(jsonObject);
                }
            }
        }
            return jsonArray;
        
    }

    private String dealType(String fieldType) {
        String result;
        if (patternTypeNumber.matcher(fieldType).matches()) {
            result = "Number";
        } else if (patternTypeString.matcher(fieldType).matches()) {
            result = "String";
        } else if (patternTypeBoolean.matcher(fieldType).matches()) {
            result = "Boolean";
        } else if (patternTypeArray.matcher(fieldType).matches()) {
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
        String idStr = "memory-" + idx;
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
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static String get(String url,String cookie) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Cookie", cookie)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}


