package org.maping.maping.api.ai.service;

import org.apache.http.HttpException;
import org.maping.maping.api.ai.dto.response.AiChatResponse;
import org.maping.maping.api.ai.dto.response.NoticeSummaryResponse;
import org.maping.maping.external.nexon.dto.notice.NoticeUpdateListDTO;

import java.io.IOException;
import java.util.List;

public interface AiService {
    String getAiStat(String ocid) throws HttpException, IOException;
    String getAiItem(String ocid) throws HttpException, IOException;
    String getAiUnion(String ocid) throws HttpException, IOException;

    String getAiArtifact(String ocid) throws HttpException, IOException;

    String getAiSkill(String ocid) throws HttpException, IOException;

    String getAiSymbol(String ocid) throws HttpException, IOException;

    List<NoticeSummaryResponse> getNoticeSummary();

    AiChatResponse getChat(Long userId, Long chatId, String ocid, String text) throws HttpException, IOException;

    String getRecommend(String ocid) throws HttpException, IOException;
}
