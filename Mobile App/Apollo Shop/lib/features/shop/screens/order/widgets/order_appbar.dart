import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/cart/cart_menu_icon.dart';
import 'package:flutter/material.dart';

class OrderAppbar extends StatelessWidget {
  const OrderAppbar({
    super.key,
    required this.isNavigationBar,
  });

  final bool isNavigationBar;

  @override
  Widget build(BuildContext context) {
    if (isNavigationBar) {
      return ApolloAppBar(
        title: Text('Store', style: Theme.of(context).textTheme.headlineMedium),
        actions: const [CartCounterIcon()],
      );
    } else {
      return ApolloAppBar(
        title: Text('Order', style: Theme.of(context).textTheme.headlineSmall),
      );
    }
  }
}
