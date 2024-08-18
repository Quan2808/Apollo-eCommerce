import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/features/personalization/controllers/payment_method/payment_method_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class AddNewPaymentMethodScreen extends StatelessWidget {
  const AddNewPaymentMethodScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final ctrl = PaymentMethodController.instance;
    return Scaffold(
      appBar: const ApolloAppBar(
        showBackArrow: true,
        title: Text('Add New Payment Method'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: ctrl.cardNumberController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.card),
                labelText: 'Card Number',
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwInputFields),
            TextField(
              controller: ctrl.nameOnCardController,
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.personalcard),
                labelText: 'Name on Card',
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwInputFields),
            TextField(
              controller: ctrl.expirationDateController,
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.calendar),
                labelText: 'Expiration Date (MM/YY)',
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwInputFields),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Obx(() {
                  return Checkbox(
                    value: ctrl.isDefaultPayment.value,
                    onChanged: (bool? value) {
                      ctrl.isDefaultPayment.value = value ?? false;
                    },
                  );
                }),
                const Text('Set as default payment method'),
              ],
            ),
            const SizedBox(height: TSizes.spaceBtwInputFields),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () async {
                  await ctrl.addNewPaymentMethod();
                },
                child: const Text('Save Payment Method'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
