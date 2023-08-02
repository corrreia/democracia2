/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.entities.Vote;
import pt.ul.fc.css.democracia2.enums.FinishState;
import pt.ul.fc.css.democracia2.enums.VoteType;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.LawProjectRepository;
import pt.ul.fc.css.democracia2.repositories.LawProposalRepository;

@Service
public class LawProjectService {

    private final int NUMBER_OF_SIGNATURES_TO_APPROVE = 10000;
    private final int MAX_NUMBER_OF_MONTHS = 12;

    private final LawProjectRepository lawProjectRepository;

    private LawProposalRepository lawProposalRepository;

    private final CitizenRepository citizenRepository;

    LawProjectService(LawProjectRepository lawProjectRepository, CitizenRepository citizenRepository,
            LawProposalRepository lawProposalRepository) {
        this.lawProjectRepository = lawProjectRepository;
        this.citizenRepository = citizenRepository;
        this.lawProposalRepository = lawProposalRepository;
    }

    // herper for use case E
    /*
     * Verifies if the law project is valid and creates it, saving it to the
     * database
     * 
     * @param title the title of the law project
     * 
     * @param description the description of the law project
     * 
     * @param projectContent the content of the law project (PDF)
     * 
     * @param startDate the start date of the law project
     * 
     * @param endDate the end date of the law project
     * 
     * @param theme the theme of the law project
     * 
     * @param delegate the delegate that created the law project
     */
    public LawProject verifyAndCreateLawProject(
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Theme theme, Delegate delegate) {

        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty");

        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description cannot be null or empty");

        if (startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if (endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if (theme == null)
            throw new IllegalArgumentException("Theme cannot be null");

        if (delegate == null)
            throw new IllegalArgumentException("Delegate cannot be null");

        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("Start date cannot be after end date");

        // difference between start date and end date can not be more than 12 months
        if (startDate.plusMonths(MAX_NUMBER_OF_MONTHS).isBefore(endDate))
            throw new IllegalArgumentException("Start date cannot be more than 12 months before end date");

        LawProject lawProject = new LawProject(title, description, startDate, endDate, theme, delegate);
        return lawProjectRepository.save(lawProject);

    }

    // use case G
    /*
     * Returns a list of all the law projects that are in progress
     * 
     * @return a list of all the law projects that are in progress
     */
    public List<LawProject> findAllActive() {
        return lawProjectRepository.findAllByFinishState(FinishState.IN_PROGRESS);
    }

    // Use case F
    /*
     * Runs every minute to check if any law project has expired and if so, it
     * changes its state to rejected
     */
    @Scheduled(fixedRate = 60000) // 1 minute so we don't overload the server
    public void closeExpiredLawProjects() {
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        expiredLawProjects.forEach(lawProject -> {
            lawProject.setState(FinishState.REJECTED);
        });
        lawProjectRepository.saveAll(expiredLawProjects);
    }

    // use case H
    /*
     * Signs a law project, if the number of signatures is equal or greater than
     * 10000, the law project is approved and converted to a law proposal
     * 
     * @param lawProjectId the id of the law project to be signed
     * 
     * @param citizenGovId the government id of the citizen that is signing the
     * 
     * @return the law project that was signed
     */
    public LawProject signLawProject(Long lawProjectId, String citizenGovId) {

        LawProject lawProject = lawProjectRepository.findById(lawProjectId)
                .orElseThrow(() -> new IllegalArgumentException("Law project not found"));

        Citizen citizen = citizenRepository.findByGovernmentId(citizenGovId);

        if (citizen == null)
            throw new IllegalArgumentException("Citizen not found");

        if (lawProject.getState() != FinishState.IN_PROGRESS)
            throw new IllegalArgumentException("Law project is already finished");

        Set<Citizen> signers = lawProject.getSigners();

        if (!signers.add(citizen))
            throw new IllegalArgumentException("Citizen already signed this law project");

        if (signers.size() >= NUMBER_OF_SIGNATURES_TO_APPROVE) {
            lawProject.setState(FinishState.APPROVED);
            convertProjectToProposal(lawProject);
        }

        lawProject.setSigners(signers);
        return lawProjectRepository.save(lawProject);
    }

    // helper for use case H
    /*
     * Converts a law project to a law proposal
     * 
     * @param lawProject the law project to be converted
     * 
     * @return the law proposal that was created
     */
    private void convertProjectToProposal(LawProject lawProject) {
        LocalDate endDate;
        if (lawProject.getEndDate().isBefore(LocalDate.now().plusDays(15))) {
            endDate = LocalDate.now().plusDays(15);
        } else if (lawProject.getEndDate().isBefore(LocalDate.now().plusMonths(2))) {
            endDate = lawProject.getEndDate();
        } else {
            endDate = LocalDate.now().plusMonths(2);
        }

        LawProposal lawProposal = new LawProposal(lawProject.getTitle(), lawProject.getDescription(), LocalDate.now(),
                endDate, lawProject.getTheme(), lawProject);

        lawProposal.addVote(new Vote(lawProposal, lawProject.getDelegate(), VoteType.APPROVE));
        lawProposalRepository.save(lawProposal);
    }

    /*
     * Returns the number of signatures of a law project
     * 
     * @param lawProjectId the id of the law project
     * 
     * @return the number of signatures of a law project
     */
    public int signatureCount(Long lawProjectId) {
        LawProject lawProject = lawProjectRepository.findById(lawProjectId).orElseThrow();
        return lawProject.getSigners().size();
    }

    /*
     * Returns a law project by its id
     * 
     * @param lawProjectId the id of the law project
     * 
     * @return the law project with the given id
     */
    public LawProject findLawProjectById(Long lawProjectId) {
        return lawProjectRepository.findById(lawProjectId).orElseThrow();
    }

}
