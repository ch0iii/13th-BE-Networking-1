package cotato.backend.domain.application.dto.response;

public record ApplicationLikeResponse(
        boolean liked,
        Long likeId
) {
    public static ApplicationLikeResponse liked(Long likeId) {
        return new ApplicationLikeResponse(true, likeId);
    }

    public static ApplicationLikeResponse unliked() {
        return new ApplicationLikeResponse(false, null);
    }
}