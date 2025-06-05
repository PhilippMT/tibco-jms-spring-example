package net.talaatharb.jms.tibco.adapter;

/**
 * Interface for adapting between Jakarta JMS and Tibco JMS implementations.
 * This provides an abstraction layer to handle potential compatibility issues.
 */
public interface JmsAdapter {
    
    /**
     * Sends a text message to the specified queue.
     * 
     * @param queueName the name of the queue to send the message to
     * @param messageText the text content of the message
     * @throws JmsAdapterException if there's an error sending the message
     */
    void sendTextMessage(String queueName, String messageText) throws JmsAdapterException;
}