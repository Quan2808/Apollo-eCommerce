import 'package:apollodeliver/Screens/MakeColor/Notification/LoadingScreen.dart';
import 'package:apollodeliver/Screens/MakeColor/Notification/OrderCompleteScreen.dart';
import 'package:flutter/material.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';
import 'package:intl/intl.dart';

class OrderDetailScreen extends StatefulWidget {
  final int orderId;
  final String token;
  final String email;

  OrderDetailScreen(
      {required this.orderId, required this.token, required this.email});

  @override
  _OrderDetailScreenState createState() => _OrderDetailScreenState();
}

class _OrderDetailScreenState extends State<OrderDetailScreen> {
  ShopOrder? order;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    _fetchOrderDetail();
  }

  void _fetchOrderDetail() async {
    try {
      DeliveryService service = DeliveryService(widget.token);
      Map<String, dynamic> orderData =
          await service.getOrderById(widget.orderId);
      setState(() {
        order = ShopOrder.fromJson(orderData);
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      print("Failed to load order details: $e");
    }
  }

  void _acceptOrder() async {
    try {
      setState(() {
        isLoading = true;
      });

      await Future.delayed(Duration(seconds: 2));

      DeliveryService service = DeliveryService(widget.token);
      await service.acceptOrder(order!.id!, widget.email);

      setState(() {
        isLoading = false;
      });

      Navigator.pushReplacement(
        context,
        MaterialPageRoute(
          builder: (context) => OrderCompleteScreen(token: widget.token, email: widget.email),
        ),
      );
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      print("Failed to accept order: $e");
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to accept order')));
    }
  }

  String formatDate(DateTime dateTime) {
    return DateFormat('dd/MM/yyyy').format(dateTime);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Order Details'),
        backgroundColor: Color(0xFF7357a4),
      ),
      body: isLoading
          ? LoadingScreen(
        message: 'Received Order....',
      )
          : order == null
          ? Center(child: Text('Order not found'))
          : SingleChildScrollView(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            buildOrderCard(),
            SizedBox(height: 16),
            buildCustomerCard(),
            SizedBox(height: 16),
            buildProductCard(),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _acceptOrder,
              style: ElevatedButton.styleFrom(
                backgroundColor: Color(0xFF7B68EE),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8.0),
                ),
                minimumSize: Size(double.infinity, 48),
                elevation: 6,
              ),
              child: Text('Receive Order',
                  style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold)),
            ),
          ],
        ),
      ),
      backgroundColor: Color(0xFFF3E5F5),
    );
  }

  Widget buildOrderCard() {
    return Card(
      elevation: 6,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Order Details',
                style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.white)),
            SizedBox(height: 8),
            buildDetailRow(
                Icons.confirmation_number, 'Order ID', '${order?.id}'),
            buildDetailRow(
                Icons.calendar_today, 'Order Date', '${(order!.orderDate)}'),
            buildDetailRow(Icons.local_offer, 'Status', '${order!.status}'),
            buildDetailRow(Icons.attach_money, 'Order Total',
                '\$${order!.orderTotal?.toStringAsFixed(2)}'),
          ],
        ),
      ),
    );
  }

  Widget buildCustomerCard() {
    return Card(
      elevation: 6,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Customer Information',
                style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.white)),
            SizedBox(height: 8),
            buildDetailRow(Icons.person, 'Name', '${order!.user.clientName}'),
            buildDetailRow(Icons.phone, 'Phone Number',
                '${order!.user.phoneNumber ?? "N/A"}'),
            buildDetailRow(
                Icons.location_on, 'Address', '${order!.address.street}'),
          ],
        ),
      ),
    );
  }

  Widget buildProductCard() {
    final imageUrl =
        order?.variant.img ?? 'https://via.placeholder.com/150'; // Fallback URL
    final productName = order?.variant.name ?? 'No Name';
    final price = order?.variant.price ?? 0.00;
    final quantity = order?.quantity ?? 0;

    return Card(
      elevation: 6,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          children: [
            Container(
              width: 100,
              height: 100,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(8),
                image: DecorationImage(
                  image: NetworkImage(imageUrl),
                  fit: BoxFit.cover,
                ),
              ),
            ),
            SizedBox(width: 10),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    productName,
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                    overflow: TextOverflow.ellipsis,
                  ),
                  SizedBox(height: 5),
                  Text(
                    'Price: \$$price',
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.white,
                    ),
                  ),
                  SizedBox(height: 5),
                  Text(
                    'Quantity: $quantity',
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.white,
                    ),
                  ),
                  SizedBox(height: 5),
                  Text(
                    'Default Payment: ${order!.paymentMethod.defaultPayment != null ? "Yes" : "No"}',
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.white,
                    ),
                  ),
                  SizedBox(height: 5),
                  Text(
                    'Shipping Method: ${order!.shippingMethod.name}',
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.white,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildDetailRow(IconData icon, String title, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: Row(
        children: [
          Icon(icon, size: 20, color: Colors.white70),
          SizedBox(width: 8),
          Expanded(
            child: Text('$title: $value',
                style: TextStyle(fontSize: 16, color: Colors.white70)),
          ),
        ],
      ),
    );
  }
}
