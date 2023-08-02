/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootApplication
@EnableScheduling
public class Democracia2 {

    // private static final Logger log = LoggerFactory.getLogger(Democracia2.class);

    public static void main(String[] args) {
        SpringApplication.run(Democracia2.class, args);
    }

    @Autowired
    LawProposalService lawProposalService;
    @Autowired
    ThemeService themeService;
    @Autowired
    DelegateService delegateService;
    @Autowired
    CitizenService citizenService;
    @Autowired
    LawProjectService lawProjectService;

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {

            // Theme theme = themeService.verifyAndCreateTheme(null, "theme");

            // Delegate delegate = delegateService.registerDelegate("delegate", "surname",
            //         "2");

            //Citizen citizen = citizenService.registerCitizen("cictizen", "surname", "1");

            // lawProposalService.verifyAndCreateLawProposal("title", "description", LocalDate.now(),
            //         LocalDate.now().plusDays(20), "theme", null);

            // lawProjectService.verifyAndCreateLawProject("title", "desc",
            //         LocalDate.now(),
            //         LocalDate.now().plusDays(20), theme, delegate);

        };
    }

}
