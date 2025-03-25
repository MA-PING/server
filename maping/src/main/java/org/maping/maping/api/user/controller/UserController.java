package org.maping.maping.api.user.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.auth.dto.request.NicknameCheckRequest;
import org.maping.maping.api.auth.dto.request.PasswordRequest;
import org.maping.maping.api.user.service.PasswordMailService;
import org.maping.maping.api.user.service.UserService;

import org.maping.maping.api.user.dto.request.SaveMainCharacterRequest;
import org.maping.maping.api.user.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;
import org.maping.maping.common.response.BaseResponse;
import org.maping.maping.common.utills.jwt.JWTUtill;
import org.maping.maping.api.user.dto.request.SaveApiRequest;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final PasswordMailService passwordMailService;
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

    @Operation(summary = "비밀번호 변경 이메일 발송", description = "비밀번호 변경 이메일 발송 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-password-reset-email")
    public BaseResponse<String> sendPasswordResetEmail(@RequestParam String email) {
        try {
            passwordMailService.sendPasswordResetEmail(email);
            return new BaseResponse<>(HttpStatus.OK.value(), "비밀번호 변경 이메일 발송 성공", "비밀번호 변경 이메일 발송 성공", true);
        } catch (MessagingException e) {
            log.error("비밀번호 변경 이메일 발송 실패: {}", e.getMessage(), e);
            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 변경 이메일 발송 실패", "비밀번호 변경 이메일 발송 실패", false);
        }
    }

    @Operation(summary = "API 등록 기능", description = "API키 등록하는 API")
    @PostMapping("/api/post")
    public ResponseEntity<BaseResponse> saveApi(HttpServletRequest request, @RequestBody SaveApiRequest saveApiRequest){
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        userService.saveApi(userId,saveApiRequest);
        return ResponseEntity.ok(new BaseResponse(200,"API 등록 성공",null,true));
    }


    @Operation(summary = "API 수정 기능", description = "API키 수정하는 API")
    @PostMapping("/api/update")
    public ResponseEntity<BaseResponse> updateApi(HttpServletRequest request, @RequestBody SaveApiRequest saveApiRequest){
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        userService.updateApi(userId,saveApiRequest);
        return ResponseEntity.ok(new BaseResponse(200,"API 수정 성공",null,true));
    }

    @Operation(summary = "본캐 설정 기능", description = "본캐 설정하는 기능")
    @PostMapping("/api/original")
    public ResponseEntity<BaseResponse> original(HttpServletRequest request, @RequestBody SaveMainCharacterRequest saveMainCharacterRequest){
        Long userId = Long.parseLong(jwtUtill.getUserId(request));
        userService.original(userId, saveMainCharacterRequest);
        return ResponseEntity.ok(new BaseResponse(200, "본캐 설정 성공", null, true));
    }

}