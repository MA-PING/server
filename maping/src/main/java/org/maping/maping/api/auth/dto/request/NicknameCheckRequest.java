package org.maping.maping.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameCheckRequest {
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}

