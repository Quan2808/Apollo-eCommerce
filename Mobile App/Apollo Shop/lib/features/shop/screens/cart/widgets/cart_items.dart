import 'package:apolloshop/common/widgets/products/cart/cart_item.dart';
import 'package:apolloshop/common/widgets/products/cart/product_quantity_adjuster.dart';
import 'package:apolloshop/common/widgets/texts/products/product_price_text.dart';
import 'package:apolloshop/features/shop/controllers/cart/cart_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CartItems extends StatelessWidget {
  const CartItems({
    super.key,
    this.showAdjustButton = true,
  });

  final bool showAdjustButton;

  @override
  Widget build(BuildContext context) {
    final cartController = CartController.instance;

    return Obx(() {
      return ListView.separated(
        shrinkWrap: true,
        physics: const NeverScrollableScrollPhysics(),
        itemCount: cartController.cartItems.length,
        separatorBuilder: (_, index) =>
            const SizedBox(height: TSizes.spaceBtwSections),
        itemBuilder: (_, index) {
          final item = cartController.cartItems[index];

          return Column(
            children: [
              // Individual cart item widget
              CartItem(cartItem: item),
              if (showAdjustButton)
                const SizedBox(height: TSizes.spaceBtwItems),

              // Adjust the quantity & display the price of the product
              if (showAdjustButton)
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    // Widget to adjust the quantity of the product
                    ProductQuantityAdjuster(
                      quantity: item.quantity,
                      add: () => cartController.incrementCartItem(item),
                      remove: () => cartController.decrementCartItem(item),
                    ),
                    // Widget to display the price of the product
                    ProductPriceText(
                        price: (item.price * item.quantity).toStringAsFixed(1)),
                  ],
                ),
            ],
          );
        },
      );
    });
  }
}
