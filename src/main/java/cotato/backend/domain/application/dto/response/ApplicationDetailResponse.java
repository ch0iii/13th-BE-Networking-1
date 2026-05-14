package cotato.backend.domain.application.dto.response;

import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.application.entity.Part;

import java.time.LocalDateTime;

public record ApplicationDetailResponse(
        String name,
        Integer period,
        Integer age,
        Part part,
        Integer ability,
        Integer passion,
        String phoneNumber,
        LocalDateTime applicationTime
) {

    public static ApplicationDetailResponse from(Application application) {
        return new ApplicationDetailResponse(
                application.getApplicant().getName(),
                application.getPeriod(),
                application.getApplicant().getAge(),
                application.getPart(),
                application.getAbility(),
                application.getPassion(),
                application.getApplicant().getPhoneNumber(),
                application.getApplicationTime()
        );
    }
}