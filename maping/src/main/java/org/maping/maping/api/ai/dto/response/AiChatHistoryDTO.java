package org.maping.maping.api.ai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AiChatHistoryDTO {

    @JsonProperty("ocid")
    private String ocid;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;
}
