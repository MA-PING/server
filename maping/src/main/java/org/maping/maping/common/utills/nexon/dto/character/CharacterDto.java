package org.maping.maping.common.utills.nexon.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterDto {
    /**
     * 캐릭터 식별자
     */
    @JsonProperty("ocid")
    private String ocid;
}
