/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.dto.LawProposalDTO;
import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.enums.VoteType;

@RestController
@RequestMapping("/api")
public class LawProposalController {

    @Autowired
    private LawProposalService lawProposalService;

    @GetMapping("/law-proposal/ongoing")
    @Transactional
    public ResponseEntity<?> getOngoingLawProposals() {
        List<LawProposal> lawProposals = lawProposalService.getActiveLawProposals();
        List<LawProposalDTO> lawProposalsDTO = new ArrayList<>();

        for (LawProposal lawProposal : lawProposals) {
            lawProposalsDTO.add(lawProposal.dtofy());
        }

        return ResponseEntity.ok().body(lawProposalsDTO);
    }

    @PostMapping("/law-proposal/vote/{id}")
    @Transactional
    public ResponseEntity<?> voteLawProposal(@PathVariable Long id, @RequestBody String lawProposalVote) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode voteJson = objectMapper.readTree(lawProposalVote);

            String citizenGov = voteJson.get("citizenGov").asText();
            VoteType vote = voteJson.get("vote").asText().equals("APROVE") ? VoteType.APPROVE
                    : VoteType.DISAPPROVE;

            LawProposal lawProposal = lawProposalService.voteOnLawProposal(citizenGov, id, vote);
            return ResponseEntity.ok(lawProposal.dtofy());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

}