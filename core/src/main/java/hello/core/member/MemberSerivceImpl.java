package hello.core.member;

public class MemberSerivceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	
	
	public MemberSerivceImpl(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		// TODO Auto-generated method stub
		return memberRepository.findById(memberId);
	}

}
