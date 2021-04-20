package tech.dimitar.jms.demo.jmsdemo;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsdemoApplication {

	public static void main(String[] args) throws Exception {

		// Start the MQ embedded server
		final ActiveMQServer activeMqServer = ActiveMQServers.newActiveMQServer(
			new ConfigurationImpl()
				.setPersistenceEnabled(false)
				.setJournalDirectory("target/data/journal")
				.setSecurityEnabled(false)
				.addAcceptorConfiguration("invm", "vm://0")
		);

		activeMqServer.start();

		// Start the app
		SpringApplication.run(JmsdemoApplication.class, args);
	}

}
