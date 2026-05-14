package cotato.backend.domain.user.dto.request;

import cotato.backend.domain.user.entity.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminCreateRequest(

        @NotBlank
        @Size(min = 2, max = 10)
        String name,

        @NotNull
        @Min(22)
        @Max(30)
        Integer age,

        @NotBlank
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리여야 합니다.")
        String phoneNumber,

        @NotNull
        Role role
) {
}