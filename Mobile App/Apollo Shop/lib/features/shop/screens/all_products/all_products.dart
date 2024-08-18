import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/sortable/sortable_products.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class AllProductsScreen extends StatelessWidget {
  const AllProductsScreen({
    super.key,
    required this.title,
    this.featuredMethod,
  });

  final String title;
  final Future<List<ProductModel>>? featuredMethod;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // App bar
      appBar: ApolloAppBar(
        title: Text(title),
        showBackArrow: true,
      ),

      // Body
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: FutureBuilder(
            future: featuredMethod,
            builder: (context, snapshot) {
              return const SortableProducts();
            },
          ),
        ),
      ),
    );
  }
}
