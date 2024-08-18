import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';

class SinglePaymentMethod extends StatelessWidget {
  const SinglePaymentMethod({
    super.key,
    required this.paymentMethod,
    required this.onTap,
  });

  final PaymentMethodModel paymentMethod;
  final VoidCallback onTap;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    return InkWell(
      child: RoundedContainer(
        width: double.infinity,
        showBorder: true,
        padding: const EdgeInsets.all(TSizes.md),
        backgroundColor: Colors.transparent,
        borderColor: dark ? TColors.darkerGrey : TColors.grey,
        margin: const EdgeInsets.only(bottom: TSizes.spaceBtwItems),
        child: Stack(
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  paymentMethod.cardNumber.toString(),
                  softWrap: true,
                ),
                const SizedBox(height: TSizes.sm / 2),
                if (paymentMethod.nameOnCard != null)
                  Text(
                    paymentMethod.nameOnCard!,
                  ),
                if (paymentMethod.expirationDate != null)
                  Text(
                    paymentMethod.expirationDate!,
                  ),
              ],
            )
          ],
        ),
      ),
    );
  }
}
