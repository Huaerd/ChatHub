package net.huaerd.service;

import net.huaerd.model.entity.Channel;
import net.huaerd.model.entity.ChannelMember;
import net.huaerd.model.entity.ChannelMessage;
import net.huaerd.repository.ChannelRepository;
import net.huaerd.repository.MemberRepository;
import net.huaerd.repository.MessageRepository;
import net.huaerd.service.dao.ChannelService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {


    private final ChannelRepository channelRepo;
    private final MessageRepository messageRepo;
    private final MemberRepository memberRepo;
    private final JdbcTemplate jdbcTemplate;

    public ChannelServiceImpl(ChannelRepository channelRepo, MessageRepository messageRepo, MemberRepository memberRepo, JdbcTemplate jdbcTemplate) {
        this.channelRepo = channelRepo;
        this.messageRepo = messageRepo;
        this.memberRepo = memberRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 创建频道
     * @param name 传入频道名称
     * @param owner 传入频道持有者id
     * @return 返回频道数据
     */
    @Override
    public Channel createChannel(String name,long owner) {
        Channel channel = new Channel();
        channel.setName(name);
        channel.setDescription("这是一个频道");
        channel.setMemberCount(0);
        channel.setCreateTime(LocalDateTime.now());
        channel.setOwner(owner);
        //数据库添加
        Channel to_join = channelRepo.saveAndFlush(channel);
        //创建后默认加入
        joinChannel(to_join.getId(), owner);
        return to_join;
    }


    /**
     * 获取频道数据
     * @param id 频道id
     * @return 返回json数据
     */
    @Override
    public Channel getChannel(long id) {
        return channelRepo.findById(id).orElseThrow(() -> new RuntimeException("channel not found"));
    }

    /**
     * 消息存储发送
     * @param message 传入的消息
     */
    @Override
    public void sendMessage(ChannelMessage message) {
        message.setSendTime(LocalDateTime.now());
        messageRepo.saveAndFlush(message);
    }

    @Override
    public void addMember(long channelId, long userId) {
        ChannelMember member = new ChannelMember();
        member.setChannelId(channelId);
        member.setUserId(userId);
        memberRepo.save(member);
    }

    @Override
    public List<Channel> getUserChannels(long userId) {
        return channelRepo.findChannelsByUserId(userId);
    }
    @Override
    public List<ChannelMessage> history(long channel_id) {
        return messageRepo.findMessagesByChannelId(channel_id);
    }
    @Override
    public int isUserInChannel(long channelId,long userId){
        return memberRepo.existsByChannelIdAndUserId(channelId,userId);
    }

    @Override
    public void joinChannel(long channelId, long userId){
        // 检查是否已加入
        if (memberRepo.existsByChannelIdAndUserId(userId, channelId) > 0) {
            throw new RuntimeException("用户已加入该频道");
        }
        else{
            ChannelMember member = new ChannelMember();
            member.setChannelId(channelId);
            member.setUserId(userId);
            member.setJoinTime(LocalDateTime.now());
            memberRepo.saveAndFlush(member);
            System.out.println("用户 "+userId+" 已加入 "+channelId+" 频道");
        }
    }
}
