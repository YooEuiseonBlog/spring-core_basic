package hello.core.member;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("memberService2") // 빈이름을 임의로 바꿀 때
@Component
=======
>>>>>>> origin/main
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	
<<<<<<< HEAD
	@Autowired // ac.getBean(MemberRepository.class)
=======
	
>>>>>>> origin/main
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
<<<<<<< HEAD
		return memberRepository.findById(memberId);
	}

	//테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
=======
		// TODO Auto-generated method stub
		return memberRepository.findById(memberId);
	}

>>>>>>> origin/main
}
