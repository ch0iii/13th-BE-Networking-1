package cotato.backend.api.controller;

import cotato.backend.api.dto.response.DefaultIdResponse;
import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.application.application.ApplicationService;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
@Tag(name = "서류", description = "서류 API")
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping
    @Operation(summary = "지원서 생성")
    public ResponseEntity<DataResponse<DefaultIdResponse>> createApplication(
            @RequestParam Long userId,
            @Valid @RequestBody ApplicationCreateRequest request
    ) {
        Long applicationId = applicationService.createApplication(userId, request);

        return ResponseEntity.ok(
                DataResponse.created(
                        DefaultIdResponse.of(applicationId)
                )
        );
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "지원서 상세 조회")
    public ResponseEntity<DataResponse<ApplicationDetailResponse>> getApplication(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(
                DataResponse.from(
                        applicationService.getApplication(applicationId)
                )
        );
    }

    @GetMapping
    @Operation(summary = "지원서 리스트 조회")
    public ResponseEntity<DataResponse<List<ApplicationListResponse>>> getApplications(
            @RequestParam(required = false) Integer period,
            @RequestParam(required = false, defaultValue = "latest") String filterBy
    ) {
        return ResponseEntity.ok(
                DataResponse.from(
                        applicationService.getApplications(period, filterBy)
                )
        );
    }

    @PostMapping("/{applicationId}/likes")
    @Operation(summary = "지원서 좋아요")
    public ResponseEntity<DataResponse<DefaultIdResponse>> likeApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId
    ) {
        Long likeId = applicationService.likeApplication(applicationId, userId);

        return ResponseEntity.ok(
                DataResponse.created(
                        DefaultIdResponse.of(likeId)
                )
        );
    }


}