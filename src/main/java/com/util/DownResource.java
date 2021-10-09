package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 下载本地资源
 * Created by Administrator on 2018/11/15.
 */
public class DownResource {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(DownResource.class);

    /**
     * 下载本地文件
     *
     * @param request  请求
     * @param response 响应
     * @param fileUrl  资源路径
     * @param fileName 文件名字
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response, String fileUrl, String fileName) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        InputStream fis = null;
        try {
            setFileDownloadHeader(request, response, fileName);
            fis = new FileInputStream(fileUrl);
            bis = new BufferedInputStream(fis);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);
            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, byteRead);
            }
            bos.flush();
            fis.close();
            bis.close();
            fos.close();
            bos.close();
        } catch (Exception e) {
            log.error("下载信息失败",e);
        }
    }

    private static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            //中文文件名支持
            String encodedfileName = null;
//            String agent = request.getHeader("USER-AGENT");
//
//            if (null != agent && -1 != agent.indexOf("MSIE")) {//IE
//                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
//                encodedfileName = new String(fileName.replaceAll(" ", "").getBytes("UTF-8"), "iso-8859-1");
////                encodedfileName = new String(fileName.replaceAll(" ", "").getBytes("UTF-8"), "UTF-8");
//            } else {
//                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//            }

            encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (UnsupportedEncodingException e) {
            log.error("下载脚本错误信息：",e);
        }
    }
}
