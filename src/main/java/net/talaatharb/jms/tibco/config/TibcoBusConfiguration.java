package net.talaatharb.jms.tibco.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import com.tibco.tibjms.TibjmsConnectionFactory;

@Configuration
@EnableJms
public class TibcoBusConfiguration {
        private final String password;
        private final String port;
        private final String queue;
        private final String server;
        private final String user;

        public TibcoBusConfiguration(
                @Value("${ems.password}") String password,
                @Value("${ems.port}") String port,
                @Value("${ems.queue}") String queue,
                @Value("${ems.server}") String server,
                @Value("${ems.user}") String user) {
                this.password = password;
                this.port = port;
                this.queue = queue;
                this.server = server;
                this.user = user;
        }

        @Bean(name = "jmsConnectionFactory")
        public ConnectionFactory jmsConnectionFactory() throws JMSException {
                final TibjmsConnectionFactory factory = new TibjmsConnectionFactory();

                factory.setServerUrl(serverURL());
                factory.setUserName(user);
                factory.setUserPassword(password);

                return factory;
        }

        @Bean
        public JmsTemplate jmsTemplate(ConnectionFactory jmsConnectionFactory) {
                final JmsTemplate jmsTemplate = new JmsTemplate();

                jmsTemplate.setConnectionFactory(jmsConnectionFactory);
                jmsTemplate.setDefaultDestinationName(queue);
                jmsTemplate.setExplicitQosEnabled(true);
                jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
                jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
                jmsTemplate.setSessionTransacted(false);

                return jmsTemplate;
        }

        private String serverURL() {
                return "tcp://" + server + ":" + port;
        }
}