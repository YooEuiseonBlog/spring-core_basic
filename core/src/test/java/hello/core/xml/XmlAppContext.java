package hello.core.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import hello.core.member.MemberService;
import hello.core.order.OrderService;

public class XmlAppContext {
	
	@Test
	@DisplayName("xml Test")
	void xmlAppContext() {
		ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
		MemberService memberService = ac.getBean("memberService",  MemberService.class);
		OrderService orderService  = ac.getBean("orderService", OrderService.class);
		assertThat(memberService).isInstanceOf(MemberService.class);
		assertThat(orderService).isInstanceOf(OrderService.class);
		
	}
}
