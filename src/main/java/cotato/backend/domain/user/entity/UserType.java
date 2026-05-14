package cotato.backend.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserType {
    APPLICANT("TYPE_APPLICANT"), ADMIN("TYPE_ADMIN");

    private final String value;
}
