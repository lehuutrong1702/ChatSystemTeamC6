package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.repository.UserActiveSessionRepository;
import com.teamc6.chatSystem.service.UserActiveSessionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserActiveSessionServiceImpl implements UserActiveSessionService {
    private UserActiveSessionRepository userActiveSessionRepository;
    @Override
    public List<User> findUserActiveByTime(Date startTime, Date endTime) {
        return userActiveSessionRepository.listUserActive(startTime, endTime);
    }

    @Override
    public List<User> sortOrderByName(Date startTime, Date endTime) {
        List<User> users = this.findUserActiveByTime(startTime, endTime);

        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = 0; j < users.size() - i - 1; j++) {
                String userName1 = users.get(j).getUserName();
                String userName2 = users.get(j + 1).getUserName();
                if (userName1.compareTo(userName2) > 0) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }
        return users;
    }

    @Override
    public List<User> sortOrderByCreateDate(Date startTime, Date endTime) {
        List<User> users = this.findUserActiveByTime(startTime, endTime);

        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = 0; j < users.size() - i - 1; j++) {
                Date timeRegister1 = users.get(j).getTimeRegister();
                Date timeRegister2 = users.get(j + 1).getTimeRegister();
                if (timeRegister1.compareTo(timeRegister2) > 0) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }
        return users;
    }

    @Override
    public List<User> filterByName(Date startTime, Date endTime, String Name) {
        List<User> users = this.findUserActiveByTime(startTime, endTime);
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getUserName().equals(Name)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
}
