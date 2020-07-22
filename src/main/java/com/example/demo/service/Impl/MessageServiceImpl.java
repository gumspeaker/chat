package com.example.demo.service.Impl;

import com.example.demo.common.commonData;
import com.example.demo.dao.MessageDao;
import com.example.demo.domain.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    String RootPath=commonData.ROOT_PATH;
    @Override
    public List<Message> getMessageNew(int Begin) {
        List<Message> recentlyMessage = null;
        int pageSize=commonData.pageSize;
        try {
            recentlyMessage=messageDao.GetNewMessage(Begin*pageSize, pageSize);
            for (Message message:recentlyMessage) {
                if(message.isDeleted()==true)
                    message.setBody("用户已撤回");
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return recentlyMessage;
    }

    @Override
    public Boolean AddMessage(Message message) {
        boolean flag=false;
        try{
            flag=messageDao.addMessage(message);
        }catch (Exception e){
            throw new RuntimeException("添加失败");
        }
        return flag;
    }

    @Override
    public Boolean deleteMessage(int MessageId) {
        boolean flag=false;
        try{
            flag=messageDao.deleteMessage(MessageId);
        }catch (Exception e){
            throw new RuntimeException("删除失败");
        }
        return flag;
    }
}
