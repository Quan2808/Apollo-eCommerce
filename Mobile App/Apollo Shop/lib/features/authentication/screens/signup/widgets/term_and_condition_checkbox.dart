import 'package:apolloshop/features/authentication/controllers/signup/signup_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/constants/text_strings.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class TermAndConditionCheckbox extends StatelessWidget {
  const TermAndConditionCheckbox({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    final controller = SignupController.instance;

    return Row(
      children: [
        SizedBox(
          width: 24,
          height: 24,
          child: Obx(
            () => Checkbox(
              value: controller.privacyPolicy.value,
              onChanged: (value) => controller.privacyPolicy.value =
                  !controller.privacyPolicy.value,
            ),
          ),
        ),
        const SizedBox(
          width: TSizes.spaceBtwItems,
        ),
        Expanded(
          child: Wrap(
            children: [
              Text(
                '${TTexts.iAgreeTo} ',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              GestureDetector(
                onTap: () => _showBottomSheet(
                  context,
                  'Privacy Policy',
                  TTexts.privacyPolicyContent,
                ),
                child: Text(
                  TTexts.privacyPolicy,
                  style: Theme.of(context).textTheme.bodySmall!.apply(
                        color: dark ? TColors.white : TColors.primary,
                        decorationColor: dark ? TColors.white : TColors.primary,
                        decoration: TextDecoration.underline,
                      ),
                ),
              ),
              Text(
                ' ${TTexts.and} ',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              GestureDetector(
                onTap: () => _showBottomSheet(
                  context,
                  'Terms of Use',
                  TTexts.termsOfUseContent,
                ),
                child: Text(
                  TTexts.termsOfUse,
                  style: Theme.of(context).textTheme.bodySmall!.apply(
                        color: dark ? TColors.white : TColors.primary,
                        decorationColor: dark ? TColors.white : TColors.primary,
                        decoration: TextDecoration.underline,
                      ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  void _showBottomSheet(BuildContext context, String title, String content) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (BuildContext context) {
        return DraggableScrollableSheet(
          initialChildSize: 0.5,
          minChildSize: 0.2,
          maxChildSize: 0.9,
          expand: false,
          builder: (_, controller) {
            return Container(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Center(
                    child: Container(
                      width: 40,
                      height: 5,
                      decoration: BoxDecoration(
                        color: Colors.grey[300],
                        borderRadius: BorderRadius.circular(2.5),
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  Text(
                    title,
                    style: Theme.of(context).textTheme.headlineMedium,
                  ),
                  const SizedBox(height: 16),
                  Expanded(
                    child: ListView(
                      controller: controller,
                      children: [
                        Text(content),
                      ],
                    ),
                  ),
                ],
              ),
            );
          },
        );
      },
    );
  }
}
