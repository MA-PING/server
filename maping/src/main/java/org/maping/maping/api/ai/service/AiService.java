package org.maping.maping.api.ai.service;

import org.maping.maping.api.ai.dto.response.NoticeSummaryResponse;
import org.maping.maping.external.nexon.dto.notice.NoticeUpdateListDTO;

import java.util.List;

public interface AiService {
    String getAiStat(String ocid);
    String getAiItem(String ocid);
    String getAiUnion(String ocid);

    String getAiArtifact(String ocid);

    String getAiSkill(String ocid);

    String getAiSymbol(String ocid);

    List<NoticeSummaryResponse> getNoticeSummary();
}
