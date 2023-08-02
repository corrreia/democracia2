package pt.ul.fc.css.democracia2.dto;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.democracia2.enums.VoteType;

@Component
public class VoteDTO {

    private Long id;
    private LawProposalDTO lawProposal;
    private CitizenDTO citizen;
    private VoteType voteType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LawProposalDTO getLawProposal() {
        return lawProposal;
    }

    public void setLawProposal(LawProposalDTO lawProposal) {
        this.lawProposal = lawProposal;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

}
