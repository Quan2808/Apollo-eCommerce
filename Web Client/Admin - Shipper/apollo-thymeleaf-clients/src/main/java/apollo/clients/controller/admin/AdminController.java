package apollo.clients.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    private static final String Layout = "admin/fragments/_layout";

    public static String renderView(Model model, String viewName, String pageTitle, String[] breadcrumb) {
        model.addAttribute("view", viewName);
        model.addAttribute("title", pageTitle);
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("activeView", viewName);
        return Layout;
    }

    @GetMapping()
    public String home(Model model) {
        String[] breadcrumb = { "Dashboard" };
        return renderView(model, "admin/index", "Dashboard", breadcrumb);
    }

}
