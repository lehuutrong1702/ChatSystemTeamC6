package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.ReportSpam;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ReportSpamService {
    List<ReportSpam> sortOrderByName();
    List<ReportSpam> sortOrderByCreateDate();
    List<ReportSpam> filterByTime(Date dateStart, Date dateFinish);
    List<ReportSpam> filterByUserName(String userName);

    ReportSpam save(Long userid);
    Set<ReportSpam> getByUserid(Long userid);
}
