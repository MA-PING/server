package org.maping.maping.api.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.maping.maping.external.gemini.GEMINIUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiServiceImpl implements AiService{
    private final GEMINIUtils geminiUtils;

    public AiServiceImpl(GEMINIUtils geminiUtils) {
        this.geminiUtils = geminiUtils;
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
}
