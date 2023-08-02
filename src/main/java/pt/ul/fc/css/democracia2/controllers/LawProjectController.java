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

import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.dto.LawProjectDTO;
import pt.ul.fc.css.democracia2.entities.LawProject;

@RestController
@RequestMapping("/api")
public class LawProjectController {

    @Autowired
    private LawProjectService lawProjectService;

    @GetMapping("/law-project/active")
    @Transactional
    public ResponseEntity<?> getActiveLawProjects() {
        List<LawProject> lawProjects = lawProjectService.findAllActive();
        List<LawProjectDTO> lawProjectsDTO = new ArrayList<>();

        for (LawProject lawProject : lawProjects) {
            lawProjectsDTO.add(lawProject.dtofy());
        }

        return ResponseEntity.ok().body(lawProjectsDTO);
    }

    @PostMapping("/law-project/sign/{id}")
    @Transactional
    public ResponseEntity<?> signLawProject(@PathVariable Long id, @RequestBody String citizenGov) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode voteJson = objectMapper.readTree(citizenGov);

            String citizenGovParsed = voteJson.get("citizenGov").asText();

            LawProject lawProject = lawProjectService.signLawProject(id, citizenGovParsed);
            return ResponseEntity.ok().body(lawProject.dtofy());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }
}