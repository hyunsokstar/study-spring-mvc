package hello.servlet.domain.member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 회원 데이터를저장할곳
    private static long sequence = 0l; // 자동 증가 시킬 id

    // 단, repository 객체는 생성자를 통해 접ㅈ근할수 있도록 하기
    private static final MemberRepository instance = new MemberRepository();
    public static MemberRepository getInstance() {
        return instance;
    }

    // 빈 생성자 함수도 만들어 놓기 
    private MemberRepository() {
    }
    
    //  회원 정보 저장
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // 회원 정보 id로 찾기
    public Member findById(Long id) {
        return store.get(id);
    }

    // 회원 리스트 조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 회원 정보 삭제
    public void clearStore() {
        store.clear();
    }

}
