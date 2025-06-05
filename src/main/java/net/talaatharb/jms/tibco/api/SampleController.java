package net.talaatharb.jms.tibco.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.talaatharb.jms.tibco.adapter.JmsAdapterException;
import net.talaatharb.jms.tibco.service.JmsSenderService;

@RestController
public class SampleController {

        private final JmsSenderService jmsService;

        public SampleController(JmsSenderService jmsService) {
                this.jmsService = jmsService;
        }

        @PostMapping(path = "/api/message", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<MessageResponse> send(@RequestBody String message) {
                jmsService.send(message);
                return ResponseEntity.ok(new MessageResponse(message));
        }
        
        @ExceptionHandler(JmsAdapterException.class)
        public ResponseEntity<ErrorResponse> handleJmsAdapterException(JmsAdapterException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse(e.getMessage()));
        }
        
        public static class MessageResponse {
                private final String message;
                
                public MessageResponse(String message) {
                        this.message = message;
                }
                
                public String getMessage() {
                        return message;
                }
        }
        
        public static class ErrorResponse {
                private final String error;
                
                public ErrorResponse(String error) {
                        this.error = error;
                }
                
                public String getError() {
                        return error;
                }
        }
}
