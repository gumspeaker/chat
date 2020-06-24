package com.example.demo.service;
import com.example.demo.domain.Message;

import java.util.List;
public interface MessageService {
    List<Message> getMessageNew(int Begin);
    Boolean AddMessage(Message message);
    Boolean deleteMessage(int MessageId);
}
