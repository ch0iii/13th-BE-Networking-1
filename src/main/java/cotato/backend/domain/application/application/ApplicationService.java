package cotato.backend.domain.application.application;

import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.user.dao.UserRepository;
import cotato.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    // 지원서 생성
    @Transactional
    public void createApplication(Long userId, ApplicationCreateRequest request) {
        UserEntity applicant = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ApplicationErrorCode.USER_NOT_FOUND));
        Application application = Application.builder()
                .applicant(applicant)
                .period(request.period())
                .part(request.part())
                .ability(request.ability())
                .passion(request.passion())
                .build();

        applicationRepository.save(application);
    }
}