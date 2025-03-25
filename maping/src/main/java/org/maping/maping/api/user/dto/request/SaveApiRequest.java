package org.maping.maping.api.user.dto.request;

import lombok.Getter;

@Getter
public class SaveApiRequest {
    private String apiKey;

    public String getCharacterName() {
        return "";
    }
}

