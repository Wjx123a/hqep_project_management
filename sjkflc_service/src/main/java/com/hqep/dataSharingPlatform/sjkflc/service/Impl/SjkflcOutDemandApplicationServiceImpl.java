package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOdsSjmlTUpDemandApplicationDao;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessAuditDao;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;
import utils.DateUtils;
import utils.MapObjUtil;
import utils.UUIDUtil;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOutDemandApplicationDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOutDemandApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外部需求受理申请单(SjkflcOutDemandApplication)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-06 14:13:39
 */
@Service("sjkflcOutDemandApplicationService")
public class SjkflcOutDemandApplicationServiceImpl implements SjkflcOutDemandApplicationService {
    @Autowired
    private SjkflcOutDemandApplicationDao sjkflcOutDemandApplicationDao;
    @Autowired
    private SjkflcProcessAuditDao sjkflcProcessAuditDao;
    @Autowired
    private SjkflcOdsSjmlTUpDemandApplicationDao sjkflcOdsSjmlTUpDemandApplicationDao;
    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    @Override
    public SjkflcOutDemandApplication queryById(String demandCode) {
        return sjkflcOutDemandApplicationDao.queryById(demandCode);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcOutDemandApplication> queryList(SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        return sjkflcOutDemandApplicationDao.queryList(sjkflcOutDemandApplication);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @param pageNum                    第几页
     * @param pageSize                   每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcOutDemandApplication> queryListByPage(SjkflcOutDemandApplication sjkflcOutDemandApplication, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcOutDemandApplication> list = sjkflcOutDemandApplicationDao.queryList(sjkflcOutDemandApplication);
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                SjkflcProcessAudit qModel = new SjkflcProcessAudit();
//                qModel.setDemandCode(list.get(i).getDemandCode());
//                List<SjkflcProcessAudit> spList = sjkflcProcessAuditDao.queryList(qModel);
//                list.get(i).setSpList(spList);
//            }
//        }
        PageInfo<SjkflcOutDemandApplication> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        String demandStartTime = DateUtils.getNowTime();
        sjkflcOutDemandApplication.setId(UUIDUtil.getUUID());
        sjkflcOutDemandApplication.setDemandName(sjkflcOutDemandApplication.getDemandOrgName()
                + sjkflcOutDemandApplication.getDemandDescReason() + demandStartTime);
        sjkflcOutDemandApplication.setDemandCode(UUIDUtil.getUUID());
        sjkflcOutDemandApplication.setDemandStartTime(demandStartTime);
        sjkflcOutDemandApplication.setCreateTime(demandStartTime);
        if (sjkflcOutDemandApplication.getSbState() == null) {
            sjkflcOutDemandApplication.setSbState("0");
            sjkflcOutDemandApplication.setSbStateName("草稿");
        }
        SjkflcOdsSjmlTUpDemandApplication model =
                MapObjUtil.Object2Object(sjkflcOutDemandApplication, SjkflcOdsSjmlTUpDemandApplication.class);
        String demandDesc = sjkflcOutDemandApplication.getDemandDescReason()+ "。"+ sjkflcOutDemandApplication.getDemandDescPurpose()+"。";
        model.setDemandDesc(demandDesc);
        model.setDemandData(sjkflcOutDemandApplication.getDemandContent());
        model.setUndertakeDept(sjkflcOutDemandApplication.getUndertakeDeptBig());
        model.setModifyType("1");
        model.setDemandStartTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandStartTime(),"ALL"));
        model.setDemandEndTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandEndTime(),"ALL"));
       // model.setBatchTime(DateUtils.getNowDate());
        // 单位名称和单位编码后期需要在编码表中取 ，目前没有编码表，写入定值。
        model.setOrgName("国网黑龙江省电力有限公司");
        model.setOrgCode("01020");
        // 上报国网对外共享
