/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
import pt.ul.fc.css.democracia2.repositories.ThemeMapperRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;
import pt.ul.fc.css.democracia2.repositories.VoteRepository;

@Service
public class LawProposalService {

    private final LawProposalRepository lawProposalRepository;
    private final ThemeRepository themeRepository;
    private final LawProjectRepository lawProjectRepository;
    private final ThemeMapperRepository themeMapperRepository;
    private final VoteRepository voteRepository;
    private final CitizenRepository citizenRepository;

    LawProposalService(LawProposalRepository lawProposalRepository, ThemeRepository themeRepository,
            LawProjectRepository lawProjectRepository, ThemeMapperRepository themeMapperRepository,
            VoteRepository voteRepository,
            CitizenRepository citizenRepository) {
        this.lawProposalRepository = lawProposalRepository;
        this.themeRepository = themeRepository;
        this.lawProjectRepository = lawProjectRepository;
        this.themeMapperRepository = themeMapperRepository;
        this.voteRepository = voteRepository;
        this.citizenRepository = citizenRepository;
    }

    // use case E
    /*
     * Verifies if the law proposal is valid and creates it (this will mostly be
     * used by the lawProject Service)
     * 
     * @param title the title of the law proposal
     * 
     * @param description the description of the law proposal
     * 
     * @param document the document of the law proposal
     * 
     * @param startDate the start date of the law proposal
     * 
     * @param endDate the end date of the law proposal
     * 
     * @param themeName the name of the theme of the law proposal
     * 
     * @param optionalLawProjectId the id of the law project that this law proposal
     * is associated with (can be null)
     * 
     * @return the law proposal created
     */
    public LawProposal verifyAndCreateLawProposal(String title, String description,
            LocalDate startDate, LocalDate endDate, String themeName, Long optionalLawProjectId /* can be null */) {

        LawProject lawProject = null;

        Theme theme = themeRepository.findThemeByName(themeName);

        if (optionalLawProjectId != null) {
            lawProject = lawProjectRepository.findById(optionalLawProjectId).orElseThrow(
                    () -> new IllegalArgumentException("Law project not found"));
        }

        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty");

        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description cannot be null or empty");

        if (startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if (endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if (theme == null)
            throw new IllegalArgumentException("Theme not found");

        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("Start date cannot be after end date");

        LawProposal lawProposal = new LawProposal(title, description, startDate, endDate, theme, lawProject);
        lawProposalRepository.save(lawProposal);
        return lawProposal;
    }

    // helper for use case J
    /*
     * Votes on a law proposal and saves it to the database
     * 
     * @param citizenGovId the government ID of the citizen that will vote
     * 
     * @param lawProposalId the ID of the law proposal
     * 
     * @param voteType the type of vote (will only be saved to the database if it is
     * a Delegate)
     * 
     * @return the citizen
     */
    public LawProposal voteOnLawProposal(String citizenGovId, Long lawProposalId, VoteType voteType) {

        if (citizenGovId == null || citizenGovId.isEmpty())
            throw new IllegalArgumentException("Citizen government ID cannot be empty");

        if (lawProposalId == null)
            throw new IllegalArgumentException("Law proposal ID cannot be empty");

        Citizen citizen = citizenRepository.findByGovernmentId(citizenGovId);

        LawProposal lawProposal = lawProposalRepository.findById(lawProposalId)
                .orElseThrow(() -> new IllegalArgumentException("Law proposal not found"));

        if (lawProposal.getState() != FinishState.IN_PROGRESS)
            throw new IllegalArgumentException("Law proposal is not in progress");

        Vote vote;
        if (citizen.getClass() == Delegate.class)
            vote = new Vote(lawProposal, (Delegate) citizen, voteType);
        else
            vote = new Vote(lawProposal, citizen, null);

        voteRepository.save(vote); // save the vote to the database

        lawProposal.addVote(vote);

        if (voteType == VoteType.APPROVE) {
            int votes = lawProposal.getAproveVotes();
            lawProposal.setAproveVotes(votes + 1);
        } else {
            int votes = lawProposal.getDisaproveVotes();
            lawProposal.setDisaproveVotes(votes + 1);
        }

        lawProposalRepository.save(lawProposal);

        Set<Vote> votes = citizen.getVotes();
        votes.add(vote);
        citizen.setVotes(votes);
        citizenRepository.save(citizen);
        return lawProposal;
    }

    // helper for use case D //DONE
    /*
     * Returns a list of all the law proposals that are currently active
     * 
     * @return a list of all the law proposals that are currently active
     */
    public List<LawProposal> getActiveLawProposals() {
        return lawProposalRepository.findByEndDateGreaterThan(LocalDate.now());
    }

    /*
     * Returns the law proposal with the given id
     * 
     * @param id the id of the law proposal
     * 
     * @return the law proposal with the given id
     */
    public LawProposal getLawProposalById(Long id) {
        return lawProposalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Law proposal not found"));
    }

    /*
     * Returns a list of all the law proposals
     * 
     * @return a list of all the law proposals
     */
    public List<LawProposal> getAllProposals() {
        return lawProposalRepository.findAll();
    }

    /*
     * Runs every minute to check if any law proposals have expired and if so, it
     * changes their state acoding to the number of votes it has
     *
     */
    @Scheduled(fixedRate = 60000)
    public void checkLawProposals() {
        List<LawProposal> expiredLawProposals = lawProposalRepository.findAllSurpassedInProgress(LocalDate.now());

        if (expiredLawProposals.size() > 0) {
            expiredLawProposals.forEach(lawProposal -> {
                AtomicInteger yesVotes = new AtomicInteger(lawProposal.getAproveVotes());
                AtomicInteger noVotes = new AtomicInteger(lawProposal.getDisaproveVotes());

                Set<Vote> votes = new HashSet<>(lawProposal.getVotes());

                for (Vote vote : lawProposal.getVotes()) {
                    if (vote.getCitizen().getClass() == Delegate.class) { // se o voto for de um delegado
                        Delegate delegate = (Delegate) vote.getCitizen();
                        List<Citizen> citizens = themeMapperRepository.findCitizensByDelegateAndTheme(delegate,
                                lawProposal.getTheme());
                        if (citizens.size() > 0) {
                            citizens.forEach(citizen -> {
                                Vote citizenVote = new Vote(lawProposal, citizen);
                                Citizen citizenD = citizenRepository.findById(citizen.getId()).get();
                                votes.add(citizenVote);
                                citizenD.getVotes().add(citizenVote);
                                citizenRepository.save(citizenD);
                                voteRepository.save(citizenVote);

                                if (vote.getVoteType() == VoteType.APPROVE) {
                                    yesVotes.incrementAndGet();
                                } else {
                                    noVotes.getAndIncrement();
                                }
                            });
                        }
                    }
                }
                lawProposal.setVotes(votes);
                lawProposal.setAproveVotes(yesVotes.get());
                lawProposal.setDisaproveVotes(noVotes.get());

                if (lawProposal.getAproveVotes() > lawProposal.getDisaproveVotes()) {
                    lawProposal.setState(FinishState.APPROVED);
                } else {
                    lawProposal.setState(FinishState.REJECTED);
                }
                lawProposalRepository.save(lawProposal);
            });
        }
    }
}
