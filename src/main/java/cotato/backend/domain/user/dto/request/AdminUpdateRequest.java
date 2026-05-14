package cotato.backend.domain.user.dto.request;

import cotato.backend.domain.user.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminUpdateRequest(
        @NotBlank String name,
        @NotNull Integer age,
        @NotBlank String phoneNumber,
        @NotNull Role role
) {
}
