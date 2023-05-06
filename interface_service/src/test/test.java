import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) {

        System.out.println(Boolean.parseBoolean("true"));
        System.out.println(1000/15);
        System.out.println(66*15);

//
//        List<Map<String,Object>> list  = new ArrayList();
//        Map<String,Object> map = new HashMap();
//        map.put("a","a");
//        list.add(map);
//        map = new HashMap();
//        map.put("a","b");
//        list.add(map);
//        map = new HashMap();
//        map.put("a","c");
//        list.add(map);
//
//        ListToStrWithComma(list,"a");
//        System.out.println( ListToStrWithComma(list,"a"));
    }

    public static String ListToStrWithComma(List<Map<String,Object>> list, String key) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).get(key));
            if ((i + 1) != list.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
