package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import java.util.Locale;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource ms;

    // getMessage 로 메세지 가져온뒤 예상한 값과 비교
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        System.out.println("result : " + result);
        assertThat(result).isEqualTo("안녕");   // ~ 은 ~과 같아야 한다
    }

    // 메세지가 없을 경우 NoSuchMessageException 에러가 발생 해야 한다.
    @Test
    void notFoundMessageCode() {
        /*ms.getMessage("no_code", null, null);*/
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    // get Message 시에 default 메세지 설정 가능
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    // 동적 메세지 설정 가능
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    // 국제화 적용, 한국일 경우
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    // 국제화 적용, 영어일 경우
    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

}
