package br.com.personal.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "fulanodetal")
    private String username;

    @Schema(example = "Desenvolvedora Java")
    private String description;

    @Schema(example = "fulano@fulano@gmail.com")
    private String email;
    private UUID id;

    @Schema(example = "fulano de Tal")
    private String name;

}
