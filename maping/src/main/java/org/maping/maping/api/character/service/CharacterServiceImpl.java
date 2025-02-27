package org.maping.maping.api.character.service;

import lombok.extern.slf4j.Slf4j;
import org.maping.maping.api.character.dto.request.OcidRequest;
import org.maping.maping.api.character.dto.response.CharacterListResponse;
import org.maping.maping.common.utills.nexon.NEXONUtils;
import org.maping.maping.common.utills.nexon.dto.character.CharacterDTO;
import org.maping.maping.common.utills.nexon.dto.character.CharacterInfoDTO;
import org.maping.maping.common.utills.nexon.dto.character.CharacterListDto;
import org.maping.maping.model.ocid.OcidJpaEntity;
import org.maping.maping.model.user.UserApiJpaEntity;
import org.maping.maping.repository.ocid.OcidRepository;
import org.maping.maping.repository.user.UserApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    private final NEXONUtils nexonUtils;
    private final UserApiRepository UserApiRepository;

    public CharacterServiceImpl(NEXONUtils nexonUtils, org.maping.maping.repository.user.UserApiRepository userApiRepository) {
        this.nexonUtils = nexonUtils;
        UserApiRepository = userApiRepository;
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

    @Override
    public CharacterListResponse getApiCharacterList(OcidRequest apiKey) {
        log.info(apiKey.getApiKey());
        CharacterListDto characterListDto = nexonUtils.getCharacterList(apiKey.getApiKey());
        CharacterInfoDTO characterInfoDTO = nexonUtils.getCharacterInfo(characterListDto.getAccountList().getFirst().getCharacterList().getFirst().getOcid());
        CharacterListResponse characterListResponse = new CharacterListResponse();
        characterListResponse.setCharacterList(characterListDto);
        characterListResponse.setCharacterInfo(characterInfoDTO);
        return characterListResponse;
    }

    @Override
    public CharacterListResponse getCharacterList(Long userId) {
//        UserApiJpaEntity userApiJpaEntity = UserApiRepository.findByUserId(userId);
//        CharacterListResponse characterListResponse = new CharacterListResponse();
//        characterListResponse.setCharacterList(characterListDto);
//        characterListResponse.setCharacterInfo(characterInfoDTO);
//        return characterListResponse;
        return null;
    }

    public UserApiRepository getUserApiRepository() {
        return UserApiRepository;
    }
}
