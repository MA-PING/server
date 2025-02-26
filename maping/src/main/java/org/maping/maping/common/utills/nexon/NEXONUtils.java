package org.maping.maping.common.utills.nexon;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.maping.maping.common.enums.expection.ErrorCode;
import org.maping.maping.common.utills.nexon.dto.character.*;
import org.maping.maping.common.utills.nexon.dto.character.ability.CharacterAbilityDTO;
import org.maping.maping.common.utills.nexon.dto.character.itemEquipment.CharacterItemEquipmentDTO;
import org.maping.maping.common.utills.nexon.dto.character.matrix.CharacterHexaMatrixDTO;
import org.maping.maping.common.utills.nexon.dto.character.matrix.CharacterHexaMatrixStatDTO;
import org.maping.maping.common.utills.nexon.dto.character.matrix.CharacterVMatrixDTO;
import org.maping.maping.common.utills.nexon.dto.character.skill.CharacterLinkSkillDTO;
import org.maping.maping.common.utills.nexon.dto.character.skill.CharacterSkillDTO;
import org.maping.maping.common.utills.nexon.dto.character.stat.CharacterHyperStatDTO;
import org.maping.maping.common.utills.nexon.dto.character.stat.CharacterStatDto;
import org.maping.maping.common.utills.nexon.dto.character.symbol.CharacterSymbolEquipmentDTO;
import org.maping.maping.common.utills.nexon.dto.notice.EventNoticeDetailDTO;
import org.maping.maping.common.utills.nexon.dto.notice.EventNoticeListDTO;
import org.maping.maping.common.utills.nexon.dto.notice.NoticeDetailDTO;
import org.maping.maping.common.utills.nexon.dto.notice.NoticeListDTO;
import org.maping.maping.common.utills.nexon.dto.union.UnionArtifactDTO;
import org.maping.maping.common.utills.nexon.dto.union.UnionDTO;
import org.maping.maping.common.utills.nexon.dto.union.UnionRaiderDTO;
import org.maping.maping.exceptions.CustomException;
import org.maping.maping.model.search.CharacterSearchJpaEntity;
import org.maping.maping.repository.search.CharacterSearchRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RestController
@Component
@Service
public class NEXONUtils {

    public final String Key;
    private final CharacterSearchRepository characterSearchRepository;

    public NEXONUtils(@Value("${spring.nexon.key}") String key, CharacterSearchRepository characterSearchRepository) {
        this.Key = key;
        this.characterSearchRepository = characterSearchRepository;
    }

    @Operation(summary = "ocid 가져오기", description = "캐릭터 이름을 통해 ocid를 가져오는 API")
    public CharacterDTO getOcid(@NonNull String characterName) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/id";
        log.info("getOcid: {}", characterName);

