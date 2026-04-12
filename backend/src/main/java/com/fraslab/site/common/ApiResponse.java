package com.fraslab.site.common;

public record ApiResponse<T>(
        int code,
        String message,
        T data,
        String requestId
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "OK", data, "");
    }
}
