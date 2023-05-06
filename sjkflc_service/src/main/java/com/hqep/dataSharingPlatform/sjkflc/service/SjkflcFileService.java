package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.FastDFSException;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (SjkflcFile)表服务接口
 *
 * @author liuzg
 * @since 2021-09-29 11:21:43
 */
public interface SjkflcFileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcFile queryById(String id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcFile> queryList(SjkflcFile sjkflcFile);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcFile 实例对象
     * @param pageNum    第几页
     * @param pageSize   每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcFile> queryListByPage(SjkflcFile sjkflcFile, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcFile 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcFile sjkflcFile);

    /**
     * 修改数据
     *
     * @param sjkflcFile 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcFile sjkflcFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 上传附件
     *
     * @param files
     * @param data
     * @return 返回附件ID
     * @throws FastDFSException
     */
    List<SjkflcFile> fileUpload(MultipartFile[] files, Map<String, String> data) throws FastDFSException;

    /**
     * 删除附件
     *
     * @param id
     * @return 是否成功
     * @throws FastDFSException
     */
    boolean fileDelete(String id) throws FastDFSException;

    /**
     * 下载附件
     *
     * @param id
     * @param response
     * @throws FastDFSException
     */
    void fileDownload(String id, HttpServletResponse response) throws FastDFSException;

    /**
     * 下载压缩包
     *
     * @param ids
     * @param response
     * @throws FastDFSException
     */
    void zipDownload(String[] ids, HttpServletResponse response);

}
