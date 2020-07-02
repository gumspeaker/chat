package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class MessageController {
    @Autowired
    MessageService messageService;
    String rootpath= commonData.RootPath;
    @PostMapping("/uploadImage")
    public ResponseData handleFileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req, HttpServletResponse response) throws IOException {
       // response.setCharacterEncoding("utf-8");
        String token =req.getHeader("Authorization");
        String userName=JwtUtil.getUserNameFromJwt(token);

        /**
         * result[0]用于判断是否上传成功，[1]用于传出错误信息或者上传的地址；
         */
        String[] result= fileUtil.saveImg(file,rootpath);
        String path=result[1];
        if(result[0]=="上传成功") {
            Message message = new Message(path, new Date(), userName, false, "pic");
            //int id=messageService.
            messageService.AddMessage(message);
            return new ResponseData(ExceptionMsg.SUCCESS, message);
        }
        else{

            return new ResponseData(ExceptionMsg.FAILED,result[1]);
        }


    }

    @GetMapping("/getNewMessage")
        public ResponseData getNewMessage(@RequestParam(value = "page") int page,HttpServletResponse response) throws IOException {
        List<Message> messages =messageService.getMessageNew(page);
        for (Message message:messages) {
            if (message.getType()=="pic"&&!message.isDeleted()){
                String path=rootpath+message.getBody();
                File file = new File(path);
                if(!file.exists()){
                    message.setBody("图片已经失效");
                    break;
                }
                FileInputStream inputStream = new FileInputStream(file);
                response.setContentType("application/force-download");
                response.addHeader("Content-disposition", "inline;fileName=" + message.getBody());
                OutputStream os = response.getOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = inputStream.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                inputStream.close();
            }
        }
        if(messages.isEmpty())
            return new ResponseData(ExceptionMsg.SUCCESS,"没有消息了");

        return new ResponseData(ExceptionMsg.SUCCESS,messages);
    }

    @PostMapping("/sendMessage")
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

    @PostMapping("/deleteMessage")
    public ResponseData deleteMessage(@RequestParam(value = "messageId") int messageId ){
        if (messageService.deleteMessage(messageId))
            return new ResponseData(ExceptionMsg.SUCCESS,"删除成功");
        else
            return new ResponseData(ExceptionMsg.FAILED,"删除失败");
    }

}