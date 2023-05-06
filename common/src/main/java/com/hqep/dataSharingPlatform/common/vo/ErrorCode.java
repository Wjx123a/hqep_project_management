/**
 * Project Name:shiroBoot+mysql+mybatis+jpa
 * File Name:ErrorCode.java
 * Package Name:com.swq.common
 * Date:2019年12月7日下午3:09:15
 */
package com.hqep.dataSharingPlatform.common.vo;

/**
 * @author 邵文强
 * @Date 2019年12月7日 下午3:09:15
 * @version 1.0
 */
public class ErrorCode {
    /**     成功code */
    public static int SUCCESS = 0;
    /**     失败code */
    public static int FAIL = -1;
    /**  认证成功 */
    public static int ACCESS_DENIED = 10001;
    /**  未认证 */
    public static int NOT_CERTIFIED = 10002;
    /**  无权限 您登录的账号无此操作权限*/
    public static int NOT_PERMISSIONS = 10003;

}
