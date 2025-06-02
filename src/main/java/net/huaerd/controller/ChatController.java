package net.huaerd.controller;


import net.huaerd.model.entity.Channel;
import net.huaerd.model.entity.ChannelMessage;
import net.huaerd.model.entity.User;
import net.huaerd.service.dao.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/channels/{userid}")
    public List<Channel> getUserChannels(@PathVariable long userid) {
        return channelService.getUserChannels(userid);
    }


    /**
     * 获取历史消息
     * @param channel_id 频道id
     * @return 返回消息表
     */
    @GetMapping("/history/{channel_id}")
    public List<ChannelMessage> getHistory(@PathVariable long channel_id) {
        return channelService.history(channel_id);
    }

    //加入频道
    @PostMapping("/join")
    public void joinChannel(long channel_id,long user_id) {
        channelService.joinChannel(channel_id,user_id);
    }
    //创建频道
    @PostMapping("/create")
    public Channel createChannel(String name,long user_id) {
        return channelService.createChannel(name,user_id);
    }
}
