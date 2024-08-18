import 'package:apollodeliver/Screens/MakeColor/Notification/DeliveryCompletedScreen.dart';
import 'package:apollodeliver/Screens/MakeColor/Notification/LoadingScreen.dart';
import 'package:flutter/material.dart';
import 'package:apollodeliver/Models/OrderDelivery.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';
import 'package:intl/intl.dart';

class ProcessDetailScreen extends StatefulWidget {
  final int orderId;
  final String token;
  final String email;

  ProcessDetailScreen({required this.orderId, required this.token, required this.email});

  @override
  _ProcessDetailScreenState createState() => _ProcessDetailScreenState();
}

class _ProcessDetailScreenState extends State<ProcessDetailScreen> {
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
        title: Text('Processing Order Details'),
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
            SizedBox(height: 16),
            buildStatusUpdateButton(),
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
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 8),
            buildDetailRow(Icons.confirmation_number, 'Order ID', '${order?.order?.id}'),
            buildDetailRow(Icons.date_range, 'Assigned Date', '${formatDate(order?.assignedDate)}'),
            buildDetailRow(Icons.access_time, 'Order Date', '${order?.order?.orderDate}'),
            buildDetailRow(Icons.local_offer, 'Status', '${order?.status}'),
            buildDetailRow(Icons.attach_money, 'Order Total', '\$${order?.order?.orderTotal?.toStringAsFixed(2)}'),
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
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 8),
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
            Text('Product Details',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white)),
            SizedBox(height: 8),
            Row(
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
                          fontWeight: FontWeight.normal,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            SizedBox(height: 10),
            Divider(color: Colors.white54, thickness: 1),
            SizedBox(height: 10),
            buildDetailRow(Icons.numbers, 'Quantity', '$quantity'),
            buildDetailRow(Icons.payment, 'Default Payment Method',
                '${order?.order?.paymentMethod.defaultPayment != null ? "Yes" : "No"}'),
            buildDetailRow(Icons.local_shipping, 'Shipping Method', '${order?.order?.shippingMethod.name}'),
          ],
        ),
      ),
    );
  }

  Widget buildStatusUpdateButton() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0),
      child: SizedBox(
        width: double.infinity,
        child: ElevatedButton(
          onPressed: () async {
            try {
              showDialog(
                context: context,
                barrierDismissible: false,
                builder: (BuildContext context) {
                  return LoadingScreen(message: 'Completing Delivery...');
                },
              );
              await DeliveryService(widget.token).changeOrderStatus(widget.orderId, 'COMPLETED', '');
              await Future.delayed(Duration(seconds: 2));
              Navigator.of(context).pop();
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                  builder: (context) => DeliveryCompletedScreen(
                    orderId: widget.orderId,
                    token: widget.token,
                    email: widget.email,
                  ),
                ),
              );
            } catch (e) {
              Navigator.of(context).pop();

              ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to update status: $e')));
            }
          },
          child: Text(
            'Complete Delivery',
            style: TextStyle(
              color: Colors.white,
              fontSize: 18,
              fontWeight: FontWeight.bold,
              letterSpacing: 1.2,
            ),
          ),
          style: ElevatedButton.styleFrom(
            backgroundColor: Color(0xFF7B68EE),
            padding: EdgeInsets.symmetric(vertical: 18),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(25),
            ),
            shadowColor: Colors.black45,
            elevation: 8,
          ),
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
            child: Text('$title: $value', style: TextStyle(fontSize: 16, color: Colors.white70)),
          ),
        ],
      ),
    );
  }
}
