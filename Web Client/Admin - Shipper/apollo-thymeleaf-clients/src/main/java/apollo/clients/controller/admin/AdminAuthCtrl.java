package apollo.clients.controller.admin;

import apollo.clients.dto.auth.AccountDTO;
import apollo.clients.dto.auth.TokenDTO;
import apollo.clients.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class AdminAuthCtrl {
    private static final String authDirect = "admin/pages/auth/";
    private final AuthenticationService service;

    public AdminAuthCtrl(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/signin")
    public String loginForm(Model model, HttpServletRequest request) {
        String authParam = request.getParameter("auth");
        if (!"true".equals(authParam)) {
            return "redirect:/";
        }
        model.addAttribute("user", new AccountDTO());
        return authDirect + "login";
    }

    @PostMapping("/submit-signin")
    public String loginAdmin(@ModelAttribute AccountDTO adminLoginDto, Model model, HttpSession session) {
        try {
            Map<String, Object> result = service.loginAdmin(adminLoginDto.getEmail(), adminLoginDto.getPassword());
            TokenDTO jwtResponse = (TokenDTO) result.get("token");
            if (jwtResponse != null && jwtResponse.getAccessToken() != null) {
                session.setAttribute("jwtToken", jwtResponse.getAccessToken());
                session.setAttribute("role", "ADMIN"); // Set role to ADMIN
                model.addAttribute("jwtResponse", jwtResponse);
                return "redirect:/dashboard";
            } else {
                model.addAttribute("error", "Login failed: Invalid JWT token received");
                return loginForm(model, null);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return loginForm(model, null);
        }
    }

    @GetMapping("/logout")
    public String logoutAdmin(HttpSession session, Model model) {
        try {
            session.invalidate();
            return "redirect:/dashboard/signin?auth=true";
        } catch (Exception e) {
            model.addAttribute("error", "Logout failed: " + e.getMessage());
            return "redirect:/dashboard/signin?auth=true";
        }
    }
}
