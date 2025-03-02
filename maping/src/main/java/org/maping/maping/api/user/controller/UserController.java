package org.maping.maping.api.user.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.auth.dto.request.LoginRequest;
import org.maping.maping.api.auth.dto.request.NicknameCheckRequest;
import org.maping.maping.api.auth.dto.request.PasswordRequest;
import org.maping.maping.api.auth.dto.request.UserRegistrationRequest;
import org.maping.maping.api.auth.dto.response.OAuthLoginResponse;
import org.maping.maping.api.auth.service.AuthService;
import org.maping.maping.api.auth.service.MailService;
import org.maping.maping.api.auth.service.OAuthService;
import org.maping.maping.api.user.service.UserService;
import org.maping.maping.common.enums.expection.ErrorCode;
import org.maping.maping.common.utills.jwt.dto.JwtDto;
import org.maping.maping.exceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;
import org.maping.maping.common.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.maping.maping.common.utills.jwt.JWTUtill;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTUtill jwtUtill;

    @Operation(summary = "회원탈퇴", description = "사용자 탈퇴하는 API")
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteUser(HttpServletRequest request) {
        // JWT에서 사용자 ID 추출
        Long userId = Long.parseLong(jwtUtill.getUserId(request));

        // 사용자 삭제 처리
        userService.deleteUser(userId);

        return ResponseEntity.ok(new BaseResponse(200, "회원 탈퇴 완료", null));
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회하는 API")
    @GetMapping("/info")
    public ResponseEntity<BaseResponse> getUserInfo(HttpServletRequest request) {
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        return ResponseEntity.ok(new BaseResponse(200, "사용자 정보 조회 성공", userService.getUserInfo(userId), true));
    }

    @Operation(summary = "닉네임 재설정", description = "사용자 닉네임을 재설정하는 API")
    @PostMapping("/info/update/nickname")
    public ResponseEntity<BaseResponse> updateNickname(HttpServletRequest request, @Valid @RequestBody NicknameCheckRequest nicknameCheckRequest) {
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        userService.updateNickname(userId, nicknameCheckRequest.getNickname());
        return ResponseEntity.ok(new BaseResponse(200, "닉네임 재설정 성공", null, true));
    }

    @Operation(summary = "비밀번호 재설정", description = "사용자 비밀번호를 재설정하는 API")
    @PostMapping("/info/update/password")
    public ResponseEntity<BaseResponse> updatePassword(HttpServletRequest request, @Valid @RequestBody PasswordRequest passwordRequest) {
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        userService.updatePassword(userId, passwordRequest);
        return ResponseEntity.ok(new BaseResponse(200, "비밀번호 재설정 성공", null, true));
    }
}
