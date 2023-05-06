import net.sf.json.JSONObject;
import utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

    public static void main(String[] args) {

        try {
            JSONObject obj=new JSONObject();
            obj.put("info", "ok");
            System.out.println(obj);

            //应交增值税
            double c = 1130/(1+0.13)*0.13 + 636/(1+0.06) * 0.06
                      - (1073.5/(1+0.13)*0.13 + 39.2/(1+0.015)*0.015);
            System.out.println(c );
             // 毛利润
            double d = 1130/(1+0.13) + 636/(1+0.06)  // 收入
                       // 采购                 //房租        //员工   //管理 //招待
             - 1073.5/(1+0.13) - 39.2/(1+0.015) -   350 -   50 -  10
                        // 社保        公积金     //房产税              // 城建税 //教育附加  工会       残保金
             -350*0.246 - 350*0.08 -  39.2/(1+0.015)*0.12 -c*0.07 - c*0.05 - 350*0.02 -(50*0.015-0)*(350/50)
                //    货物销售    服务销售            货物采购
             - ((1130/(1+0.13) + 636/(1+0.06) + 1073.5/(1+0.13))*3/10000 + 39.2/(1+0.015) /1000)-(0.072+0.048);
            System.out.println(d);

//            System.out.println(d-c);
//            double t = ((1130/(1+0.13) + 636/(1+0.06) + 1073.5/(1+0.13))*3/10000 + 39.2/(1+0.015) /1000);
//            System.out.println(t);













            String format = "yyyy-MM-dd HH:mm:ss";
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat(format);
            // new Date()为获取当前系统时间
            System.out.println( df.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        String st = "sjzt_hlj_ods_pro.ods_cms_epmljyx_epm_hlj_a_pay_flow ";
//        System.out.println(st.indexOf("."));
//        System.out.println(st.substring(st.indexOf(".")+1));
//
//        int i=1;
//        int j=i++;
//        if((j>++j)&&(i++==j)){j+=i;}
//        System.out.println(j);

    }
}
