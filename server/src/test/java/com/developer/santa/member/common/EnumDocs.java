package com.developer.santa.member.common;


import java.util.Map;

public class EnumDocs {
    Map<String, String> provider;

    public EnumDocs(Map<String, String> provider) {
        this.provider = provider;
    }

    public Map<String, String> getProvider() {
        return provider;
    }

    public void setProvider(Map<String, String> provider) {
        this.provider = provider;
    }
}
