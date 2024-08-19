package apollo.clients.service.shipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import apollo.clients.dto.shipper.OrderDeliveryDTO;

@Service
public class ShipperService {

    private static final String API_URL = "http://localhost:9999/api/delivery";

    private final RestTemplate restTemplate;

    public ShipperService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void acceptOrder(Long orderId) {
        String url = API_URL + "/submit/" + orderId;
        restTemplate.postForObject(url, null, Void.class);
    }

    public void saveOrderDelivery(Long orderId, String shipperEmail) {
        String url = API_URL + "/save";
        restTemplate.postForObject(url + "?orderId={orderId}&shipperEmail={shipperEmail}", null, Void.class, orderId, shipperEmail);
    }

    public boolean changeOrderStatus(Long orderId, String newStatus, String inducement) {
        String url = API_URL + "/changestatus";
        restTemplate.postForObject(url + "?orderId={orderId}&newStatus={newStatus}&inducement={inducement}", null, Void.class, orderId, newStatus, inducement);
        return true;
    }

    public List<Map<String, Object>> getAllShopOrdersByStatus(String status) {
        List<Map<String, Object>> allOrders = restTemplate.exchange(
                API_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {}).getBody();

        List<Map<String, Object>> acceptedOrders = new ArrayList<>();
        for (Map<String, Object> order : allOrders) {
            Object orderStatus = order.get("status");
            if (orderStatus != null && orderStatus.equals(status)) {
                acceptedOrders.add(order);
            }
        }
        return acceptedOrders;
    }

    public Map<String, Object> getShopOrderById(Long orderId) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    API_URL + "/orders/" + orderId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching order: " + e.getMessage(), e);
        }
    }

    public List<Map<String, Object>> getAllOrderDeliveriesByStatus(String status) {
        List<Map<String, Object>> allOrderDeliveries = restTemplate.exchange(
                API_URL + "/orderdelivery",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {}).getBody();

        // Filter order deliveries by status
        return allOrderDeliveries.stream()
                .filter(orderDelivery -> status.equals(orderDelivery.get("status")))
                .collect(Collectors.toList());
    }

    public OrderDeliveryDTO getOrderDeliveryById(Long orderId) {
        try {
            ResponseEntity<OrderDeliveryDTO> response = restTemplate.exchange(
                    API_URL + "/orderdelivery/" + orderId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<OrderDeliveryDTO>() {});
            OrderDeliveryDTO body = response.getBody();
            if (body == null || body.getOrder() == null || body.getOrder().getOrderDate() == null) {
                throw new RuntimeException("Order delivery or order date is null for ID: " + orderId);
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching order delivery: " + e.getMessage(), e);
        }
    }


    public List<Object> getAllShippers() {
        try {
            ResponseEntity<List<Object>> response = restTemplate.exchange(
                    API_URL + "/shippers",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Object>>() {});
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching shippers: " + e.getMessage(), e);
        }
    }

    public List<Map<String, Object>> getOrdersByShipperEmail(String shipperEmail) {
        String url = API_URL + "/orderdelivery/email/" + shipperEmail;
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );
        return response.getBody();
    }
}