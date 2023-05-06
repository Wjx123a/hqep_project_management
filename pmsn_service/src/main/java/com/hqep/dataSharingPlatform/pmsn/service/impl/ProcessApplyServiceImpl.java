package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.google.gson.Gson;
import com.hqep.dataSharingPlatform.common.model.SysLogs;
import com.hqep.dataSharingPlatform.common.utils.*;
import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.interface_serv.dao.SysLogsDao;
import com.hqep.dataSharingPlatform.pmsn.dao.ProcessApplyDao;
import com.hqep.dataSharingPlatform.pmsn.dao.ProcessManageDao;
import com.hqep.dataSharingPlatform.pmsn.dao.QueryGdDao;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessApplyService;
import com.hqep.dataSharingPlatform.pmsn.unit.AddDshzUnit;
import com.hqep.dataSharingPlatform.sjkflc.service.ReportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hqep.dataSharingPlatform.common.utils.StringUtil2.isBlank;


@Service
public class ProcessApplyServiceImpl implements ProcessApplyService {

    @Autowired
    private ProcessApplyDao dao;
    @Autowired
    private ProcessManageDao processManageDao;
    @Autowired
    private QueryGdDao queryGdDao;
    @Autowired
    private QueryGdServiceImpl queryGdService;
    @Autowired
    private SysLogsDao sysLogsDao;

    /**
     * 查询相关内容
     */

