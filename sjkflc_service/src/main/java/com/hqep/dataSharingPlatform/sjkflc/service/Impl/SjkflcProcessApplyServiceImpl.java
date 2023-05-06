package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOutDemandApplicationDao;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessApplyDao;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessManageDao;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessApplyService;
import com.hqep.dataSharingPlatform.common.utils.NormalEnum;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.DateUtils;

import javax.xml.rpc.ServiceException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class SjkflcProcessApplyServiceImpl implements SjkflcProcessApplyService {

    @Autowired
    private SjkflcProcessApplyDao dao;
    @Autowired
    private SjkflcProcessManageDao processManageDao;
    @Autowired
    private SjkflcOutDemandApplicationDao sjkflcOutDemandApplicationDao;
    /**
     * 流程相关内容
     */

    // 工单管理查询
    @Override
    public List<PageData> queryOrders(PageData pd) throws ServiceException {
        try {
            return dao.queryOrders(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询流程最新进度
    @Override
    public List<PageData> queryProgressBySqxqID(PageData pd) throws ServiceException {
        try {
            return dao.queryProgressBySqxqID(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询流程最新进度
    @Override
    public List<PageData> queryLatestProgress(PageData pd) throws ServiceException {
        try {
            return dao.queryLatestProgress(pd);
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
                boolean isTZ = !"0".equals(curProStatusData.get("tzFlag"));//true; //判断是否涉密，如果为0 是不涉密，为1 为涉密
                if (NormalEnum.NODE_OUT.equals(nodeType)) {//第一个节点就是子流程:即业务流程
                    if (isTZ) {
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
                                curProStatusData.put("spzt", NormalEnum.AUDIT_GQ);
                            }
                        }

                    } else {
                        //若申请的表不在负面清单里则跳过业务审批流程,
                        //当前系统里只配有一个子流程:业务审批流程
                        //所以下一个节点肯定是普通节点
                        if(nodelist.size()<2){
                            // 如果审批只有一个节点
                            firstNode = nodelist.get(0);
                            curProStatusData.put("spzt", NormalEnum.AUDIT_END);
                            // 当最后一级审批时，插入审批完成日期
                            SjkflcOutDemandApplication model = new SjkflcOutDemandApplication();
                            model.setDemandCode(applyData.getString("demandCode"));
                            model.setDemandEndTime(DateUtils.getNowTime());
                            model.setSbState( nodelist.get(0).getString("id"));
                            model.setSbStateName( nodelist.get(0).getString("jdmc"));
                            sjkflcOutDemandApplicationDao.update(model);
                        } else {
                            // 如果还有下一个节点
                            firstNode = nodelist.get(1);
                            curProStatusData.put("spzt", NormalEnum.AUDIT_WAIT);
                        }
                    }
                }else{
                    curProStatusData.put("spzt", NormalEnum.AUDIT_WAIT);
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
                    curProStatusData.put("sqsj", applyData.getString("sqsj"));
//                    curProStatusData.put("sqxqmc", "12313");
//                    curProStatusData.put("sqrssdw", "物资部");
                    dao.insertLatestProgress(curProStatusData);//添加所属流程状态
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
        // 判断是否要跳转流程，根据业务信息判断赋值为TRUE
        boolean isTZ = !"0".equals(applyData.get("tzFlag"));//true; //判断是否涉密，如果为0 是不涉密，为1 为涉密
        boolean isoutnode = NormalEnum.NODE_OUT.equals(curNode.getString("jdlx"));
        if (isoutnode && isTZ) {
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


    // 流程审批通过
    @Override
    public void auditProgressYes(PageData applyData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        PageData par1 = new PageData();
        par1.put("sslcbm", applyData.getString("lcbm"));
        List<PageData> nodelist = processManageDao.queryProcessNodeList(par1);
        int len = nodelist.size();
        if (len > 0) {
            // 判断是否要跳转流程，根据业务信息判断赋值为TRUE
            boolean isTZ = !"0".equals(applyData.get("tzFlag"));//true; //判断是否涉密，如果为0 是不涉密，为1 为涉密
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
            if(!"".equals(applyData.get("shjg")) && applyData.get("shjg") != null){
                dao.insertProcessLog(logData);//插入审批日志
            }

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
            if (isoutnode && isTZ) {
                //当前节点是子流程节点，什么都不做，因为子流程自己会去递归更新
                //判断子流程状态是否存在，存在则什么都不做
                //不存在子流程则创建
                PageData query1 = new PageData();
                query1.put("sjlc", applyData.getString("lcpch"));
                query1.put("lcbm", nextNode.getString("lcbm"));
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
                updateStep(applyData, nextNode, lastNode, curNode);
            }
        }
    }

    private void updateStep(PageData applyData, PageData nextNode, PageData lastNode, PageData curNode) {
        //更新当前流程审批进度
        if (curNode.equals(lastNode)) {//当前是本流程最后一个节点,即没有下一节点了
            bubleUpdateYes(applyData);
        } else {
            applyData.put("dqjdid", nextNode.getString("id"));
            applyData.put("dqjdbm", nextNode.getString("jdbm"));
            applyData.put("dqjdmc", nextNode.getString("jdmc"));
            applyData.put("dqjdlx", nextNode.getString("jdlx"));
            dao.editLatestProgress(applyData);
        }
    }

    private void bubleUpdateYes(PageData applyData) {
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
            String sqbsqbm = applyData.getString("sqbsqbm");
            String sqbywbm = applyData.getString("sqbywbm");
            System.out.println(sqbywbm);
            System.out.println(sqbsqbm);
//            String[] sqyhArr = sqyh.split(",");
            System.out.println(applyData);
            // 当最后一级审批时，插入审批完成日期
            SjkflcOutDemandApplication model = new SjkflcOutDemandApplication();
            if(applyData.getString("demandCode")== null){
                model.setDemandCode(applyData.getString("sqxqbm"));
            } else {
                model.setDemandCode(applyData.getString("demandCode"));
            }
            model.setDemandEndTime(DateUtils.getNowTime());
            sjkflcOutDemandApplicationDao.update(model);
        }
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
        } else {
            // 当最后一级审批时，插入审批完成日期
            SjkflcOutDemandApplication model = new SjkflcOutDemandApplication();
            model.setDemandCode(applyData.getString("demandCode"));
            model.setDemandEndTime(DateUtils.getNowTime());
            model.setSbState("-1");
            model.setSbStateName("驳回");
            sjkflcOutDemandApplicationDao.update(model);
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
            dao.insertProcessLog(logData);//插入审批日志

            //更新当前流程审批进度
            applyData.put("spzt", NormalEnum.AUDIT_NO);//本流程审核不通过
            bubleUpdateNo(applyData);//递归不通过父流程
        }
    }


    // 流程审批撤回
    public void auditProgressBack(PageData applyData) {
        List<PageData> list = dao.queryLatestProgress(applyData);
        System.out.println(list);
        if (list != null && list.size()<2) {
            PageData pd = new PageData();
            pd.put("lcpch",list.get(0).get("lcpch"));
            dao.delLcsprzb(pd);
        }
        dao.delLatestProgress(applyData);
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

    @Override
    public void delLcsprzb(PageData pd) {
        dao.delLcsprzb(pd);
    }

    @Override
    public List<PageData>  addNextNode( List<PageData> list) {
        for (int i = 0; i < list.size(); i++) {
            PageData par1 = new PageData();
            par1.put("sslcbm",  list.get(i).getString("lcbm"));
            List<PageData> nodelist = processManageDao.queryProcessNodeList(par1);
            int len = nodelist.size();
            if (len > 0) {
                String curNodeId =  list.get(i).getString("dqjdbm");//当前审批的节点编码
                PageData curNode = getCurNode(nodelist, curNodeId);
                PageData nextNode = getNextNode(nodelist, curNode, list.get(i));//下一个节点
                list.get(i).put("spjdmc",nextNode.get("jdmc"));
            }
        }
        return  list;
//
    }

}


















