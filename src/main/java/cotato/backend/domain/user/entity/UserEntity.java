package cotato.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(STRING)
    private UserType userType;

    @Enumerated(STRING)
    private Role role;

    @Builder
    public UserEntity(
            String name,
            Integer age,
            String phoneNumber,
            UserType userType,
            Role role
    ) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.role = role;
    }

    public void updateApplicantInfo(String name, Integer age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public void updateAdminInfo(String name, Integer age, String phoneNumber, Role role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;

    }
}
