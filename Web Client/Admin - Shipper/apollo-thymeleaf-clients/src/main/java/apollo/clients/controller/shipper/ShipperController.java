package apollo.clients.controller.shipper;

import apollo.clients.dto.shipper.OrderDeliveryDTO;
import apollo.clients.service.shipper.ShipperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    private static final String Layout = "shipper/fragments/_layout";

    static String renderView(Model model, String viewName, String pageTitle, String[] breadcrumb) {
        model.addAttribute("view", viewName);
        model.addAttribute("title", pageTitle);
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("activeView", viewName);
        return Layout;
    }

    @GetMapping()
    public String home(Model model) {
        String[] breadcrumb = { "Shipper Dashboard" };
        return renderView(model, "shipper/index", "Dashboard", breadcrumb);
    }

    @GetMapping("/neworder")
    public String newOrder(Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        List<Map<String, Object>> orders = shipperService.getAllShopOrdersByStatus("ACCEPTED");
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("orders", orders);
        model.addAttribute("shipperEmail", shipperEmail);
        return renderView(model, "shipper/pages/catalog/neworder", "New Order", breadcrumb);
    }

    @GetMapping("/process")
    public String process(Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        List<Map<String, Object>> orderDeliveries = shipperService.getAllOrderDeliveriesByStatus("PENDING");
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("orderDeliveries", orderDeliveries);
        model.addAttribute("shipperEmail", shipperEmail);
        return renderView(model, "shipper/pages/catalog/process", "Process", breadcrumb);
    }

    @PostMapping("/acceptOrder")
    public String acceptOrder(@RequestParam Long orderId, @RequestParam String shipperEmail, Model model) {
        if (shipperEmail == null || shipperEmail.isEmpty()) {
            model.addAttribute("error", "Shipper email cannot be null or empty.");
            return "redirect:/shipper/neworder";
        }
        shipperService.saveOrderDelivery(orderId, shipperEmail);
        return "redirect:/shipper/process";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String newStatus, @RequestParam(required = false) String inducement, Model model) {
        try {
            shipperService.changeOrderStatus(orderId, newStatus, inducement);
            model.addAttribute("success", "Order status updated successfully.");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update order status: " + e.getMessage());
        }
        return "redirect:/shipper/process";
    }


    @GetMapping("/completed")
    public String completed(Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        List<Map<String, Object>> orderDeliveries = shipperService.getAllOrderDeliveriesByStatus("COMPLETED");
        List<Object> shippers = shipperService.getAllShippers();
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("orderDeliveries", orderDeliveries);
        model.addAttribute("shippers", shippers);
        model.addAttribute("shipperEmail", shipperEmail);
        return renderView(model, "shipper/pages/catalog/completed", "Completed", breadcrumb);
    }

    @GetMapping("/cancel")
    public String cancel(Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        List<Map<String, Object>> orderDeliveries = shipperService.getAllOrderDeliveriesByStatus("CANCEL");
        List<Object> shippers = shipperService.getAllShippers();
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("orderDeliveries", orderDeliveries);
        model.addAttribute("shippers", shippers);
        model.addAttribute("shipperEmail", shipperEmail);
        return renderView(model, "shipper/pages/catalog/cancel", "Cancel", breadcrumb);
    }

    @GetMapping("/detail-order/{orderId}")
    public String detailOrder(@PathVariable Long orderId, Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("shipperEmail", shipperEmail);
        Map<String, Object> order = shipperService.getShopOrderById(orderId);
        model.addAttribute("order", order);
        return renderView(model, "shipper/pages/details/detailorder", "Order Detail", breadcrumb);
    }

    @GetMapping("/detail-delivery/{orderId}")
    public String detailDelivery(@PathVariable Long orderId, Model model, HttpSession session) {
        String[] breadcrumb = { "Shipper Dashboard" };
        String shipperEmail = (String) session.getAttribute("shipperEmail");
        model.addAttribute("shipperEmail", shipperEmail);
        OrderDeliveryDTO orderDelivery = shipperService.getOrderDeliveryById(orderId);
        if (orderDelivery == null || orderDelivery.getOrder() == null || orderDelivery.getOrder().getOrderDate() == null) {
            model.addAttribute("error", "Order delivery or order date not found.");
            return renderView(model, "shipper/pages/error", "Error", breadcrumb);
        }
        model.addAttribute("orderDelivery", orderDelivery);
        model.addAttribute("orderDeliveryDTO", new OrderDeliveryDTO());
        return renderView(model, "shipper/pages/details/detaildelivery", "Delivery Details", breadcrumb);
    }


    @GetMapping("/detail-cancel/{orderId}")
    public String detailCancel(@PathVariable Long orderId, Model model, HttpSession session) {
        String[] breadcrumb = {"Shipper Dashboard"};
        return handleOrderDetails(orderId, model, breadcrumb, "Cancel Shipping Details", "shipper/pages/details/detailcancel");
    }

    @GetMapping("/detail-completed/{orderId}")
    public String detailCompleted(@PathVariable Long orderId, Model model, HttpSession session) {
        String[] breadcrumb = {"Shipper Dashboard"};
        return handleOrderDetails(orderId, model, breadcrumb, "Completed Shipping Details", "shipper/pages/details/detailcompleted");
    }

    /**
     * Handles the order details retrieval and view rendering.
     */
    private String handleOrderDetails(Long orderId, Model model, String[] breadcrumb, String viewTitle, String viewPath) {
        OrderDeliveryDTO orderDelivery = shipperService.getOrderDeliveryById(orderId);

        if (orderDelivery == null) {
            model.addAttribute("error", "Order delivery not found.");
            return renderView(model, "shipper/pages/error", "Error", breadcrumb);
        }

        String formattedDeliveryDate = getFormattedDeliveryDate(orderDelivery);
        model.addAttribute("formattedDeliveryDate", formattedDeliveryDate);

        // Add the orderDelivery object to the model
        model.addAttribute("orderDelivery", orderDelivery);

        // Render the view
        return renderView(model, viewPath, viewTitle, breadcrumb);
    }

    /**
     * Formats the delivery date from the OrderDeliveryDTO.
     */
    private String getFormattedDeliveryDate(OrderDeliveryDTO orderDelivery) {
        String deliveryDateString = orderDelivery.getOrder().getDeliveryDate();
        if (deliveryDateString != null) {
            try {
                ZonedDateTime deliveryDate = ZonedDateTime.parse(deliveryDateString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return deliveryDate.format(formatter);
            } catch (DateTimeParseException e) {
                return "Invalid delivery date format.";
            }
        }
        return "N/A";
    }

}
