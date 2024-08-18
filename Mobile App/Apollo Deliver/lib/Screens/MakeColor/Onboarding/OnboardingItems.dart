import 'package:apollodeliver/Screens/MakeColor/Onboarding/OnboardingInfo.dart';

class OnboardingItems{
  List<OnboardingInfo> items = [
    OnboardingInfo(
        title: "Welcome to Apollo",
        descriptions: "Apollo Delivery App from Apollo Company",
        image: "assets/images/walkthrough/logoBlack.png"),

    OnboardingInfo(
        title: "Safe Packing",
        descriptions: "Ensure your items are packed securely to prevent damage during transit. "
            "Proper packing techniques can make a significant difference in the safety of your shipments.",
        image: "assets/images/walkthrough/packing.gif"),

    OnboardingInfo(
        title: "Fast Delivery",
        descriptions: "Experience swift and reliable delivery services that ensure your packages arrive on time, every time. "
            "We prioritize speed without compromising the safety of your shipments.",
        image: "assets/images/walkthrough/delivery-car.gif"),
  ];
}