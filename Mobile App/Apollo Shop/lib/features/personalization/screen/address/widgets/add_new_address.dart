import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';
import 'package:apolloshop/features/personalization/controllers/address/address_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';

class AddNewAddressScreen extends StatelessWidget {
  const AddNewAddressScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final ctrl = AddressController.instance;
    return Scaffold(
      appBar: const ApolloAppBar(
        showBackArrow: true,
        title: Text('Add new Address'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: Column(
          children: [
            TextField(
              controller: ctrl.street,
              onChanged: (value) {
                if (value.length > 2) {
                  ctrl.autosuggest(value);
                } else {
                  ctrl.suggestions.clear();
                }
              },
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.building),
                labelText: 'Address',
              ),
            ),
            Obx(() {
              return Expanded(
                child: ListView.builder(
                  itemCount: ctrl.suggestions.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(ctrl.suggestions[index]),
                      onTap: () {
                        ctrl.selectedAddressText.value =
                            ctrl.suggestions[index];
                        ctrl.street.text = ctrl.selectedAddressText.value;
                        ctrl.suggestions.clear();
                        ctrl.calculateDistance(ctrl.selectedAddressText.value);
                      },
                    );
                  },
                ),
              );
            }),
            Obx(() {
              return ctrl.distance.isNotEmpty
                  ? Text('Distance: ${ctrl.distance.value}')
                  : Container();
            }),
            const SizedBox(height: TSizes.spaceBtwInputFields),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () async {
                  await ctrl.addNewAddress();
                },
                child: const Text('Save Address'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
