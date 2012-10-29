package se.johannesc;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class AMQTest {

	ApplicationContext context;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext(
				new String[] { "context.xml" });
	}

	@Test
	public void test() throws JMSException {
		final String someMessage = "SOME MESSAGE";
		JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
		Destination destination = (Destination) context
				.getBean("yourDestination");
		jmsTemplate.send(destination, new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(someMessage);
			}
		});

		Message msg = jmsTemplate.receive(destination);
		Assert.assertEquals(someMessage, ((TextMessage) msg).getText());
	}
}