//        sjkflcOdsSjmlTUpDemandApplicationDao.insert(model);
        return sjkflcOutDemandApplicationDao.insert(sjkflcOutDemandApplication) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 是否成功
     */
    @Override
    public SjkflcOutDemandApplication insertForModel(SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        sjkflcOutDemandApplication.setId(UUIDUtil.getUUID());
        sjkflcOutDemandApplication.setCreateTime(DateUtils.getNowTime());
        if (sjkflcOutDemandApplication.getSbState() == null) {
            sjkflcOutDemandApplication.setSbState("0");
            sjkflcOutDemandApplication.setSbStateName("草稿");
        }
        return sjkflcOutDemandApplicationDao.insertForModel(sjkflcOutDemandApplication);
    }


    /**
     * 修改数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        // 如果数据是撤回（2）或者草稿（0）状态 对 数据记录上传表执行操作
        if ("0".equals(sjkflcOutDemandApplication.getSbState())
                || "2".equals(sjkflcOutDemandApplication.getSbState())) {
            // 转换实体写入数据记录上传表中
            SjkflcOdsSjmlTUpDemandApplication model =  MapObjUtil.Object2Object(sjkflcOutDemandApplication, SjkflcOdsSjmlTUpDemandApplication.class);
            String demandDesc = sjkflcOutDemandApplication.getDemandDescReason()+ "。"+ sjkflcOutDemandApplication.getDemandDescPurpose()+"。";
            model.setDemandDesc(demandDesc);
            model.setDemandData(sjkflcOutDemandApplication.getDemandContent());
            model.setUndertakeDept(sjkflcOutDemandApplication.getUndertakeDeptBig());
            model.setModifyType("2");
            model.setDemandStartTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandStartTime(),"ALL"));
            model.setDemandEndTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandEndTime(),"ALL"));
            //  model.setBatchTime(DateUtils.getNowDate());
            // 单位名称和单位编码后期需要在编码表中取 ，目前没有编码表，写入定值。
            model.setOrgName("国网黑龙江省电力有限公司");
            model.setOrgCode("01020");
            // 判断数据记录上传表中是否有该需求编码的记录
            SjkflcOdsSjmlTUpDemandApplication qModel = new SjkflcOdsSjmlTUpDemandApplication();
            qModel.setDemandCode(sjkflcOutDemandApplication.getDemandCode());
            List<SjkflcOdsSjmlTUpDemandApplication> qList = sjkflcOdsSjmlTUpDemandApplicationDao.queryList(qModel);
            if (qList !=null && qList.size()>0){
                // 存在 执行更新操作
                // 上报国网对外共享
                // sjkflcOdsSjmlTUpDemandApplicationDao.update(model);
            } else {
                // 不存在 执行写入操作
                // 上报国网对外共享
                // sjkflcOdsSjmlTUpDemandApplicationDao.insert(model);
            }
        }
        // 更新 外部需求受理申请单
        return sjkflcOutDemandApplicationDao.update(sjkflcOutDemandApplication) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String demandCode) {
        return sjkflcOutDemandApplicationDao.deleteById(demandCode) > 0;
    }


    /**
     * 通过主键更新上报标志数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean submitById(String demandCode) {
        SjkflcOutDemandApplication sjkflcOutDemandApplication = new SjkflcOutDemandApplication();
        sjkflcOutDemandApplication.setDemandCode(demandCode);
        sjkflcOutDemandApplication.setSbState("1");
        return sjkflcOutDemandApplicationDao.update(sjkflcOutDemandApplication) > 0;
    }
    /**
     * 通过主键进行撤回操作
     * 更新上报标志数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean backById(String demandCode) {
            // 数据是撤回（2）对 数据记录上传表执行操作
            // 转换实体写入数据记录上传表中
            SjkflcOutDemandApplication sjkflcOutDemandApplication = sjkflcOutDemandApplicationDao.queryById(demandCode);
            SjkflcOdsSjmlTUpDemandApplication model =  MapObjUtil.Object2Object(sjkflcOutDemandApplication, SjkflcOdsSjmlTUpDemandApplication.class);
            String demandDesc = sjkflcOutDemandApplication.getDemandDescReason()+ "。"+ sjkflcOutDemandApplication.getDemandDescPurpose()+"。";
            model.setDemandDesc(demandDesc);
            model.setDemandData(sjkflcOutDemandApplication.getDemandContent());
            model.setUndertakeDept(sjkflcOutDemandApplication.getUndertakeDeptBig());
            model.setModifyType("2");
            model.setDemandStartTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandStartTime(),"ALL"));
            model.setDemandEndTime(DateUtils.parseDate(sjkflcOutDemandApplication.getDemandEndTime(),"ALL"));
            //  model.setBatchTime(DateUtils.getNowDate());
            // 单位名称和单位编码后期需要在编码表中取 ，目前没有编码表，写入定值。
            model.setOrgName("国网黑龙江省电力有限公司");
            model.setOrgCode("01020");
            // 判断数据记录上传表中是否有该需求编码的记录
            SjkflcOdsSjmlTUpDemandApplication qModel = new SjkflcOdsSjmlTUpDemandApplication();
            qModel.setDemandCode(sjkflcOutDemandApplication.getDemandCode());
            List<SjkflcOdsSjmlTUpDemandApplication> qList = sjkflcOdsSjmlTUpDemandApplicationDao.queryList(qModel);
            if (qList !=null && qList.size()>0){
                // 存在 执行更新操作
                // 上报国网对外共享
                   // sjkflcOdsSjmlTUpDemandApplicationDao.update(model);
            } else {
                // 不存在 执行写入操作
                // 上报国网对外共享
                   // sjkflcOdsSjmlTUpDemandApplicationDao.insert(model);
            }
        sjkflcOutDemandApplication = new SjkflcOutDemandApplication();
        sjkflcOutDemandApplication.setDemandCode(demandCode);
        sjkflcOutDemandApplication.setSbState("2");
        sjkflcOutDemandApplication.setSbStateName("撤回");
        return sjkflcOutDemandApplicationDao.update(sjkflcOutDemandApplication) > 0;
    }
}
