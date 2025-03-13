package org.maping.maping.external.gemini;

import lombok.extern.slf4j.Slf4j;
import org.maping.maping.external.gemini.dto.GeminiSearchRequestDTO;
import org.maping.maping.external.gemini.dto.GeminiResponseDTO;
import org.maping.maping.external.nexon.NEXONUtils;
import org.maping.maping.external.nexon.dto.character.CharacterBasicDTO;
import org.maping.maping.external.nexon.dto.character.itemEquipment.CharacterItemEquipmentDTO;
import org.maping.maping.external.nexon.dto.character.skill.CharacterLinkSkillDTO;
import org.maping.maping.external.nexon.dto.character.skill.CharacterSkillDTO;
import org.maping.maping.external.nexon.dto.character.stat.CharacterStatDto;
import org.maping.maping.external.nexon.dto.character.symbol.CharacterSymbolEquipmentDTO;
import org.maping.maping.external.nexon.dto.union.UnionArtifactDTO;
import org.maping.maping.external.nexon.dto.union.UnionDTO;
import org.maping.maping.external.nexon.dto.union.UnionRaiderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@RestController
@Component
@Service
public class GEMINIUtils {

    private final String GEMINI_API_KEY;
    private final NEXONUtils nexonUtils;

    private final String Gemini2URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-pro-exp-02-05:generateContent?key="; //:streamGenerateContent?alt=sse
    public GEMINIUtils(@Value("${spring.gemini.key}") String geminiApiKey, NEXONUtils nexonUtils) {
        GEMINI_API_KEY = geminiApiKey;
        this.nexonUtils = nexonUtils;
    }

    public GeminiSearchRequestDTO getGemini(String text) {

        log.info("GEMINI_API_KEY: {}", GEMINI_API_KEY);
        return null;
    }

    public String getAiStat(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterStatDto stat = nexonUtils.getCharacterStat(ocid);
        String statString = nexonUtils.statString(stat);
        log.info("stat: {}", stat);

        String text = "기본정보 : {" + basicString + "}, 스탯 : {" + statString + "}\n" +
                "메이플 캐릭터의 기본 정보와 스탯 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return getGeminiResponse(text);
    }

    public String getAiItem(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterItemEquipmentDTO itemEquipment = nexonUtils.getCharacterItemEquip(ocid);
        String itemString = nexonUtils.itemString(itemEquipment);
        log.info("itemString: {}", itemString);

        String text = "기본정보 : {" + basicString + "}, 장비 : {" + itemString + "}\n" +
                "메이플 캐릭터의 기본 정보와 장비 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return getGeminiResponse(text);
    }

    public String getAiUnion(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        UnionDTO union = nexonUtils.getUnion(ocid);
        UnionRaiderDTO unionRaider = nexonUtils.getUnionRaider(ocid);
        String unionString = nexonUtils.unionString(union, unionRaider);
        log.info("union: {}", union);

        String text = "기본정보 : {" + basicString + "}, 유니온 : {" + unionString + "}\n" +
                "메이플 캐릭터의 기본 정보와 유니온과 유니온 레이더 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return getGeminiResponse(text);
    }

    public String getAiArtifact(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        UnionArtifactDTO unionArtifact = nexonUtils.getUnionArtifact(ocid);
        String artifactString = nexonUtils.artifactString(unionArtifact);

        String text = "기본정보 : {" + basicString + "}, 유니온 아티팩트 : {" + artifactString + "}\n" +
                "메이플 캐릭터의 기본 정보와 유니온 아티팩트 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";
        return ocid;
    }
    public String getAiSkill(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterSkillDTO skill5 = nexonUtils.getCharacterSkill5(ocid, 5);
        CharacterSkillDTO skill6 = nexonUtils.getCharacterSkill5(ocid, 6);
        CharacterLinkSkillDTO linkSkill = nexonUtils.getCharacterLinkSkill(ocid);
        String skillString = nexonUtils.skillString(skill5, skill6, linkSkill);

        String text = "기본정보 : {" + basicString + "}, 스킬 : {" + skillString + "}\n" +
                "메이플 캐릭터의 기본 정보와 스킬 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";
        return ocid;
    }

    public String getAiSymbol(String ocid) {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterSymbolEquipmentDTO symbol = nexonUtils.getCharacterSymbolEquipment(ocid);
        String symbolString = nexonUtils.symbolString(symbol);
        String text = "기본정보 : {" + basicString + "}, 심볼 : {" + symbolString + "}\n" +
                "메이플 캐릭터의 기본 정보와 심볼 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";
        return ocid;
    }

    private String getGeminiResponse(String text){
        GeminiSearchRequestDTO geminiSearchRequestDTO = new GeminiSearchRequestDTO();
        String fullURL = Gemini2URL + GEMINI_API_KEY;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        geminiSearchRequestDTO.setText(text);
        String requestBody = geminiSearchRequestDTO.getContents();

        ResponseEntity<GeminiResponseDTO> response = new RestTemplate().exchange(fullURL, HttpMethod.POST, new HttpEntity<>(requestBody, headers), GeminiResponseDTO.class);

        return Objects.requireNonNull(response.getBody()).getCandidates().getFirst().getContent().getParts().getFirst().getText();
    }



}
