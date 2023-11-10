package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.ReportSpam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportSpamRepository extends JpaRepository<ReportSpam,Long> {
}
