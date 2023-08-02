/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.webcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.DelegateService;

@Controller
public class LoginController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private DelegateService delegateService;

    @GetMapping({"/", "/login"})
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("governmentId") String governmentId, @RequestParam("role") String role, HttpSession session) {
        try {
            if (role.equals("citizen")) {
                try {
                    Citizen citizen = citizenService.findCitizenByGovernmentId(governmentId);
                    session.setAttribute("citizen", citizen);
                    return "redirect:/citizenDashboard";
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return "redirect:/login";
                }
            } else if (role.equals("delegate")) {
                try {
                    Delegate delegate = delegateService.findDelegateByGovernmentId(governmentId);
                    session.setAttribute("delegate", delegate);
                    return "redirect:/delegateDashboard";
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return "redirect:/login";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        
        return "redirect:/login";
    }

}
