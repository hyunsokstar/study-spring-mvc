package hello.servlet.domain.member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    // 메 테스트 마다 저장소 초기화
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    // 레포지터리 객체 가져 오기
    MemberRepository memberRepository = MemberRepository.getInstance();

    @Test
    void save() {
        Member member = new Member("hello", 20);
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId());
        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        Member member1 = new Member("memver1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();

        //than
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(member1, member2);
    }

}