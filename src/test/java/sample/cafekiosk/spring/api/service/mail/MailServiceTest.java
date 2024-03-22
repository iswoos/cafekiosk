package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

/*
 * 해당 테스트를 위한 클래스는 MailSendClient와 MailSendHistoryRepository 2가지 인자를 필요로한다.
 * 이 두가지 인자는 spring을 사용하면 둘 다 bean으로 넣어줄 수 있으니 @MockBean을 사용하면 되지만,
 * 스프링을 쓰지 않을 경우, Mockito익스텐션 써서 순수 Mock객체로 진행하는 법을 할 예정
 */

// 테스트가 시작될 때, Mockito를 사용해서 Mock을 만들 것임을 밝히는 어노테이션. @Mock 어노테이션 사용 시 필수로 붙여야함.
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    // MailService의 생성자를 보고, Mockito의 Mock객체로 선언된 위 2개를 주입해줌
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        // 2가지 목 객체 생성 (아래와 같이 하건, 위와 같이 @Mock으로 선언해도 됨)
//        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        // 2개의 목 객체를 가지고 있는 mailService 생성 (아래와 같이 하건, 위와 같이 @InjectMocks를 선언해도 됨)
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // any(String.class)로 써야하나, 아래와 같이 간단하게 바꿔쓸 수 있음
        // Mockito의 Spy사용 시, 아래와 같이 when절 사용불가. Spy는 실제 객체를 기반으로 만들어지기 때문.
//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
        // 위와 동일한 기능을 하나, BDD스타일에 맞게끔 하였음 실제로 Mockito를 상속받고 있으며 모든 기능이 동일하다.
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);



        // Mockito의 Spy사용 시, 아래 코드를 사용해야함
        // doReturn으로 Stubbing해준 sendEmail만 원하는 행위대로 진행이 되고, 실제 객체의 나머지 a,b,c 기능은 그대로 동작이 되었음
        // Spy => 한 객체에서 일부 기능은 실제 객체대로 동작하게 하고, 일부 기능은 Stubbing을 하고싶을 때 사용 (쓰는 빈도는 그리 많지 않고 주로 Mock을 씀)
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        // Mockito verify로 아래 mailSendHistroyRepository를 감싸는데, 이것은 1번 불릴 것이라고 지정
        // 해당 리포지토리는 save라는 행동을 하는데, 인자는 아래와 같음을 의미
        // => save라는 행위가 1번 불렸는지 검증하는 메서드임
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}