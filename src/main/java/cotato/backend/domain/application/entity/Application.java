package cotato.backend.domain.application.entity;

import cotato.backend.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer period;

    private Part part;

    private Integer ability;

    private Integer passion;

    private LocalDateTime applicationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity applicant;

    @Builder
    public Application(
            UserEntity applicant,
            Integer period,
            Part part,
            Integer ability,
            Integer passion
    ){
        this.applicant = applicant;
        this.period = period;
        this.part = part;
        this.ability = ability;
        this.passion = passion;
        this.applicationTime = LocalDateTime.now();


    }
}