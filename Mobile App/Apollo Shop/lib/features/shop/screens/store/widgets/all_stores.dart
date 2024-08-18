import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/shimmers/store_shimmer.dart';
import 'package:apolloshop/common/widgets/stores/store_card.dart';
import 'package:apolloshop/features/shop/controllers/store/store_controller.dart';
import 'package:apolloshop/features/shop/screens/store/widgets/store_products.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class AllStoresScreen extends StatelessWidget {
  const AllStoresScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final storeController = StoreController.instance;

    return Scaffold(
      // App bar
      appBar:
          const ApolloAppBar(title: Text('All Stores'), showBackArrow: true),

      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            children: [
              // Stores
              Obx(() {
                if (storeController.isLoading.value) {
                  return const StoreShimmer();
                }

                if (storeController.stores.isEmpty) {
                  return Center(
                    child: Text(
                      'No Data Found!',
                      style: Theme.of(context)
                          .textTheme
                          .bodyMedium!
                          .apply(color: Colors.white),
                    ),
                  );
                }
                return GridLayout(
                  itemCount: storeController.stores.length,
                  mainAxisExtent: 80,
                  itemBuilder: (_, index) {
                    final store = storeController.stores[index];
                    final productCount =
                        storeController.productsCountByStore[store.id] ?? 0;
                    return StoreCard(
                      store: store,
                      showBorder: false,
                      productCount: productCount,
                      onTap: () => Get.to(
                        () => StoreProducts(store: store),
                      ),
                    );
                  },
                );
              })
            ],
          ),
        ),
      ),
    );
  }
}
