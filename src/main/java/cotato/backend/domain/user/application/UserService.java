package cotato.backend.domain.user.application;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.user.dao.UserRepository;
import cotato.backend.domain.user.dto.request.AdminUpdateRequest;
import cotato.backend.domain.user.dto.request.ApplicantUpdateRequest;
import cotato.backend.domain.user.dto.response.AdminResponse;
import cotato.backend.domain.user.dto.response.ApplicantResponse;
import cotato.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public ApplicantResponse getApplicant(Long userId) {
        UserEntity user = getUser(userId);
        return ApplicantResponse.from(user);
    }

    public AdminResponse getStaff(Long userId) {
        UserEntity user = getUser(userId);
        return AdminResponse.from(user);
    }

    @Transactional
    public void updateApplicant(Long userId, ApplicantUpdateRequest request) {
        UserEntity user = getUser(userId);

        user.updateApplicantInfo(
                request.name(),
                request.age(),
                request.phoneNumber()
        );
    }

    @Transactional
    public void updateStaff(Long userId, AdminUpdateRequest request) {
        UserEntity user = getUser(userId);

        user.updateStaffInfo(
                request.name(),
                request.age(),
                request.phoneNumber(),
                request.role()
        );
    }

    private UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}