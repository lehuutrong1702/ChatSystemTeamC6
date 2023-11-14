package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public User save(User u) {
        var optional = userRepository.findById(u.getUserId());
        if(optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("User", "user", u.getUserId());
        }
        return userRepository.saveAndFlush(u);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updatePassWord(User u, String passWord) {
        var optional = userRepository.findById(u.getUserId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", u.getUserId());
        }
        u.setPassword(passWord);
        return userRepository.saveAndFlush(u);
    }

    @Override
    public User findById(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ", ID);
        }
        return optional.get();
    }

    @Override
    public User findByUserName(String userName) {
        Optional<User> optional = userRepository.findByUsername(userName);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "User Name: ", userName);
        }
        return optional.get();
    }

    @Override
    public Boolean delete(User u) {
        Optional<User> optional = userRepository.findById(u.getUserId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ", u.getUserId());
        }
        userRepository.delete(optional.get());
        return true;
    }
}
