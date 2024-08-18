import 'package:flutter/material.dart';
import 'package:apollodeliver/Models/OrderDelivery.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';
import 'package:intl/intl.dart';

class CompleteDetailScreen extends StatefulWidget {
  final int orderId;
  final String token;

  CompleteDetailScreen({required this.orderId, required this.token});

  @override
  _CompleteDetailScreenState createState() => _CompleteDetailScreenState();
}

class _CompleteDetailScreenState extends State<CompleteDetailScreen> {
  OrderDelivery? order;
  String errorMessage = '';

  @override
  void initState() {
    super.initState();
    fetchOrder();
  }

  Future<void> fetchOrder() async {
    try {
      final orderData = await DeliveryService(widget.token).getOrderDeliveryById(widget.orderId);
      setState(() {
        order = orderData;
      });
    } catch (e) {
      setState(() {
        errorMessage = 'Failed to load order: $e';
      });
    }
  }

  String formatDate(DateTime? dateTime) {
    if (dateTime == null) return 'N/A';
    return DateFormat('dd/MM/yyyy').format(dateTime);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Completed Order Details'),
        backgroundColor: Color(0xFF7357a4),
      ),
      body: order == null
          ? Center(
        child: errorMessage.isNotEmpty
            ? Text(errorMessage, style: TextStyle(color: Colors.red, fontSize: 16))
            : CircularProgressIndicator(),
      )
          : SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            buildOrderCard(),
            SizedBox(height: 16),
            buildCustomerCard(),
            SizedBox(height: 16),
            buildProductCard(),
          ],
        ),
      ),
      backgroundColor: Color(0xFFF3E5F5),
    );
  }

  Widget buildOrderCard() {
    return Card(
      elevation: 8,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Order Details',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 10),
            buildDetailRow(Icons.confirmation_number, 'Order ID', '${order?.order?.id}'),
            buildDetailRow(Icons.date_range, 'Assigned Date', '${formatDate(order?.assignedDate)}'),
            buildDetailRow(Icons.access_time, 'Order Date', '${order?.order?.orderDate}'),
            buildDetailRow(Icons.calendar_today, 'Delivery Date', '${formatDate(order?.order?.deliveryDate)}'),
            buildDetailRow(Icons.local_offer, 'Status', '${order?.status}'),
            buildDetailRow(Icons.attach_money, 'Order Total', '\$${order?.order?.orderTotal?.toStringAsFixed(2)}'),
          ],
        ),
      ),
    );
  }

  Widget buildCustomerCard() {
    return Card(
      elevation: 8,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Customer Information',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 10),
            buildDetailRow(Icons.person, 'Client Name', '${order?.order?.user.clientName}'),
            buildDetailRow(Icons.phone, 'Phone Number', '${order?.order?.user.phoneNumber ?? "N/A"}'),
            buildDetailRow(Icons.home, "Address", '${order?.order!.address.street}'),
          ],
        ),
      ),
    );
  }

  Widget buildProductCard() {
    final imageUrl = order?.order?.variant?.img ?? 'error';
    final productName = order?.order?.variant?.name ?? 'No Name';
    final price = order?.order?.variant?.price ?? 0.00;
    final quantity = order?.order?.quantity ?? 0;

    return Card(
      elevation: 8,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      color: Color(0xFF7B68EE),
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Product Details',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 10),
            Row(
              children: [
                Container(
                  width: 100,
                  height: 100,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(12),
                    image: DecorationImage(
                      image: NetworkImage(imageUrl),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
                SizedBox(width: 15),
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
                      SizedBox(height: 8),
                      Text(
                        'Price: \$$price',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.normal,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            SizedBox(height: 12),
            Divider(color: Colors.white54, thickness: 1.2),
            SizedBox(height: 12),
            buildDetailRow(Icons.numbers, 'Quantity', '$quantity'),
            buildDetailRow(Icons.payment, 'Default Payment Method',
                '${order?.order?.paymentMethod.defaultPayment != null ? "Yes" : "No"}'),
            buildDetailRow(Icons.local_shipping, 'Shipping Method', '${order?.order?.shippingMethod.name}'),
          ],
        ),
      ),
    );
  }

  Widget buildDetailRow(IconData icon, String title, String value) {
    return Row(
      children: [
        Icon(icon, size: 22, color: Colors.white70),
        SizedBox(width: 12),
        Expanded(
          child: Text('$title: $value', style: TextStyle(fontSize: 18, color: Colors.white70)),
        ),
      ],
    );
  }
}
