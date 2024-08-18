import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/sortable/sortable_products.dart';
import 'package:apolloshop/data/models/category/category_model.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class SubCategoriesScreen extends StatelessWidget {
  const SubCategoriesScreen({
    super.key,
    required this.category,
  });

  final CategoryModel category;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // App bar
      appBar:
          ApolloAppBar(title: Text(category.attribute), showBackArrow: true),

      // Body
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            children: [
              SortableProducts(category: category),
            ],
          ),
        ),
      ),
    );
  }
}
