import 'package:flutter/material.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';
import 'package:apollodeliver/Screens/MainScreen/OrderDetailScreen.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class OrderManagementScreen extends StatefulWidget {
  final String token;
  final String email;

  OrderManagementScreen({required this.token, required this.email});

  @override
  _OrderManagementScreenState createState() => _OrderManagementScreenState();
}

class _OrderManagementScreenState extends State<OrderManagementScreen> {
  List<ShopOrder> orders = [];
  List<ShopOrder> filteredOrders = [];
  bool isLoading = true;
  TextEditingController searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _fetchOrders();
    searchController.addListener(_filterOrders);
  }

  @override
  void dispose() {
    searchController.dispose();
    super.dispose();
  }

  void _fetchOrders() async {
    try {
      DeliveryService service = DeliveryService(widget.token);
      List<ShopOrder> fetchedOrders = await service.getAllOrders();
      List<ShopOrder> acceptedOrders = fetchedOrders.where((order) => order.status == 'ACCEPTED').toList();
      setState(() {
        orders = acceptedOrders;
        filteredOrders = acceptedOrders;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      print("Failed to load orders: $e");
    }
  }

  void _filterOrders() {
    String query = searchController.text.toLowerCase();
    setState(() {
      filteredOrders = orders.where((order) {
        final name = order.variant?.name?.toLowerCase() ?? '';
        return name.contains(query);
      }).toList();
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Orders',
          style: TextStyle(
              color: Colors.black54,
              fontSize: 20,
              fontWeight: FontWeight.bold
          ),
        ),
        backgroundColor: Color(0xFF7357a4),
        bottom: PreferredSize(
          preferredSize: Size.fromHeight(50),
          child: Container(
            padding: EdgeInsets.symmetric(horizontal: 15),
            color: Colors.white54,
            child: TextField(
              controller: searchController,
              decoration: InputDecoration(
                hintText: 'Search by product name...',
                hintStyle: TextStyle(color: Colors.white70),
                border: InputBorder.none,
                icon: Icon(Icons.search, color: Colors.white),
              ),
              style: TextStyle(color: Colors.white),
            ),
          ),
        ),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : filteredOrders.isEmpty
          ? Center(child: Text('No orders found'))
          : ListView.builder(
        itemCount: filteredOrders.length,
        itemBuilder: (context, index) {
          final order = filteredOrders[index];
          final variant = order.variant;
          final imageUrl = variant?.img ?? 'error';
          final name = variant?.name ?? 'No Name';
          final quantity = order.quantity ?? 0;
          final price = variant?.price ?? 0.00;
          final subtotal = order.orderTotal;
          final orderStatus = order.status ?? 'Unknown';

          return Card(
            margin: EdgeInsets.symmetric(horizontal: 10.0, vertical: 5.0),
            elevation: 5.0,
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
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
                              name,
                              style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                              ),
                              overflow: TextOverflow.ellipsis,
                            ),
                            SizedBox(height: 5),
                            Align(
                              alignment: Alignment.centerRight,
                              child: Text(
                                'Price: \$$price',
                                style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.normal,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 10),
                  Divider(color: Colors.grey[300], thickness: 1),
                  SizedBox(height: 10),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Quantity: $quantity',
                        style: TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.normal,
                        ),
                      ),
                      Text(
                        'SubTotal: \$$subtotal',
                        style: TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 10),
                  Divider(color: Colors.grey[300], thickness: 1),
                  SizedBox(height: 10),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Row(
                        children: [
                          Icon(
                            FontAwesomeIcons.truck,
                            color: Colors.green,
                          ),
                          SizedBox(width: 5),
                          Text(
                            ' $orderStatus',
                            style: TextStyle(
                              fontSize: 14,
                              fontWeight: FontWeight.bold,
                              color: Colors.green,
                            ),
                          ),
                        ],
                      ),
                      Container(
                        width: 100,
                        height: 40,
                        child: OutlinedButton(
                          style: OutlinedButton.styleFrom(
                            backgroundColor: Color(0xFF7357a4),
                            side: BorderSide(color: Color(0xFF7357a4)),
                          ),
                          onPressed: () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => OrderDetailScreen(
                                  orderId: order.id!,
                                  token: widget.token,
                                  email: widget.email,
                                ),
                              ),
                            );
                          },
                          child: Text(
                            'Details',
                            style: TextStyle(color: Colors.white),
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
