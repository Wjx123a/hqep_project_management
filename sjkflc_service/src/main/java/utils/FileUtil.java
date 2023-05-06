package utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @Description TODO  文件上传工具类
 * @Author liuzg
 * @Date 2019/10/10 10:51
 * @Version 1.0
 **/
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 创建目录
     *
     * @param dir 目录
     */
    public static void mkdir(String dir) {
        try {
            String dirTemp = dir;
            File dirPath = new File(dirTemp);
            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        } catch (Exception e) {
            logger.error("创建目录操作出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 扫描指定路径下指定文件类型
     *
     * @param folderPath
     * @return
     */
    public static List<File> getListFiles(String folderPath) {
        logger.debug("扫描指定路径(" + folderPath + ")下的文件。");
        File folderFile = new File(folderPath);
        List<File> resultFiles = new ArrayList<File>();
        if (folderFile.isFile()) {
            logger.error("输入路径为文件非文件夹路径。");
        } else {
            File[] files = folderFile.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (!file.isFile()) {
                        continue;
                    }
                    resultFiles.add(file);
                }
            } else {
                logger.debug("指定路径(" + folderPath + ")下没有文件。");
            }
        }
        logger.debug("指定路径(" + folderPath + ")下，共有" + resultFiles.size() + "个文件。");
        return resultFiles;
    }

    /**
     * 扫描指定路径下指定文件类型
     *
     * @param folderPath
     * @param fileType
     * @return
     */
    public static List<File> getListFilesByType(String folderPath, String fileType) {
        if (fileType == null || "".equals(fileType)) {
            logger.error("指定拓展名为空。");
            return null;
        }
        logger.debug("扫描指定路径(" + folderPath + ")下的" + fileType + "文件。");
        File folderFile = new File(folderPath);
        List<File> resultFiles = new ArrayList<File>();
        if (folderFile.isFile()) {
            logger.error("输入路径为文件非文件夹路径。");
        } else {
            File[] files = folderFile.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (!file.isFile()) {
                        continue;
                    }
                    if ((file.getName().toUpperCase()).indexOf(fileType.toUpperCase()) != -1) {
                        resultFiles.add(file);
                    }
                }
            } else {
                logger.debug("指定路径(" + folderPath + ")下没有文件。");
            }
        }
        logger.debug("指定路径(" + folderPath + ")下，共有" + resultFiles.size() + "个" + fileType + "文件。");
        return resultFiles;
    }

    /**
     * 文件按时间顺序排列
     *
     * @param list
     * @param path
     * @return
     */
    public static List<File> getFileSort(List<File> list, String path) {
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
        return list;
    }

    /**
     * 复制单个文件
     *
     * @param sourceFilePath   包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param targetFolderPath 目标文件目录；若文件目录不存在则自动创建  如：E:/phsftp/dest
     * @return
     */
    public static boolean copyFile(String sourceFilePath, String targetFolderPath) {
        try {
            FileInputStream in = new FileInputStream(sourceFilePath);
            FileOutputStream out = new FileOutputStream(targetFolderPath + "/" + new File(sourceFilePath).getName());
            int len;
            byte buffer[] = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            in.close();
            logger.debug("复制文件" + sourceFilePath + "到" + targetFolderPath + "成功。");
            return true;
        } catch (Exception e) {
            logger.error("复制文件操作出错:" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param sourceFilePath 包含路径的文件名
     * @return
     */
    public static boolean delFile(String sourceFilePath) {
        try {
            File delFile = new File(sourceFilePath);
            if (delFile.delete()) {
                System.out.println("删除文件：" + sourceFilePath);
                logger.debug("删除文件" + sourceFilePath + "成功。");
                return true;
            } else {
                logger.debug("删除文件" + sourceFilePath + "失败。");
                return false;
            }
        } catch (Exception e) {
            logger.error("删除文件操作出错: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移动文件
     *
     * @param sourceFilePath
     * @param targetFolderPath
     * @return
     */
    public static boolean moveFile(String sourceFilePath, String targetFolderPath) {
        boolean isCopy = copyFile(sourceFilePath, targetFolderPath);
        boolean isDel = delFile(sourceFilePath);
        return (isCopy && isDel);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param filePath
     * @return
     */
    public static String upLoadFile(MultipartFile file, String filePath) {
        if (Objects.isNull(file) || file.isEmpty()) {
            logger.info("文件为空");
            return "文件为空，请重新上传";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                String rootDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
                Files.createDirectories(Paths.get(rootDir));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            logger.debug("文件写入成功...");
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }

    /**
     * 文件下载
     *
     * @param response
     * @param fileName
     * @param path
     */
    public static void downloadFile(HttpServletResponse response, String fileName, String path) {
        if (fileName != null) {
            //设置文件路径
            File file = new File(path);
            if (file.exists()) {
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                try {
                    response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                OutputStream outPutStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    int len = 0;
                    outPutStream = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    while ((len = bufferedInputStream.read(buffer)) > 0) {
                        outPutStream.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outPutStream != null) outPutStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (bufferedInputStream != null) bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fileInputStream != null) fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 批量下载（文件流插入zip输出流）
     *
     * @param fileName
     * @param bufferedInputStream
     * @param zipOutputStream
     */
    public static void zipDownload(String fileName, BufferedInputStream bufferedInputStream, ZipOutputStream zipOutputStream) {
        try {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = bufferedInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, len);
            }
            zipOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件名称的后缀
     *
     * @param fileName 文件名 或 文件路径
     * @return 文件后缀
     */
    public static String getFileSuffix(String fileName) {
        String suffix = null;
        String originalFilename = fileName;
        if (StringUtils.isNotBlank(fileName)) {
            if (fileName.contains(File.separator)) {
                fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            }
            if (fileName.contains(".")) {
                suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error("filename error without suffix : {}", originalFilename);
                }
            }
        }
        return suffix;
    }


}