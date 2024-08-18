import 'package:apolloshop/common/widgets/appbar/appbar.dart';
import 'package:apolloshop/features/personalization/controllers/address/address_controller.dart';
import 'package:apolloshop/features/personalization/screen/address/widgets/add_new_address.dart';
import 'package:apolloshop/features/personalization/screen/address/widgets/single_address.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class UserAddressScreen extends StatelessWidget {
  const UserAddressScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final ctrl = Get.put(AddressController());

    return Scaffold(
      appBar: ApolloAppBar(
        showBackArrow: true,
        title: Text(
          'Address',
          style: Theme.of(context).textTheme.headlineSmall,
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(TSizes.defaultSpace),
        child: Obx(
          () => FutureBuilder(
            // Key to trigger refresh
            key: Key(ctrl.refreshData.value.toString()),
            future: ctrl.getUserAddresses(),
            builder: (context, snapshot) {
              final response =
                  THelperFunctions.checkMultiRecordState(snapshot: snapshot);
              if (response != null) return response;

              final address = snapshot.data!;
              return ListView.builder(
                itemCount: address.length,
                shrinkWrap: true,
                itemBuilder: (_, index) => SingleAddress(
                  address: address[index],
                  onTap: () => address[index],
                ),
              );
            },
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: TColors.primary,
        onPressed: () => Get.to(const AddNewAddressScreen()),
        child: const Icon(Iconsax.add, color: TColors.white),
      ),
    );
  }
}
