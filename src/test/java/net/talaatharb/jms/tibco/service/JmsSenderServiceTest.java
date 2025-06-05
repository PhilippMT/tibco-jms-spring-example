package net.talaatharb.jms.tibco.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.talaatharb.jms.tibco.adapter.JmsAdapter;
import net.talaatharb.jms.tibco.adapter.JmsAdapterException;

@ExtendWith(MockitoExtension.class)
public class JmsSenderServiceTest {

    @Mock
    private JmsAdapter jmsAdapter;

    private JmsSenderService jmsSenderService;
    
    private static final String QUEUE_NAME = "test.queue";
    private static final String MESSAGE = "Test message";

    @BeforeEach
    public void setUp() {
        jmsSenderService = new JmsSenderService(jmsAdapter, QUEUE_NAME);
    }

    @Test
    public void testSendMessage() {
        // Act
        jmsSenderService.send(MESSAGE);
        
        // Assert
        verify(jmsAdapter, times(1)).sendTextMessage(QUEUE_NAME, MESSAGE);
    }
    
    @Test
    public void testSendMessageWithException() {
        // Arrange
        doThrow(new JmsAdapterException("Test exception"))
            .when(jmsAdapter).sendTextMessage(anyString(), anyString());
        
        // Act & Assert
        try {
            jmsSenderService.send(MESSAGE);
        } catch (JmsAdapterException e) {
            // Expected exception
        }
        
        verify(jmsAdapter, times(1)).sendTextMessage(QUEUE_NAME, MESSAGE);
    }
}