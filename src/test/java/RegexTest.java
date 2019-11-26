import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    private static final String ANNOTATION_EXP = "(\\s*|\\t*)\\*(\\s*|\\t*)((?!\\s+|\\s?/).*)|(\\s*|\\t*)/\\**(\\s*|\\t*)(.*)\\*/|(\\s*|\\t*)(//\\s*|//\\t*)(.*)";
    private static final String FIELD_EXP = "(\\s*|\\t*)(private|protected|public)\\s+(.*)\\s+(\\w+);$";
    private static final String BEGIN_PARSE_CLASS_EXP = "(\\s*|\\t*)public(\\s+abstract)?\\s+class\\s+(\\w+(<\\w+>)?)(\\s+extends\\s+(\\w+)(<\\w+>)?)?(\\s+implements\\s+Serializable)?\\s*\\{";
    //KingsBankCard[]
    private static final String PARSE_ARRAY_TYPE_EXP = "(.*)\\[]";
    //List<KingsHobby>
    private static final String PARSE_LIST_TYPE_EXP = "(List|ArrayList|LinkedList|Set|SortedSet|HashSet|TreeSet)(\\s*|\\t*)(<(.*)>)?";
    private static final String MAP_PROPERTY_EXP = "(Map|HashMap|LinkedHashMap|TreeMap|SortedMap|Hashtable)(\\s*|\\t*)(<(.*)>)?";
    private static final String TYPE_ARRAY_EXP = PARSE_LIST_TYPE_EXP + "|(.*)\\[]" + "|" + MAP_PROPERTY_EXP;
    
    private static Pattern PATTERN_ANNOTATION = Pattern.compile(ANNOTATION_EXP);
    private static Pattern PATTERN_FIELD = Pattern.compile(FIELD_EXP);
    private static Pattern PATTERN_BEGIN_PARSE_CLASS = Pattern.compile(BEGIN_PARSE_CLASS_EXP);
    private static Pattern PATTERN_MAP_PROPERTY = Pattern.compile(MAP_PROPERTY_EXP);
    private static Pattern PATTERN_PARSE_ARRAY_TYPE = Pattern.compile(PARSE_ARRAY_TYPE_EXP);
    private static Pattern PATTERN_PARSE_LIST_TYPE = Pattern.compile(PARSE_LIST_TYPE_EXP);
    private static Pattern PATTERN_TYPE_ARRAY = Pattern.compile(TYPE_ARRAY_EXP);
    
    public static void main(String[] args) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            Matcher matcherAnnotation = PATTERN_ANNOTATION.matcher(in);
            Matcher matcherField = PATTERN_FIELD.matcher(in);
            Matcher matcherBeginParseClass = PATTERN_BEGIN_PARSE_CLASS.matcher(in);
            Matcher matcherMapProperty = PATTERN_MAP_PROPERTY.matcher(in);
            Matcher matcherArray = PATTERN_PARSE_ARRAY_TYPE.matcher(in);
            Matcher matcherList = PATTERN_PARSE_LIST_TYPE.matcher(in);
            Matcher matcherArrayAll = PATTERN_TYPE_ARRAY.matcher(in);
            if(matcherArrayAll.matches()){
                System.out.println("是集合");
            }
            
            if(matcherArray.matches()){
                System.out.println(matcherArray.group(1));
            }

            if(matcherList.matches()){
                System.out.println(matcherList.group(4));
            }
            
            if(matcherMapProperty.matches()){
                System.out.println(matcherMapProperty.group(3));
            }
            if(matcherBeginParseClass.matches()){
                System.out.println(matcherBeginParseClass.group(3));
                System.out.println(matcherBeginParseClass.group(6));
            }
            if(matcherField.matches()){
                String fieldName = matcherField.group(4);
                System.out.println(fieldName);
            }
            if (matcherAnnotation.matches()) {
                String group;
                int idx = 3;
                do {
                    group = matcherAnnotation.group(idx);
                    idx += 3;
                } while (idx <= 12 && group == null);
                System.out.println(group);
            }
        }
        
        
        
        
    }
}


