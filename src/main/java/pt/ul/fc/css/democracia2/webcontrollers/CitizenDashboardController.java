package pt.ul.fc.css.democracia2.webcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.LawProposalService;

@Controller
public class CitizenDashboardController {

    @Autowired
    private LawProposalService lawProposalService;

    @Autowired
    private LawProjectService lawProjectService;
    
    @GetMapping("/citizenDashboard")
    public String showCitizenDashboard(Model model) {
        // Fetch law proposals and projects data from a source
        List<LawProposal> lawProposals = lawProposalService.getActiveLawProposals();
        List<LawProject> lawProjects = lawProjectService.findAllActive();

        model.addAttribute("lawProposals", lawProposals);
        model.addAttribute("lawProjects", lawProjects);

        return "citizenDashboard";
    }
}
