package org.maping.maping.api.character.service;

import org.maping.maping.api.character.dto.request.OcidRequest;
import org.maping.maping.api.character.dto.response.CharacterListResponse;
import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDTO;

public interface CharacterService {
    public CharacterInfoDTO getCharacterInfo(String characterName);
    public CharacterListResponse getApiCharacterList(OcidRequest apiKey);
    public CharacterListResponse getCharacterList(Long userId);
}
