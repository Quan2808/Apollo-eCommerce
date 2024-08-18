import 'package:apollodeliver/Screens/MainScreen/CompleteDetailScreen.dart';
import 'package:apollodeliver/Screens/MainScreen/ProcessDetailScreen.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:apollodeliver/Models/OrderDelivery.dart';
import 'package:apollodeliver/Services/DeliveryService.dart';

class DeliveryManagementScreen extends StatefulWidget {
  final String token;
  final String email;

  DeliveryManagementScreen({required this.token, required this.email});

  @override
  _DeliveryManagementScreenState createState() =>
      _DeliveryManagementScreenState();
}

class _DeliveryManagementScreenState extends State<DeliveryManagementScreen> {
  List<OrderDelivery> pendingOrders = [];
  List<OrderDelivery> completedOrders = [];
  List<OrderDelivery> filteredPendingOrders = [];
  List<OrderDelivery> filteredCompletedOrders = [];
  bool isLoading = true;
  String errorMessage = '';
  TextEditingController searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchOrders();
    searchController.addListener(_filterOrders);
  }

  @override
  void dispose() {
    searchController.dispose();
    super.dispose();
  }

  Future<void> fetchOrders() async {
    try {
      final List<OrderDelivery> orders =
      await DeliveryService(widget.token).getAllOrderDeliveries();
      setState(() {
        final List<OrderDelivery> filteredOrders = orders
            .where((order) => order.shipper?.email == widget.email)
            .toList();
        pendingOrders = filteredOrders
            .where((order) => order.status == 'PENDING')
            .toList()
          ..sort((a, b) {
            final dateA = a.assignedDate ?? DateTime.fromMillisecondsSinceEpoch(0);
            final dateB = b.assignedDate ?? DateTime.fromMillisecondsSinceEpoch(0);
            return dateB.compareTo(dateA);
          });

        completedOrders = filteredOrders
            .where((order) => order.status == 'COMPLETED')
            .toList()
          ..sort((a, b) {
            final dateA = a.order?.deliveryDate ?? DateTime.fromMillisecondsSinceEpoch(0);
            final dateB = b.order?.deliveryDate ?? DateTime.fromMillisecondsSinceEpoch(0);
            return dateB.compareTo(dateA);
          });

        filteredPendingOrders = pendingOrders;
        filteredCompletedOrders = completedOrders;

        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
        errorMessage = 'Failed to load orders: $e';
      });
    }
  }

  void _filterOrders() {
    String query = searchController.text.toLowerCase();
    setState(() {
      filteredPendingOrders = pendingOrders
          .where((order) =>
      order.order?.variant?.name?.toLowerCase().contains(query) ?? false)
          .toList();
      filteredCompletedOrders = completedOrders
          .where((order) =>
      order.order?.variant?.name?.toLowerCase().contains(query) ?? false)
          .toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Delivery',
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
          : errorMessage.isNotEmpty
          ? Center(child: Text(errorMessage))
          : DefaultTabController(
        length: 2,
        child: Column(
          children: [
            Container(
              color: Color(0xFF5A4791),
              child: TabBar(
                indicatorColor: Colors.grey,
                labelColor: Colors.white,
                unselectedLabelColor: Colors.white70,
                tabs: [
                  Tab(text: 'PROCESS'),
                  Tab(text: 'COMPLETED'),
                ],
              ),
            ),
            Expanded(
              child: TabBarView(
                children: [
                  buildOrderList(filteredPendingOrders, 'PENDING'),
                  buildOrderList(filteredCompletedOrders, 'COMPLETED'),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildOrderList(List<OrderDelivery> orders, String status) {
    if (orders.isEmpty) {
      return Center(child: Text('No $status orders available'));
    }
    return ListView.builder(
      padding: EdgeInsets.zero,
      itemCount: orders.length,
      itemBuilder: (context, index) {
        final order = orders[index];
        final variant = order.order?.variant;
        final imageUrl = variant?.img ?? 'error';
        final name = variant?.name ?? 'No Name';
        final quantity = order.order?.quantity ?? 0;
        final price = variant?.price ?? 0.00;
        final subtotal = order.order?.orderTotal;
        final orderStatus = order.status ?? 'UNKNOWN';

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
                              builder: (context) => status == 'PENDING'
                                  ? ProcessDetailScreen(
                                orderId: order.id!,
                                token: widget.token,
                                email: widget.email,
                              )
                                  : CompleteDetailScreen(
                                orderId: order.id!,
                                token: widget.token,
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
    );
  }
}
