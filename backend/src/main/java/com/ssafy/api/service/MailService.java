package com.ssafy.api.service;

import com.ssafy.db.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
//    void sendConfirmMail(User user, String authKey)
//            throws MessagingException, UnsupportedEncodingException;
    void sendFindPwdMail(User user) throws Exception;
}
