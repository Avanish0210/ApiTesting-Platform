package com.example.ApiTesting.scheduler;

import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CronUtils {

    public static boolean shouldRun(String cron,
                                    LocalDateTime lastRun) {

        CronExpression expression =
                CronExpression.parse(cron);

        ZonedDateTime now =
                ZonedDateTime.now();

        if (lastRun == null) {

            ZonedDateTime previous =
                    expression.next(now.minusYears(1));

            return previous != null &&
                    !previous.isAfter(now);

        }

        ZonedDateTime last =
                lastRun.atZone(
                        ZoneId.systemDefault());

        ZonedDateTime next =
                expression.next(last);

        return next != null &&
                !next.isAfter(now);
    }

    public static LocalDateTime nextRun(String cron) {

        CronExpression expression =
                CronExpression.parse(cron);

        ZonedDateTime next =
                expression.next(ZonedDateTime.now());

        return next.toLocalDateTime();
    }

}
