package org.maping.maping.api.ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AiChatResponse {
    private Long chatId;
    private String topic;
    private String ocid;
    private String text;

}
