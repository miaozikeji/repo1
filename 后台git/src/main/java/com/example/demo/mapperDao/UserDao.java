package com.example.demo.mapperDao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserDao {
    public List<User> selectusers();
    public User selectUserByPhone(String phone);
    public void updateuser(User user);
}
