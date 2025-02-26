package org.maping.maping.api.character.service;

import lombok.extern.slf4j.Slf4j;
import org.maping.maping.common.utills.nexon.NEXONUtils;
import org.maping.maping.common.utills.nexon.dto.character.CharacterDTO;
import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDTO;
import org.maping.maping.model.ocid.OcidJpaEntity;
import org.maping.maping.repository.ocid.OcidRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class CharacterServiceImpl implements CharacterService {

    private final NEXONUtils nexonUtils;
    private final OcidRepository ocidRepository;

    public CharacterServiceImpl(NEXONUtils nexonUtils, OcidRepository ocidRepository) {
        this.nexonUtils = nexonUtils;
        this.ocidRepository = ocidRepository;
    }

    public CharacterInfoDTO getCharacterInfo(String characterName) {
        if(characterName == null || characterName.trim().isEmpty()) {
            throw new IllegalArgumentException("캐릭터 이름을 입력해주세요.");
        }
        CharacterDTO characterDto = nexonUtils.getOcid(characterName);
        String ocid = characterDto.getOcid();
        log.info("service ocid: {}", ocid);

        if (ocid == null || ocid.trim().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 ocid입니다.");
        }
        return nexonUtils.getCharacterInfo(ocid);
    }
}
