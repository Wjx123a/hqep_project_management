package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.ConfigUtil;
import com.hqep.dataSharingPlatform.common.utils.FastDFSClient;
import com.hqep.dataSharingPlatform.common.utils.FastDFSException;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcFileDao;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcFile;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * (SjkflcFile)表服务实现类
 *
 * @author liuzg
 * @since 2021-09-29 11:21:45
 */
@Service("sjkflcFileService")
public class SjkflcFileServiceImpl implements SjkflcFileService {
    @Autowired
    private SjkflcFileDao sjkflcFileDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SjkflcFile queryById(String id) {
        return sjkflcFileDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcFile 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcFile> queryList(SjkflcFile sjkflcFile) {
        return sjkflcFileDao.queryList(sjkflcFile);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcFile 实例对象
     * @param pageNum    第几页
     * @param pageSize   每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcFile> queryListByPage(SjkflcFile sjkflcFile, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcFile> list = sjkflcFileDao.queryList(sjkflcFile);
        PageInfo<SjkflcFile> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcFile 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcFile sjkflcFile) {
        if (sjkflcFile != null && "".equals(sjkflcFile.getId())) {
            String id = UUID.randomUUID().toString().toUpperCase();
            sjkflcFile.setId(id);
        }
        sjkflcFile.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        return sjkflcFileDao.insert(sjkflcFile) > 0;
    }

    /**
     * 修改数据
     *
     * @param sjkflcFile 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcFile sjkflcFile) {
        return sjkflcFileDao.update(sjkflcFile) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return sjkflcFileDao.deleteById(id) > 0;
    }

    /**
     * 上传附件
     *
     * @param files
     * @param data
     * @return 返回附件ID
     * @throws FastDFSException
     */
    @Override
    public List<SjkflcFile> fileUpload(MultipartFile[] files, Map<String, String> data) throws FastDFSException {
        List<SjkflcFile> result = new ArrayList<>();
        String fileServer = ConfigUtil.getString("file_server");
        String filePath = ConfigUtil.getString("local_file_path");
        for (MultipartFile file : files) {
            // 文件上传
            String id = UUID.randomUUID().toString().toUpperCase();
            boolean uploadFlag = false;
            if ("LOCAL".equalsIgnoreCase(fileServer)) {
                filePath = filePath + File.separator + id + "." + FileUtil.getFileSuffix(file.getOriginalFilename());
                String uploadResult = FileUtil.upLoadFile(file, filePath);
                if ("文件上传成功".equals(uploadResult)) {
                    uploadFlag = true;
                }
            } else if ("FDFS".equalsIgnoreCase(fileServer)) {
                FastDFSClient fastdfsClient = new FastDFSClient();
                filePath = fastdfsClient.upload(file, new HashMap<>());
                if (filePath != null && filePath.length() > 0) {
                    uploadFlag = true;
                }
            }
            // 如果上传成功，将文件信息入库
            if (uploadFlag) {
                SjkflcFile sjdklcFile = new SjkflcFile();
                sjdklcFile.setId(id);
                sjdklcFile.setCode(data.get("code"));
                sjdklcFile.setRemark(data.get("remark"));
                sjdklcFile.setFlag("0");
                sjdklcFile.setCreateUser(data.get("createUser"));
                sjdklcFile.setCreateTime(data.get("createTime"));
                sjdklcFile.setFileServer(fileServer);
                sjdklcFile.setFileName(file.getOriginalFilename());
                sjdklcFile.setFileSuffix(FileUtil.getFileSuffix(file.getOriginalFilename()));
                sjdklcFile.setFileType(FileUtil.getFileSuffix(file.getOriginalFilename()));
                sjdklcFile.setFilePath(filePath);
                if (insert(sjdklcFile)) {
                    result.add(sjdklcFile);
                }
            }
        }
        return result;
    }

    /**
     * 删除附件
     *
     * @param id
     * @return 是否成功
     * @throws FastDFSException
     */
    @Override
    public boolean fileDelete(String id) throws FastDFSException {
        boolean result = false;
        // 获取文件信息
        SjkflcFile sjkflcFile = queryById(id);
        String fileServer = sjkflcFile.getFileServer();
        String filePath = sjkflcFile.getFilePath();
        // 删除文件
        if ("LOCAL".equalsIgnoreCase(fileServer)) {
            if (FileUtil.delFile(filePath)) {
                result = true;
            }
        } else if ("FDFS".equalsIgnoreCase(fileServer)) {
            FastDFSClient fastdfsClient = new FastDFSClient();
            if (fastdfsClient.deleteFile(filePath) == 0) {
                result = true;
            }
        }
        // 删除库
        if (result) {
            result = this.deleteById(id);
        }
        return result;
    }

    /**
     * 下载附件
     *
     * @param id
     * @param response
     * @throws FastDFSException
     */
    @Override
    public void fileDownload(String id, HttpServletResponse response) throws FastDFSException {
        // 获取文件信息
        SjkflcFile sjkflcFile = queryById(id);
        String fileServer = sjkflcFile.getFileServer();
        String filePath = sjkflcFile.getFilePath();
        // 下载文件
        if ("LOCAL".equalsIgnoreCase(fileServer)) {
            FileUtil.downloadFile(response, "download.zip", filePath);
        } else if ("FDFS".equalsIgnoreCase(fileServer)) {
            FastDFSClient fastdfsClient = new FastDFSClient();
            fastdfsClient.downloadFile(filePath, response);
        }
    }

    /**
     * 下载压缩包
     *
     * @param ids
     * @param response
     * @throws FastDFSException
     */
    @Override
    public void zipDownload(String[] ids, HttpServletResponse response) {
        ZipOutputStream zipOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            String downloadFileName = URLEncoder.encode("download.zip", "UTF-8");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1"));
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                SjkflcFile sjkflcFile = queryById(id);
                String fileServer = sjkflcFile.getFileServer();
                String fileName = sjkflcFile.getFileName();
                String filePath = sjkflcFile.getFilePath();
                // 获取文件流
                if ("LOCAL".equalsIgnoreCase(fileServer)) {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
                } else if ("FDFS".equalsIgnoreCase(fileServer)) {
                    String urlPath = ConfigUtil.getString("file_server_addr");
                    String fileUrl = "http://" + urlPath + "/" + filePath;
                    HttpURLConnection conn = null;
                    try {
                        URL url = new URL(fileUrl);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(50000);
                        conn.setConnectTimeout(50000);
                        conn.setDoInput(true);
                        conn.setRequestProperty("Charset", "UTF-8");
                        conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
                        conn.setRequestProperty("Accept-Encoding", "identity");
                        int lengh = conn.getContentLength();
                        conn.connect();
                        int responseCode = conn.getResponseCode();
                        if (responseCode == 200) {
                            bufferedInputStream = new BufferedInputStream(conn.getInputStream());
                        } else {
                            bufferedInputStream = new BufferedInputStream(conn.getErrorStream());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (conn != null) conn.disconnect();
                    }
                }
                // 插入输出流zip包
                FileUtil.zipDownload(fileName, bufferedInputStream, zipOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipOutputStream != null) zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bufferedInputStream != null) bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
