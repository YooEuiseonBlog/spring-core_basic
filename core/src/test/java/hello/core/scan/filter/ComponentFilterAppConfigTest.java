package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    /*
    * FilterType 옵션
    *   ANNOTATION : 기본값, 애노테이션을 인식해서 동작한다.
    *       ex) org.example.SomeAnnotation
    *   ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 해서 동작한다.
    *       ex) org.example.SomeClass
    *   ASPECTJ : AspectJ패턴 사용
    *       ex) org.example..*Service+
    *   REGEX : 정규 표현식
    *       ex) org\.example\.Default.*
    *   CUSTOM : 'TypeFilter' 이라는 인터페이스를 구현해서 처리
    *       ex) org.example.MyTypeFilter
    *
    * */
    @Configuration
    @ComponentScan(
            includeFilters = {
                    @Filter(classes = MyIncludeComponent.class) //type = FilterType.ANNOTATION : 기본값이 생략가능
            },
            excludeFilters = {
                    @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
//                    , @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class) // BeanA도 빼고 싶다면.... 특정 클래스를 필터릴 할 때 사용.
            }
    )
    static class ComponentFilterAppConfig {

    }
}
