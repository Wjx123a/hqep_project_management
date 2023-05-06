package com.hqep.dataSharingPlatform.sjfwgj.dao;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.LogDO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LogDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    int deleteByPrimaryKey(String ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    int insert(LogDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    int insertSelective(LogDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    LogDO selectByPrimaryKey(String ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    int updateByPrimaryKeySelective(LogDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    int updateByPrimaryKey(LogDO record);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demend_log_d_to
     *
     * @mbg.generated Mon May 01 19:29:45 CST 2023
     */
    Map select();

    boolean insert(Map map);
}