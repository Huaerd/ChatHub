package net.huaerd.repository;

import net.huaerd.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where u.email = :email")
    User findByEmail(
            @Param("email") String email);
    @Query("select u from User u where u.uid = :uid")
    User findByUid(
            @Param("uid") long uid);
}
