package com.ssafy.community.report.controller;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void collectMonthlyReportData() throws Exception {
        MvcResult result = mockMvc.perform(post("/reports/collect-monthly-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"yearMonth\":\"2023.03\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().encoding("UTF-8"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        System.out.println("결과!!!");
        System.out.println(responseBody);
    }

    @Test
    void getMonthlyReport() throws Exception {
//        mockMvc.perform(get("/reports/monthly")
//                        .param("yearMonth", "2023.03"))
//                .andExpect(status().isOk());
    }

    @Test
    void getAnnualReport() throws Exception {
//        mockMvc.perform(get("/reports/annual")
//                        .param("year", "2023"))
//                .andExpect(status().isOk());
    }

    @Test
    void getMonthlyFeeChanges() throws Exception {
//        mockMvc.perform(get("/reports/fee-changes")
//                        .param("yearMonth", "2023.03"))
//                .andExpect(status().isOk());
    }

    @Test
    void getActivityReport() throws Exception {
//        mockMvc.perform(get("/reports/activity-report")
//                        .param("yearMonth", "2023.03"))
//                .andExpect(status().isOk());
    }

    @Test
    void getFeeStatus() throws Exception {
//        mockMvc.perform(get("/reports/fee-status")
//                        .param("yearMonth", "2023.03"))
//                .andExpect(status().isOk());
    }

    @Test
    void getTopMembers() throws Exception {
//        mockMvc.perform(get("/reports/top-members")
//                        .param("startMonth", "2023.01")
//                        .param("endMonth", "2023.03"))
//                .andExpect(status().isOk());
    }

    @Test
    void recommendAccount() throws Exception {
//        mockMvc.perform(post("/reports/recommend-account")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"answers\":\"Some answers\"}"))
//                .andExpect(status().isOk());
    }

    @Test
    void recommendNextActivity() throws Exception {
//        mockMvc.perform(post("/reports/recommend-next-activity")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"monthlyData\":\"Some data\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("여기에 다음 활동 추천 결과"));
    }
}