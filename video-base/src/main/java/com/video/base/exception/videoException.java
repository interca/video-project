package com.video.base.exception;

import javax.management.RuntimeMBeanException;

public class videoException extends RuntimeException {
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public videoException() {
    }

    public videoException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public static void cast(String errMessage) {
        throw new videoException(errMessage);
    }

    public static void cast(CommonError commonError) {
        throw new videoException(commonError.getErrMessage());
    }
}
