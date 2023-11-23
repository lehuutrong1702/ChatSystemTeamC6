package com.teamc6.chatSystem.validation.Email;

import com.teamc6.chatSystem.validation.Validation;
import com.teamc6.chatSystem.utils.EmailRegexConfig;
import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailChecking implements Validation {
    @Override
    public boolean check(String data) {
        String regexPatternString = EmailRegexConfig.EMAIL_REGREX;
        Pattern pattern = Pattern.compile(regexPatternString);

        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }
}
