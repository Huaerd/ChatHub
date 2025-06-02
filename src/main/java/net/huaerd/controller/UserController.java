package net.huaerd.controller;

import net.huaerd.model.DTO.UserInfoDTO;
import net.huaerd.model.DTO.UserLoginDTO;
import net.huaerd.model.DTO.UserResisterDTO;
import net.huaerd.model.entity.User;
import net.huaerd.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserLoginDTO dto) {
        User user = userService.getLoginInfo(dto.getEmail(), dto.getPassword());
        if(user != null) {
            return ResponseEntity.ok(Map.of(
                    "uid", user.getUid(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "avatar", user.getAvatar()
            ));
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody UserResisterDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        if(userService.getUserByEmail(dto.getEmail()) == null)
        {
            User toReturn = userService.register(user);
            return ResponseEntity.ok(Map.of(
                    "uid", toReturn.getUid(),
                    "username", toReturn.getUsername(),
                    "email", toReturn.getEmail(),
                    "avatar", toReturn.getAvatar()
            ));
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/info/{userid}")
    public UserInfoDTO getUserInfo(@PathVariable("userid") long userid){
        User user = userService.getUser(userid);
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(user.getUid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setSex(user.getSex());
        dto.setAvatar(user.getAvatar());
        dto.setRegister_time(user.getRegisterTime());
        return dto;
    }
}
