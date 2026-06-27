package com.youtube.exception;

public class ChannelWithSuchNameExistsException extends BaseException {
    public ChannelWithSuchNameExistsException(String message) {
        super(message);
    }
}