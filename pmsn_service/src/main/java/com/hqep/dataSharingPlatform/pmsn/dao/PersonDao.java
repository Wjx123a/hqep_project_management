package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @类名: PersonDao
 * @功能描述 人员信息模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/18
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface PersonDao {
    /**
     * @方法名:saveLoginInfo
     * @功能描述:创建账号信息
     * @作者信息：Wang_XD
     * @创建时间:16:56 2019/9/18
     **/
   Integer saveLoginInfo (PageData pd);

   /**
    * @方法名:savePersonInfo
    * @功能描述:创建人员信息
    * @作者信息：Wang_XD
    * @创建时间:18:17 2019/9/18
    **/
   Integer savePersonInfo (PageData pd);
   /**
    * @方法名:queryLoginNum
    * @功能描述:查询登陆账号是否可用
    * @作者信息：Wang_XD
    * @创建时间:15:03 2019/10/12
    **/
   Boolean queryLoginNum (String loginNum);

   /**
    * 查询用户列表
    * @param pd
    * @return
    */
   List<PageData> queryUserInfo(PageData pd);

   /**
    * 修改用户状态
    * @param pd
    */
   void updateUserStatus(PageData pd);

   /**
    * 重置密码123456
    * @param pd
    */
   void resetPassword(PageData pd);

   // 新建角色
   void insertRole(PageData pd);

   // 修改角色
   void updateRole(PageData pd);

   // 查询角色
   List<PageData> queryRole(PageData pd);

   // 查询单位
   List<PageData> queryCompany(PageData pd);

}
