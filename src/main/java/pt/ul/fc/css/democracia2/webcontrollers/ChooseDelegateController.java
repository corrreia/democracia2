package pt.ul.fc.css.democracia2.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.ThemeMapperService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@Controller
public class ChooseDelegateController {
    
    @Autowired
    private ThemeMapperService mapperService;

    @Autowired
    private DelegateService delegateService;

    @Autowired
    private ThemeService themeService;

    @GetMapping("/chooseDelegate")
    public String showChooseDelegate(Model model) {
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("delegates", delegateService.findAll());
        return "chooseDelegate";
    }
    
}
