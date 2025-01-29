package org.launchcode.demo;

import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public abstract class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String createUser(String username, String password){
        User user = User(username, )
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities("ROLE_USER")
                .build();
        userRepository.save(user);
        return "Account Created!";
    }

}