        // 캐릭터 이름을 URL 인코딩
        String encodedCharacterName = URLEncoder.encode(characterName, UTF_8);
        log.info(encodedCharacterName);

        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("character_name", characterName)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        try {
            ResponseEntity<CharacterDTO> responseEntity = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterDTO.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Error response: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    @Operation(summary = "캐릭터 기본 정보 가져오기", description = "ocid를 통해 캐릭터의 기본 정보를 가져오는 API")
    public CharacterBasicDTO getCharacterBasic(@NonNull String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/basic";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterBasicDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterBasicDTO.class);

        return response.getBody();
    }

    @Operation(summary = "캐릭터 리스트 가져오기", description = "API 키를 통해 캐릭터 리스트를 가져오는 API")
    public CharacterListDto getCharacterList(@NotBlank String apiKey) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterListDto> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterListDto.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 스탯 가져오기", description = "ocid를 통해 캐릭터의 스탯을 가져오는 API")
    public CharacterStatDto getCharacterStat(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<CharacterStatDto> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterStatDto.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 하이퍼스탯 가져오기", description = "ocid를 통해 캐릭터의 하이퍼스탯을 가져오는 API")
    public CharacterHyperStatDTO getCharacterHyperStat(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hyper-stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHyperStatDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHyperStatDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 어빌리티 가져오기", description = "ocid를 통해 캐릭터의 능력치를 가져오는 API")
    public CharacterAbilityDTO getCharacterAbility(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/ability";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterAbilityDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterAbilityDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 아이템 정보 가져오기", description = "ocid를 통해 캐릭터의 아이템 정보를 가져오는 API")
    public CharacterItemEquipmentDTO getCharacterItemEquip(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/item-equipment";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<CharacterItemEquipmentDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterItemEquipmentDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 심볼 정보 가져오기", description = "ocid를 통해 캐릭터의 심볼 정보를 가져오는 API")
    public CharacterSymbolEquipmentDTO getCharacterSymbolEquipment(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/symbol-equipment";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterSymbolEquipmentDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterSymbolEquipmentDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 스킬 정보 가져오기", description = "ocid를 통해 캐릭터의 스킬 정보를 가져오는 API")
    public CharacterSkillDTO getCharacterSkill5(@NotBlank String ocid, int skillGrade) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/skill";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("character_skill_grade", skillGrade).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterSkillDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterSkillDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 링크 스킬 정보 가져오기", description = "ocid를 통해 캐릭터의 링크 스킬 정보를 가져오는 API")
    public CharacterLinkSkillDTO getCharacterLinkSkill(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/link-skill";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterLinkSkillDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterLinkSkillDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 V매트릭스 정보 가져오기", description = "ocid를 통해 캐릭터의 V매트릭스 정보를 가져오는 API")
    public CharacterVMatrixDTO getCharacterVmatrix(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/vmatrix";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterVMatrixDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterVMatrixDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 HEXA 매트릭스 정보 가져오기", description = "ocid를 통해 캐릭터의 HEXA 매트릭스 정보를 가져오는 API")
    public CharacterHexaMatrixDTO getCharacterHexamatrix(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hexamatrix";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHexaMatrixDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHexaMatrixDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 HEXA 매트릭스 스탯 정보 가져오기", description = "ocid를 통해 캐릭터의 HEXA 매트릭스 스탯 정보를 가져오는 API")
    public CharacterHexaMatrixStatDTO getCharacterHexamatrixStat(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hexamatrix-stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("hexa_matrix_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHexaMatrixStatDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHexaMatrixStatDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 정보를 가져오는 API")
    public UnionDTO getUnion(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/user/union";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("union_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<UnionDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), UnionDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 레이더 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 레이더 정보를 가져오는 API")
    public UnionRaiderDTO getUnionRaider(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/user/union-raider";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("union_id", 1).queryParam("raider_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<UnionRaiderDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), UnionRaiderDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 아티팩트 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 아티팩트 정보를 가져오는 API")
    public UnionArtifactDTO getUnionArtifact(@NotBlank String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/user/union-artifact";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("union_id", 1).queryParam("artifact_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<UnionArtifactDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), UnionArtifactDTO.class);
        return response.getBody();
    }

    @Operation(summary = "공지사항 가져오기", description = "공지사항 리스트를 가져오는 API")
    public NoticeListDTO getNoticeList() {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<NoticeListDTO> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), NoticeListDTO.class);
        return response.getBody();
    }

    @Operation(summary = "공지사항 상세 정보 가져오기", description = "공지사항 상세 정보를 가져오는 API")
    public NoticeDetailDTO getNoticeDetail(int noticeId) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice/detail";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("notice_id", noticeId).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<NoticeDetailDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), NoticeDetailDTO.class);
        return response.getBody();
    }

    @Operation(summary = "공지사항 업데이트 리스트 가져오기", description = "공지사항 업데이트 리스트를 가져오는 API")
    public NoticeListDTO getNoticeUpdateList() {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice-update";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<NoticeListDTO> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), NoticeListDTO.class);
        return response.getBody();
    }

    @Operation(summary = "공지사항 업데이트 상세 정보 가져오기", description = "공지사항 업데이트 상세 정보를 가져오는 API")
    public NoticeDetailDTO getNoticeUpdateDetail(long noticeId) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice-update/detail";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("notice_id", noticeId).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<NoticeDetailDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), NoticeDetailDTO.class);
        return response.getBody();
    }

    @Operation(summary = "진행 중 이벤트 리스트 가져오기", description = "진행 중 이벤트 리스트를 가져오는 API")
    public EventNoticeListDTO getNoticeEventList() {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice-event";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<EventNoticeListDTO> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), EventNoticeListDTO.class);
        return response.getBody();
    }

    @Operation(summary = "진행 중 이벤트 상세 정보 가져오기", description = "진행 중 이벤트 상세 정보를 가져오는 API")
    public EventNoticeDetailDTO getNoticeEventDetail(long noticeId) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/notice-event/detail";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("notice_id", noticeId).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<EventNoticeDetailDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), EventNoticeDetailDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 정보 첫페이지 가져오기", description = "캐릭터 이름을 통해 캐릭터의 기본 정보를 가져오는 API")
    public CharacterInfoDTO getCharacterInfo(String ocid) {
        CharacterInfoDTO characterInfo = new CharacterInfoDTO();
        log.info("getCharacterInfo: {}", ocid);

        // 비동기 호출을 사용하여 각 정보를 가져옵니다.
        CompletableFuture<CharacterBasicDTO> basicFuture = CompletableFuture.supplyAsync(() -> getCharacterBasic(ocid));
        CompletableFuture<CharacterStatDto> statFuture = CompletableFuture.supplyAsync(() -> getCharacterStat(ocid));
        CompletableFuture<CharacterHyperStatDTO> hyperStatFuture = CompletableFuture.supplyAsync(() -> getCharacterHyperStat(ocid));
        CompletableFuture<CharacterAbilityDTO> abilityFuture = CompletableFuture.supplyAsync(() -> getCharacterAbility(ocid));
        CompletableFuture<CharacterItemEquipmentDTO> itemEquipmentFuture = CompletableFuture.supplyAsync(() -> getCharacterItemEquip(ocid));
        CompletableFuture<CharacterSymbolEquipmentDTO> symbolEquipmentFuture = CompletableFuture.supplyAsync(() -> getCharacterSymbolEquipment(ocid));
        CompletableFuture<CharacterSkillDTO> skill5Future = CompletableFuture.supplyAsync(() -> getCharacterSkill5(ocid, 5));
        CompletableFuture<CharacterSkillDTO> skill6Future = CompletableFuture.supplyAsync(() -> getCharacterSkill5(ocid, 6));
        CompletableFuture<CharacterLinkSkillDTO> linkSkillFuture = CompletableFuture.supplyAsync(() -> getCharacterLinkSkill(ocid));
        CompletableFuture<CharacterVMatrixDTO> vMatrixFuture = CompletableFuture.supplyAsync(() -> getCharacterVmatrix(ocid));
        CompletableFuture<CharacterHexaMatrixDTO> hexaMatrixFuture = CompletableFuture.supplyAsync(() -> getCharacterHexamatrix(ocid));
        CompletableFuture<CharacterHexaMatrixStatDTO> hexaMatrixStatFuture = CompletableFuture.supplyAsync(() -> getCharacterHexamatrixStat(ocid));
        CompletableFuture<UnionDTO> unionFuture = CompletableFuture.supplyAsync(() -> getUnion(ocid));
        CompletableFuture<UnionRaiderDTO> unionRaiderFuture = CompletableFuture.supplyAsync(() -> getUnionRaider(ocid));
        CompletableFuture<UnionArtifactDTO> unionArtifactFuture = CompletableFuture.supplyAsync(() -> getUnionArtifact(ocid));

        // 모든 비동기 호출의 결과를 기다립니다.
        characterInfo.setOcid(ocid);
        characterInfo.setBasic(basicFuture.join());
        characterInfo.setStat(statFuture.join());
        characterInfo.setHyperStat(hyperStatFuture.join());
        characterInfo.setAbility(abilityFuture.join());
        characterInfo.setItemEquipment(itemEquipmentFuture.join());
        characterInfo.setSymbolEquipment(symbolEquipmentFuture.join());
        characterInfo.setSkill5(skill5Future.join());
        characterInfo.setSkill6(skill6Future.join());
        characterInfo.setLinkSkill(linkSkillFuture.join());
        characterInfo.setVMatrix(vMatrixFuture.join());
        characterInfo.setHexaMatrix(hexaMatrixFuture.join());
        characterInfo.setHexaMatrixStat(hexaMatrixStatFuture.join());
        characterInfo.setUnion(unionFuture.join());
        characterInfo.setUnionRaider(unionRaiderFuture.join());
        characterInfo.setUnionArtifact(unionArtifactFuture.join());


        return characterInfo;
    }

    public String getWorldImgUrl(String worldName) {
        if(Objects.equals(worldName, "노바")) {
            return "https://lh3.google.com/u/0/d/1Wx3lx6-Qe8Hm8S7lNwlNGtiSemJ5X9Pv=w1920-h968-iv1";
        }else if(Objects.equals(worldName, "레드")) {
            return "https://lh3.google.com/u/0/d/1a9YYUARXdVzUUu-aUarHgyJdqNBx5Mbf=w1920-h968-iv1";
        }else if(Objects.equals(worldName, "루나")) {
            return "https://lh3.google.com/u/0/d/1mZPYCSxll88VLUr4cGFVEGhb_kJ5k6CJ=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "베라")) {
            return "https://lh3.google.com/u/0/d/1wJiCzHW8Rk1nr7JsHZUcsBtoiMRT8Isz=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "스카니아")) {
            return "https://lh3.google.com/u/0/d/1fVg6ThMqPJsEg9KuypXHUtUFnlboUwFN=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "아케인")) {
            return "https://lh3.google.com/u/0/d/1IcE7Xx1RUTJTF6HGsX40pptB9kXEmZC3=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "엘리시움")) {
            return "https://lh3.google.com/u/0/d/1cLtG3h4EKuMzhtzG4PkJhQBTVatZQssE=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "오로라")) {
            return "https://lh3.google.com/u/0/d/1tUc4BMDtIUAUIKH47nkZwbQcFqta_B-T=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "유니온")) {
            return "https://lh3.google.com/u/0/d/1RiRArYAAJ3FDOfInklir6vficLOGAT8q=w1920-h968-iv1";
        }else if(Objects.equals(worldName, "이노시스")) {
            return "https://lh3.google.com/u/0/d/1W7mw46omb1PjNFA61W6InL3n3fT5OnWn=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "제니스")) {
            return "https://lh3.google.com/u/0/d/1Y7kwZO5DeE3PouKnkTrQYGKiNavKpwUz=w2000-h1668-iv1";
        }else if(Objects.equals(worldName, "크로아")) {
            return "https://lh3.google.com/u/0/d/1HTAKqVQ8QxFrWZO6mkEW9_pdy314zSSN=w2000-h1668-iv1";
        } else if(Objects.equals(worldName, "에오스")) {
            return "https://lh3.google.com/u/0/d/1WKmBiemmm5LHCdt6VJ4pDj5HlviWWxkI=w1920-h968-iv1";
        } else if(Objects.equals(worldName, "핼리오스")) {
            return "https://lh3.google.com/u/0/d/11kV1yU4St0EQIx_u26P3_lQ-Xf4v0_-j=w2000-h1668-iv1";
        }else{
            return new CustomException(ErrorCode.NotFound, worldName).getMessage();
        }
    }

    public void setCharacterInfo(CharacterBasicDTO characterInfo) {
        String characterName = characterInfo.getCharacterName();
        log.info("setCharacterInfo : {}", characterName);
        Optional<CharacterSearchJpaEntity> characterSearch = characterSearchRepository.findByCharacterName(characterName);

        if(characterSearch.isPresent()){
            CharacterSearchJpaEntity entity = CharacterSearchJpaEntity.builder()
                    .id(characterSearch.get().getId())
                    .characterName(characterInfo.getCharacterName())
                    .characterLevel(characterInfo.getCharacterLevel())
                    .worldName(characterInfo.getWorldName())
                    .characterClass(characterInfo.getCharacterClass())
                    .image(characterInfo.getCharacterImage())
                    .worldImg(getWorldImgUrl(characterInfo.getWorldName()))
                    .jaso(separateJaso(characterInfo.getCharacterName()).toString())
                    .count(characterSearch.get().getCount() + 1)
                    .createdAt(characterSearch.get().getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            characterSearchRepository.save(entity);
        }else{
            CharacterSearchJpaEntity entity = CharacterSearchJpaEntity.builder()
                    .characterName(characterInfo.getCharacterName())
                    .characterLevel(characterInfo.getCharacterLevel())
                    .worldName(characterInfo.getWorldName())
                    .characterClass(characterInfo.getCharacterClass())
                    .image(characterInfo.getCharacterImage())
                    .worldImg(getWorldImgUrl(characterInfo.getWorldName()))
                    .jaso(separateJaso(characterInfo.getCharacterName()).toString())
                    .count(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            characterSearchRepository.save(entity);
        }
    }
    // 자소 분리 함수
    public static List<String> separateJaso(String input) {
        log.info("separateJaso: {}", input);
        List<String> result = new ArrayList<>();

        for (char ch : input.toCharArray()) {
            // 한글 자모 분해
            if (isHangul(ch)) {
                String[] jaso = decomposeHangul(ch);
                for (String j : jaso) {
                    result.add(j);
                }
            } else {
                result.add(String.valueOf(ch)); // 한글이 아닌 경우 그대로 추가
            }
        }
        return result;
    }

    // 한글 여부 확인
    private static boolean isHangul(char ch) {
        return ch >= 0xAC00 && ch <= 0xD7A3; // 가 ~ 힣 범위
    }

    // 한글 자모 분해
    private static String[] decomposeHangul(char hangul) {
        int base = hangul - 0xAC00; // '가'의 유니코드
        int cho = base / (21 * 28); // 초성
        int jung = (base % (21 * 28)) / 28; // 중성
        int jong = base % 28; // 종성

        String[] jaso = new String[3];
        jaso[0] = String.valueOf((char) (0x1100 + cho)); // 초성
        jaso[1] = String.valueOf((char) (0x1161 + jung)); // 중성
        jaso[2] = jong > 0 ? String.valueOf((char) (0x11A7 + jong)) : ""; // 종성

        return jaso;
    }
}
