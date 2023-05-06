package com.hqep.dataSharingPlatform.common.utils;/**
 * Created by Administrator on 2018/7/24.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * @program:SerializeUtil
 * @description:序列化工具类
 * @author:Mr.G
 * @create:2018-07-24 10:41
 */
public class SerializeUtil {
    static Logger logger = LogManager.getLogger(SerializeUtil.class);
    public static byte[] serialize(Object value) {
        if (value == null) {
            logger.error("util模块-序列化类(serialize(Object value))");
            logger.error("序列化对象为null");
        }
        byte[] returnByte = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            returnByte = bos.toByteArray();
            os.close();
            bos.close();
        } catch (Exception e) {
            logger.error("util模块-序列化类(serialize(Object value))");
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            logger.error("error:"+writer.toString());
        } finally {
            close(os);
            close(bos);
        }
        return returnByte;
    }
    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    public static <T> T deserialize(byte[] in, Class<T>...requiredType) {
        Object returnValue = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                returnValue = is.readObject();
            }
        } catch (Exception e) {
            logger.error("util模块-反序列化类(deserialize)");
            logger.error(e.getMessage());
        } finally {
            close(is);
            close(bis);
        }
        return (T) returnValue;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error("util模块-关闭流类(close)");
                logger.error(e.getMessage());
            }
    }
}
