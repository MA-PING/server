package org.maping.maping.api.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.maping.maping.api.ai.dto.response.*;
import org.maping.maping.common.enums.expection.ErrorCode;
import org.maping.maping.common.response.BaseResponse;
import org.maping.maping.exceptions.CustomException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;
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
    private final AiHistoryConvert setAiHistoryConvert;

    public AiServiceImpl(GEMINIUtils geminiUtils, NoticeRepository noticeRepository, NEXONUtils nexonUtils, AiHistoryRepository aiHistoryRepository, UserRepository userRepository, AiHistoryConvert setAiHistoryConvert) {
        this.geminiUtils = geminiUtils;
        this.noticeRepository = noticeRepository;
        this.nexonUtils = nexonUtils;
        this.aiHistoryRepository = aiHistoryRepository;
        this.userRepository = userRepository;
        this.setAiHistoryConvert = setAiHistoryConvert;
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
    public AiChatResponse getChat(Long userId, String chatId, String characterName, String type, String ocid, String text) throws HttpException, IOException {
        OffsetDateTime koreaTime = OffsetDateTime.now(ZoneId.of("Asia/Seoul"));
        UserInfoJpaEntity userInfoJpaEntity = userRepository.findById(userId).orElse(null);
        AiChatResponse aiChatResponse = new AiChatResponse();
        AiHistoryJpaEntity aiHistoryJpaEntity = new AiHistoryJpaEntity();
        List<AiChatHistoryDTO> HistoryListDTO = new ArrayList<>();
        AiChatHistoryDTO aiChatHistoryDTO = new AiChatHistoryDTO();
        String content;
        if(chatId == null){
            content = geminiUtils.getGeminiGoogleResponse(text);
            String topic = geminiUtils.getGeminiResponse(text + "\n 이 내용에 대한 간단한 요약으로 30자 이내로 알려줘.");

            //DTO에 값 넣기
            aiChatHistoryDTO.setOcid(ocid);
            aiChatHistoryDTO.setCharacterName(characterName);
            aiChatHistoryDTO.setType(type);
            aiChatHistoryDTO.setQuestion(text);
            aiChatHistoryDTO.setAnswer(content);

            //JPA에 값 넣기
            aiHistoryJpaEntity.setChatId(UUID.randomUUID().toString());
            log.info("chatId: {}", aiHistoryJpaEntity.getChatId());
            aiHistoryJpaEntity.setUser(userInfoJpaEntity);
            aiHistoryJpaEntity.setTopic(topic);
            HistoryListDTO.add(aiChatHistoryDTO);
            aiHistoryJpaEntity.setContent(setAiHistoryConvert.getHistoryJson(HistoryListDTO));
            aiHistoryJpaEntity.setUpdatedAt(koreaTime);
            AiHistoryJpaEntity savedEntity = aiHistoryRepository.save(aiHistoryJpaEntity);

            //Response에 값 넣기
            aiChatResponse.setOcid(ocid);
            aiChatResponse.setText(content);
            aiChatResponse.setTopic(topic);
            aiChatResponse.setChatId(savedEntity.getChatId());
        }else{
            AiHistoryJpaEntity aiHistoryChatId = aiHistoryRepository.findByChatId(Objects.requireNonNull(chatId));

            if(aiHistoryChatId == null){
                throw new CustomException(ErrorCode.NotFound, "챗봇 대화가 존재하지 않습니다.");
            }

            HistoryListDTO = setAiHistoryConvert.setAiHistoryConvert(Objects.requireNonNull(aiHistoryChatId).getContent());
            log.info("historyListDTO: {}", HistoryListDTO);
            content = geminiUtils.getGeminiChatResponse(HistoryListDTO, text);

            //DTO에 값 넣기
            aiChatHistoryDTO.setOcid(ocid);
            aiChatHistoryDTO.setCharacterName(characterName);
            aiChatHistoryDTO.setType(type);
            aiChatHistoryDTO.setQuestion(text);
            aiChatHistoryDTO.setAnswer(content);

            //JPA에 값 넣기
            HistoryListDTO.add(aiChatHistoryDTO);
            log.info(setAiHistoryConvert.getHistoryJson(HistoryListDTO));
            aiHistoryJpaEntity.setChatId(aiHistoryChatId.getChatId());
            aiHistoryJpaEntity.setUser(aiHistoryChatId.getUser());
            aiHistoryJpaEntity.setTopic(aiHistoryChatId.getTopic());
            aiHistoryJpaEntity.setContent(setAiHistoryConvert.getHistoryJson(HistoryListDTO));
            aiHistoryJpaEntity.setUpdatedAt(koreaTime);
            aiHistoryRepository.save(aiHistoryJpaEntity);

            //Response에 값 넣기
            aiChatResponse.setOcid(ocid);
            aiChatResponse.setText(content);
            aiChatResponse.setTopic(Objects.requireNonNull(aiHistoryChatId).getTopic());
            aiChatResponse.setChatId(chatId);
        }

        return aiChatResponse;
    }

    @Override
    public String getRecommend(String ocid) throws HttpException, IOException {
        CharacterBasicDTO basic = nexonUtils.getCharacterBasic(ocid);
        String basicString = nexonUtils.basicString(basic);
        String text = "기본정보 : {" + basicString + "}\n" +
                "메이플 캐릭터의 기본 정보야 사용자에게 AI 에게 물어볼만한 추천 질문 5개를 알려줘.";
        return geminiUtils.getGeminiGoogleResponse(text);
    }

    @Override
    public List<AiHistoryResponse> getHistory(Long userId) {
        return aiHistoryRepository.findByUserId(userId).stream().map(history ->
            AiHistoryResponse.builder()
                    .chatId(history.getChatId())
                    .topic(history.getTopic())
                    .dateTime(history.getUpdatedAt())
                    .build()
        ).toList().reversed();
    }

    @Override
    public BaseResponse<AiChatHistoryDetailResponse> getHistory(Long userId, String chatId) {
        AiHistoryJpaEntity aiHistoryChatId = aiHistoryRepository.findByChatId(chatId);
        if(aiHistoryChatId == null){
            throw new CustomException(ErrorCode.NotFound, "챗봇 대화 기록이 존재하지 않습니다.");
        }else if(aiHistoryChatId.getChatId().equals(chatId)){
            return new BaseResponse<>(HttpStatus.OK.value(), "대화 기록을 가져왔습니다.", AiChatHistoryDetailResponse.builder()
                    .chatId(chatId)
                    .topic(aiHistoryChatId.getTopic())
                    .dateTime(aiHistoryChatId.getUpdatedAt())
                    .history(setAiHistoryConvert.setAiHistoryConvert(aiHistoryChatId.getContent()))
                    .build());
        }
        throw new CustomException(ErrorCode.BadRequest, "대화 기록을 찾을 수 없습니다.");
    }

    @Override
    public BaseResponse<String> deleteHistory(Long userId, String chatId) {
        AiHistoryJpaEntity aiHistoryChatId = aiHistoryRepository.findByChatId(chatId);
        log.info("aiHistoryChatId: {}", aiHistoryChatId);
        if(aiHistoryChatId == null){
            return new BaseResponse<>(HttpStatus.OK.value(), "챗봇 대화 기록이 존재하지 않습니다.", "챗봇 대화 기록이 존재하지 않습니다.");
        }else if(aiHistoryChatId.getUser().getUserId().equals(userId)){
            aiHistoryRepository.delete(aiHistoryChatId);
            return new BaseResponse<>(HttpStatus.OK.value(), "챗봇 대화 기록을 삭제하였습니다.", "챗봇 대화 기록을 삭제하였습니다.");
        }
        return new BaseResponse<>(HttpStatus.OK.value(), "챗봇 대화 기록을 삭제할 수 없습니다.", "챗봇 대화 기록을 삭제할 수 없습니다.");
    }
}
