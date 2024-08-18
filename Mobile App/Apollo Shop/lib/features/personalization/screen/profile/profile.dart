import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:apolloshop/features/personalization/screen/profile/widgets/profile_menu.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final controller = UserController.instance;

    return Scaffold(
      appBar: const ApolloAppBar(
        showBackArrow: true,
        title: Text('Profile'),
      ),

      /// Body
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: Column(
          children: [
            /// Heading Profile Information
            const SectionHeading(
              title: 'Profile Information',
              showActionButton: false,
            ),
            const SizedBox(height: TSizes.spaceBtwSections),

            ProfileMenu(
              title: 'Name',
              value: controller.user.value?.fullName ?? '',
              onPressed: () {},
            ),
            const Divider(),

            // const SizedBox(height: TSizes.spaceBtwItems),
            // const Divider(),
            // const SizedBox(height: TSizes.spaceBtwItems),

            /// Heading Personal Information
            // const SectionHeading(
            //   title: 'Personal Information',
            //   showActionButton: false,
            // ),
            // const SizedBox(height: TSizes.spaceBtwSections),

            ProfileMenu(
              title: 'E-mail',
              value: controller.user.value?.email ?? '',
              onPressed: () {},
            ),
            const Divider(),
            ProfileMenu(
              title: 'Phone Number',
              value: controller.user.value?.phoneNumber ?? '',
              onPressed: () {},
            ),
            const Divider(),

            // const Divider(),
            // const SizedBox(height: TSizes.spaceBtwItems),

            // Center(
            //   child: TextButton(
            //     onPressed: () {},
            //     child: const Text(
            //       'Close Account',
            //       style: TextStyle(color: Colors.red),
            //     ),
            //   ),
            // ),
          ],
        ),
      ),
    );
  }
}
