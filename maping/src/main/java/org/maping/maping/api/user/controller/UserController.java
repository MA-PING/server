package org.maping.maping.api.user.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.maping.maping.api.user.dto.request.SaveMainCharacterRequest;
import org.maping.maping.api.user.service.UserService;
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
    private final UserServiceImpl userService;
    private final JWTUtill jwtUtill;



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




