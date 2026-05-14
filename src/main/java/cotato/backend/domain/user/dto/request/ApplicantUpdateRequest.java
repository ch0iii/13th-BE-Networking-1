package cotato.backend.domain.user.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ApplicantUpdateRequest(

        @NotBlank
        @Size(min = 2, max = 10)
        String name,

        @Min(22)
        @Max(30)
        Integer age,

        @NotBlank
        String phoneNumber
) {
}