import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/data/models/shipping_method/shipping_method_model.dart';
import 'package:apolloshop/data/repositories/shipping_method/shipping_method_repository.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ShippingMethodController extends GetxController {
  static ShippingMethodController get instance => Get.find();

  final RxList<ShippingMethodModel> shippingMethods =
      <ShippingMethodModel>[].obs;
  final Rx<ShippingMethodModel> selectedShippingMethod =
      ShippingMethodModel.empty().obs;

  final ShippingMethodRepository shippingMethodRepository =
      Get.put(ShippingMethodRepository());

  Future<List<ShippingMethodModel>> getPaymentMethods() async {
    try {
      final response = await shippingMethodRepository.fetchShippingMethods();
      selectedShippingMethod.value = response.firstWhere(
        (e) => e.isSelected,
        orElse: () => ShippingMethodModel.empty(),
      );
      return response;
    } catch (e) {
      Get.snackbar('Error', 'Unable to fetch payment methods');
      return [];
    }
  }

  Future<void> setPaymentMethod(BuildContext context) async {
    await showModalBottomSheet(
      context: context,
      builder: (_) => SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(TSizes.lg),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SectionHeading(
                title: 'Select Payment Method',
                showActionButton: false,
              ),
              const SizedBox(height: TSizes.spaceBtwItems),
              FutureBuilder<List<ShippingMethodModel>>(
                future: getPaymentMethods(),
                builder: (_, snapshot) {
                  final response = THelperFunctions.checkMultiRecordState(
                      snapshot: snapshot);
                  if (response != null) {
                    return response;
                  } else if (snapshot.hasData && snapshot.data!.isNotEmpty) {
                    return Column(
                      children: snapshot.data!.map((paymentMethod) {
                        return ListTile(
                          title: Text(paymentMethod.name),
                          subtitle: Text(paymentMethod.price.toString()),
                          onTap: () async {
                            selectedShippingMethod.value = paymentMethod;
                            Navigator.of(context).pop();
                          },
                        );
                      }).toList(),
                    );
                  } else {
                    return const Center(
                        child: Text('No payment methods available.'));
                  }
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
