package cotato.backend.domain.application.dto.request;
import cotato.backend.domain.application.entity.Part;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ApplicationCreateRequest (

    @NotNull
    @Min(1)
    Integer period,

    @NotNull
    Part part,

    @NotNull
    @Min(0)
    @Max(10)
    Integer ability,

    @NotNull
    @Min(0)
    @Max(10)
    Integer passion

)
{}
