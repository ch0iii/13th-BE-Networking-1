package cotato.backend.domain.application.dto.response;

import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.application.entity.Part;

public record ApplicationListResponse(
        String name,
        Integer period,
        Part part,
        Long likeCount
) {

    public static ApplicationListResponse of(Application application, Long likeCount) {
        return new ApplicationListResponse(
                application.getApplicant().getName(),
                application.getPeriod(),
                application.getPart(),
                likeCount
        );
    }
}