package org.maping.maping.api.character.service;

import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDto;

public interface CharacterService {
    public CharacterInfoDto getCharacterInfo(String characterName);

}
