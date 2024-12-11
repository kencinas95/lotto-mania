package ar.com.kaijusoftware.lotto_mania.utils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasicError {

    String message;

    Throwable cause;

}
