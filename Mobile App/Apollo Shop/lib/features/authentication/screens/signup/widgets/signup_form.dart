import 'package:apolloshop/features/authentication/controllers/signup/signup_controller.dart';
import 'package:apolloshop/features/authentication/screens/signup/widgets/term_and_condition_checkbox.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/constants/text_strings.dart';
import 'package:apolloshop/utils/validators/validation.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class SignupForm extends StatelessWidget {
  const SignupForm({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignupController());

    return Form(
      key: controller.signupFormKey,
      child: Column(
        children: [
          // First Name and Last Name
          TextFormField(
            controller: controller.fullName,
            validator: (value) =>
                TValidator.validateFullName(value),
            decoration: const InputDecoration(
              labelText: 'Full name',
              prefixIcon: Icon(Iconsax.user),
            ),
          ),
          const SizedBox(
            height: TSizes.spaceBtwInputFields,
          ),

          // Email
          TextFormField(
            controller: controller.email,
            validator: (value) => TValidator.validateEmail(value),
            decoration: const InputDecoration(
              labelText: TTexts.email,
              prefixIcon: Icon(Iconsax.direct),
            ),
          ),
          const SizedBox(
            height: TSizes.spaceBtwInputFields,
          ),

          //  Phone Number
          TextFormField(
            controller: controller.phoneNumber,
            validator: (value) => TValidator.validatePhoneNumber(value),
            decoration: const InputDecoration(
              labelText: TTexts.phoneNo,
              prefixIcon: Icon(Iconsax.call),
            ),
          ),
          const SizedBox(
            height: TSizes.spaceBtwInputFields,
          ),

          // Password
          Obx(
            () => TextFormField(
              controller: controller.password,
              validator: (value) => TValidator.validatePassword(value),
              obscureText: controller.hidePassword.value,
              decoration: InputDecoration(
                labelText: TTexts.password,
                prefixIcon: const Icon(Iconsax.password_check),
                suffixIcon: IconButton(
                  onPressed: () => controller.hidePassword.value =
                      !controller.hidePassword.value,
                  icon: controller.hidePassword.value
                      ? const Icon(Iconsax.eye_slash)
                      : const Icon(Iconsax.eye),
                ),
              ),
            ),
          ),
          const SizedBox(
            height: TSizes.spaceBtwInputFields,
          ),

          // Privacy Policy
          const TermAndConditionCheckbox(),
          const SizedBox(
            height: TSizes.spaceBtwSections,
          ),

          // Sign Up Button
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: () => controller.performSignUp(),
              child: const Text(TTexts.createAccount),
            ),
          ),
        ],
      ),
    );
  }
}
