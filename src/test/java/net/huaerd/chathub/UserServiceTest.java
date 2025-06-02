package net.huaerd.chathub;

import net.huaerd.ChatHubApplication;
import net.huaerd.model.entity.Channel;
import net.huaerd.repository.MemberRepository;
import net.huaerd.service.ChannelServiceImpl;
import net.huaerd.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ChatHubApplication.class)
@AutoConfigureMockMvc
class UserServiceTest {
//    @Autowired
//    private UserService service;
//    @Test
//    @Transactional
//    void search(){
//        User user = service.getLoginInfo("3021169141@qq.com","1");
//        System.out.println(user);
//        User user2 = service.getUser(1);
//        System.out.println(user2);
//    }


    @Autowired
    private ChannelServiceImpl channelService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void ChannelTest(){
        channelService.joinChannel(3,1);
    }

}
