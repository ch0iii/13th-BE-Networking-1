package cotato.backend.domain.application.application;
import java.time.LocalDateTime;
import cotato.backend.domain.application.dao.ApplicationLikeRepository;
import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import cotato.backend.domain.application.dto.response.ApplicationDetailResponse;
import cotato.backend.domain.application.dto.response.ApplicationLikeResponse;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.application.entity.ApplicationLike;
import cotato.backend.domain.user.dao.UserRepository;
import cotato.backend.domain.user.entity.Type;
import cotato.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ApplicationLikeRepository applicationLikeRepository;

    // 지원서 생성
    @Transactional
    public Long createApplication(ApplicationCreateRequest request) {
        UserEntity applicant = userRepository.findByPhoneNumber(request.phoneNumber())
                .orElseGet(() -> userRepository.save(
                        UserEntity.builder()
                                .name(request.name())
                                .age(request.age())
                                .phoneNumber(request.phoneNumber())
                                .userType(Type.APPLICANT)
                                .role(null)
                                .build()
                ));

        if (applicationRepository.existsByApplicantIdAndPeriod(applicant.getId(), request.period())) {
            throw new AppException(ErrorCode.ALREADY_SUBMITTED_APPLICATION);
        }


        Application application = Application.builder()
                .applicant(applicant)
                .period(request.period())
                .part(request.part())
                .ability(request.ability())
                .passion(request.passion())
                .applicationTime(request.applicationTime())
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
                .map(application -> {
                    Long likeCount = applicationLikeRepository.countByApplicationId(application.getId());
                    return ApplicationListResponse.of(application, likeCount);
                })
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
                .admin(user)
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

    // 지원서 좋아요 토글
    @Transactional
    public ApplicationLikeResponse toggleApplicationLike(Long applicationId, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        UserEntity admin = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (admin.getUserType() != Type.ADMIN) {
            throw new AppException(ErrorCode.ONLY_ADMIN_CAN_LIKE);
        }

        Optional<ApplicationLike> existingLike =
                applicationLikeRepository.findByApplicationIdAndAdminId(applicationId, userId);

        if (existingLike.isPresent()) {
            applicationLikeRepository.delete(existingLike.get());
            return ApplicationLikeResponse.unliked();
        }

        ApplicationLike applicationLike = ApplicationLike.builder()
                .application(application)
                .admin(admin)
                .build();

        ApplicationLike savedLike = applicationLikeRepository.save(applicationLike);

        return ApplicationLikeResponse.liked(savedLike.getId());
    }


}