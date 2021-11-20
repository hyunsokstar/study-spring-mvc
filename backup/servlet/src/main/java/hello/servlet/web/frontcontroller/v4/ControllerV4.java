package hello.servlet.web.frontcontroller.v4;
import java.util.Map;

// 아래의 인터페이스를 구현해서 컨트롤러를 구현
public interface ControllerV4 {
    /**
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}