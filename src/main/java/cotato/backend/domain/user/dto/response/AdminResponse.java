package cotato.backend.domain.user.dto.response;

import cotato.backend.domain.user.entity.Role;
import cotato.backend.domain.user.entity.UserEntity;

public record AdminResponse(
        String name,
        Integer age,
        String phoneNumber,
        Role role
) {
    public static AdminResponse from(UserEntity user) {
        return new AdminResponse(
                user.getName(),
                user.getAge(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}