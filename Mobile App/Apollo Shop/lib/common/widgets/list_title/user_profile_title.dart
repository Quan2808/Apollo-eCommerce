import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:flutter/material.dart';
import 'package:iconsax/iconsax.dart';

class UserProfileTitle extends StatelessWidget {
  const UserProfileTitle({
    super.key,
    required this.onPressed,
  });

  final VoidCallback onPressed;

  @override
  Widget build(BuildContext context) {
    final controller = UserController.instance;
    return ListTile(
      leading: const Icon(
        Iconsax.user,
        color: TColors.white,
        size: 30,
      ),
      title: Text(
        controller.user.value?.fullName ?? '',
        style: Theme.of(context).textTheme.headlineSmall!.apply(
              color: TColors.white,
            ),
      ),
      subtitle: Text(
        controller.user.value?.email ?? '',
        style: Theme.of(context).textTheme.bodyMedium!.apply(
              color: TColors.white,
            ),
      ),
      trailing: IconButton(
        onPressed: onPressed,
        icon: const Icon(
          Iconsax.edit,
          color: TColors.white,
        ),
      ),
    );
  }
}
