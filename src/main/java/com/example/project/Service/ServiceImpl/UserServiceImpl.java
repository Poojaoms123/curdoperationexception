package com.example.project.Service.ServiceImpl;

import com.example.project.Exception.CollegeNotFound;

import com.example.project.Exception.EmailAlreadyExists;
import com.example.project.Exception.MobileNoAlreadyExists;
import com.example.project.Exception.UserNotFound;
import com.example.project.Model.SaveRequest.SaveUserRequest;
import com.example.project.Model.User;
import com.example.project.Repository.Projection.UserProjection;
import com.example.project.Repository.UserRepository;
import com.example.project.Response.pageDTO;
import com.example.project.Service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Object saveOrUpdate(SaveUserRequest saveUserRequest) {
        if (userRepository.existsById(saveUserRequest.getUserId())) {
            User user = userRepository.findById(saveUserRequest.getUserId()).get();
            user.setUserName(saveUserRequest.getUserName());
            user.setUserEmail(saveUserRequest.getUserEmail());
            user.setUserMobileNo(saveUserRequest.getUserMobileNo());
            user.setUserIsDeleted(false);
            user.setUserIsActive(true);
            userRepository.save(user);
            return "Updated Sucessfully";
        } else {
            User user = new User();
            user.setUserName(saveUserRequest.getUserName());
            /*if (userRepository.existsByUserEmail(saveUserRequest.getUserEmail())) {
                throw new EmailAlreadyExists("Email already exist");
            }*/
            user.setUserEmail(saveUserRequest.getUserEmail());
           /* if (userRepository.existsByUserMobileNo(saveUserRequest.getUserMobileNo())) {
                throw new MobileNoAlreadyExists("Mobile No already exist");
            }*/
            user.setUserMobileNo(saveUserRequest.getUserMobileNo());
            user.setUserIsDeleted(false);
            user.setUserIsActive(true);
            userRepository.save(user);

            Context context = new Context();
            context.setVariable("name", user.getUserName());
            String process = templateEngine.process("EmailTemplate.html", context);
            this.sendEmailTOHtmlTemplate("pooja.omsoftware1@gmail.com", "Application", process);

            int otp = OtpService.generateOtp(saveUserRequest.getUserEmail());
            int Otp = OtpService.getOtp(saveUserRequest.getUserEmail());
            OtpService.clearOtp(saveUserRequest.getUserEmail());

            this.sendEmail("pooja.omsoftware1@gmail.com", "Report", "hello",otp);
            return "Saved Sucessfully";
        }
/*
        return userRepository.findById(saveUserRequest.getUserId()).map(user -> {
            user = User.builder().userName(saveUserRequest.getUserName()).userMobileNo(saveUserRequest.getUserMobileNo()).userIsDeleted(false).userIsActive(true).
                    userEmail(saveUserRequest.getUserEmail()).build();
            userRepository.save(user);
            return "Updated successfully";
        }).orElseGet(() -> {
            User user = User.builder().userName(saveUserRequest.getUserName()).userMobileNo(saveUserRequest.getUserMobileNo()).userIsDeleted(false).userIsActive(true).
                    userEmail(saveUserRequest.getUserEmail()).build();
            userRepository.save(user);
            return "saved successfully";
        });
    }*/
    }

    public void sendEmail(String to, String subject, String body, int otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText("your otp is:"+otp);
        javaMailSender.send(mailMessage);
    }

    public void sendEmailTOHtmlTemplate(String to, String body, String subject) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            mimeMessageHelper.setTo(to);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object deleteById(Long userId) {
       /* if (userRepository.existsById((userId))) {
            User user = userRepository.findById(userId).get();
            user.setUserIsDeleted(false);
            userRepository.save(user);
            return "deleted successfully";
        } else {
            throw new CollegeNotFound("college not found");
        }*/
        return userRepository.findById(userId).map(user -> {
            userRepository.deleteById(userId);
            return "deleted successfully";
        }).orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Override
    public Object getById(Long userId) {
        /*if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            return user;
        } else {
            throw new UserNotFound("user not found");
        }*/
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFound("user not found"));
    }

    @Override
    public Object changeStatus(Long userId) {
       /* if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            if (user.getUserIsDeleted()) {
                user.setUserIsDeleted(false);
                userRepository.save(user);
                return "Inactive";
            } else {
                user.setUserIsDeleted(false);
                userRepository.save(user);
                return "Active";
            }
        } else {
            throw new UserNotFound("user not found");
        }*/
        return userRepository.findById(userId).map(user -> {
            if (user.getUserIsDeleted()) {
                user.setUserIsDeleted(false);
                userRepository.save(user);
                return "Inactive";
            } else {
                user.setUserIsDeleted(false);
                userRepository.save(user);
                return "Active";
            }
        }).orElseThrow(() -> new UserNotFound("user not found"));
    }

    @Override
    public List<User> getAllUser(String userName, String mobileNo, String email, Pageable pageable) {
        List<User> user = userRepository.getAllUser();
        return user;

    }

    @Override
    public Object getAllUser11(String userName, String userMobileNo, String userEmail, Pageable pageable) {
        Page<UserProjection> users = null;
        if (StringUtils.isNotBlank(userName)) {
            userName = userName.toLowerCase();
        } else {
            userName = null;
        }
        if (StringUtils.isNotBlank(userEmail)) {
            userEmail = userName.toLowerCase();
        } else {
            userEmail = null;
        }
        if (StringUtils.isNotBlank(userMobileNo)) {
            userMobileNo = null;
        }
        if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(userEmail) && StringUtils.isBlank(userMobileNo)) {
            System.out.println("userName");
            users = userRepository.getAllByUserName(userName, pageable);
        } else if (StringUtils.isBlank(userName) && StringUtils.isNotBlank(userEmail) && StringUtils.isBlank(userMobileNo)) {
            System.out.println("userEmail");
            users = userRepository.getAllUserEmail(userEmail, pageable);
        } else if (StringUtils.isBlank(userName) && StringUtils.isBlank(userEmail) && StringUtils.isNotBlank(userMobileNo)) {
            System.out.println("userMobileNo");
            users = userRepository.getAllUserMobileNo(userMobileNo, pageable);
        } else if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userEmail) && StringUtils.isBlank(userMobileNo)) {
            System.out.println("userNameAndUserEmail");
            users = userRepository.getAllByUserNameAndUserEmail(userName, userEmail, pageable);
        } else if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(userEmail) && StringUtils.isNotBlank(userMobileNo)) {
            System.out.println("userNameAndUserMobileNo");
            users = userRepository.getAllUserNameAndUserMobileNo(userName, userMobileNo, pageable);
        } else if (StringUtils.isBlank(userName) && StringUtils.isNotBlank(userEmail) && StringUtils.isNotBlank(userMobileNo)) {
            System.out.println("userEmailAndUserMobileNo");
            users = userRepository.getAllUserEmailAndUserMobileNo(userEmail, userMobileNo, pageable);
        } else if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userEmail) && StringUtils.isNotBlank(userMobileNo)) {
            System.out.println("userNameAndUserEmailAndUserMobileNo");
            users = userRepository.getAllByUserNameAndUserEmailAndUserMobileNo(userName, userEmail, userMobileNo, pageable);
        } else {
            users = userRepository.getAll(pageable);
        }
        return new pageDTO(users.getContent(), users.getTotalElements(), users.getNumber(), users.getTotalPages());
    }

    @Override
    public Object deleteMultipleUser(String userId) {
        List<Long> userList = new ArrayList<>();
        String a[] = userId.split(",");
        for (int i = 0; i < a.length; i++) {
            Long a1 = Long.parseLong(a[i]);
            userList.add(a1);
        }
        userRepository.deleteByIdIn(userList);
        return "delete sucessfully";
    }

    @Override
    public Object checkUserEmailExits(String userEmail, Long userId) {
        if (userId > 0) {
            List<Long> userIds = new ArrayList<>();
            userIds.add(userId);
            if (userRepository.existsByUserEmailAndUserIdNotIn(userEmail.trim(), userIds)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (userRepository.existsByUserEmail(userEmail.trim())) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Object checkMobileNoExits(String userMobileNo, Long userId) {
        if (userId > 0) {
            List<Long> userIds = new ArrayList<>();
            userIds.add(userId);
            if (userRepository.existsByUserMobileNoAndUserIdNotIn(userMobileNo.trim(), userIds)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (userRepository.existsByUserMobileNo(userMobileNo.trim())) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream()
                .collect(Collectors.toList());
    }


}
