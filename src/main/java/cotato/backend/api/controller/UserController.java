package cotato.backend.api.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.user.application.UserService;
import cotato.backend.domain.user.dto.request.AdminUpdateRequest;
import cotato.backend.domain.user.dto.request.ApplicantUpdateRequest;
import cotato.backend.domain.user.dto.response.AdminResponse;
import cotato.backend.domain.user.dto.response.ApplicantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cotato.backend.api.dto.response.DefaultIdResponse;
import cotato.backend.domain.user.dto.request.AdminCreateRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "유저", description = "지원자 및 운영진 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/applicants/{userId}")
    @Operation(summary = "지원자 정보 조회")
    public ResponseEntity<DataResponse<ApplicantResponse>> getApplicant(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                DataResponse.from(userService.getApplicant(userId))
        );
    }


    @PatchMapping("/applicants/{userId}")
    @Operation(summary = "지원자 정보 수정")
    public ResponseEntity<Void> updateApplicant(
            @PathVariable Long userId,
            @Valid @RequestBody ApplicantUpdateRequest request
    ) {
        userService.updateApplicant(userId, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @Operation(summary = "운영진 생성")
    public ResponseEntity<DataResponse<DefaultIdResponse>> createAdmin (
            @Valid @RequestBody AdminCreateRequest request
    ) {
        Long userId = userService.createAdmin(request);

        return ResponseEntity.ok(
                DataResponse.created(
                        DefaultIdResponse.of(userId)
                )
        );
    }



    @GetMapping("/admin/{userId}")
    @Operation(summary = "운영진 정보 조회")
    public ResponseEntity<DataResponse<AdminResponse>> getAdmin(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                DataResponse.from(userService.getAdmin(userId))
        );
    }

    @PatchMapping("/admin/{userId}")
    @Operation(summary = "운영진 정보 수정")
    public ResponseEntity<Void> updateAdmin(
            @PathVariable Long userId,
            @Valid @RequestBody AdminUpdateRequest request
    ) {
        userService.updateAdmin(userId, request);
        return ResponseEntity.noContent().build();
    }
}