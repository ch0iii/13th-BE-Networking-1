package cotato.backend.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

        PRESIDENT("ROLE_PRESIDENT"),
        VICE_PRESIDENT("ROLE_VICE_PRESIDENT"),
        PART_LEADER("ROLE_PART_LEADER"),
        PLANNING_LEADER("ROLE_PLANNING_LEADER"),
        PROMOTION_LEADER("ROLE_PROMOTION_LEADER"),
        EDUCATION_LEADER("ROLE_EDUCATION_LEADER");

        private final String value;

}
