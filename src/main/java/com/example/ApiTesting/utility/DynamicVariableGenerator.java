package com.example.ApiTesting.utility;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Component
public class DynamicVariableGenerator {

    private final Random random = new Random();

    public String uuid() {
        return UUID.randomUUID().toString();
    }

    public String guid() {
        return UUID.randomUUID().toString();
    }

    public String timestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public String isoTimestamp() {
        return Instant.now().toString();
    }

    public String randomInt() {
        return String.valueOf(random.nextInt(100000));
    }

    public String randomBoolean() {
        return String.valueOf(random.nextBoolean());
    }

    public String randomString() {

        String chars =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {

            builder.append(
                    chars.charAt(
                            random.nextInt(chars.length())
                    )
            );
        }

        return builder.toString();
    }

    public String randomEmail() {

        return "user"
                + random.nextInt(100000)
                + "@test.com";
    }

    public String randomFirstName() {

        String[] names = {
                "John",
                "Alice",
                "David",
                "Emma",
                "Sophia",
                "Liam",
                "Noah"
        };

        return names[random.nextInt(names.length)];
    }

    public String randomLastName() {

        String[] names = {
                "Smith",
                "Brown",
                "Wilson",
                "Taylor",
                "Johnson",
                "Clark"
        };

        return names[random.nextInt(names.length)];
    }

    public String randomPhone() {

        return "9"
                + (100000000L + random.nextInt(900000000));
    }

}
