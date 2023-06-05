package com.video.base.exception;

public class VideoException extends RuntimeException {
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public VideoException() {
    }

    public VideoException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public static void cast(String errMessage) {
        throw new VideoException(errMessage);
    }

    public static void cast(CommonError commonError) {
        throw new VideoException(commonError.getErrMessage());
    }
}
