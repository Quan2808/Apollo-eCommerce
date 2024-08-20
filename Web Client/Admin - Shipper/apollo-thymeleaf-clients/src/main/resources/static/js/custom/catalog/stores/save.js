document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("kt_ecommerce_add_store_form");
    const submitButton = document.getElementById("kt_ecommerce_add_store_submit");

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        submitButton.disabled = true;
        submitButton.querySelector(".indicator-label").style.display = "none";
        submitButton.querySelector(".indicator-progress").style.display = "inline-block";

        const storeAttribute = form.querySelector('input[name="name"]').value;

        if (storeAttribute.trim() === "") {
            showErrorAlert("Store Name is required.");
            resetSubmitButton();
            return;
        }

        // Sử dụng Fetch API để gửi form
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (!response.ok) {
                // Nếu có lỗi từ server, kiểm tra và hiển thị thông báo lỗi
                return response.json().then(data => {
                    showErrorAlert(data.error || "Failed to create store.");
                    resetSubmitButton();
                });
            } else {
                // Nếu không có lỗi, hiển thị thông báo thành công
                showSuccessAlert().then(() => {
                    window.location.href = "/dashboard/catalog/stores"; // Chuyển hướng sau khi thành công
                });
            }
        }).catch(error => {
            // Xử lý lỗi nếu có vấn đề với request
            showErrorAlert("An unexpected error occurred.");
            resetSubmitButton();
        });
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

const input = document.getElementById('file-input');
const image = document.getElementById('img-preview');

input.addEventListener('change', (e) => {
    if (e.target.files.length) {
        const src = URL.createObjectURL(e.target.files[0]);
        image.src = src;
    }
});
