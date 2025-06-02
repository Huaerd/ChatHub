package net.huaerd.service;

import lombok.RequiredArgsConstructor;
import net.huaerd.model.entity.User;
import net.huaerd.repository.UserRepository;
import net.huaerd.service.dao.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    //获取登录数据
    @Override
    public User getLoginInfo(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            // 只返回必要的基础字段
            User toReturn = new User();
            toReturn.setUid(user.getUid());
            toReturn.setUsername(user.getUsername());
            toReturn.setEmail(user.getEmail());
            toReturn.setAvatar(user.getAvatar());
            return toReturn;
        }
        return null;
    }

    @Override
    public User getUser(long uid) {
        return userRepo.findByUid(uid);
    }

    @Override
    public User register(User user) {
        return userRepo.saveAndFlush(user);
    }
    @Override
    public User getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
}