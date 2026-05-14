package cotato.backend.domain.application.dao;

import cotato.backend.domain.application.entity.ApplicationLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApplicationLikeRepository extends JpaRepository<ApplicationLike, Long> {

    Long countByApplicationId(Long applicationId);
    Optional<ApplicationLike> findByApplicationIdAndAdminId(Long applicationId, Long adminId);

}