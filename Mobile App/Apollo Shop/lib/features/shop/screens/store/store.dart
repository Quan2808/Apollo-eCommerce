import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/appbar/tabbar.dart';
import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/products/cart/cart_menu_icon.dart';
import 'package:apolloshop/common/widgets/shimmers/store_shimmer.dart';
import 'package:apolloshop/common/widgets/stores/store_card.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/shop/controllers/category/category_controller.dart';
import 'package:apolloshop/features/shop/controllers/store/store_controller.dart';
import 'package:apolloshop/features/shop/screens/store/widgets/all_stores.dart';
import 'package:apolloshop/features/shop/screens/store/widgets/category_tab.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class StoreScreen extends StatelessWidget {
  const StoreScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    final categories = CategoryController.instance.categories;
    final storeController = Get.put(StoreController());

    return DefaultTabController(
      length: categories.length,
      child: Scaffold(
        /// App bar
        appBar: ApolloAppBar(
          title:
              Text('Store', style: Theme.of(context).textTheme.headlineMedium),
          actions: const [
            CartCounterIcon(),
          ],
        ),
        body: NestedScrollView(
          /// Header
          headerSliverBuilder: (_, innerBoxIsScrolled) => [
            SliverAppBar(
              automaticallyImplyLeading: false,
              pinned: true,
              floating: true,
              backgroundColor: dark ? TColors.black : TColors.white,
              expandedHeight: 340,
              flexibleSpace: Padding(
                padding: const EdgeInsets.all(TSizes.defaultSpace),
                child: ListView(
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  children: [
                    /// Featured Stores
                    SectionHeading(
                      title: 'Featured Stores',
                      onPressed: () => Get.to(() => const AllStoresScreen()),
                    ),
                    const SizedBox(height: TSizes.spaceBtwItems / 1.5),

                    /// Stores GRID
                    Obx(() {
                      if (storeController.isLoading.value) {
                        return const StoreShimmer();
                      }

                      if (storeController.featuredStores.isEmpty) {
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
                        itemCount: storeController.featuredStores.length,
                        mainAxisExtent: 80,
                        itemBuilder: (_, index) {
                          final store = storeController.featuredStores[index];
                          final productCount =
                              storeController.productsCountByStore[store.id] ??
                                  0;
                          return StoreCard(
                            store: store,
                            showBorder: false,
                            productCount: productCount, // Pass productCount
                          );
                        },
                      );
                    })
                  ],
                ),
              ),

              /// Tabs
              bottom: ApolloTabBar(
                tabs: categories
                    .map((e) => Tab(child: Text(e.attribute)))
                    .toList(),
              ),
            ),
          ],
          body: TabBarView(
            children: categories
                .map((e) => CategoryTab(
                      category: e,
                    ))
                .toList(),
          ),
        ),
      ),
    );
  }
}
