package cotato.backend.domain.application.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import cotato.backend.domain.application.entity.Part;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ApplicationCreateRequest (

        @NotBlank
        @Size(min = 2, max = 10)
        String name,

        @NotNull
        @Min(1)
        Integer period,

        @NotNull
        @Min(22)
        @Max(30)
        Integer age,

        @NotNull
        Part part,

        @NotNull
        @Min(0)
        @Max(10)
        Integer ability,

        @NotNull
        @Min(0)
        @Max(10)
        Integer passion,

        @NotBlank
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리여야 합니다.")
        String phoneNumber,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime applicationTime
)
{}
