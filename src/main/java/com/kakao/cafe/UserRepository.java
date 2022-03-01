package com.kakao.cafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    private int userNum = 0;

    public User save(User user) {

        user.setUserNum(++userNum);
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    public Integer getUserNum() {
        return userNum;
    }
}
