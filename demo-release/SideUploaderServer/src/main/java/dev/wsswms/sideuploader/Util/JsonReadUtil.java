package dev.wsswms.sideuploader.Util;

import java.io.*;

/**
 * @author: yin
 * @className: ReadUtil
 * @packageName: dev.wsswms.sideuploader.Util
 * @description: Json读取工具类
 * @data: 2020/5/8 13:48
 **/
public class JsonReadUtil {
    /**
     * 读取为字符串
     *
     * @param filePath Json文件路径
     * @return 字符串
     */
    public static String ToString(String filePath) {
        try {
            File jsonFile = new File(filePath);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();

            //reader.read() 到达文件流末尾时返回-1
            //到达文件尾之前将文件写入StringBuffer
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();

            String jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
