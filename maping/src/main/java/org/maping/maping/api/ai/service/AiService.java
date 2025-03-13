package org.maping.maping.api.ai.service;

public interface AiService {
    String getAiStat(String ocid);
    String getAiItem(String ocid);
    String getAiUnion(String ocid);

    String getAiArtifact(String ocid);

    String getAiSkill(String ocid);

    String getAiSymbol(String ocid);
}
