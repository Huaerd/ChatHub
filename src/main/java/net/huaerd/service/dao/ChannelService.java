package net.huaerd.service.dao;

import net.huaerd.model.entity.Channel;
import net.huaerd.model.entity.ChannelMessage;

import java.util.List;

public interface ChannelService {
    Channel createChannel(String name,long owner);
    Channel getChannel(long id);
    void sendMessage(ChannelMessage message);
    void addMember(long channelId, long userId);
    List<Channel> getUserChannels(long userId);
    List<ChannelMessage> history(long channel_id);

    int isUserInChannel(long channelId, long userId);

    void joinChannel(long channelId, long userId);
}
