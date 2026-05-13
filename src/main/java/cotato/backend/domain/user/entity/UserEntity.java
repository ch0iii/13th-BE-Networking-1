package cotato.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name= "user")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(STRING)
    private UserType userType;

    @Enumerated(STRING)
    private Role staffRole;
}
