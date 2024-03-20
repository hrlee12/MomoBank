package com.ssafy.community.report.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.community.report.dto.MonthlyReportDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class ReportService {

    @Value("${gpt.token}")
    String gptToken;

    public String getAIRecomendation(MonthlyReportDto monthlyReportDto) throws IOException {




        System.out.println(gptToken);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");

        // Bearer 토큰 추가

        httpPost.setHeader("Authorization", "Bearer " + gptToken);




        String system = "You are a teacher who writes cute reports based on children's group accounting and postings.";
        String assistant = "View the accounting and posting information for this group, and prepare an overall report for this group. The report format is as follows." +
                "1. Accounting" +
                "Status of unpaid fees. Praise those who paid more, and criticize those who paid less." +
                "2. Post" +
                "Analyze the ratings of posts that were popular, and write down why they were good and what the reaction was." +
                "3. Best member" +
                "The best member must first pay all fees in full, and if no one pays in full, there is no best member. Give it to someone who has paid in full and faithfully participated in the bulletin board activities. Please also tell us the reason for selecting the best member" +
                "Lastly, write the name of the best member in one line at the end." +
                "The format to write is" +
                "$!$name$!$" +
                "Please do it." +
                "I would like you to respond in Korean and send it in md format.";
        String user = "data: " + monthlyReportDto.toString();

        System.out.println( monthlyReportDto.toString());

        // POST 요청을 위한 데이터 설정 (JSON 형태로 예시)
        StringEntity postData = new StringEntity("{\n" +
                "  \"model\": \"gpt-4\",\n" +
                "  \"stream\" : false,\n" +
                "  \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"" + system + "\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"role\": \"assistant\",\n" +
                "          \"content\": \"" + assistant + "\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\" : \"user\",\n" +
                "            \"content\" : \"" + user + "\"\n" +
                "        }\n" +
                "    ]\n" +
                "}", StandardCharsets.UTF_8);
        httpPost.setEntity(postData);
        httpPost.setHeader("Accept-Charset", "UTF-8");
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpPost);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        String jsonResponse = result.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode choicesNode = rootNode.path("choices");

        if (choicesNode.isArray()) {
            return choicesNode.get(0).get("message").get("content").asText();
//            for (JsonNode choice : choicesNode) {
//                JsonNode messageNode = choice.get("message");
//                String content = messageNode.get("content").asText();
//                System.out.println(content);
//
//            }
        }
        return "error";


    }

}
