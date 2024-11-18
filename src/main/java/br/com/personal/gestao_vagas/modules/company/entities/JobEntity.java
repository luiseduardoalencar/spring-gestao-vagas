package br.com.personal.gestao_vagas.modules.company.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Vaga para design")
    private String description;

    @Schema(example = "Gympass, plano de saúde")
    private String benefits;

    @NotBlank(message = "Esse campo é obrigatório")
    @Schema(example = "SENIOR")
    private String level;

    @ManyToOne()
    @JoinColumn(name = "company_id", insertable=false, updatable=false)
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
