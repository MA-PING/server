package org.maping.maping.api.auth.controller;

import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.auth.service.MailService;
import org.maping.maping.common.utills.jwt.JWTUtill;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;
import org.maping.maping.common.response.BaseResponse;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@RestController
@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;

    @Operation(summary = "이메일 인증번호 발송", description = "이메일 인증번호를 발송하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-email-verification")
    public BaseResponse<String> sendVerificationCode(@RequestParam String email) {
        try {
            String authCode = mailService.sendSimpleMessage(email);
            return new BaseResponse<>(HttpStatus.OK.value(), "메일 발송 성공", authCode);
        } catch (MessagingException e) {
            log.error("메일 발송 오류: {}", e.getMessage(), e);
            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "메일 발송 실패", "메일 발송 실패");
        }
    }

    @Operation(summary = "이메일 인증번호 확인", description = "사용자가 입력한 인증번호를 검증하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verfy-email")
    public BaseResponse<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        if (mailService.verifyCode(email, code)) {
            // 인증 성공 시
            return new BaseResponse<String>(HttpStatus.OK.value(),"인증에 성공하였습니다.", "인증에 성공하였습니다.");
        } else {
            // 인증 실패 시
            return new BaseResponse<String>(HttpStatus.BAD_REQUEST.value(), "인증에 실패하였습니다.", "인증에 실패하였습니다.");
        }
    }

}
