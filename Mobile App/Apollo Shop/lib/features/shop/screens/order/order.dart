import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/cart/cart_menu_icon.dart';
import 'package:apolloshop/features/shop/screens/order/widgets/orders_list.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class OrderScreen extends StatelessWidget {
  const OrderScreen({
    super.key,
    required this.isNavigationBar,
  });
  final bool isNavigationBar;
  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context).textTheme;

    return Scaffold(
      // AppBar
      appBar: ApolloAppBar(
        title: Text(
          'Order',
          style: isNavigationBar ? theme.headlineMedium : theme.headlineSmall,
        ),
        showBackArrow: isNavigationBar ? false : true,
        actions: isNavigationBar ? [const CartCounterIcon()] : null,
      ),

      // Body
      body: const Padding(
        padding: EdgeInsets.all(TSizes.defaultSpace),

        // Order Items
        child: OrdersListItems(),
      ),
    );
  }
}
