package apollo.clients.controller.shipper;

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
@RequestMapping("/shipper")
public class ShipperAuthCtrl {
    private static final String authDirect = "shipper/pages/auth/";
    private final AuthenticationService service;

    public ShipperAuthCtrl(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/signin")
    public String loginForm(Model model, HttpServletRequest request) {
        String authParam = request.getParameter("auth");
        if (!"true".equals(authParam)) {
            return "redirect:/";
        }
        model.addAttribute("shipper", new AccountDTO());
        return authDirect + "login";
    }

    @PostMapping("/submit-login")
    public String loginShipper(@ModelAttribute AccountDTO shipperLoginDto, Model model, HttpSession session) {
        try {
            Map<String, Object> result = service.loginShipper(shipperLoginDto.getEmail(), shipperLoginDto.getPassword());
            TokenDTO jwtResponse = (TokenDTO) result.get("token");
            if (jwtResponse != null && jwtResponse.getAccessToken() != null) {
                session.setAttribute("jwtToken", jwtResponse.getAccessToken());
                session.setAttribute("role", "SHIPPER"); // Set role to SHIPPER
                session.setAttribute("shipperEmail", shipperLoginDto.getEmail()); // Store shipper email in session
                model.addAttribute("jwtResponse", jwtResponse);
                return "redirect:/shipper/neworder";
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
    public String logoutShipper(HttpSession session, Model model) {
        try {
            session.invalidate();
            return "redirect:/shipper/signin?auth=true";
        } catch (Exception e) {
            model.addAttribute("error", "Logout failed: " + e.getMessage());
            return "redirect:/shipper/signin?auth=true";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpServletRequest request) {
        String authParam = request.getParameter("auth");
        if (!"true".equals(authParam)) {
            return "redirect:/";
        }
        model.addAttribute("shipper", new AccountDTO());
        return authDirect + "register";
    }

    @PostMapping("/submit-register")
    public String registerShipper(@ModelAttribute AccountDTO shipperRegisterDto, Model model) {
        try {
            Map<String, Object> result = service.registerShipper(
                    shipperRegisterDto.getEmail(),
                    shipperRegisterDto.getPassword(),
                    shipperRegisterDto.getShipperName(),
                    shipperRegisterDto.getPhoneNumber()
            );

            AccountDTO registeredShipper = (AccountDTO) result.get("shipper");

            if (registeredShipper != null) {
                model.addAttribute("success", "Registration successful. Please login.");
                return "redirect:/shipper/register?success=true"; // Redirect with query parameter
            } else {
                model.addAttribute("error", "Registration failed: Invalid response received");
                return "redirect:/shipper/register?error=true"; // Redirect with query parameter
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/shipper/register?error=true"; // Redirect with query parameter
        }
    }

}
