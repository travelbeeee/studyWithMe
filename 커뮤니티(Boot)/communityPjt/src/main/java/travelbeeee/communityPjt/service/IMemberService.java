package travelbeeee.communityPjt.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import travelbeeee.communityPjt.controller.MemberController;
import travelbeeee.communityPjt.domain.Member;
import travelbeeee.communityPjt.repository.MemberRepository;
import travelbeeee.communityPjt.util.Sha256Util;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class IMemberService implements MemberService {


    private final MemberRepository memberRepository;
    private final Sha256Util sha256Util;
    private final Logger logger = LoggerFactory.getLogger(IMemberService.class);

    @Override
    public void join(Member member) {
        // 아이디중복
        Member selectedMember = memberRepository.selectByName(member.getUsername());
        if(selectedMember != null){
            System.out.println("아이디중복!!");
            return;
        }

        // 회원가입
        memberRepository.insert(member);

        return;
    }

    @Override
    public void modify(Member member) throws NoSuchAlgorithmException{
        // 새로운 아이디 중복체크
        Member selectedMember = memberRepository.selectByName(member.getUsername());
        if (selectedMember != null && selectedMember.getMemberId() != member.getMemberId())
            return;

        // Salt값 불러와서 비번 다시 암호화
        String salt = memberRepository.selectById(member.getMemberId()).getSalt();
        member.setUserpwd(sha256Util.sha256(member.getUserpwd(), salt));
        memberRepository.update(member);
        return;
    }

    @Override
    public void delete(Long memberId) {
        memberRepository.delete(memberId);

        return;
    }

    /**
     * 입력된 회원아이디로 Salt값 조회
     * --> Salt값과 입력된 비밀번호로 암호화 진행
     * --> 입력된 회원아이디와 암호화된 비밀번호르 로그인 진행!
     */
    @Override
    public Member login(String username, String userpwd) throws NoSuchAlgorithmException {
        Member member = memberRepository.selectByName(username);
        if(member == null) return null;

        String salt = memberRepository.selectByName(username).getSalt();
        userpwd = sha256Util.sha256(userpwd, salt);

        // member가 null이라면 비밀번호가 틀린 경우.
        Member findMember = memberRepository.selectByNamePwd(username, userpwd);
        return findMember;
    }

    @Override
    public Member getMemberById(Long memberId) {
        Member member = memberRepository.selectById(memberId);
        return member;
    }

    @Override
    public void auth(Long memberId) {
        memberRepository.updateAuth(memberId);
        return;
    }
}
