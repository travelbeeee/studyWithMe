package study.signUpUsingJDBC.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.signUpUsingJDBC.domain.Member;
import study.signUpUsingJDBC.repository.MemberRepository;
import study.signUpUsingJDBC.util.Sha256Util;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class IMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final Sha256Util sha256Util;

    @Override
    public void join(Member member) {
        // 아이디중복
        Member selectedMember = memberRepository.selectByName(member.getUsername());
        if(selectedMember.getId() != null)
            return;

        // 회원가입
        memberRepository.insert(member);

        return;
    }

    @Override
    public void modify(Member member) throws NoSuchAlgorithmException{
        // 새로운 아이디 중복체크
        Member selectedMember = memberRepository.selectByName(member.getUsername());
        if(selectedMember.getId() != null)
            return;

        // Salt값 불러와서 비번 다시 암호화
        String salt = memberRepository.selectById(member.getId()).getSalt();
        member.setUserpwd(sha256Util.sha256(member.getUserpwd(), salt));
        memberRepository.update(member);

        return;
    }

    @Override
    public void delete(Long id) {
        memberRepository.delete(id);

        return;
    }

    /**
     * 입력된 회원아이디로 Salt값 조회
     * --> Salt값과 입력된 비밀번호로 암호화 진행
     * --> 입력된 회원아이디와 암호화된 비밀번호르 로그인 진행!
     */
    @Override
    public Member login(String username, String userpwd) throws NoSuchAlgorithmException {
        String salt = memberRepository.selectByName(username).getSalt();
        userpwd = sha256Util.sha256(userpwd, salt);
        Member member = memberRepository.selectByNamePwd(username, userpwd);

        return member;
    }

    @Override
    public Member getMemberById(Long id) {
        Member member = memberRepository.selectById(id);
        return member;
    }

    @Override
    public void auth(Long id) {
        memberRepository.updateAuth(id);
        return;
    }
}
