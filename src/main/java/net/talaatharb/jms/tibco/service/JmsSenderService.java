package net.talaatharb.jms.tibco.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.talaatharb.jms.tibco.adapter.JmsAdapter;
import net.talaatharb.jms.tibco.adapter.JmsAdapterException;

@Service
public class JmsSenderService {
        
        private final JmsAdapter jmsAdapter;
        private final String queue;

        public JmsSenderService(JmsAdapter jmsAdapter, @Value("${ems.queue}") String queue) {
                this.jmsAdapter = jmsAdapter;
                this.queue = queue;
        }

        public void send(final String msg) {
                try {
                        jmsAdapter.sendTextMessage(queue, msg);
                } catch (JmsAdapterException e) {
                        throw e;
                }
        }
}
