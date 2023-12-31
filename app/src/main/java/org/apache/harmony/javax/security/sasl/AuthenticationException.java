

package org.apache.harmony.javax.security.sasl;

public class AuthenticationException extends SaslException {

    private static final long serialVersionUID = -3579708765071815007L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String detail) {
        super(detail);
    }

    public AuthenticationException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
