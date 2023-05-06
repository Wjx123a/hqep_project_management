package com.hqep.dataSharingPlatform.pmsn.service.impl;


import com.hqep.dataSharingPlatform.pmsn.dao.PersonDao;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.hqep.dataSharingPlatform.common.utils.MD5;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.common.utils.StringUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.UUID;

/**
 * @类名: PersonServiceImple
 * @功能描述 人员信息模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/18
 */
@Service
public class PersonServiceImple implements PersonService {

    @Autowired
    private PersonDao personDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String NEW_PASSWORD = "123456";

    /**
     * @方法名:savePersonInfo
     * @功能描述:创建用户信息
     * @作者信息：Wang_XD
     * @创建时间:16:30 2019/9/18
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Integer savePersonInfo(PageData pd) {
        Integer person = 0;
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        if (!"".equals(pd.get("user_id")) && pd.get("user_id") != null) {
            id = (String) pd.get("user_id");
        }
        if (StringUtil2.isNotEmpty(pd.getString("password"))) {
            String password = MD5.md5(pd.getString("password"));
            System.out.println(pd.toString());
            if (id != null && !"".equals(id)) {
                boolean usable = personDao.queryLoginNum (pd.getString("loginNum"));
                if (!usable) {
                    pd.put("id",id);
                    pd.put("password", password);
                    logger.info("pd-->>" + pd);
                    person = personDao.saveLoginInfo(pd);
                    if (person > 0) {
                        personDao.savePersonInfo(pd);//创建人员信息
                    }
                } else {
                    return -2;
                }
            }
        } else {
            return -1;
        }
        return person;
    }

    /**
     * @方法名:boolenUsablePersonService
     * @功能描述:验证用户名是否存在
     * @作者信息：Wang_XD
     * @创建时间:16:07 2019/10/12
     **/
    @Override
    public boolean boolenUsablePersonService(String loginNum) {
        boolean usable = personDao.queryLoginNum(loginNum);
        return usable;
    }

    @Override
    public List<PageData> queryUserInfo(PageData pd) throws Exception {
        try {
            return personDao.queryUserInfo(pd);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void updateUserStatus(PageData pd) throws Exception {
        try {
            personDao.updateUserStatus(pd);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void resetPassword(PageData pd) throws Exception {
        try {
            String newPassword = MD5.md5(NEW_PASSWORD);
            pd.put("newPassword", newPassword);
            personDao.resetPassword(pd);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void insertRole(PageData pd) throws ServiceException {
        try {
            personDao.insertRole(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRole(PageData pd) throws ServiceException {
        try {
            personDao.updateRole(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PageData> queryRole(PageData pd) throws ServiceException {
        try {
            return personDao.queryRole(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PageData> queryCompany(PageData pd) throws ServiceException {
        try {
            return personDao.queryCompany(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public static void main(String[] args) {
        String os = "hqep";
        String mm = MD5.md5(os);
        System.out.println("hqep--->>" + mm);
    }
}


