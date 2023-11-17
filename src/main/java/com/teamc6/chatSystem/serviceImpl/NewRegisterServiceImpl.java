package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.NewRegisterService;

import java.util.Date;
import java.util.List;

public class NewRegisterServiceImpl implements NewRegisterService {
    private UserRepository userRepository;
    @Override
    public List<User> findAllUser(Date dateStart, Date dateFinish) {
        return userRepository.filterByTime(dateStart, dateFinish);
    }

    @Override
    public List<User> sortOrderByName(Date dateStart, Date dateFinish) {
        return userRepository.sortOrderByNameAscNewRegister(dateStart, dateFinish);
    }

    @Override
    public List<User> sortOrderByCreateDate(Date dateStart, Date dateFinish) {
        return userRepository.sortOrderByCreateDateAscNewRegister(dateStart, dateFinish);
    }

    @Override
    public List<User> filterByName(Date dateStart, Date dateFinish, String name) {
        return userRepository.filterByNameNewRegister(dateStart, dateFinish, name);
    }
}
