package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.Anotation.PassToken;
import com.example.demo.Anotation.UserLoginToken;
import com.example.demo.domain.Message;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import com.example.demo.service.MessageService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.fileUtil;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;
    @UserLoginToken
    @PostMapping("/uploadImage")
    public ResponseData handleFileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) throws IOException {
        String token =req.getHeader("token");
        String userName = JWT.decode(token).getAudience().get(0);
        String filePath="src/main/resources/static/";
        /**
         * result[0]用于判断是否上传成功，[1]用于传出错误信息或者上传的地址；
         */
        String[] result= fileUtil.saveImg(file,filePath);
        String path=result[1];
        if(result[0]=="上传成功") {
            Message message = new Message(path, new Date(), userName, false, "pic");
            messageService.AddMessage(message);
            return new ResponseData(ExceptionMsg.SUCCESS, message);
        }
        else
            return new ResponseData(ExceptionMsg.FAILED,result[1]);

    }
    @UserLoginToken
    @GetMapping("/getNewMessage")
    public ResponseData getNewMessage(@RequestParam(value = "page") int page){
        List<Message> messages =messageService.getMessageNew(page);
        if(messages.isEmpty())
            return new ResponseData(ExceptionMsg.SUCCESS,"没有消息了");
        return new ResponseData(ExceptionMsg.SUCCESS,messages);
    }
    @UserLoginToken
    @PostMapping("/sendMessage")
    public ResponseData sendMessage(@RequestParam(value = "body") String body, HttpServletRequest request){
        String token=request.getHeader("token");
        String owner = JWT.decode(token).getAudience().get(0);
        Message message =new Message(body,new Date(),owner,false,"normal");
        if (messageService.AddMessage(message))
        return new ResponseData(ExceptionMsg.SUCCESS,message);
        else
            return new ResponseData(ExceptionMsg.FAILED,"发送失败");
    }
    @UserLoginToken
    @PostMapping("/deleteMessage")
    public ResponseData sendMessage(@RequestParam(value = "messageId") int messageId ){
        if (messageService.deleteMessage(messageId))
            return new ResponseData(ExceptionMsg.SUCCESS,"删除成功");
        else
            return new ResponseData(ExceptionMsg.FAILED,"删除失败");
    }
}