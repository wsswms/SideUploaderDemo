package dev.wsswms.sideuploader.Util;

import java.io.*;

/**
 * @author: yin
 * @className: WriteUtil
 * @packageName: dev.wsswms.sideuploader.Util
 * @description: Json写入 工具类
 * @data: 2020/5/8 13:48
 **/
public class JsonWriteUtil {

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param jsonStr  Json字符串
     */
    public static void ToFile(String filePath, String jsonStr) {
        try {
            FileWriter fw = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(fw);
            out.write(jsonStr);
            out.println();
            fw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
