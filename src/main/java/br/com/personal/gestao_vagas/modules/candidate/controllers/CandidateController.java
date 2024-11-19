package br.com.personal.gestao_vagas.modules.candidate.controllers;


import br.com.personal.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.personal.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.personal.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.personal.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.personal.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.personal.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.personal.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "informações do candidato")
public class CandidateController {

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro do candidato", description = "Função que permite cadastrar o candidato" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = ProfileCandidateResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "User already exist")}
    )
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{

            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

/*Página pessoal do candidato*/
    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Função que permite ver as informações sobre o candidato" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = ProfileCandidateResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "User not Found")}
)

    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id");

        try{

            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Função que permite ver as vagas disponíveis através de um filtro")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {@Content(
                    array = @ArraySchema (schema = @Schema(implementation = JobEntity.class)))})
    )
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição de um candidato para uma vaga", description = "Função responsável por realizar a inscrição de um candidato a uma vaga")
    @SecurityRequirement(name =  "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob){

        var idCandidate = request.getAttribute("candidate_id");

        try{

            var result = applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
