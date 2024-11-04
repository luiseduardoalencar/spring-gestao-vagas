package br.com.personal.gestao_vagas.modules.company.repositories;
import br.com.personal.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

    public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID>{
        Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);
    }
