package com.hqep.dataSharingPlatform.sjfwgj.dao;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.FileDO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FileDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    int deleteByPrimaryKey(String ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    int insert(FileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    int insertSelective(FileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    FileDO selectByPrimaryKey(String ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    int updateByPrimaryKeySelective(FileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcfw_demand_file_d_to
     *
     * @mbg.generated Mon May 01 17:55:07 CST 2023
     */
    int updateByPrimaryKey(FileDO record);

    boolean insert(Map map1);

    Map select();
}