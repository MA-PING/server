package org.maping.maping.api.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.maping.maping.api.ai.dto.response.AiChatResponse;
import org.maping.maping.api.ai.dto.response.NoticeSummaryResponse;
import org.maping.maping.external.gemini.GEMINIUtils;
import org.maping.maping.external.nexon.NEXONUtils;
import org.maping.maping.external.nexon.dto.character.CharacterBasicDTO;
import org.maping.maping.external.nexon.dto.character.itemEquipment.CharacterItemEquipmentDTO;
import org.maping.maping.external.nexon.dto.character.skill.CharacterLinkSkillDTO;
import org.maping.maping.external.nexon.dto.character.skill.CharacterSkillDTO;
import org.maping.maping.external.nexon.dto.character.stat.CharacterStatDto;
import org.maping.maping.external.nexon.dto.character.symbol.CharacterSymbolEquipmentDTO;
import org.maping.maping.external.nexon.dto.notice.NoticeUpdateListDTO;
import org.maping.maping.external.nexon.dto.union.UnionArtifactDTO;
import org.maping.maping.external.nexon.dto.union.UnionDTO;
import org.maping.maping.external.nexon.dto.union.UnionRaiderDTO;
import org.maping.maping.model.ai.AiHistoryJpaEntity;
import org.maping.maping.model.user.UserInfoJpaEntity;
import org.maping.maping.repository.ai.AiHistoryRepository;
import org.maping.maping.repository.ai.NoticeRepository;
import org.maping.maping.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AiServiceImpl implements AiService{
    private final GEMINIUtils geminiUtils;
    private final NoticeRepository noticeRepository;
    private final NEXONUtils nexonUtils;
    private final AiHistoryRepository aiHistoryRepository;
    private final UserRepository userRepository;

    public AiServiceImpl(GEMINIUtils geminiUtils, NoticeRepository noticeRepository, NEXONUtils nexonUtils, AiHistoryRepository aiHistoryRepository, UserRepository userRepository) {
        this.geminiUtils = geminiUtils;
        this.noticeRepository = noticeRepository;
        this.nexonUtils = nexonUtils;
        this.aiHistoryRepository = aiHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String getAiStat(String ocid) throws HttpException, IOException {

        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterStatDto stat = nexonUtils.getCharacterStat(ocid);
        String statString = nexonUtils.statString(stat);

        String text = "기본정보 : {" + basicString + "}, 스탯 : {" + statString + "}\n" +
                "메이플 캐릭터의 기본 정보와 스탯 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public String getAiItem(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterItemEquipmentDTO itemEquipment = nexonUtils.getCharacterItemEquip(ocid);
        String itemString = nexonUtils.itemString(itemEquipment);

        String text = "기본정보 : {" + basicString + "}, 장비 : {" + itemString + "}\n" +
                "메이플 캐릭터의 기본 정보와 장비 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public String getAiUnion(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        UnionDTO union = nexonUtils.getUnion(ocid);
        UnionRaiderDTO unionRaider = nexonUtils.getUnionRaider(ocid);
        String unionString = nexonUtils.unionString(union, unionRaider);

        String text = "기본정보 : {" + basicString + "}, 유니온 : {" + unionString + "}\n" +
                "메이플 캐릭터의 기본 정보와 유니온과 유니온 레이더 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";

        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public String getAiArtifact(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        UnionArtifactDTO unionArtifact = nexonUtils.getUnionArtifact(ocid);
        String artifactString = nexonUtils.artifactString(unionArtifact);

        String text = "기본정보 : {" + basicString + "}, 유니온 아티팩트 : {" + artifactString + "}\n" +
                "메이플 캐릭터의 기본 정보와 유니온 아티팩트 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";
        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public String getAiSkill(String ocid) throws HttpException, IOException {
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
        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public String getAiSymbol(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        CharacterSymbolEquipmentDTO symbol = nexonUtils.getCharacterSymbolEquipment(ocid);
        String symbolString = nexonUtils.symbolString(symbol);
        String text = "기본정보 : {" + basicString + "}, 심볼 : {" + symbolString + "}\n" +
                "메이플 캐릭터의 기본 정보와 심볼 정보야 이걸로 같은 레벨과 비교해서 좋은지 나쁜지 평가해줘.\n" +
                "좋으면 답변할 때 맨 앞에 1~5로 매우 좋으면 5에서 매우 안 좋으면 1로 점수를 매겨서 알려줘 \n" +
                "답변할때 내가 알려준 기본정보와 스택을 다시 알려주지 않아도돼. \n" +
                "그리고 200자 이내로 대답해줘.";
        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public List<NoticeSummaryResponse> getNoticeSummary() {
        return noticeRepository.getNotice(3).stream().map(notice -> {
            NoticeSummaryResponse noticeSummaryResponse = new NoticeSummaryResponse();
            noticeSummaryResponse.setTitle(notice.getNoticeTitle());
            noticeSummaryResponse.setUrl(notice.getNoticeUrl());
            noticeSummaryResponse.setDate(notice.getNoticeDate());
            noticeSummaryResponse.setSummary(notice.getNoticeSummary());
            noticeSummaryResponse.setVersion(notice.getVersion());
            return noticeSummaryResponse;
        }).toList();
    }

    @Override
    public AiChatResponse getChat(Long userId, Long chatId, String ocid, String text) throws HttpException, IOException {
        UserInfoJpaEntity userInfoJpaEntity = userRepository.findById(userId).orElse(null);
        AiChatResponse aiChatResponse = new AiChatResponse();
        AiHistoryJpaEntity aiHistoryJpaEntity = new AiHistoryJpaEntity();
        String content;
        if(chatId == null){
            content = geminiUtils.getGeminiGoogleResponse(text);
            String topic = geminiUtils.getGeminiResponse(text + "\n 이 내용에 대한 간단한 요약으로 30자 이내로 알려줘.");
            aiHistoryJpaEntity.setUser(userInfoJpaEntity);
            aiHistoryJpaEntity.setTopic(topic);
            aiHistoryJpaEntity.setContent("user:::" + text + ";;;model:::" + content);
            aiHistoryJpaEntity.setAiDateTime(OffsetDateTime.now());
            AiHistoryJpaEntity savedEntity = aiHistoryRepository.save(aiHistoryJpaEntity);

            aiChatResponse.setOcid(ocid);
            aiChatResponse.setText(content);
            aiChatResponse.setTopic(topic);
            aiChatResponse.setChatId(savedEntity.getId());
        }else{
            AiHistoryJpaEntity aiHistoryChatId = aiHistoryRepository.findById(Objects.requireNonNull(chatId)).orElse(null);
            content = geminiUtils.getGeminiChatResponse(Objects.requireNonNull(aiHistoryChatId).getContent(), text);
            aiChatResponse.setOcid(ocid);
            aiChatResponse.setText(text);
            aiChatResponse.setTopic(Objects.requireNonNull(aiHistoryChatId).getTopic());
            aiChatResponse.setChatId(chatId);

            aiHistoryJpaEntity.setTopic(Objects.requireNonNull(aiHistoryChatId).getTopic());
            aiHistoryJpaEntity.setContent(aiHistoryChatId.getContent() +"user:" + text + ", model:" + content);
            aiHistoryJpaEntity.setAiDateTime(OffsetDateTime.now());
            aiHistoryRepository.save(aiHistoryJpaEntity);
        }



        return aiChatResponse;
    }

    @Override
    public String getRecommend(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        String text = "기본정보 : {" + basicString + "}\n" +
                "메이플 캐릭터의 기본 정보야 사용자에게 AI에게 물어볼만한 추천 질문 5개를 알려줘.";
        return geminiUtils.getGeminiGoogleResponse(text);
    }
}
