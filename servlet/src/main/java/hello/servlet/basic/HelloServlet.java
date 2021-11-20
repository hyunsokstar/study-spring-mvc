package hello.servlet.basic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*super.service(req, resp);*/
        System.out.println("hello servlet ##############");
        System.out.println("request : " +request);
        System.out.println("response : " +response);

        // 쿼리 파라미터 조회 하기 ex) http://127.0.0.1:8080/hello?username="hyun
        String username = request.getParameter("username");
        System.out.println("username " + username);

        // 응답 헤더 설정
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // 응답 바디 설정
        response.getWriter().write("hello " +username);
    }
}