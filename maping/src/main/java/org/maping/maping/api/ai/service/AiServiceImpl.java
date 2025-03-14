package org.maping.maping.api.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.maping.maping.api.ai.dto.response.NoticeSummaryResponse;
import org.maping.maping.external.gemini.GEMINIUtils;
import org.maping.maping.external.nexon.NEXONUtils;
import org.maping.maping.external.nexon.dto.notice.NoticeUpdateListDTO;
import org.maping.maping.repository.ai.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AiServiceImpl implements AiService{
    private final GEMINIUtils geminiUtils;
    private final NoticeRepository noticeRepository;
    private final NEXONUtils nexonUtils;

    public AiServiceImpl(GEMINIUtils geminiUtils, NoticeRepository noticeRepository, NEXONUtils nexonUtils) {
        this.geminiUtils = geminiUtils;
        this.noticeRepository = noticeRepository;
        this.nexonUtils = nexonUtils;
    }

    @Override
    public String getAiStat(String ocid) {

        return geminiUtils.getAiStat(ocid);
    }

    @Override
    public String getAiItem(String ocid) {
        return geminiUtils.getAiItem(ocid);
    }

    @Override
    public String getAiUnion(String ocid) {
        return geminiUtils.getAiUnion(ocid);
    }

    @Override
    public String getAiArtifact(String ocid) {
        return geminiUtils.getAiArtifact(ocid);
    }

    @Override
    public String getAiSkill(String ocid) {
        return geminiUtils.getAiSkill(ocid);
    }

    @Override
    public String getAiSymbol(String ocid) {
        return geminiUtils.getAiSymbol(ocid);
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
}
