package com.video.base.exception;

public class RestErrorResponse {
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }
}
