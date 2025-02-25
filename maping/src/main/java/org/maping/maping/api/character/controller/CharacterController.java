package org.maping.maping.api.character.controller;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.maping.maping.api.character.service.CharacterServiceImpl;
import org.maping.maping.common.response.BaseResponse;
import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "캐릭터 정보", description = "캐릭터 정보를 가져오는 API")
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterServiceImpl characterServiceImpl;

    @Operation(summary = "캐릭터 정보", description = "캐릭터 정보를 가져오는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("character")
    public BaseResponse<CharacterInfoDto> getCharacterInfo(@RequestParam String characterName) {
        return new BaseResponse<>(HttpStatus.OK.value(), "캐릭터 정보를 가져오는데 성공하였습니다.", characterServiceImpl.getCharacterInfo(characterName));
    }
}
