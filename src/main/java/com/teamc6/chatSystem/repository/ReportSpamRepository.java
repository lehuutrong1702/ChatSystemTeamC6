package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.ReportSpam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReportSpamRepository extends JpaRepository<ReportSpam,Long> {

    //List<ReportSpam> findAllByOrderByNameAsc();

    @Query("SELECT r FROM ReportSpam r ORDER BY r.timeReport ASC")
    List<ReportSpam> sortByOrderByCreateDateAsc();

    @Query("SELECT r FROM ReportSpam r WHERE r.timeReport BETWEEN :dateStart AND :dateFinish")
    List<ReportSpam> filterByTime(Date dateStart, Date dateFinish);

   // @Query("SELECT r FROM ReportSpam r WHERE r.groupName LIKE %:searchName%")
  //  List<GroupChat> filterName(@Param("searchName") String searchName);
}
