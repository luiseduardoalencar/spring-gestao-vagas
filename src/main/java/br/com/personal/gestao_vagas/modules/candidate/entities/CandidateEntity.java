package br.com.personal.gestao_vagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "candidate")
@Data
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Daniel de Souza")
    private String name;


    @Pattern(regexp = "\\S+", message = "O campo [usename] não deve conter espaços")
    @Schema(example = "Daniel_souza")
    private String username;

    @Email(message = "O campo deve conter um Email Válido")
    @Schema(example = "Daniel@daniel.com")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres")
    @Schema(example = "admin@123", minLength = 10, maxLength = 100)
    private String password;

    @Schema(example = "Desenvolvedor java")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
