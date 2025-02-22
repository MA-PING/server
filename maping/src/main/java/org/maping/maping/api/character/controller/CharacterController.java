package org.maping.maping.api.character.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.maping.maping.api.auth.dto.request.PasswordRequest;
import org.maping.maping.api.auth.service.AuthService;
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
@Tag(name = "캐릭터 정보", description = "캐릭터 정보를 가져오는 API")
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CharacterController {

    @Operation(summary = "캐릭터 정보", description = "캐릭터 정보를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("character/{characterName}")
    public BaseResponse<String> getCharacterInfo(@PathVariable String characterName) {

        return new BaseResponse<>(HttpStatus.OK.value(), "캐릭터 정보를 가져오는데 성공하였습니다.", characterName);
    }
}
