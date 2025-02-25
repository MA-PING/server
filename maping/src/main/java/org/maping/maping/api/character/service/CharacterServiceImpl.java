package org.maping.maping.api.character.service;

import org.maping.maping.common.utills.nexon.NEXONUtils;
import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDto;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final NEXONUtils nexonUtils;

    public CharacterServiceImpl(NEXONUtils nexonUtils) {
        this.nexonUtils = nexonUtils;
    }

    public CharacterInfoDto getCharacterInfo(String characterName) {

        return nexonUtils.getCharacterInfo(characterName);
    }
}
