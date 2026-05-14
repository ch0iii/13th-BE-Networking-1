package cotato.backend.domain.application.application;

import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import cotato.backend.domain.application.dto.response.ApplicationDetailResponse;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.application.entity.ApplicationLike;
import cotato.backend.domain.user.dao.UserRepository;
import cotato.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    // 지원서 생성
    @Transactional
    public Long createApplication(Long userId, ApplicationCreateRequest request) {
        UserEntity applicant = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Application application = Application.builder()
                .applicant(applicant)
                .period(request.period())
                .part(request.part())
                .ability(request.ability())
                .passion(request.passion())
                .build();


        Application savedApplication = applicationRepository.save(application);

        return savedApplication.getId();
    }

    public ApplicationDetailResponse getApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        return ApplicationDetailResponse.from(application);
    }

    public List<ApplicationListResponse> getApplications(Integer period, String filterBy) {
        List<Application> applications = applicationRepository.findAll();

        if (period != null) {
            applications = applications.stream()
                    .filter(application -> application.getPeriod().equals(period))
                    .toList();
        }

        return applications.stream()
                .map(application -> ApplicationListResponse.of(
                        application,
                        applicationLikeRepository.countByApplicationId(application.getId())
                ))
                .sorted(getComparator(filterBy))
                .limit(10)
                .toList();
    }

    @Transactional
    public Long likeApplication(Long applicationId, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        ApplicationLike applicationLike = ApplicationLike.builder()
                .application(application)
                .staff(user)
                .build();

        ApplicationLike savedApplicationLike = applicationLikeRepository.save(applicationLike);

        return savedApplicationLike.getId();
    }

    private Comparator<ApplicationListResponse> getComparator(String filterBy) {
        if ("likes".equals(filterBy) || "gisu+likes".equals(filterBy)) {
            return Comparator.comparing(ApplicationListResponse::likeCount).reversed();
        }

        return Comparator.comparing(ApplicationListResponse::period).reversed();
    }
}