class HelperException {
  static String getAuthErrorMessage(e) {
    String errorMessage = 'An error occurred. Please try again.';

    if (e.contains('User not found')) {
      errorMessage = 'Invalid User or Password. Please try again.';
    } else if (e.contains('Wrong password')) {
      errorMessage = 'Invalid User or Password. Please try again.';
    } else if (e.contains('User already exists')) {
      errorMessage =
          'The email is already registered. Please try logging in or use a different email to register.';
    } else if (e.contains('Phone number already exists')) {
      errorMessage =
          'The Phone number is already registered. Please try a different to register.';
    } else if (e.contains('User has not been confirmed')) {
      errorMessage =
          'Your account has not been confirmed. Please check your email for the confirmation link.';
    } else {
      errorMessage = 'An error occurred. Please try again.';
    }

    return errorMessage;
  }
}
