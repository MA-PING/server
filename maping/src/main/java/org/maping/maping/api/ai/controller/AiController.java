package org.maping.maping.api.ai.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.ai.dto.request.AiAdviceRequest;
import org.maping.maping.api.ai.service.AiServiceImpl;
import org.maping.maping.common.response.BaseResponse;
import org.maping.maping.common.utills.jwt.JWTUtill;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@lombok.extern.slf4j.Slf4j
@Slf4j
@RestController
@Tag(name = "캐릭터 정보", description = "캐릭터 정보를 가져오는 API")
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiServiceImpl aiServiceImpl;
    private final JWTUtill jwtUtil;

    @Operation(summary = "스텟 맞춤 훈수", description = "GEMINI 스텟 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("stat")
    public BaseResponse<String> getAiStat(HttpServletRequest request,
                                          @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "스텟 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiStat(requestDTO.getOcid()));
    }

    @Operation(summary = "장비 맞춤 훈수", description = "GEMINI 장비 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("item")
    public BaseResponse<String> getAiEquip(HttpServletRequest request,
                                           @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "장비 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiItem(requestDTO.getOcid()));
    }

    @Operation(summary = "유니온 맞춤 훈수", description = "GEMINI 유니온 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("union")
    public BaseResponse<String> getAiUnion(HttpServletRequest request,
                                           @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "유니온 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiUnion(requestDTO.getOcid()));
    }

    @Operation(summary = "아티펙트 맞춤 훈수", description = "GEMINI 아티펙트 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("artifact")
    public BaseResponse<String> getAiArtifact(HttpServletRequest request,
                                              @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "아티펙트 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiArtifact(requestDTO.getOcid()));
    }

    @Operation(summary = "스킬 맞춤 훈수", description = "GEMINI 스킬 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("skill")
    public BaseResponse<String> getAiSkill(HttpServletRequest request,
                                           @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "���그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "스킬 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiSkill(requestDTO.getOcid()));
    }

    @Operation(summary = "심볼 맞춤 훈수", description = "GEMINI 심볼 맞춤 훈수를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("symbol")
    public BaseResponse<String> getAiSymbol(HttpServletRequest request,
                                            @RequestBody AiAdviceRequest requestDTO) {
        if(jwtUtil.getUserId(request) == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", "로그인이 필요합니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "심볼 맞춤 훈수를 가져오는데 성공하였습니다.", aiServiceImpl.getAiSymbol(requestDTO.getOcid()));
    }
}
