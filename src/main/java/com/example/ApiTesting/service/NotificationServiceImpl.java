package com.example.ApiTesting.service;

import com.example.ApiTesting.entity.CollectionRun;
import com.example.ApiTesting.entity.NotificationConfig;
import com.example.ApiTesting.entity.Schedule;
import com.example.ApiTesting.repository.NotificationConfigRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final NotificationConfigRepository repository;
    private final EmailTemplateService emailTemplateService;


    @Override
    public void sendTestNotification(NotificationConfig config) {

        if (config.getEmail() == null ||
                config.getEmail().isBlank()) {
            return;
        }

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(config.getEmail());

            helper.setSubject("API Testing Platform");

            String html = """
                <html>
                    <body>
                        <h2>Congratulations!</h2>

                        <p>Email notification is working successfully.</p>

                        <p>Your notification configuration is correct.</p>
                    </body>
                </html>
                """;

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }
    }

    @Override
    public void sendExecutionReport(
            NotificationConfig config,
            CollectionRun run) {

        if (config.getEmail() == null ||
                config.getEmail().isBlank()) {
            return;
        }

        try {
            String html =
                    emailTemplateService
                            .buildExecutionReport(run);

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(config.getEmail());

            helper.setSubject(
                    "Collection Execution Report");


            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }

    }
    @Override
    public void notifyCollectionRun(CollectionRun run) {

        List<NotificationConfig> configs =
                repository.findByEnabledTrue();

        for (NotificationConfig config : configs) {

            try {

                sendExecutionReport(config, run);

            } catch (Exception ex) {

                log.error(
                        "Failed to send notification to {}",
                        config.getName(),
                        ex);

            }

        }
    }
}
