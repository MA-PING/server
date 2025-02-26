package org.maping.maping.common.utills.nexon;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RestController
@Component
@Service
public class NEXONUtils {

    public final String Key;

    public NEXONUtils(@Value("${spring.nexon.key}") String key) {
        Key = key;
    }

    @Operation(summary = "ocid 가져오기", description = "캐릭터 이름을 통해 ocid를 가져오는 API")
    public CharacterDTO getOcid(String characterName) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/id";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("character_name", characterName).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterDTO> responseEntity = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterDTO.class);
        return responseEntity.getBody();
    }

    @Operation(summary = "캐릭터 기본 정보 가져오기", description = "ocid를 통해 캐릭터의 기본 정보를 가져오는 API")
    public CharacterBasicDTO getCharcterBasic(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/basic";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        // API 호출
        ResponseEntity<CharacterBasicDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterBasicDTO.class);

        // 응답 반환
        return response.getBody(); // API 응답 그대로 반환
    }

    @Operation(summary = "캐릭터 리스트 가져오기", description = "API 키를 통해 캐릭터 리스트를 가져오는 API")
    public CharacterListDto getCharcterList(String apiKey) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/list";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterListDto> response = new RestTemplate().exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterListDto.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 스탯 가져오기", description = "ocid를 통해 캐릭터의 스탯을 가져오는 API")
    public CharacterStatDto getCharcterStat(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<CharacterStatDto> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterStatDto.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 하이퍼스탯 가져오기", description = "ocid를 통해 캐릭터의 하이퍼스탯을 가져오는 API")
    public CharacterHyperStatDTO getCharcterHyperStat(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hyper-stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHyperStatDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHyperStatDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 어빌리티 가져오기", description = "ocid를 통해 캐릭터의 능력치를 가져오는 API")
    public CharacterAbilityDTO getCharcterAbility(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/ability";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterAbilityDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterAbilityDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 아이템 정보 가져오기", description = "ocid를 통해 캐릭터의 아이템 정보를 가져오는 API")
    public CharacterItemEquipmentDTO getCharcterItemEquip(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/item-equipment";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);
        ResponseEntity<CharacterItemEquipmentDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterItemEquipmentDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 심볼 정보 가져오기", description = "ocid를 통해 캐릭터의 심볼 정보를 가져오는 API")
    public CharacterSymbolEquipmentDTO getCharcterSymbolEquipment(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/symbol-equipment";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterSymbolEquipmentDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterSymbolEquipmentDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 스킬 정보 가져오기", description = "ocid를 통해 캐릭터의 스킬 정보를 가져오는 API")
    public CharacterSkillDTO getCharcterSkill5(String ocid, int skillGrade) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/skill";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("character_skill_grade", skillGrade).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterSkillDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterSkillDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 링크 스킬 정보 가져오기", description = "ocid를 통해 캐릭터의 링크 스킬 정보를 가져오는 API")
    public CharacterLinkSkillDTO getCharcterLinkSkill(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/link-skill";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterLinkSkillDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterLinkSkillDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 V매트릭스 정보 가져오기", description = "ocid를 통해 캐릭터의 V매트릭스 정보를 가져오는 API")
    public CharacterVMatrixDTO getCharcterVmatrix(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/vmatrix";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterVMatrixDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterVMatrixDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 HEXA 매트릭스 정보 가져오기", description = "ocid를 통해 캐릭터의 HEXA 매트릭스 정보를 가져오는 API")
    public CharacterHexaMatrixDTO getCharcterHexamatrix(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hexamatrix";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHexaMatrixDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHexaMatrixDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 HEXA 매트릭스 스탯 정보 가져오기", description = "ocid를 통해 캐릭터의 HEXA 매트릭스 스탯 정보를 가져오는 API")
    public CharacterHexaMatrixStatDTO getCharcterHexamatrixStat(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/hexamatrix-stat";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("hexa_matrix_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<CharacterHexaMatrixStatDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CharacterHexaMatrixStatDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 정보를 가져오는 API")
    public UnionDTO getUnion(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/user/union";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("union_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<UnionDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), UnionDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 레이더 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 레이더 정보를 가져오는 API")
    public UnionRaiderDTO getUnionRaider(String ocid) {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/user/union-raider";
        String fullUrl = UriComponentsBuilder.fromUriString(apiUrl).queryParam("ocid", ocid).queryParam("union_id", 1).queryParam("raider_id", 1).build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", Key);

        ResponseEntity<UnionRaiderDTO> response = new RestTemplate().exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), UnionRaiderDTO.class);
        return response.getBody();
    }

    @Operation(summary = "캐릭터 유니온 아티팩트 정보 가져오기", description = "ocid를 통해 캐릭터의 유니온 아티팩트 정보를 가져오는 API")
    public UnionArtifactDTO getUnionArtifact(String ocid) {
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

    @Operation(summary = "캐릭터 정보 가져오기", description = "캐릭터 이름을 통해 캐릭터의 기본 정보를 가져오는 API")
    public CharacterInfoDTO getCharacterInfo(String characterName) {
        CharacterInfoDTO characterInfo = new CharacterInfoDTO();
        CharacterDTO characterDto = getOcid(characterName);
        log.info("ocid: {}", characterDto.getOcid());
        String ocid = characterDto.getOcid();
        characterInfo.setOcid(ocid);
        characterInfo.setBasic(getCharcterBasic(ocid));
        characterInfo.setStat(getCharcterStat(ocid));
        characterInfo.setHyperStat(getCharcterHyperStat(ocid));
        characterInfo.setAbility(getCharcterAbility(ocid));
        characterInfo.setItemEquipment(getCharcterItemEquip(ocid));
        characterInfo.setSymbolEquipment(getCharcterSymbolEquipment(ocid));
        characterInfo.setSkill5(getCharcterSkill5(ocid, 5));
        characterInfo.setSkill6(getCharcterSkill5(ocid, 6));
        characterInfo.setLinkSkill(getCharcterLinkSkill(ocid));
        characterInfo.setVMatrix(getCharcterVmatrix(ocid));
        characterInfo.setHexaMatrix(getCharcterHexamatrix(ocid));
        characterInfo.setHexaMatrixStat(getCharcterHexamatrixStat(ocid));
        characterInfo.setUnion(getUnion(ocid));
        characterInfo.setUnionRaider(getUnionRaider(ocid));
        characterInfo.setUnionArtifact(getUnionArtifact(ocid));
        return characterInfo;
    }
}
