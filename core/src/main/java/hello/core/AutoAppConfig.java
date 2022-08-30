package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
/*        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class, // 지정한 클래스의 패키지를 시작으로 탐색한다.
        ---> @ComponentScan를 붙인 클래스의 패키지부터 탐색한다. (default설정)
        basePackages = {"hello.core.member","hello.core.order"}, // 여러 개를 넣을 때는 {...}안에 넣어서 실행*/
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
/*
    @Autowired MemberRepository memberRepository;
    @Autowired DiscountPolicy discountPolicy;
    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(memberRepository, discountPolicy);
    }
*/

/*
    @Bean
    OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        return new OrderServiceImpl(memberRepository, discountPolicy);
    }
*/

/*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
*/
}
