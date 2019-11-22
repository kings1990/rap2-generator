import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    private static final String ANNOTATION_EXP = "(\\s*|\\t*)\\*(\\s*|\\t*)((?!\\s+|\\s?/).*)|(\\s*|\\t*)/\\**(\\s*|\\t*)(.*)\\*/|(\\s*|\\t*)(//\\s*|//\\t*)(.*)";
    private static final String FIELD_EXP = "(\\s*|\\t*)(private|protected|public)\\s+(.*)\\s+(\\w+);$";
    private static final String BEGIN_PARSE_CLASS_EXP = "(\\s*|\\t*)public(\\s+abstract)?\\s+class\\s+(\\w+(<\\w+>)?)(\\s+extends\\s+(\\w+)(<\\w+>)?)?(\\s+implements\\s+Serializable)?\\s*\\{";
    
    private static Pattern PATTERN_ANNOTATION = Pattern.compile(ANNOTATION_EXP);
    private static Pattern PATTERN_FIELD = Pattern.compile(FIELD_EXP);
    private static Pattern PATTERN_BEGIN_PARSE_CLASS = Pattern.compile(BEGIN_PARSE_CLASS_EXP);
    
    public static void main(String[] args) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            Matcher matcherAnnotation = PATTERN_ANNOTATION.matcher(in);
            Matcher matcherField = PATTERN_FIELD.matcher(in);
            Matcher matcherBeginParseClass = PATTERN_BEGIN_PARSE_CLASS.matcher(in);
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


