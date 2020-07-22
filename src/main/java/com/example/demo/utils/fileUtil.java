package com.example.demo.utils;

import com.example.demo.common.FIleType;
import com.mysql.cj.log.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;
import java.util.logging.Logger;

public class fileUtil {
//    private Logger logger=new Logger();
    public static String[] saveImg(MultipartFile file, String path) throws IOException {
         String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String newName = UUID.randomUUID() + suffixName;
       String fileType= (String) FIleType.getMap().get(suffixName);
        File dest = new File(path + newName);
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return  new String[]{"上传失败," , e.getMessage(),fileType};
            } catch (IOException e) {
                e.printStackTrace();
                return new String[]{"上传失败," , e.getMessage(),fileType};
            }

        }
        return new String[]{"上传成功",newName,fileType};
    }
    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }
}
