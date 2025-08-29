package com.example.webhook_sql_runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Send POST request to generate webhook
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");

        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(url, request, WebhookResponse.class);

        WebhookResponse webhookResponse = response.getBody();
        if (webhookResponse == null) {
            System.out.println("No webhook response received.");
            return;
        }

        String webhookUrl = webhookResponse.getWebhook();
        String accessToken = webhookResponse.getAccessToken();

        // 2. SQL solution query (replace with your actual query)
        String finalQuery = "SELECT "
                + "p.amount AS SALARY, "
                + "CONCAT(e.first_name, ' ', e.last_name) AS NAME, "
                + "TIMESTAMPDIFF(YEAR, e.dob, p.payment_time) AS AGE, "
                + "d.department_name AS DEPARTMENT_NAME "
                + "FROM payments p "
                + "JOIN employee e ON p.emp_id = e.emp_id "
                + "JOIN department d ON e.department = d.department_id "
                + "WHERE DAY(p.payment_time) != 1 "
                + "ORDER BY p.amount DESC "
                + "LIMIT 1;";

        // 3. Prepare and send the final solution to the webhook URL
        Map<String, String> solution = new HashMap<>();
        solution.put("finalQuery", finalQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(solution, headers);

        ResponseEntity<String> submission = restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, String.class);

        System.out.println("Submission response: " + submission.getBody());
    }
}