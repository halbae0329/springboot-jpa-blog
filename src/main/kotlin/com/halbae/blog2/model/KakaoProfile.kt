package com.halbae.blog2.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoProfile @JsonCreator constructor(
    @JsonProperty("id") val id: Int,
    @JsonProperty("connected_at") val connectedAt: String,
    @JsonProperty("properties") val properties: Properties,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount,
) {
    data class Properties @JsonCreator constructor(
        @JsonProperty("nickname") val nickname: String,
        @JsonProperty("profile_image") val profileImage: String,
        @JsonProperty("thumbnail_image") val thumbnailImage: String,
    )

    data class KakaoAccount @JsonCreator constructor(
        @JsonProperty("profile_needs_agreement") val profileNeedsAgreement: Boolean,
        @JsonProperty("profile") val profile: Profile,
        @JsonProperty("has_email") val hasEmail: Boolean,
        @JsonProperty("email_needs_agreement") val emailNeedsAgreement: Boolean,
        @JsonProperty("is_email_valid") val isEmailValid: Boolean,
        @JsonProperty("is_email_verified") val isEmailVerified: Boolean,
        @JsonProperty("email") val email: String,
    ) {
        data class Profile @JsonCreator constructor(
            @JsonProperty("nickname") val nickname: String,
            @JsonProperty("thumbnail_image_url") val thumbnailImageUrl: String,
            @JsonProperty("profile_image_url") val profileImageUrl: String,
            @JsonProperty("is_default_image") val isDefaultImage: Boolean,
        )
    }
}