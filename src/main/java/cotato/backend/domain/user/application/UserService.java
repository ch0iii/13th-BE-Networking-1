package cotato.backend.domain.user.application;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.user.dao.UserRepository;
import cotato.backend.domain.user.dto.request.AdminCreateRequest;
import cotato.backend.domain.user.dto.request.AdminUpdateRequest;
import cotato.backend.domain.user.dto.request.ApplicantUpdateRequest;
import cotato.backend.domain.user.dto.response.AdminResponse;
import cotato.backend.domain.user.dto.response.ApplicantResponse;
import cotato.backend.domain.user.entity.Type;
import cotato.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cotato.backend.domain.user.entity.Type;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public ApplicantResponse getApplicant(Long userId) {
        UserEntity user = getUser(userId);
        validateApplicant(user);

        return ApplicantResponse.from(user);
    }

    public AdminResponse getAdmin(Long userId) {
        UserEntity user = getUser(userId);
        validateAdmin(user);

        return AdminResponse.from(user);
    }

    @Transactional
    public void updateApplicant(Long userId, ApplicantUpdateRequest request) {
        UserEntity user = getUser(userId);
        validateAdmin(user);

        user.updateApplicantInfo(
                request.name(),
                request.age(),
                request.phoneNumber()
        );
    }

    @Transactional
    public void updateStaff(Long userId, AdminUpdateRequest request) {
        UserEntity user = getUser(userId);

        user.updateAdminInfo(
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

    @Transactional
    public Long createStaff(AdminCreateRequest request) {
        UserEntity staff = userRepository.findByPhoneNumber(request.phoneNumber())
                .map(user -> {
                    user.updateAdminInfo(
                            request.name(),
                            request.age(),
                            request.phoneNumber(),
                            request.role()
                    );
                    return user;
                })
                .orElseGet(() -> userRepository.save(
                        UserEntity.builder()
                                .name(request.name())
                                .age(request.age())
                                .phoneNumber(request.phoneNumber())
                                .userType(Type.ADMIN)
                                .role(request.role())
                                .build()
                ));

        return staff.getId();
    }

    private void validateApplicant(UserEntity user) {
        if (user.getUserType() != Type.APPLICANT) {
            throw new AppException(ErrorCode.NOT_APPLICANT);
        }
    }

    private void validateAdmin(UserEntity user) {
        if (user.getUserType() != Type.ADMIN) {
            throw new AppException(ErrorCode.NOT_ADMIN);
        }
    }
}