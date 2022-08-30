package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.CoreApplication;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {
    // 수동 빈 vs 자동 빈 (중복충돌시) 수동 빈이 등록이 우선권을 가진다.(수동 빈이 자동 빈을 오버라이딩 해버린다.)
    // Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [hello.core.member.MemoryMemberRepository];
    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }

//    @Test
//    @DisplayName("내가 궁금해서 하는 테스트? springbootapplication을 조회하면 어떻게 될까?")
//    void springbootAppConfigTest() {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(CoreApplication.class);
//    }


}
