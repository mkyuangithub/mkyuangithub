package org.sky.exception;

import java.io.Serializable;

public class DemoRpcRunTimeException extends RuntimeException implements Serializable {
	public DemoRpcRunTimeException() {
	}

	public DemoRpcRunTimeException(String msg) {
		super(msg);
	}

	public DemoRpcRunTimeException(Throwable cause) {
		super(cause);
	}

	public DemoRpcRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
