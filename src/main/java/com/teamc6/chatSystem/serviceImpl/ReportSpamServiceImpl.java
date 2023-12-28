package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.ReportSpam;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.ReportSpamRepository;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.ReportSpamService;
import com.teamc6.chatSystem.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.representer.Represent;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReportSpamServiceImpl implements ReportSpamService {
    private ReportSpamRepository reportSpamRepository;
    private UserRepository userRepository;

    @Override
    public List<ReportSpam> sortOrderByName() {
        return null;//reportSpamRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<ReportSpam> sortOrderByCreateDate() {

        return reportSpamRepository.sortByOrderByCreateDateAsc();
    }

    @Override
    public List<ReportSpam> filterByTime(Date dateStart, Date dateFinish) {
        return reportSpamRepository.filterByTime(dateStart, dateFinish);
    }

    @Override
    public List<ReportSpam> filterByUserName(String userName) {
        Optional<User> optional = userRepository.findByUsername(userName);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("User", "user name: ", userName);
        }
        return null;
    }

    @Override
    public ReportSpam save(Long userid) {
        var optional = userRepository.findById(userid);

        if(!optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("User", "username", userid);
        }
        //u.setPassword(PasswordGenerator.generatePassword());
        ReportSpam reportSpam = new ReportSpam();
        reportSpam.setTimeReport(new Date());
        reportSpam.setReportUser(optional.get());
        reportSpamRepository.saveAndFlush(reportSpam);
        return reportSpam;
    }

    @Override
    public Set<ReportSpam> getByUserid(Long userid) {
        var optional = userRepository.findById(userid);

        if(!optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("User", "username", userid);
        }
        return optional.get().getReportSpams();
    }
}
