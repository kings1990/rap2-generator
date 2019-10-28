package com.kings.rap.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestExp {
    private static final String BEGIN_PARSE_CLASS_EXP = "public\\s+class\\s+(\\w+(<\\w+>)?)\\s+(implements\\s+Serializable\\s+)?\\{";
    private static Pattern patternBeginParseClass = Pattern.compile(BEGIN_PARSE_CLASS_EXP);
    
    public static void main(String[] args) {
        Matcher matcher = patternBeginParseClass.matcher("public class Pagination<T> implements Serializable {");
        System.out.println(matcher.matches());

    }
}


