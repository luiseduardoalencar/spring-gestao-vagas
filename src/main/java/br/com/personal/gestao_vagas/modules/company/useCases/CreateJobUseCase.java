package br.com.personal.gestao_vagas.modules.company.useCases;

import br.com.personal.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.personal.gestao_vagas.modules.company.entities.JobEntity;
import br.com.personal.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.personal.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity){
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(()->{
            throw new CompanyNotFoundException();
        });

        return this.jobRepository.save(jobEntity);
    }

}
