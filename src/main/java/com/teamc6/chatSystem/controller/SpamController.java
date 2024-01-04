package com.teamc6.chatSystem.controller;

import com.teamc6.chatSystem.entity.ReportSpam;
import com.teamc6.chatSystem.service.ReportSpamService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/spams")
@AllArgsConstructor
public class SpamController {
    ReportSpamService reportSpamService;

    @GetMapping()
    public Page<ReportSpam> findAllSpam(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int perPage) {

        Pageable pageable = PageRequest.of(page, perPage);

        return reportSpamService.findAll(pageable);
    }
}
