package pt.ul.fc.css.democracia2.webcontrollers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.ThemeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AddLawProjectController {

    @Autowired
    private LawProjectService lawProjectService;

    @Autowired
    private ThemeService themeService;

    @GetMapping("/add-lawproject")
    public String addLawProject(Model model, HttpSession session) {
        Delegate delegate = (Delegate) session.getAttribute("delegate");
        String delegateName = delegate.getFullName();
        model.addAttribute("delegateName", delegateName);
        model.addAttribute("themes", themeService.findAll());
        return "add-lawproject";
    }

    @PostMapping("/add-lawproject")
    public String addLawProject(Model model, HttpSession session, @RequestParam("title") String title,
            @RequestParam("description") String description, @RequestParam("theme") String theme,
            @RequestParam("expiration") String expiration) {
        Delegate delegate = (Delegate) session.getAttribute("delegate");

        Theme selectedTheme = themeService.getThemeByName(theme);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.parse(expiration);

        try {
            lawProjectService.verifyAndCreateLawProject(title, description, startDate, endDate, selectedTheme,
                    delegate);
            model.addAttribute("successMessage", "Law project successfully added!");
            return "redirect:/delegateDashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Failed to add law project. Please check your input and try again.");
            return "add-lawproject";
        }
    }

}
