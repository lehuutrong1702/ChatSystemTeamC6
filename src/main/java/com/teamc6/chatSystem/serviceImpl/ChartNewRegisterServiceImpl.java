package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.ChartNewRegisterService;

import java.util.List;

public class ChartNewRegisterServiceImpl implements ChartNewRegisterService {
    private UserRepository userRepository;
    @Override
    public List<Object[]> getRegistrationCountByMonthInYear(int year) {
        return userRepository.getRegistrationCountByMonthInYear(year);
//        List<RegistrationDTO> registrationDTOList = new ArrayList<>();
//
//        for (Object[] row : data) {
//            Month month = Month.of((int) row[0]);
//            Long quantity = (Long) row[1];
//            registrationDTOList.add(new RegistrationDTO(month, quantity));
//        }

    }
}
