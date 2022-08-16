package com.developer.santa.member.oauth.exception;

public class TokenValidFailedException extends RuntimeException{

    public TokenValidFailedException() {
        super("Failed to generate Token.");
    }

    public TokenValidFailedException(String message) {
        super(message);
    }
}
