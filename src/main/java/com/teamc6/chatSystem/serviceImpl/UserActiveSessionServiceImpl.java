package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.UserActiveSessionRepository;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.UserActiveSessionService;
import com.teamc6.chatSystem.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserActiveSessionServiceImpl implements UserActiveSessionService {
    private UserActiveSessionRepository userActiveSessionRepository;
    private UserRepository userRepository;

    @Override
    public List<UserActiveSession> getAll() {
        return userActiveSessionRepository.findAll();
    }

    @Override
    public List<UserActiveSession> getByTime(Date start, Date end) {
      return userActiveSessionRepository.listSession(start,end);
    }

    @Override
    public UserActiveSession update(Long sesionid) {


        var optional = userActiveSessionRepository.findById(sesionid);

        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("Session", "session id", sesionid);
        }

        var session = optional.get();
        optional.get().setTimeLogout(new Date());
        return userActiveSessionRepository.saveAndFlush(session);

    }
@Override
    public UserActiveSession save(Long userid) {

       ;
        var optional = userRepository.findById(userid);

        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "userid", userid);
        }
        var session = new UserActiveSession();
        session.setTimeActive(new Date());
        session.setSessionUser(optional.get());
        return userActiveSessionRepository.saveAndFlush(session);

    }
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

    @Override
    public boolean isOnline(Long userId) {
        List<UserActiveSession> list = userActiveSessionRepository.sessionOnline(userId);
        if(list.size() == 0)
            return false;
        return true;
    }
}
