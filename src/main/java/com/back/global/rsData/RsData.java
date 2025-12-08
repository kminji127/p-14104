package com.back.global.rsData;

public record RsData<T>(
        String resultCode,
        String msg,
        T data
) {
    // RsData<Void>로 호출 가능
    public RsData(String resultCode, String msg) {
        this(resultCode, msg, null);
    }
}