    //查询标签
    @Override
    public List<PageData> queryBq(PageData pd) throws ServiceException {
        try {
            return dao.queryBq(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询有效表信息,申请表如果是负面清单就走子流程，如果不是负面清单就走主流程
    @Override
    public List<PageData> queryTableList(PageData pd) throws ServiceException {
        try {
            return dao.queryTableList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 根据表名查询所有字段信息
    @Override
    public List<PageData> queryCloumnList(PageData pd) throws ServiceException {
        try {
            return dao.queryCloumnList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询申请去审批
    @Override
    public List<PageData> queryProgressToAudit(PageData pd) throws ServiceException {
        try {
            return dao.queryProgressToAudit(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 用户查询自己提交的申请
    @Override
    public List<PageData> queryProgressListForMe(PageData pd) throws ServiceException {
        try {
            return dao.queryProgressListForMe(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 用户查询自己提交的申请的审批日志记录
    @Override
    public List<PageData> queryProgressListLogForMe(PageData pd) throws ServiceException {
        try {
            return dao.queryProgressListLogForMe(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 审批角色查询自己审批过的申请
    @Override
    public List<PageData> queryProgressListForAudit(PageData pd) throws ServiceException {
        try {
            return dao.queryProgressListForAudit(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询有效表信息总条数
    @Override
    public int queryTableListCount(PageData pd) throws ServiceException {
        try {
            return dao.queryTableListCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 流程相关内容
     */

    // 查询流程最新进度
    @Override
    public List<PageData> queryLatestProgress(PageData pd) throws ServiceException {
        try {
            return dao.queryLatestProgress(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    // 查询负面清单零审批的流程是否启用
    @Override
    public List<PageData> queryZeroState(PageData pd) throws ServiceException {
        try {
            return dao.queryZeroState(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 插入流程最新进度
    @Override
    public void addProgress(PageData applyData) {
        PageData par1 = new PageData();
        par1.put("sslcbm", applyData.getString("lcbm"));
        List<PageData> nodelist = processManageDao.queryProcessNodeList(par1);//获取当前流程节点信息
        int len = nodelist.size();
        if (len > 0) {
            PageData firstNode = nodelist.get(0);//第一个节点
            if (firstNode != null) {
                PageData curProStatusData = new PageData(applyData);//复制申请信息
                String nodeType = firstNode.getString("jdlx");
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //负面清单
                PageData badTable = dao.isInBadList(applyData);
                if (NormalEnum.NODE_OUT.equals(nodeType)) {//第一个节点就是子流程:即业务流程
                    if (badTable != null) {
                        //申请的表是在负面清单里且当前节点是子流程节点
                        PageData childData = new PageData(applyData);//复制申请人等信息

                        //查询子流程节点
                        PageData par2 = new PageData();
                        par2.put("jdbm", firstNode.getString("xjjdbm"));
                        List<PageData> nodelist2 = processManageDao.queryProcessNodeList(par2);//获取当前流程节点信息
                        PageData childFirstNode = nodelist2.isEmpty() ? null : nodelist2.get(0);
                        if (childFirstNode != null) {
                            PageData par3 = new PageData();
                            par3.put("lcbm", childFirstNode.getString("sslcbm"));
                            List<PageData> childList = processManageDao.queryProcessList(par3);
                            if (childList.size() > 0) {
                                childData.put("lcbm", childList.get(0).getString("lcbm"));
                                childData.put("lcmc", childList.get(0).getString("lcmc"));
                                childData.put("sjlc", uuid);//子流程的上级流程即为当前流程批次号
                                addProgress(childData);//递归添加子流程状态
                                curProStatusData.put("spzt", NormalEnum.AUDIT_GQ); // AUDIT_GQ="-1";//流程挂起
                            }
                        }

                    } else {
                        //若申请的表不在负面清单里则跳过业务审批流程,
                        //当前系统里只配有一个子流程:业务审批流程
                        //所以下一个节点肯定是普通节点
                        firstNode = nodelist.get(1);
                        curProStatusData.put("spzt", NormalEnum.AUDIT_WAIT);
                    }
                }else{
                    curProStatusData.put("spzt", NormalEnum.AUDIT_WAIT); // AUDIT_WAIT="0";//待审批
                }
                if (firstNode != null) {
                    //以下信息系统赋值，其他字段如申请人、申请的表信息等由前端赋值
                    curProStatusData.put("lcpch", uuid);//批次号
                    curProStatusData.put("lcmc", applyData.getString("lcmc"));
                    curProStatusData.put("lcbm", applyData.getString("lcbm"));
                    //设置初始节点信息
                    curProStatusData.put("dqjdid", firstNode.getString("id"));
                    curProStatusData.put("dqjdbm", firstNode.getString("jdbm"));
                    curProStatusData.put("dqjdmc", firstNode.getString("jdmc"));
                    curProStatusData.put("dqjdlx", firstNode.getString("jdlx"));
                    curProStatusData.put("parent_lcspid",applyData.get("lcpch"));
                    if (curProStatusData.get("uuid") == null) {
                        curProStatusData.put("uuid", UUIDUtil.getUUID());
                    }
                    dao.insertLatestProgress(curProStatusData);//添加所属流程状态

                    queryGdDao.addGd(curProStatusData);
                }
            }
        }
    }

    // 更新流程最新进度
    @Override
    public void editLatestProgress(PageData pd) throws ServiceException {
        try {
            dao.editLatestProgress(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 插入流程审批日志
    @Override
    public void insertProcessLog(PageData pd) throws ServiceException {
        try {
            dao.insertProcessLog(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询表名存在的流程ID及当前节点ID
    @Override
    public List<PageData> queryZxStatus(PageData pd) throws ServiceException {
        try {
            return dao.queryZxStatus(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 获取下级节点
    private PageData getNextNode(List<PageData> nodeList, PageData curNode, PageData applyData) {
        PageData par = new PageData();
        PageData nextNode = null;
        boolean isinbad = isInBadList(applyData); // 是否为负面清单
        boolean isoutnode = NormalEnum.NODE_OUT.equals(curNode.getString("jdlx"));
        if (isoutnode && isinbad) {
            //当前节点是转出节点即转到子流程的节点，则查询子流程的第一个节点
            par.put("jdbm", curNode.getString("xjjdbm"));//转出节点的下级节点编码即为子流程第一个节点
            List<PageData> temp = processManageDao.queryProcessNodeList(par);
            nextNode = temp.isEmpty() ? null : temp.get(0);
        } else {
            par.put("sslcbm", curNode.getString("sslcbm"));
            par.put("sjjdbm", curNode.getString("jdbm"));//下一个节点的上级节点编码为本节点的编码
            //par.put("jdpx", Integer.parseInt(curNode.getString("jdpx")) + 1);
            List<PageData> temp = processManageDao.queryProcessNodeList(par);
            nextNode = temp.isEmpty() ? null : temp.get(0);
        }
        return nextNode;
    }

    private PageData getCurNode(List<PageData> nodeList, String nodeid) {
        int len = nodeList.size();
        for (int i = 0; i < len; i++) {
            PageData node = nodeList.get(i);
            if (nodeid.equals(node.getString("jdbm"))) {
                return nodeList.get(i);
            }
        }
        return null;
    }

    // 是否负面清单
    private boolean isInBadList(PageData applyData) {
        //负面清单
        PageData badTable = dao.isInBadList(applyData);
        return badTable != null;
    }

    // 流程审批通过
    @Override
    public Map auditProgressYes(PageData applyData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        PageData par1 = new PageData();
        par1.put("sslcbm", applyData.getString("lcbm"));
        List<PageData> nodelist = processManageDao.queryProcessNodeList(par1);
        int len = nodelist.size();  // 存在流程节点
        if (len > 0) {
            boolean is;
            String curNodeId = applyData.getString("dqjdbm");//当前审批的节点编码
            PageData curNode = getCurNode(nodelist, curNodeId);
            PageData nextNode = getNextNode(nodelist, curNode, applyData);//下一个节点
            PageData lastNode = nodelist.get(len - 1);
            //插入日志
            PageData logData = new PageData(applyData);
            //以下字段由系统提供,其他由前端赋值
            logData.put("jdlx", curNode.getString("jdlx"));
            logData.put("jdbm", curNode.getString("jdbm"));
            logData.put("jdmc", curNode.getString("jdmc"));
            logData.put("jdzt", NormalEnum.AUDIT_YES);
            logData.put("shsj", simpleDateFormat.format(new Date()));
            // 对内审批时 插入审批日志时会有空值 这里判断一下 不插入（问题逻辑需要检查）
            if (logData.get("shrid") != null && !"".equals(logData.get("shrid"))) {
                dao.insertProcessLog(logData);//插入审批日志
            }
//            dao.insertProcessLog(logData);//插入审批日志


            /**
             * 每个流程转出节点(流程节点)，需添加子流程状态
             * 此时主流程暂停审批，进入子流程审批流
             * 可以理解成线程处理
             * 当主线程创建了一条子线程，则主线程挂起等待，进入子线程
             * 等子线程处理完后再继续往下运行主线程
             * 1个流程=N个节点
             * 1个流程可以有N个进度，1个进度只有一个批次号
             * */
            boolean isoutnode = NormalEnum.NODE_OUT.equals(curNode.getString("jdlx"));
            if (isoutnode && isInBadList(applyData)) {
                //当前节点是子流程节点，什么都不做，因为子流程自己会去递归更新
                //判断子流程状态是否存在，存在则什么都不做
                //不存在子流程则创建
                PageData query1 = new PageData();
                query1.put("sjlc", applyData.getString("lcpch"));
                List<PageData> childprolist = dao.queryLatestProgress(query1);//获取该批次号流程的子流程状态
                if (childprolist.isEmpty()) {//子流程尚无状态
                    PageData queryPro = new PageData();
                    queryPro.put("lcbm", nextNode.getString("sslcbm"));//获取子流程信息
                    List<PageData> proList = processManageDao.queryProcessList(queryPro);
                    PageData childPro = proList.isEmpty() ? null : proList.get(0);
                    if (childPro != null) {
                        PageData childData = new PageData(applyData);//复制当前流程的申请信息
                        childData.put("lcbm", childPro.getString("lcbm"));
                        childData.put("lcmc", childPro.getString("lcmc"));
                        childData.put("sjlc", applyData.getString("lcpch"));
                        addProgress(childData);//创建子流程状态
                        //主流程挂起,进入子流程
                        try {
                            PageData end = new PageData();
                            end.put("id", applyData.getString("id"));
                            end.put("spzt", NormalEnum.AUDIT_GQ);
                            this.editLatestProgress(end);
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    PageData childPro = childprolist.get(0);
                    if (NormalEnum.AUDIT_END.equals(childPro.getString("spzt"))) {
                        //子流程已结束
                        if (curNode.equals(lastNode)) {//当前是本流程最后一个节点,即没有下一节点了
                            bubleUpdateYes(applyData);
                        } else {
                            PageData par = new PageData();
                            //不用getNextNode是因为当前不用转出到子流程了，
                            // getNextNode因为没有判断流程状态在此调用仍然会返回子流程的第一个节
                            //所以只能重新搜
                            par.put("sslcbm", curNode.getString("sslcbm"));
                            par.put("sjjdbm", curNode.getString("jdbm"));//下一个节点的上级节点编码为本节点的编码
                            List<PageData> temp = processManageDao.queryProcessNodeList(par);
                            PageData nextNode2 = temp.isEmpty() ? null : temp.get(0);
                            applyData.put("dqjdid", nextNode2.getString("id"));
                            applyData.put("dqjdbm", nextNode2.getString("jdbm"));
                            applyData.put("dqjdmc", nextNode2.getString("jdmc"));
                            applyData.put("dqjdlx", nextNode2.getString("jdlx"));
                            applyData.put("spzt", NormalEnum.AUDIT_WAIT);
                            dao.editLatestProgress(applyData);
                        }
                    }
                }
            } else {
                //更新当前流程审批进度
                Map map = updateStep(applyData, nextNode, lastNode, curNode);
                map.put("curNodeId",curNodeId);
                return map;
            }
        }
        return null;
    }
    //更新购物车提出申请状态0或1（提交为1，未提交为0）
    @Override
    public void updateshoppingcat(PageData applyData) {
        dao.updateshoppingcat(applyData);
    }

    //更新购物车（撤回为-2）
    @Override
    public void updateshoppingcatForRevoke(PageData applyData) {
        dao.updateshoppingcatForRevoke(applyData);
    }

    private Map updateStep(PageData applyData, PageData nextNode, PageData lastNode, PageData curNode) {
        //更新当前流程审批进度
        if (curNode.equals(lastNode)) {//当前是本流程最后一个节点,即没有下一节点了
            Map map = bubleUpdateYes(applyData);
            return map;
        } else {
            applyData.put("dqjdid", nextNode.getString("id"));
            applyData.put("dqjdbm", nextNode.getString("jdbm"));
            applyData.put("dqjdmc", nextNode.getString("jdmc"));
            applyData.put("dqjdlx", nextNode.getString("jdlx"));
            dao.editLatestProgress(applyData);
        }
        return new HashMap();
    }

    @Autowired
    private ProcessApplyService service;
    @Autowired
    private ReportCenterService reportCenterService;

    private long defaultExpPeriod = 1000L * 60 * 60 * 24 * 180;

    // 定位阿里授权接口
    private Map bubleUpdateYes(PageData applyData) {
        applyData.put("spzt", NormalEnum.AUDIT_END);//结束本流程
        dao.editLatestProgress(applyData);
        //每个流程的最后一个节点即更新上级流程
        PageData par2 = new PageData();
        String curProPCH = applyData.getString("sjlc");//当前审批的流程的上级流程
        par2.put("lcpch", curProPCH);
        List<PageData> templist = dao.queryLatestProgress(par2);//获取上级流程状态信息
        PageData parent = templist.isEmpty() ? null : templist.get(0);
        if (parent != null && templist.size() == 1) {//应该只有一个父流程否则是错误的结果
            //递归返回主流程，再次审批时会判断子流程是否结束状态
            auditProgressYes(parent);
        } else {
            //没有上级流程了，此时本流程就是主流程，结束主流程后的操作，此时调用中台接口的授权用户的方法
            //拆分授权用户的数组
            System.out.println("进入流程最后一级节点且审批通过，直接为用户授权!!!");
            GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
            String sqyh = applyData.getString("sqyh");
            //由于中台表命名规则改变，所以程序改成根据表源端名，表源端用户名，表所属源端系统，申请表的用户所属单位（地市）
            //去查询申请的表名
            //源端系统名
//            System.out.println(applyData);
//            //源端表名
//            String ydbm = applyData.getString("sqbywbm");
//            //源端用户名
//            String ydyhm = applyData.getString("sqbxtyh");
//            //申请用户所属单位
//            String sqrssbm = applyData.getString("shrssdw");
//            //源端系统编码
//            String ydxtbm = applyData.getString("xtbm");
            // 返回的授权结果
//            Map sqMap =  JsonMsg.creatErrorMap("200", "授权成功");
             Map sqMap = null;
            // 查询申请表名
            String sqbsqbm = applyData.getString("sqbsqbm");
            System.out.println("-----------------------------------------------");
            System.out.println("申请表名：" + sqbsqbm);
            // 是否授权报表中心
            boolean bbzxflag = false;
            String username = "本部"; // "授权报表中心数据库用户名";
            String tablename = "授权报表中心表名";
            // 如果不是 国网黑龙江省电力本部 的用户 查询地市数据是否拆分
            if (!"国网黑龙江省电力本部".equals(applyData.get("sqrssdw"))) {
                System.out.println("匹配地市表名==");
                AddDshzUnit.addDshz(applyData);
                applyData.put("sffmqd",applyData.get("fmqdlx"));
                username = applyData.get("username").toString();
                List<PageData> list = service.querySqTableName(applyData);
                if (list != null && list.size()>0) {
                    PageData data = list.get(0);
                    // 如果未拆分数据，前台提示 此表地市数据未拆分，请与业务人员联系！
                    if (data.getString("table_ename") == null || "".equals(data.getString("table_ename"))){
                        return JsonMsg.creatErrorMap("500", "授权失败，未查到此表信息，请与业务人员联系！");
                    } else {
                        sqbsqbm = list.get(0).getString("table_ename");
                        System.out.println("申请的地市表名：" + sqbsqbm);
                    }
                    tablename = list.get(0).getString("table_cn_name");
                    if(applyData.get("bbzxflag") != null && "是".equals(applyData.get("bbzxflag"))) {
                        bbzxflag = true;
                    }

                }
            }
            sqbsqbm = sqbsqbm.replace("odps.sjzt_hlj_","sjzt_hlj_");
            System.out.println("申请的中台表名：" + sqbsqbm);
            String[] sqyhArr = sqyh.split(",");
            // 匹配项目名称
            String projectName = sqbsqbm.substring(0,sqbsqbm.indexOf("."));
            String use_time = applyData.getString("use_time");
            Long expDate = defaultExpPeriod;
            if(use_time !=null && !"".equals(use_time)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date birthDate = new Date();
                    birthDate = dateFormat.parse(use_time);
                    expDate = birthDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    expDate = defaultExpPeriod;
                }
            } else {
                expDate = defaultExpPeriod;
            }


            // （对授权的账号进行授权）==》ram账号
            for (int i = 0 ; i <sqyhArr.length ; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                System.out.println("授权用户-----" + i + "-----" + sqyhArr[i]);
                Map params_grantList = new LinkedHashMap<>();
                params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                params_grantList.put("projectName", projectName); //项目名称 sjzt_hlj_ods_pro被邵文强改成了sjzt_hlj_dwd_pro（张一飞让改的）
                params_grantList.put("tableName", sqbsqbm); //具体表名
                params_grantList.put("toUserId", sqyhArr[i]); //ram账号
                //params_grantList.put("expDate",  System.currentTimeMillis()+10*365*24*60*60*1000);
                params_grantList.put("expDate", expDate);
                // 正式授权方法
                 sqMap = sqAction.grantSelectDesc(params_grantList);
//                 /*测试的时候使用*/
//                sqMap = sqAction.grantSelectDescTest(params_grantList);
//                sqMap.put("state","success");
//                sqMap.put("error",null);
                stopWatch.stop();
                PageData params = new PageData(params_grantList);
                params.put("result",sqMap);
                insertSysLogs(params,stopWatch);
                PageData param = new PageData();
                param.put("grant_type","数据中台");
                param.put("tablename",sqbsqbm);
                param.put("username",username);
                param.put("ztzh",sqyhArr[i]);
                if(sqMap.get("error") == null) { // 判断中台是否授权成功
                    param.put("opstate","success");
                    reportCenterService.inertGrantForLogs(param);
                    try { // 对报表中心的报错进行捕获 不影响正常中台授权返回结果
                        param = new PageData();
                        param.put("grant_type","报表中心");
                        param.put("tablename",tablename);
                        param.put("username",username);
                        if (bbzxflag) { // 首先要是确认授权报表中心
                            System.out.println("申请成功，报表中心授权开始======");
                            PageData gpd = reportCenterService.grantQueryTableForUser(param);
                            if (gpd.get("error") == null){ // 是否插入成功
                                param.put("opstate","success");
                                reportCenterService.inertGrantForLogs(param);
                            } else {
                                param.put("opstate","error");
                                reportCenterService.inertGrantForLogs(param);
                            }
                            System.out.println("申请成功，报表中心授权结束======");
                        } else {
                            System.out.println("不是对报表中心的授权，不对报表中心授权======");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("报表中心授权失败:tablename="+tablename+",username="+username);
                    }
                } else {
                    param.put("opstate","error");
                    reportCenterService.inertGrantForLogs(param);
                }


            }
            return sqMap;
        }
        return JsonMsg.creatErrorMap("500", "授权失败！");
    }

    private void bubleUpdateNo(PageData applyData) {
        applyData.put("spzt", NormalEnum.AUDIT_NO);//本流程审核不通过
        dao.editLatestProgress(applyData);

        //每个流程的最后一个节点即更新上级流程
        PageData par2 = new PageData();
        String curProPCH = applyData.getString("sjlc");//当前审批的流程的上级流程
        par2.put("lcpch", curProPCH);
        List<PageData> templist = dao.queryLatestProgress(par2);//获取上级流程状态信息
        PageData parent = templist.isEmpty() ? null : templist.get(0);
        if (parent != null && templist.size() == 1) {//应该只有一个父流程否则是错误的结果
            //递归返回主流程，再次审批时会判断子流程是否结束状态
            auditProgressNo(parent);
        }
    }

    // 流程审批不通过
    public void auditProgressNo(PageData applyData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        PageData par1 = new PageData();
        par1.put("sslcbm", applyData.getString("lcbm"));
        List<PageData> nodelist = processManageDao.queryProcessNodeList(par1);
        int len = nodelist.size();
        if (len > 0) {
            String curNodeId = applyData.getString("dqjdbm");//当前审批的节点id
            PageData curNode = getCurNode(nodelist, curNodeId);
            PageData logData = new PageData(applyData);
            //以下字段由系统提供,其他由前端赋值
            logData.put("jdlx", curNode.getString("jdlx"));
            logData.put("jdbm", curNode.getString("jdbm"));
            logData.put("jdmc", curNode.getString("jdmc"));
            logData.put("jdzt", NormalEnum.AUDIT_NO);
            logData.put("shsj", simpleDateFormat.format(new Date()));
            logData.put("shjg", "驳回");
            //logData.put("shjg", applyData.getString("shjg"));
            queryGdService.updateSpZtBh(logData);
            queryGdService.delShoppingCart(logData);
            dao.insertProcessLog(logData);//插入审批日志

            //更新当前流程审批进度
            applyData.put("spzt", NormalEnum.AUDIT_NO);//本流程审核不通过
            bubleUpdateNo(applyData);//递归不通过父流程
        }
    }


    /**
     * 暂时没用
     */

    // 判断流程是否再进行中
    @Override
    public int queryStatus(PageData pd) throws ServiceException {
        return dao.queryStatus(pd);
    }

    // 查询当前启用流程的最后一级审批节点ID
    @Override
    public String queryLatestNodeId() throws ServiceException {
        try {
            return dao.queryLatestNodeId();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 20210302第二次修改
     */

    // 将待申请信息保存至购物车
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void insertDataToShoppingcat(PageData pd) throws ServiceException {
        try {
            dao.insertDataToShoppingcat(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 根据登录人ID查询购物车信息
    @Override
    public List<PageData> queryShoppingcat(PageData pd) throws ServiceException {
        try {
            return dao.queryShoppingcat(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询所有工单信息
    @Override
    public List<PageData> queryAllApplyDataList(PageData pd) throws ServiceException {
        try {
            return dao.queryAllApplyDataList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    // 查询工单信息下的申请子表
    @Override
    public List<PageData> queryAllApplyDataListForNext(PageData pd) throws ServiceException {
        try {
            return dao.queryAllApplyDataListForNext(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询工单流程审批详情信息
    @Override
    public List<PageData> queryAllApplyDataListForDetails(PageData pd) throws ServiceException {
        try {
            return dao.queryAllApplyDataListForDetails(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询中台授权用户信息
    @Override
    public List<PageData> querySjztUserList(PageData pd) throws ServiceException {
        try {
            return dao.querySjztUserList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageData querySpjb(PageData pd) {
        return dao.querySpjb(pd);
    }

    @Override
    public boolean delForShoppingcat(PageData pd) {
        return dao.delForShoppingcat(pd) > 0;
    }


    /**
     * 根据本部授权表查询地市授权表信息
     * @param pd
     * @return
     */
    @Override
    public List<PageData> querySqTableName(PageData pd) {
        return dao.querySqTableName(pd);
    }


    public void insertSysLogs (Map pd, StopWatch stopWatch) {
        try {
            Gson gson = new Gson();
//            //7封装日志信息
            SysLogs log = new SysLogs();
            String ip = IPUtils.getIpAddr();
            log.setIp(ip);
            log.setOperation("调用授权方法");
            log.setParams(gson.toJson(pd));
            System.out.println(GetDataWorksIntoStockAction.class.getName());
            String clsMethod = GetDataWorksIntoStockAction.class.getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName();
            log.setMethod(clsMethod);
            System.out.println(clsMethod);
            String managerName = null;
            // TODO 此处获取token信息
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if(isBlank(token)){
                managerName = "";
            }else{
                managerName = JwtUtil.getUserNameOfToken(token);
            }
            log.setManagerName(managerName);
            log.setTime(stopWatch.getTotalTimeMillis());
            log.setCreatedTime(new Date());
//            //8.将日志对象存储到数据库
            System.out.println(log);
            sysLogsDao.insert(log);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


















