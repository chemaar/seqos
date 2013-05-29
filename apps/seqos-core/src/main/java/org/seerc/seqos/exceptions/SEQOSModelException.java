package org.seerc.seqos.exceptions;

import java.io.IOException;

public class SEQOSModelException extends RuntimeException {


	public SEQOSModelException(String msg, Exception e) {
		super(msg, e);
	}

}
