package cotato.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "users")@NoArgsConstructor(access = PROTECTED)
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
    private Type userType;

    @Enumerated(STRING)
    private Role staffRole;

    @Builder
    public UserEntity(
            String name,
            Integer age,
            String phoneNumber,
            Type userType,
            Role staffRole
    ) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.staffRole = staffRole;
    }

    public void updateApplicantInfo(String name, Integer age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public void updateStaffInfo(String name, Integer age, String phoneNumber, Role staffRole) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.staffRole = staffRole;
    }
}
