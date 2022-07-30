
package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService extends UserDetailsService {

    User getUser(Long id);
    User getUserByUsername(String username);

    List<User> showUser();

    User saveUser(User user);

    @Transactional
    void updateUser(User user, int id);

    void removeUser(int id);



}

