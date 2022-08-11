package com.ssafy.api.service;

import com.ssafy.db.entity.User;

public interface MailService {
    void sendFindPwdMail(User user) throws Exception;
}
