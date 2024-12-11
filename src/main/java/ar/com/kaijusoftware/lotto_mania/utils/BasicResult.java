package ar.com.kaijusoftware.lotto_mania.utils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasicResult<T> {

    T result;

    BasicError error;

    public boolean isOk() {
        return error == null;
    }

    public static <T> BasicResult<T> ok(T result) {
        return BasicResult.<T>builder()
                .result(result)
                .build();
    }


    public static <T> BasicResult<T> error(final String message) {
        return BasicResult.<T>builder()
                .error(BasicError.builder().message(message).build())
                .build();
    }

    public static <T> BasicResult<T> error(final String message, final Throwable ex) {
        return BasicResult.<T>builder()
                .error(BasicError.builder().message(message).cause(ex).build())
                .build();
    }


}
