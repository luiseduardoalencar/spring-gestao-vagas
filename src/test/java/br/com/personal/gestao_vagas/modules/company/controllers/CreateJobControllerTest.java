package br.com.personal.gestao_vagas.modules.company.controllers;


import br.com.personal.gestao_vagas.modules.company.DTO.CreateJobDTO;
import br.com.personal.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.personal.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.personal.gestao_vagas.utils.TestUtils;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = CompanyEntity.builder()
                .description("ESCRIPTION")
                .email("email@email.com")
                .name("NAME")
                .username("company_username")
                .password("1234567890").build();

        company = companyRepository.saveAndFlush(company);

        var createdjobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdjobDTO))
                .header("Authorization",
                        TestUtils.generateToken(company.getId(),
                                "NOMESITE_@123#")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception{


        var createdjobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();


        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(createdjobDTO))
                        .header("Authorization",
                                TestUtils.generateToken(UUID.randomUUID(),
                                        "NOMESITE_@123#")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
