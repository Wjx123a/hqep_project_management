package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.hqep.dataSharingPlatform.common.utils.DataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessManageDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.rpc.ServiceException;
import java.util.List;

@Service
public class SjkflcProcessManageServiceImpl implements SjkflcProcessManageService {


    @Autowired
    private SjkflcProcessManageDao dao;

    @Override
    public List<PageData> queryProcessList(PageData pd) throws ServiceException {
        try {
            return dao.queryProcessList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PageData> queryProcessNodeList(PageData pd) throws ServiceException {
        try {
            return dao.queryProcessNodeList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateProcess(PageData pd) throws ServiceException {
        try {
            dao.updateProcess(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateProcessNode(PageData pd) throws ServiceException {
        try {
            dao.updateProcessNode(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateProcessNodeJdpx(PageData pd) throws ServiceException {
        try {
            dao.updateProcessNodeJdpx(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    // 批量添加保存
    @DataSource(name=DataSource.DATA_SOURCE_1)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void insertProcess(PageData pd) throws ServiceException {
        try {
            dao.insertProcess(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    // 批量添加保存
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void insertProcessNode(PageData pd) throws ServiceException {
        try {
            dao.insertProcessNode(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 判断流程编码是否存在
    @Override
    public int queryCount(PageData pd) throws ServiceException {
        try {
            return dao.queryCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 判断相同流程下的流程节点是否存在
    @Override
    public int queryCountLcjd(PageData pd) throws ServiceException {
        try {
            return dao.queryCountLcjd(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 判断是否只有一条主流程或者子流程在启用
    @Override
    public int queryProcessStatus(PageData pd) throws ServiceException {
        try {
            return dao.queryProcessStatus(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询当前启用子流程的节点编码
    public List<PageData> queryEnableChildProcessNodeList(PageData pd) throws ServiceException {
        try {
            return dao.queryEnableChildProcessNodeList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
