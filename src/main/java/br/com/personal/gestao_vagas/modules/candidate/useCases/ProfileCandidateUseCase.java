package br.com.personal.gestao_vagas.modules.candidate.useCases;

import br.com.personal.gestao_vagas.exceptions.UserNotFoundException;
import br.com.personal.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.personal.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

        public ProfileCandidateResponseDTO execute(UUID idCandidate){

            var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
                throw new UserNotFoundException();
            });


            var candidateDTO = ProfileCandidateResponseDTO.builder()
                    .description(candidate.getDescription())
                    .username(candidate.getUsername())
                    .name(candidate.getName())
                    .email(candidate.getEmail())
                    .id(candidate.getId())
                    .build();


            return candidateDTO;
        }
}
