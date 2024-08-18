import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/cart/cart_menu_icon.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/text_strings.dart';
import 'package:apolloshop/common/widgets/shimmers/shimmer_effect.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HomeAppBar extends StatelessWidget {
  const HomeAppBar({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context).textTheme;
    final controller = Get.put(UserController());

    return ApolloAppBar(
      title: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            TTexts.homeAppbarTitle,
            style: theme.labelMedium!.apply(color: TColors.grey),
          ),
          Obx(
            () {
              if (controller.profileLoading.value) {
                return const ShimmerEffect(width: 80, height: 15);
              }
              return Text(
                controller.user.value?.fullName.toString() ?? '',
                style: theme.headlineMedium!.apply(color: TColors.white),
              );
            },
          ),
        ],
      ),
      actions: const [
        CartCounterIcon(
          iconColor: TColors.white,
        )
      ],
    );
  }
}
