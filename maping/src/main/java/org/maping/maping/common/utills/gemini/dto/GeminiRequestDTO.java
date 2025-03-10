package org.maping.maping.common.utills.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.maping.maping.common.utills.gemini.dto.request.content;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class GeminiRequestDTO {

        private String contents;

        private String tools = """
                [
                          {
                              "google_search": {}
                          }
                      ]""";


        public void setText(String text) {
            this.contents = String.format("""
{
    "contents": [
        {
            "parts": [
                {"text": "%s"}
            ]
        }
    ]
}""", text);
        }
}
