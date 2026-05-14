package cotato.backend.domain.user.dto.response;

import cotato.backend.domain.user.entity.UserEntity;

public record ApplicantResponse(
        String name,
        Integer age,
        String phoneNumber
) {
    public static ApplicantResponse from(UserEntity user) {
        return new ApplicantResponse(
                user.getName(),
                user.getAge(),
                user.getPhoneNumber()
        );
    }
}