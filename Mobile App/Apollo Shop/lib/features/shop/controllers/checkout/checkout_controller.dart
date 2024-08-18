// import 'package:apolloshop/common/widgets/list_title/payment_title.dart';
// import 'package:apolloshop/common/widgets/texts/section_heading.dart';
// import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
// import 'package:apolloshop/utils/constants/image_strings.dart';
// import 'package:apolloshop/utils/constants/sizes.dart';
// import 'package:flutter/material.dart';
// import 'package:get/get.dart';

// class CheckoutController extends GetxController {
//   static CheckoutController get instance => Get.find();

//   final Rx<PaymentMethodModel> selectedPaymentMethod =
//       PaymentMethodModel.empty().obs;

//   @override
//   void onInit() {
//     super.onInit();
//     selectedPaymentMethod.value =
//         PaymentMethodModel(name: 'Paypal', image: TImages.paypal);
//   }

//   Future<dynamic> setPaymentMethod(BuildContext context) {
//     return showModalBottomSheet(
//       context: context,
//       builder: (_) => SingleChildScrollView(
//         child: Container(
//           padding: const EdgeInsets.all(TSizes.lg),
//           child: Column(
//             crossAxisAlignment: CrossAxisAlignment.start,
//             children: [
//               const SectionHeading(
//                 title: 'Select Payment Method',
//                 showActionButton: false,
//               ),
//               const SizedBox(height: TSizes.spaceBtwItems),
//               PaymentTitle(
//                 paymentMethod:
//                     PaymentMethodModel(name: 'Paypal', image: TImages.paypal),
//               ),
//               const SizedBox(height: TSizes.spaceBtwItems / 2),
//               PaymentTitle(
//                 paymentMethod:
//                     PaymentMethodModel(name: 'VISA', image: TImages.visa),
//               ),
//             ],
//           ),
//         ),
//       ),
//     );
//   }
// }
