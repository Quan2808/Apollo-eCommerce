package apollo.clients.controller.admin.user;

import apollo.clients.controller.admin.AdminController;
import apollo.clients.dto.auth.AccountDTO;
import apollo.clients.service.AuthenticationService;
import apollo.clients.service.shipper.ShipperService;
import apollo.clients.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard/users")
public class UserManagerCtrl {

    private static final String usersDirect = "admin/pages/users/";
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ShipperService shipperService;

    public UserManagerCtrl(AuthenticationService authenticationService, UserService userService, ShipperService shipperService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.shipperService = shipperService;
    }

    @GetMapping("/{type}")
    public String getUsersList(@PathVariable String type, Model model) {
        String viewTitle = type.toUpperCase().charAt(0) + type.substring(1) + "s";
        String[] breadcrumb = {"Users", viewTitle};

        List<?> users = userService.getAllAccountsType(type);
        model.addAttribute("users", users);

        String viewName = switch (type.toLowerCase()) {
            case "customer" -> usersDirect + "/listing/customer";
            case "admin" -> usersDirect + "/listing/admin";
            case "shipper" -> usersDirect + "/listing/shipper";
            default -> usersDirect + "index";
        };
        model.addAttribute("userDTO", new AccountDTO());
        return AdminController.renderView(model, viewName, viewTitle, breadcrumb);
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@ModelAttribute AccountDTO adminRegisterDto, Model model) {
        try {
            Map<String, Object> result = authenticationService.registerAdmin(adminRegisterDto.getEmail(), adminRegisterDto.getPassword(), adminRegisterDto.getName());
            AccountDTO registeredAdmin = (AccountDTO) result.get("admin");
            if (registeredAdmin != null) {
                model.addAttribute("success", "Registration successful. Please login.");
                return getUsersList("admin", model);
            } else {
                model.addAttribute("error", "Registration failed: Invalid response received");
                return getUsersList("admin", model);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return getUsersList("admin", model);
        }
    }

    @GetMapping("/ordersdelivery/{shipperEmail}")
    public String getOrdersByShipperEmail(@PathVariable String shipperEmail, Model model) {
        List<Map<String, Object>> orders = shipperService.getOrdersByShipperEmail(shipperEmail);

        model.addAttribute("ordersdelivery", orders);
        model.addAttribute("shipperEmail", shipperEmail);
        String viewName = "admin/pages/users/details/showdelivery";
        String pageTitle = "Orders Delivery for " + shipperEmail;
        String[] breadcrumb = { "Users", "Orders Delivery" };
        return AdminController.renderView(model, viewName, pageTitle, breadcrumb);
    }

/*
    @GetMapping("/{type}/{id}")
    public String getUserById(@PathVariable String type, @PathVariable Long id, Model model) {
        Object user = userService.getAccountTypeById(type, id);
        model.addAttribute("user", user);
        return VIEW_PREFIX + "details";
    }
*/
}
