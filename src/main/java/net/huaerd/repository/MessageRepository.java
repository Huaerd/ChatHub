package net.huaerd.repository;

import net.huaerd.model.entity.ChannelMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChannelMessage,Long> {

    @Query(value = """
    SELECT * FROM channel_messages
    WHERE channel_id = :channelId
    ORDER BY send_time
    """, nativeQuery = true)
    List<ChannelMessage> findMessagesByChannelId(
            @Param("channelId") Long channelId
    );

}
