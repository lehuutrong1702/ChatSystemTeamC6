package com.teamc6.chatSystem.utils;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtils {
    private static  EmailUtils INSTANCE ;
    Properties props ;




    private EmailUtils() {
        //initial property

        props= new Properties();


        props.put("mail.smtp.host",EmailConfig.HOST_NAME); // smtp host
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");

        props.put("mail.smtp.starttls.enable", "true");

    }

    public void sendEmail(String receiveEmail, String text) {

        //create session (javax.mail)

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.APP_EMAIL, EmailConfig.APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            // nguoi gui
            message.setFrom(new InternetAddress(EmailConfig.APP_EMAIL));
            // nguoi nhan
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail, false));
            // set tieu de
            message.setSubject("Reset password group 108");
            //
            message.setContent( text,"text/plain");




            Transport.send(message);

            System.out.println("Message sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void sendPassword(String receiveEmail, String password) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.APP_EMAIL, EmailConfig.APP_PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            // nguoi gui
            message.setFrom(new InternetAddress(EmailConfig.APP_EMAIL));
            // nguoi nhan
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail, false));
            // set tieu de
            message.setSubject("Reset password group 108");
            //


            String text = "<html lang=\"en\">\r\n"
                    + "<head>\r\n"
                    + "    <meta charset=\"UTF-8\">\r\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                    + "    <style >\r\n"
                    + "        body {\r\n"
                    + "            font-family: Arial, Helvetica, sans-serif; \r\n"
                    + "            width: 500px;\r\n"
                    + "            border: 2px solid rgb(235, 227, 227);\r\n"
                    + "        }\r\n"
                    + "        .header{\r\n"
                    + "            color: yellow;\r\n"
                    + "            background-color: black;\r\n"
                    + "            height: 100px;\r\n"
                    + "            justify-content: center;\r\n"
                    + "            text-align: center;\r\n"
                    + "            margin: 0 auto;\r\n"
                    + "            display: flex;\r\n"
                    + "            align-items: center;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        .header_content{\r\n"
                    + "            justify-content: center;\r\n"
                    + "            margin: auto;\r\n"
                    + "            display: flex;\r\n"
                    + "            align-items: center;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        .container {\r\n"
                    + "            padding: 0 20px 0 20px;\r\n"
                    + "            display: block;\r\n"
                    + "            margin: 20px 0;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        .title{\r\n"
                    + "            margin: 0;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        .description {\r\n"
                    + "            margin: 20px 0 0 0;\r\n"
                    + "        }\r\n"
                    + "\r\n"
                    + "        .password {\r\n"
                    + "            padding: 0 0 0 10px;\r\n"
                    + "            color: rgb(223, 223, 20);\r\n"
                    + "            font-size: 30px;\r\n"
                    + "            font-weight: bold;\r\n"
                    + "            margin: 10px 0;\r\n"
                    + "        }\r\n"
                    + "    </style>\r\n"
                    + "</head>\r\n"
                    + "<body>\r\n"
                    + "    <div class=\"header\">\r\n"
                    + "        <h1 class=\"header_content\">CHAT SYSTEM C6 TEAM</h1>\r\n"
                    + "    </div>\r\n"
                    + "    <div class=\"container\">\r\n"
                    + "        <h2 class=\"title\">Reset password</h2>\r\n"
                    + "        <p class=\"description\">Your password</p>\r\n"
                    + "        <p class=\"password\">Password is: " + password +"</p>\r\n"
                    + "        <p class=\"auto_text\">This is an automated message, please do not reply. Thanks</p>\r\n"
                    + "    </div>\r\n"
                    + "</body>\r\n"
                    + "</html>";
            message.setContent( text,"text/html");




            Transport.send(message);

            System.out.println("Message sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static EmailUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailUtils();

        }
        return INSTANCE;
    }
}
