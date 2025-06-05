package net.talaatharb.jms.tibco.adapter;

/**
 * Exception thrown by the JMS adapter when there's an error with JMS operations.
 */
public class JmsAdapterException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public JmsAdapterException(String message) {
        super(message);
    }
    
    public JmsAdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}