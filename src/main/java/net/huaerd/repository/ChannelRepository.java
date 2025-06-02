package net.huaerd.repository;

import net.huaerd.model.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long>{

    Optional<Channel> findById(long id);

    @Modifying(flushAutomatically = true,clearAutomatically = true)
    @Query(value = "INSERT INTO channels (name) VALUES (:name)",nativeQuery = true)
    void insert(@Param("name") String name);

    @Query(value = """
    SELECT c.*
    FROM channels c
    JOIN channel_members m ON c.id = m.channel_id
    WHERE m.user_id = :userId
    """, nativeQuery = true)
    List<Channel> findChannelsByUserId(@Param("userId") long userId);


}
