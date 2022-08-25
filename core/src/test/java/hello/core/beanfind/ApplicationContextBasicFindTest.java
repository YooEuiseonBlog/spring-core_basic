package hello.core.beanfind;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemberService;

class ApplicationContextBasicFindTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Test
	@DisplayName("빈 이름으로 조회")
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
//		System.out.println("memberService = " + memberService);
//		System.out.println("memberService.getClass() = " + memberService.getClass());
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("이름 없이 타입으로만 조회")
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("구체 타입으로 조회")
	void findBeanByName2() {
		MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("빈 이름으로 조회X")
	void findBeanByNameX() {
		//ac.getBean("xxxxx", MemberService.class);
//		MemberService memberService = ac.getBean("xxxxx", MemberService.class);
		assertThrows(NoSuchBeanDefinitionException.class, 
				() -> ac.getBean("xxxxx", MemberService.class));
	}
	
	@Test
	@DisplayName("클래스 타입 없이 오직 빈 이름만으로 조회")
	void findBeanByName3() {
		// 빈 이름으로 지정할 경우, 리턴타입이 Object로 반환된다. 이 경우는 cast를 따로 지정해줘야 한다. ---> samebeanTest를 통해서 알게된 사실은 class type과 bean name을 통해서 검색한다.
		MemberService memberService = (MemberService) ac.getBean("memberService"); // 뒤에 있는 클래스타입 반환할 리턴타입을 지정 
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
}
