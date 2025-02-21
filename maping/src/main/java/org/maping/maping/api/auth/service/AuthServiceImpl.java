package org.maping.maping.api.auth.service;
import lombok.RequiredArgsConstructor;
import org.maping.maping.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=]{6,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final String NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]{2,10}$";
    private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);


    @Override
    public boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean isValidNickname(String nickname) {
        if (nickname == null) {
            return false;
        }
        return NICKNAME_PATTERN.matcher(nickname).matches();
    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByuserName(nickname);
    }
}
