package com.developer.santa.member.oauth.info;

import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.info.impl.GoogleOAuth2UserInfo;
import com.developer.santa.member.oauth.info.impl.KakaoOAuth2UserInfo;
import com.developer.santa.member.oauth.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type");
        }
    }
}