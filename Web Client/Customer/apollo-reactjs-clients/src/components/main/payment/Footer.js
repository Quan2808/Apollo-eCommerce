import React from "react";

function Footer() {
    return (
      <div className="flex flex-col text-sm py-5 px-20">
        <div class="border-t border-gray-400 py-3">
          <span className="text-xs">
            * Why has sales tax been applied? See tax and seller information.
          </span>
          <span className="py-2 text-xs">
            Need help? Check our Help pages or contact us
          </span>
          <span className="py-2 text-xs">
            For an item sold by Amazon.com: When you click the "Place your
            order" button, we'll send you an email message acknowledging receipt
            of your order. Your contract to purchase an item will not be
            complete until we send you an email notifying you that the item has
            been shipped.
          </span>
          <span className="py-2 text-xs">
            All items in this order are sold by Amazon Export Sales LLC (AES),
            unless otherwise noted. By placing your order, you authorize AES to
            designate a carrier to clear the package and pay the import fees on
            your (or the recipient's) behalf. Customs declarations will be made
            in the name and on the behalf of your (or the recipient's) behalf by
            the designated carrier. You can find the complete terms and
            conditions of your order here
          </span>
          <span className="py-2 text-xs">
            Important information about sales tax you may owe in your state
          </span>
          <span className="py-2 text-xs">
            You may return new, unopened merchandise in original condition
            within 30 days of delivery. Exceptions and restrictions apply. See
            Amazon.com's Returns Policy.
          </span>
          <span className="py-2 text-xs">
            Need to add more items to your order? Continue shopping on the
            Amazon.com homepage.
          </span>
        </div>
      </div>
    );
}

export default Footer;