package org.maping.maping.api.auth.service;

public interface AuthService {

    boolean isValidPassword(String password);

    boolean isValidNickname(String nickname);

    boolean isDuplicateNickname(String nickname);

}
