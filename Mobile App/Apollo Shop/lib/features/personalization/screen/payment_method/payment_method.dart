import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/features/personalization/controllers/payment_method/payment_method_controller.dart';
import 'package:apolloshop/features/personalization/screen/payment_method/widgets/add_new_payment_method.dart';
import 'package:apolloshop/features/personalization/screen/payment_method/widgets/single_payment_method.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class PaymentMethodScreen extends StatelessWidget {
  const PaymentMethodScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final ctrl = Get.put(PaymentMethodController());
    return Scaffold(
      appBar: ApolloAppBar(
        showBackArrow: true,
        title: Text(
          'Payment Method',
          style: Theme.of(context).textTheme.headlineSmall,
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: Obx(
          () => FutureBuilder(
            // Key to trigger refresh
            key: Key(ctrl.refreshData.value.toString()),
            future: ctrl.getPaymentMethods(),
            builder: (context, snapshot) {
              final response =
                  THelperFunctions.checkMultiRecordState(snapshot: snapshot);
              if (response != null) return response;

              final paymentMethod = snapshot.data!;
              return ListView.builder(
                itemCount: paymentMethod.length,
                shrinkWrap: true,
                itemBuilder: (_, index) => SinglePaymentMethod(
                  paymentMethod: paymentMethod[index],
                  onTap: () => paymentMethod[index],
                ),
              );
            },
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: TColors.primary,
        onPressed: () => Get.to(const AddNewPaymentMethodScreen()),
        child: const Icon(Iconsax.add, color: TColors.white),
      ),
    );
  }
}
