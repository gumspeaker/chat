package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.demo.Anotation.UserLoginToken;
import com.example.demo.common.commonData;
import com.example.demo.domain.Message;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import com.example.demo.service.MessageService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.fileUtil;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api")
public class MessageController {
    @Autowired
    MessageService messageService;
    String rootpath= commonData.RootPath;
    @PostMapping("/uploadImage")
    @UserLoginToken
    public ResponseData handleFileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req, HttpServletResponse response) throws IOException {
       // response.setCharacterEncoding("utf-8");
        String token =req.getHeader("Authorization");
        String userName=JwtUtil.getUser(token);

        /**
         * result[0]用于判断是否上传成功，[1]用于传出错误信息或者上传的地址；
         */
        String[] result= fileUtil.saveImg(file,rootpath);
        String path=result[1];
        if(result[0]=="上传成功") {
            Message message = new Message(path, new Date(), userName, false, "pic");
            //int id=messageService.
            messageService.AddMessage(message);
            WebSocketServer.sendAll(JSON.toJSONString(message));
            return new ResponseData(ExceptionMsg.SUCCESS, message);
        }
        else{

            return new ResponseData(ExceptionMsg.FAILED,result[1]);
        }


    }

    @GetMapping("/getNewMessage")
    @UserLoginToken
        public ResponseData getNewMessage(@RequestParam(value = "page") int page,HttpServletResponse response) throws IOException {
        List<Message> messages =  messageService.getMessageNew(page);
        if(messages.isEmpty())
            return new ResponseData(ExceptionMsg.SUCCESS,"没有消息了");

        return new ResponseData(ExceptionMsg.SUCCESS,messages);
    }

    @PostMapping("/sendMessage")
    @UserLoginToken
    public ResponseData sendMessage(@RequestParam(value = "body") String body, HttpServletRequest request) throws IOException {
        String token=request.getHeader("Authorization");
        String owner = JWT.decode(token).getAudience().get(0);
        Message message =new Message(body,new Date(),owner,false,"normal");
        if (messageService.AddMessage(message)){
            WebSocketServer.sendAll(JSON.toJSONString(message));
        return new ResponseData(ExceptionMsg.SUCCESS,message);
        }
        else
            return new ResponseData(ExceptionMsg.FAILED,"发送失败");
    }
    @RequestMapping(value = "/image/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    @UserLoginToken
    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String image_name) throws Exception{

        byte[] imageContent ;
        String path = rootpath + image_name;
        imageContent = fileToByte(new File(path));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
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
    @PostMapping("/deleteMessage")
    public ResponseData deleteMessage(@RequestParam(value = "messageId") int messageId ){
        if (messageService.deleteMessage(messageId))
            return new ResponseData(ExceptionMsg.SUCCESS,"删除成功");
        else
            return new ResponseData(ExceptionMsg.FAILED,"删除失败");
    }

}