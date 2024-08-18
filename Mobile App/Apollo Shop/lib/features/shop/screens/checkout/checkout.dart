import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/features/shop/controllers/cart/cart_controller.dart';
import 'package:apolloshop/features/shop/controllers/order/order_controller.dart';
import 'package:apolloshop/features/shop/screens/cart/widgets/cart_items.dart';
import 'package:apolloshop/features/shop/screens/checkout/widgets/billing_address_section.dart';
import 'package:apolloshop/features/shop/screens/checkout/widgets/billing_amount_section.dart';
import 'package:apolloshop/features/shop/screens/checkout/widgets/billing_payment_section.dart';
import 'package:apolloshop/features/shop/screens/checkout/widgets/billing_shipping_section.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:apolloshop/utils/helpers/pricing_calculator.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CheckoutScreen extends StatelessWidget {
  const CheckoutScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    final cartCtrl = CartController.instance;
    final subtotal = cartCtrl.totalCartPrice.value;
    final orderCtrl = Get.put(OrderController());
    final totalAmount = PricingCalculator.calculateTotalPrice(subtotal, '123');

    return Scaffold(
      // AppBar
      appBar: ApolloAppBar(
        showBackArrow: true,
        title: Text('Order Review',
            style: Theme.of(context).textTheme.headlineSmall),
      ),

      // Body
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            children: [
              // Items in Cart
              const CartItems(showAdjustButton: false),
              const SizedBox(height: TSizes.spaceBtwSections),

              // Billing Section
              RoundedContainer(
                showBorder: true,
                padding: const EdgeInsets.all(TSizes.md),
                backgroundColor: dark ? TColors.black : TColors.white,
                child: const Column(
                  children: [
                    // Pricing
                    BillingAmountSection(),
                    SizedBox(height: TSizes.spaceBtwItems),

                    // Divider
                    Divider(),
                    SizedBox(height: TSizes.spaceBtwItems),

                    // Payment method
                    BillingPaymentSection(),
                    SizedBox(height: TSizes.spaceBtwItems),

                    // Shipping method
                    BillingShippingSection(),
                    SizedBox(height: TSizes.spaceBtwItems),

                    // Address
                    BillingAddressSection(),
                    SizedBox(height: TSizes.spaceBtwItems),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),

      // Checkout
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: ElevatedButton(
          onPressed: () => orderCtrl.processOrder(),
          child: Text('Checkout \$${totalAmount.toStringAsFixed(1)}'),
        ),
      ),
    );
  }
}
