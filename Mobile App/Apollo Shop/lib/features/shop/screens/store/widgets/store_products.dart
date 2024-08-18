import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/common/widgets/products/sortable/sortable_products.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class StoreProducts extends StatelessWidget {
  const StoreProducts({
    super.key,
    required this.store,
  });

  final StoreModel store;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: ApolloAppBar(title: Text(store.name), showBackArrow: true),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            children: [
              SortableProducts(store: store),
            ],
          ),
        ),
      ),
    );
  }
}
