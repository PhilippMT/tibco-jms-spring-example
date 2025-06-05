package net.talaatharb.jms.tibco.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import net.talaatharb.jms.tibco.adapter.JmsAdapterException;
import net.talaatharb.jms.tibco.service.JmsSenderService;

@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JmsSenderService jmsSenderService;

    @Test
    public void testSendMessage() throws Exception {
        // Arrange
        String message = "Test message";
        
        // Act & Assert
        mockMvc.perform(post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message));
        
        verify(jmsSenderService, times(1)).send(message);
    }
    
    @Test
    public void testSendMessageWithException() throws Exception {
        // Arrange
        String message = "Test message";
        String errorMessage = "Error sending message";
        
        doThrow(new JmsAdapterException(errorMessage))
            .when(jmsSenderService).send(anyString());
        
        // Act & Assert
        mockMvc.perform(post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value(errorMessage));
        
        verify(jmsSenderService, times(1)).send(message);
    }
}