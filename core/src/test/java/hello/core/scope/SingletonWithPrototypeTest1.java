package hello.core.scope;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {
/*
    여러 (Singleton)빈에서 같은 프로토타입 빈을 주입 받으면, 주입 받는 시점에 각각 새로운 프로토타입 빈이
    생성된다. 예를 들어서 clientA, clientB가 각각 의존관계 주입을 받으면 각각 다른 인스턴스의 프로토타입
    빈을 주입 받는다.
    > clientA prototypeBean@x01
    > clientB prototypeBean@x02
    > ** 물론 사용할 때 마다 새로 생성되는 것은 아니다.
*/


    @Test
    void prototypeFind() {
        // spring container 생성 및 parameter 빈으로 등록
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);//조회(타입 기준) --> 주입
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1); // val.equals(1);
        assertThat(prototypeBean1.getCount()).isSameAs(1);  // val == 1;

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);//조회(타입 기준)
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1); // val.equals(1);
        assertThat(prototypeBean2.getCount()).isSameAs(1);  // val == 1;
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }
    
    /* 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다. 그런데 싱글톤
    빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과
    함께 계속 유지되는 것이 문제다. */

    // 예시
    @Scope("singleton") // 생략 가능
    static class ClientBean_prev_01 {
        private final PrototypeBean prototypeBean; //생성시점에 주입 x01

//        @Autowired
//        ApplicationContext applicationContext; // @Autowired를 통한 의존성 주입

        @Autowired // 생략가능
        public ClientBean_prev_01(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    @Scope("singleton") // 생략 가능
    static class ClientBean_prev_02 {
        private final PrototypeBean prototypeBean; //생성시점에 주입 x02

//        @Autowired
//        ApplicationContext applicationContext; // @Autowired를 통한 의존성 주입

        @Autowired // 생략가능
        public ClientBean_prev_02(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    // 해결법 (사용할 때마다 새로운 Prototype Bean을 생성)

/*
    실행해보면 prototypeBeanProvider.getObject() 을 통해서 항상 새로운 프로토타입 빈이 생성되는
    것을 확인할 수 있다.
    ObjectProvider 의 getObject() 를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서
    반환한다. (DL)
    스프링이 제공하는 기능을 사용하지만, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는
    훨씬 쉬워진다.
    ObjectProvider 는 지금 딱 필요한 DL 정도의 기능만 제공한다.
*/
    @Scope("singleton")
    static class ClientBean {

    @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;
        private Provider<PrototypeBean> prototypeBeanProvider;

    public int logic() {
//        PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
        PrototypeBean prototypeBean = prototypeBeanProvider.get();
        prototypeBean.addCount();
        return prototypeBean.getCount(); // 인라인 만들기 ctrl + alt + n;
    }

}


    @Scope("prototype") // 요청받고 의존성주입을 할 때마다 새로운 인스턴스를 생성(객체생성 -> 의존성주입 -> 초기화)
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init : " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
