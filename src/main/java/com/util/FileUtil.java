package com.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    //写文件
    public static void writeFile(File file, String writeStr) {
        // 向文件写入内容(输出流)
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(writeStr);
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static boolean deletefile(File file) throws Exception {
        try {
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(file.getPath() + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deletefile(new File(file.getPath() + "\\" + filelist[i]));
                    }
                }
                file.delete();
            }

        } catch (FileNotFoundException e) {
            logger.error("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }

    public static String uploadFile(MultipartFile[] files, String uploadPath) {
        String rtnFileName = "";
        for (MultipartFile multipartFile : files) {
            OutputStream os = null;
            InputStream inputStream = null;
            String fileName = null;
            try {
                inputStream = multipartFile.getInputStream();

                String originalFilename=multipartFile.getOriginalFilename();

                int unixSep=originalFilename.lastIndexOf("/");
                int windowSep=originalFilename.lastIndexOf("\\");
                int pos=windowSep>unixSep?windowSep:unixSep;
                if(pos!=-1){
                    originalFilename=originalFilename.substring(pos+1);
                }

                fileName = UUID.randomUUID().toString().replace("-", "") + "_" + originalFilename;

                if (StringUtils.isNotEmpty(rtnFileName)) {
                    rtnFileName += ",";
                }
                rtnFileName += fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 2、保存到临时文件
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流保存到本地文件
                File tempFile = new File(uploadPath);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
                // 开始读取
                while ((len = inputStream.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 完毕，关闭所有链接
                try {
                    os.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return rtnFileName;
    }
}
