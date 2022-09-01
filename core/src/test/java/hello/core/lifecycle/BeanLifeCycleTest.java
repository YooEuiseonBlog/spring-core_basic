package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
/*
    생성자 부분을 보면 url 정보 없이 connect가 호출되는 것을 확인할 수 있다.
    너무 당연한 이야기이지만 객체를 생성하는 단계에는 url이 없고, 객체를 생성한 다음에 외부에서 수정자
    주입을 통해서 setUrl() 이 호출되어야 url이 존재하게 된다
*/

/*
    빈의 라이프사이클
    객체 생성 --> 의존관계 주입
*/
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class); // 의존관계 주입
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
//        @Bean(initMethod = "init", destroyMethod = "close") // 빈 등록
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient(); // 1. 객체 생성 2. 의존관계 주입
            networkClient.setUrl("http://hello-spring.dev"); // 초기화
            return networkClient;
        }
    }
}
