package hello.servlet.web.frontcontroller.v5;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    // poinst url 요청과 핸들러를 매핑
    public void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    // 어댑터는 핸들러의 타입을 검사한뒤
    // 필요한 어댑터들을 handlerAdapters 에 추가 현재는 v3 어댑터만 추가 되어 있음
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("서비스 함수 실행 확인 !!!!!!!!!!!!!!!!");
        // 요청 URI 에 매칭되는 컨트롤러를 첮아 와라 at handlerMappingMap
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        // 핸들러에 해당하는 어댑터를 찾아라
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        // 어댑트의 hansdle 함수를 호출해서 mv 객체를 리턴 받은뒤
        ModelView mv = adapter.handle(request, response, handler);
        // mv 객체에서 viewName 을 얻고
        String viewName = mv.getViewName();
        // MyView 객체로 만든뒤
        MyView view = viewResolver(viewName);
        // render 함수 호출
        view.render(mv.getModel(), request, response);
    }


    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 어댑터들중에 핸들러를 지원하는 어댑터를 리턴
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler idapter를 찾을수 없습니다. handler" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
