package service;

import com.dzw.VedioApplication;
import com.dzw.entity.UserMood;
import com.dzw.service.ChatService;
import com.dzw.service.UserMoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VedioApplication.class)
@WebAppConfiguration
//@ContextConfiguration
public class TestService {

    @Autowired
    private UserMoodService userMoodService;

    @Autowired
    private ChatService chatService;

    @Test
    public void getMood() {
        UserMood userMood = userMoodService.publishMoodType(0);
    }

    @Test
    public void saveMatch() {
        String str = chatService.matchUser(1);
        System.out.println(str);
    }


}
