package hello.servlet.web.frontcontroller.v4.controller;
import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import java.util.Map;


public class MemberSaveControllerV4 implements ControllerV4 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        // 입력 받은 정보를 paramMap.get() 으로 얻어와 초기화 한뒤
        // memberRepository.save(member); 으로 저장
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member = new Member(username, age);
        memberRepository.save(member);

        // 여기서 설정한 모델 객체를
        // src\main\java\hello\servlet\web\frontcontroller\v4\FrontControllerServletV4.java 에서
        // 다음과 같이 활용 view.render(model, request, response);
        model.put("member", member);

        return "save-result";
    }
}
