package com.example.demo.dao;

import com.example.demo.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface MessageDao {
    public static final int page=10;
    Boolean addMessage(Message message);
    Boolean deleteMessage(int messageId);
    List<Message> GetUserMessage(String userName);
    List<Message> GetNewMessage(int Begin,  int pageSize);
}
