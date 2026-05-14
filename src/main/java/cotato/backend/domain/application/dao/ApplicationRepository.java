package cotato.backend.domain.application.dao;

import cotato.backend.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByApplicantIdAndPeriod(Long applicantId, Integer period);
}
