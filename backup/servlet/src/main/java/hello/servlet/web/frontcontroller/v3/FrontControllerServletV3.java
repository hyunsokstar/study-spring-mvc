package hello.servlet.web.frontcontroller.v3;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 키값으로 검색해서 해당 컨트롤러 객체를 가져 온다.
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //
        // 컨트롤러가 널이 아닐 경우  request 로부터 파라미터 정보를 가져와서  paramMap 객체를 생성
        Map<String, String> paramMap = createParamMap(request);

        // 각 uri 에 매칭되는 process 함수를 실행
        // 인자값은 입력 정보 <=> paramMap 이라는 객체 형식으로 넘김
        // controller.process(paramMap); 의 기능
        // 1. paramMap 으로 필요한 작업
        // 2.해당 ModelView 객체를 반환
        ModelView mv = controller.process(paramMap);

        // 뷰 이름을 얻어 오기 + 완전한 경로의 뷰 정보 가져오기 + 렌더링
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        //
        view.render(mv.getModel(), request, response);
    }

    // 함수의 목적 
    // 입력한 내용 정보를 HttpServletRequest request 에서 가져와서 
    // paramMap 에 저장 한뒤 렌더링할떄 modelToRequestAttribute(model, request); 이런식으로 설정 <=> 템플릿에서 사용하기 위해
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}