package apollo.clients.controller.admin.sale;

import apollo.clients.controller.admin.AdminController;
import apollo.clients.dto.shipper.OrderDeliveryDTO;
import apollo.clients.service.sale.OrderService;
import apollo.clients.service.shipper.ShipperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard/sales/order")
public class OrderManagerCtrl {

    private static final String orderDirect = "admin/pages/sale/orders/";

    private final OrderService orderService;
    private final ShipperService shipperService;

    public OrderManagerCtrl(OrderService orderService, ShipperService shipperService) {
        this.orderService = orderService;
        this.shipperService = shipperService;
    }

    @GetMapping()
    public String index(Model model) {
        String[] breadcrumb = { "Catalog", "Orders" };
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("order_pending", shipperService.getAllShopOrdersByStatus("PENDING"));
        return AdminController.renderView(model, orderDirect + "index", "Orders", breadcrumb);
    }

    @GetMapping("/cancel")
    public String cancel(Model model) {
        String[] breadcrumb = { "Admin Dashboard" };
        List<Map<String, Object>> orders = shipperService.getAllOrderDeliveriesByStatus("CANCEL");
        model.addAttribute("orders", orders);
        return AdminController.renderView(model, orderDirect + "cancel", "Orders Cancel", breadcrumb);
    }

    @PostMapping("/submit/{orderId}")
    public String submit(@PathVariable Long orderId) {
        shipperService.acceptOrder(orderId);
        return "redirect:/dashboard/sales/order";
    }

    @GetMapping("/detail-order/{orderId}")
    public String detailOrder(Model model, @PathVariable("orderId") Long orderId, HttpSession session) {
        String[] breadcrumb = { "Admin Dashboard" };
        Map<String, Object> order = shipperService.getShopOrderById(orderId);
        model.addAttribute("order", order);
        return AdminController.renderView(model, orderDirect + "detailorder", "Detail", breadcrumb);
    }

    @GetMapping("/detail-completed/{orderId}")
    public String detailCompleted(@PathVariable Long orderId, Model model, HttpSession session) {
        String[] breadcrumb = { "Admin Dashboard" };
        OrderDeliveryDTO orderDelivery = shipperService.getOrderDeliveryById(orderId);
        if (orderDelivery == null || orderDelivery.getOrder() == null || orderDelivery.getOrder().getOrderDate() == null) {
            model.addAttribute("error", "Order delivery or order date not found.");
            return AdminController.renderView(model, "shipper/pages/error", "Error", breadcrumb);
        }
        model.addAttribute("orderDelivery", orderDelivery);
        return AdminController.renderView(model, "shipper/pages/details/detailcompleted", "Delivery Detail", breadcrumb);
    }
}
