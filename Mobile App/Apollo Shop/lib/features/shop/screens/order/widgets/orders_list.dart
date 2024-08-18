import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/common/widgets/loaders/animation_loader.dart';
import 'package:apolloshop/features/shop/controllers/order/order_controller.dart';
import 'package:apolloshop/navigation_menu.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';
import 'package:intl/intl.dart';

class OrdersListItems extends StatelessWidget {
  const OrdersListItems({super.key});

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    final theme = Theme.of(context).textTheme;
    final ctrl = Get.put(OrderController());

    String formatOrderDate(String orderDate) {
      DateTime dateTime = DateTime.parse(orderDate);
      return DateFormat('dd/MM/yyyy').format(dateTime);
    }

    String formatStatus(String text) {
      return text.toUpperCase();
    }

    return FutureBuilder(
      future: ctrl.fetchUserOrders(),
      builder: (context, snapshot) {
        final emptyWidget = AnimationLoaderWidget(
          text: 'Whoops! No orders yet',
          animation: TImages.emptyListingOrderAnimation,
          showAction: true,
          actionText: 'Let\'s fill it',
          onActionPressed: () => Get.off(() => const NavigationMenu()),
        );

        final response = THelperFunctions.checkMultiRecordState(
            snapshot: snapshot, nothingFound: emptyWidget);
        if (response != null) return response;

        final orders = snapshot.data!;
        return ListView.separated(
          shrinkWrap: true,
          itemCount: orders.length,
          separatorBuilder: (_, index) =>
              const SizedBox(height: TSizes.spaceBtwItems),
          itemBuilder: (_, index) {
            final order = orders[index];
            return RoundedContainer(
              showBorder: true,
              padding: const EdgeInsets.all(TSizes.md),
              backgroundColor: dark ? TColors.dark : TColors.light,
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  // Status & Date
                  Row(
                    children: [
                      const Icon(Iconsax.ship),
                      const SizedBox(width: TSizes.spaceBtwItems / 2),
                      Expanded(
                        child: Column(
                          mainAxisSize: MainAxisSize.min,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              formatStatus(order.status),
                              style: theme.bodyLarge!.apply(
                                  color: TColors.primary, fontWeightDelta: 1),
                            ),
                          ],
                        ),
                      ),
                      // IconButton(
                      //   onPressed: () {},
                      //   icon: const Icon(
                      //     Iconsax.arrow_right_34,
                      //     size: TSizes.iconSm,
                      //   ),
                      // )
                    ],
                  ),
                  const SizedBox(height: TSizes.spaceBtwItems),
                  Row(
                    children: [
                      Expanded(
                        child: Row(
                          children: [
                            const Icon(Iconsax.tag),
                            const SizedBox(width: TSizes.spaceBtwItems / 2),
                            Expanded(
                              child: Column(
                                mainAxisSize: MainAxisSize.min,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text('Order ID', style: theme.labelMedium),
                                  Text(
                                    '[#${order.id}]',
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                    style: theme.titleMedium,
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                      Expanded(
                        child: Row(
                          children: [
                            const Icon(Iconsax.calendar),
                            const SizedBox(width: TSizes.spaceBtwItems / 2),
                            Expanded(
                              child: Column(
                                mainAxisSize: MainAxisSize.min,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text('Order Date', style: theme.labelMedium),
                                  Text(
                                    formatOrderDate(order.orderDate.toString()),
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                    style: theme.titleMedium,
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  )
                ],
              ),
            );
          },
        );
      },
    );
  }
}
