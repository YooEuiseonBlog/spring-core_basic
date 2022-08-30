package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("service") // bean 이름이 같은 게 두개 이상 있을 경우 충돌발생
@Component
public class OrderServiceImpl implements OrderService {
	
/*	직접 구현체 연결(주입)
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();*/
	
/*	필드주입 @Autowird --> spring DI로만 실행가능 --> 순수자바코드로는 실행불가/변경불가 ---> 테스트용으로만 사용
	@Autowired MemberRepository memberRepository;
	@Autowired DiscountPolicy discountPolicy;*/
	
/*	setter를 활용한 Autowired 의존성 주입 --> 변경에 유리
	private  MemberRepository memberRepository;
	private  DiscountPolicy discountPolicy;
	@Autowired(required = false)
	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}
	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}*/

	/*	일반 메서드 주입
	private MemberRepository memberRepository;
	private DiscountPolicy discountPolicy;

	@Autowired
	public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}*/

	// 생성자 주입 ---> 불변/고정
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

//	new OrderServiceImpl(memberRepository, discountPolicy);
//	@Autowired // 생성자가 1개일 때는 자동으로 @Autowird가 적용된다.
	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//		System.out.println("1. OrderServiceImpl.OrderServiceImpl");
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}


	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

	//테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
