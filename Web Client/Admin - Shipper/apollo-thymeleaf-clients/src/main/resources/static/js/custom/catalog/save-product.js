document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("kt_ecommerce_add_product_form");
    const submitButton = document.getElementById("kt_ecommerce_add_product_submit");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        submitButton.disabled = true;
        submitButton.querySelector(".indicator-label").style.display = "none";
        submitButton.querySelector(".indicator-progress").style.display = "inline-block";

        const productName = form.querySelector('input[name="title"]').value;

        if (productName.trim() === "") {
            showErrorAlert("Product Name is required.");
            resetSubmitButton();
            return;
        }

        setTimeout(function () {
            showSuccessAlert().then(() => {
                form.submit();
            });
        }, 2000);
    });

    function showSuccessAlert() {
        return Swal.fire({
            text: "Form has been successfully submitted!",
            icon: "success",
            buttonsStyling: false,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary",
            },
        }).then(() => {
            resetSubmitButton();
        });
    }

    function showErrorAlert(errorMessage) {
        Swal.fire({
            text: errorMessage || "Sorry, looks like there are some errors detected, please try again.",
            icon: "error",
            buttonsStyling: false,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary",
            },
        });
    }

    function resetSubmitButton() {
        submitButton.disabled = false;
        submitButton.querySelector(".indicator-label").style.display = "inline-block";
        submitButton.querySelector(".indicator-progress").style.display = "none";
    }
});
