package org.maping.maping.api.character.service;

import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDTO;

public interface CharacterService {
    public CharacterInfoDTO getCharacterInfo(String characterName);

}
