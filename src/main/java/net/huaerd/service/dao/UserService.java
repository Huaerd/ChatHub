package net.huaerd.service.dao;

import net.huaerd.model.entity.User;

public interface UserService {
    User getLoginInfo(String username, String password);
    User getUser(long uid);
    User getUserByEmail(String Email);
    User register(User user);
}
