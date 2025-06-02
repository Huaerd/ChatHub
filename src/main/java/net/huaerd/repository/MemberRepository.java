package net.huaerd.repository;

import net.huaerd.model.entity.ChannelMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<ChannelMember,Long> {
    @Query(value = """
    SELECT COUNT(*) > 0 FROM channel_members
    WHERE channel_id = :channelId 
    AND user_id = :userId
    """, nativeQuery = true)
    int existsByChannelIdAndUserId(@Param("channelId") long channelId, @Param("userId") long userId);
}
