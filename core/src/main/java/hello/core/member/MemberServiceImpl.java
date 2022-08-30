package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("memberService2") // 빈이름을 임의로 바꿀 때
//@Component("service") // 같은 bean name이  둘 이상 있으면 (자동) 빈끼리 충돌가능
@Component
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	@Autowired // ac.getBean(MemberRepository.class)
	public MemberServiceImpl(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}

	//테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
