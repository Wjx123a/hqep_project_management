package com.hqep.dataSharingPlatform.pmsn.unit;

import java.util.Map;

public class grantSjztUnits {


    public Map grant() {
        {
//            //没有上级流程了，此时本流程就是主流程，结束主流程后的操作，此时调用中台接口的授权用户的方法
//            //拆分授权用户的数组
//            System.out.println("进入流程最后一级节点且审批通过，直接为用户授权!!!");
//            GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
//            String sqyh = applyData.getString("sqyh");
//            //由于中台表命名规则改变，所以程序改成根据表源端名，表源端用户名，表所属源端系统，申请表的用户所属单位（地市）
//            //去查询申请的表名
//            //源端系统名
////            System.out.println(applyData);
////            //源端表名
////            String ydbm = applyData.getString("sqbywbm");
////            //源端用户名
////            String ydyhm = applyData.getString("sqbxtyh");
////            //申请用户所属单位
////            String sqrssbm = applyData.getString("shrssdw");
////            //源端系统编码
////            String ydxtbm = applyData.getString("xtbm");
//            // 返回的授权结果
////            Map sqMap =  JsonMsg.creatErrorMap("200", "授权成功");
//            Map sqMap = null;
//            // 查询申请表名
//            String sqbsqbm = applyData.getString("sqbsqbm");
//            System.out.println("-----------------------------------------------");
//            System.out.println("申请表名：" + sqbsqbm);
//            // 是否授权报表中心
//            boolean bbzxflag = false;
//            String username = "本部"; // "授权报表中心数据库用户名";
//            String tablename = "授权报表中心表名";
//            // 如果不是 国网黑龙江省电力本部 的用户 查询地市数据是否拆分
//            if (!"国网黑龙江省电力本部".equals(applyData.get("sqrssdw"))) {
//                System.out.println("匹配地市表名==");
//                AddDshzUnit.addDshz(applyData);
//                applyData.put("sffmqd",applyData.get("fmqdlx"));
//                username = applyData.get("username").toString();
//                List<PageData> list = service.querySqTableName(applyData);
//                if (list != null && list.size()>0) {
//                    PageData data = list.get(0);
//                    // 如果未拆分数据，前台提示 此表地市数据未拆分，请与业务人员联系！
//                    if (data.getString("table_ename") == null || "".equals(data.getString("table_ename"))){
//                        return JsonMsg.creatErrorMap("500", "授权失败，未查到此表信息，请与业务人员联系！");
//                    } else {
//                        sqbsqbm = list.get(0).getString("table_ename");
//                        System.out.println("申请的地市表名：" + sqbsqbm);
//                    }
//                    tablename = list.get(0).getString("table_cn_name");
//                    if(applyData.get("bbzxflag") != null && "是".equals(applyData.get("bbzxflag"))) {
//                        bbzxflag = true;
//                    }
//
//                }
//            }
//            sqbsqbm = sqbsqbm.replace("odps.sjzt_hlj_","sjzt_hlj_");
//            System.out.println("申请的中台表名：" + sqbsqbm);
//            String[] sqyhArr = sqyh.split(",");
//            // 匹配项目名称
//            String projectName = sqbsqbm.substring(0,sqbsqbm.indexOf("."));
//            // （对授权的账号进行授权）==》ram账号
//            for (int i = 0 ; i <sqyhArr.length ; i++) {
//                StopWatch stopWatch = new StopWatch();
//                stopWatch.start();
//                System.out.println("授权用户-----" + i + "-----" + sqyhArr[i]);
//                Map params_grantList = new LinkedHashMap<>();
//                params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
//                params_grantList.put("projectName", projectName); //项目名称 sjzt_hlj_ods_pro被邵文强改成了sjzt_hlj_dwd_pro（张一飞让改的）
//                params_grantList.put("tableName", sqbsqbm); //具体表名
//                params_grantList.put("toUserId", sqyhArr[i]); //ram账号
//                params_grantList.put("expDate",  System.currentTimeMillis()+10*365*24*60*60*1000);
//                // 正式授权方法
//                sqMap = sqAction.grantSelectDesc(params_grantList);
////                 /*测试的时候使用*/
////                sqMap = sqAction.grantSelectDescTest(params_grantList);
////                sqMap.put("state","success");
////                sqMap.put("error",null);
//                stopWatch.stop();
//                PageData params = new PageData(params_grantList);
//                params.put("result",sqMap);
//                insertSysLogs(params,stopWatch);
//                PageData param = new PageData();
//                param.put("grant_type","数据中台");
//                param.put("tablename",sqbsqbm);
//                param.put("username",username);
//                param.put("ztzh",sqyhArr[i]);
//                if(sqMap.get("error") == null) { // 判断中台是否授权成功
//                    param.put("opstate","success");
//                    reportCenterService.inertGrantForLogs(param);
//                    try { // 对报表中心的报错进行捕获 不影响正常中台授权返回结果
//                        param = new PageData();
//                        param.put("grant_type","报表中心");
//                        param.put("tablename",tablename);
//                        param.put("username",username);
//                        if (bbzxflag) { // 首先要是确认授权报表中心
//                            System.out.println("申请成功，报表中心授权开始======");
//                            PageData gpd = reportCenterService.grantQueryTableForUser(param);
//                            if (gpd.get("error") == null){ // 是否插入成功
//                                param.put("opstate","success");
//                                reportCenterService.inertGrantForLogs(param);
//                            } else {
//                                param.put("opstate","error");
//                                reportCenterService.inertGrantForLogs(param);
//                            }
//                            System.out.println("申请成功，报表中心授权结束======");
//                        } else {
//                            System.out.println("不是对报表中心的授权，不对报表中心授权======");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("报表中心授权失败:tablename="+tablename+",username="+username);
//                    }
//                } else {
//                    param.put("opstate","error");
//                    reportCenterService.inertGrantForLogs(param);
//                }
//
//
//            }
            return null;
//            return sqMap;
        }
    }
}
