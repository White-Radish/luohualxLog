package com.carrot.luohualx.exception;

import com.carrot.luohualx.enums.ErrorCode;
import lombok.Getter;

/**
 * @author carrot
 */
@Getter
public class LuohualxException extends RuntimeException {
    private static final long serialVersionUID = 2793490843413553866L;

    private final int code;
    private final String msg;

    public LuohualxException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public LuohualxException(String msg) {
        this(ErrorCode.SYS_ERROR.getCode(), msg);
    }
}
