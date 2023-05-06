import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

    public static void main(String[] args) {
        Date d = new Date(1657691188 * 1000L);
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        System.out.println(str);




//        String tb = "odps.sjzt_hlj_dwd_pro.ads_mat_gylyyzx_aqkcsldrb_df";
//        String sqbsqbm = tb.replace("odps.sjzt_hlj_","sjzt_hlj_");
//        String projectName = sqbsqbm.substring(0,sqbsqbm.indexOf("."));
//        System.out.println(tb);
//        System.out.println(sqbsqbm);
//        System.out.println(projectName);
    }
}
