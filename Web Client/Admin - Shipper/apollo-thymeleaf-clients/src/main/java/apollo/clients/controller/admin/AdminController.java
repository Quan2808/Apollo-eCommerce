package apollo.clients.controller.admin;

import apollo.clients.service.sale.OrderService;
import apollo.clients.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    private final OrderService orderService;

    private final UserService userService;

    private static final String Layout = "admin/fragments/_layout";

    public AdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

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
        model.addAttribute("orders", orderService.getAllOrders());


        // Customer listing
        List<?> customer = userService.getAllAccountsType("customer");
        model.addAttribute("users", customer);

        // Shipper listing
        List<?> shipper = userService.getAllAccountsType("shipper");
        model.addAttribute("shipper", shipper);

        //Admin listing
        List<?> admin = userService.getAllAccountsType("admin");
        model.addAttribute("admin", admin);
        return renderView(model, "admin/index", "Dashboard", breadcrumb);
    }

}
