package hello.core.member;

public class MemberSerivceImpl implements MemberService {
	
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
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
