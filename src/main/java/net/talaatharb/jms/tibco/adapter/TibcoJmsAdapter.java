package net.talaatharb.jms.tibco.adapter;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Implementation of the JmsAdapter interface that uses Spring's JmsTemplate
 * to interact with Tibco JMS.
 */
@Component
public class TibcoJmsAdapter implements JmsAdapter {
    
    private final JmsTemplate jmsTemplate;
    
    public TibcoJmsAdapter(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public void sendTextMessage(String queueName, String messageText) throws JmsAdapterException {
        try {
            jmsTemplate.send(queueName, session -> {
                try {
                    return session.createTextMessage(messageText);
                } catch (Exception e) {
                    throw new JmsAdapterException("Error creating text message", e);
                }
            });
        } catch (JmsException e) {
            throw new JmsAdapterException("Error sending message to queue: " + queueName, e);
        }
    }
}