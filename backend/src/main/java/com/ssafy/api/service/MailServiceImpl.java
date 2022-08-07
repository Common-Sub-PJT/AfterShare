package com.ssafy.api.service;

import com.ssafy.common.auth.AuthKey;
import com.ssafy.common.util.MailUtils;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service("MailService")
public class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    MailUtils sendMail;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 메일 전송
//    @Override
//    public void sendConfirmMail(User user, String authKey)
//            throws MessagingException, UnsupportedEncodingException {
//
//        sendMail = new MailUtils(mailSender);
//
//        sendMail.setSubject("[뒤풀이어] 회원가입 이메일 인증");
//        sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
//                .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
//                .append("<a href='https://i7b303.p.ssafy.io/api/v1/mail/valid?email=")
//                .append(user.getEmail())
//                .append("&authKey=")
//                .append(authKey)
//                .append("' target='_blenk'>이메일 인증 확인</a>")
//                .toString());
//
//        sendMail.setFrom("chasonghui@naver.com", "뒤풀이어");
//        sendMail.setTo(user.getEmail());
//        sendMail.send();
//    }
    @Override
    public void sendFindPwdMail(User user) throws Exception {
        // password 생성
        String password = new AuthKey().getKey(10);
        user.setPassword(passwordEncoder.encode(password));

        sendMail = new MailUtils(mailSender);

        sendMail.setSubject("[뒤풀이어] 비밀번호 변경 안내");
        sendMail.setText(new StringBuffer().append("<h1>[비밀번호 변경]</h1>")
                .append("<p>변경된 비밀번호를 안내드립니다.</p>")
                .append(password)
                .toString());

        sendMail.setFrom("chasonghui@naver.com", "뒤풀이어");
        sendMail.setTo(user.getEmail());
        sendMail.send();

        userRepository.save(user);
    }
}
