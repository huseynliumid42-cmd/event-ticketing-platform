package com.eventticketing.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendTicketEmail(
            String to,
            String ticketCode
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Your Event Ticket");
        message.setText(
                "Thank you for your purchase!\n\n" +
                        "Ticket Code: " + ticketCode
        );

        mailSender.send(message);
    }

    public void sendWaitlistPromotionEmail(
            String to,
            String eventTitle
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Ticket Available: " + eventTitle);

        message.setText(
                "Good news!\n\n" +
                        "A ticket is now available for event: "
                        + eventTitle +
                        "\n\n" +
                        "Please complete your purchase."
        );

        mailSender.send(message);
    }
}
