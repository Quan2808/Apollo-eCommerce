import 'package:apolloshop/features/shop/controllers/cart/cart_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/pricing_calculator.dart';
import 'package:flutter/material.dart';

class BillingAmountSection extends StatelessWidget {
  const BillingAmountSection({super.key});

  @override
  Widget build(BuildContext context) {
    final ctrl = CartController.instance;
    final subtotal = ctrl.totalCartPrice.value;
    final theme = Theme.of(context).textTheme;
    return Column(
      children: [
        // Subtotal
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('Subtotal', style: theme.bodyMedium),
            Text('\$$subtotal', style: theme.bodyMedium),
          ],
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),

        // Shipping fee
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('Shipping fee', style: theme.bodyMedium),
            Text(
              '\$${PricingCalculator.getFormattedShippingCost('US')}',
              style: theme.labelLarge,
            ),
          ],
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),

        // Order total
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('Order total', style: theme.bodyMedium),
            Text('\$${PricingCalculator.calculateTotalPrice(subtotal, 's')}',
                style: theme.titleMedium),
          ],
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),
      ],
    );
  }
}
