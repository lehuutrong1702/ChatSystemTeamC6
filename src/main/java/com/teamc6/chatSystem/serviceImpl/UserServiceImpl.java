package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    public User save(User u) {
        var optional = userRepository.findById(u.getUserId());

        if(optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("User", "username", u.getUserId());
        }
        return userRepository.saveAndFlush(u);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Override
    public User update(User u, Long id) {
        var optional = userRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", u.getUserId());
        }
        User existUser = optional.get();
        existUser.setFullName(u.getFullName());
        existUser.setPassword(u.getPassword());
        existUser.setUserName(u.getUserName());
        existUser.setEmail(u.getEmail());
        existUser.setBirthDay(u.getBirthDay());

        return userRepository.saveAndFlush(existUser);
    }

    @Override
    public User findById(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ", ID);
        }
//        System.out.println(optional.get().getRelationships().toString());
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

    @Override
    public Boolean deleteById(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ",ID);
        }
        userRepository.delete(optional.get());
        return true;
    }


}
