package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.ReportSpam;
import com.teamc6.chatSystem.repository.ReportSpamRepository;
import com.teamc6.chatSystem.service.ReportSpamService;

import java.util.Date;
import java.util.List;

public class ReportSpamServiceImpl implements ReportSpamService {
    private ReportSpamRepository reportSpamRepository;

    @Override
    public List<ReportSpam> sortOrderByName() {
        return null;//reportSpamRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<ReportSpam> sortOrderByCreateDate() {
        return null;//reportSpamRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<ReportSpam> filterByTime(Date dateStart, Date dateFinish) {
        return reportSpamRepository.filterByTime(dateStart, dateFinish);
    }

    @Override
    public List<ReportSpam> filterByUserName(String userName) {
        return null;
    }
}
