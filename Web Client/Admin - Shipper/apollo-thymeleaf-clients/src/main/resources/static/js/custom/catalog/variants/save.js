"use strict";

var KTvariantsUpdateVariant = function () {
    const t = document.getElementById("kt_modal_update_variant"),
        e = t.querySelector("#kt_modal_update_variant_form"),
        n = new bootstrap.Modal(t);

    return {
        init: function () {
            document.querySelectorAll('[data-bs-target="#kt_modal_update_variant"]').forEach(button => {
                button.addEventListener("click", function () {
                    const variantId = this.getAttribute("data-variant-id");
                    e.querySelector('input[name="variantId"]').value = variantId;
                });
            });

            const i = t.querySelector('[data-kt-variants-modal-action="submit"]');
            i.addEventListener("click", (event) => {
                event.preventDefault();
                const form = new FormData(e);
                const jsonData = {};

                form.forEach((value, key) => {
                    jsonData[key] = value;
                });

                fetch(e.action, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(jsonData)
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.text();
                    })
                    .then(html => {
                        i.removeAttribute("data-kt-indicator");
                        i.disabled = false;
                        Swal.fire({
                            text: "Variant has been successfully updated!",
                            icon: "success",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: { confirmButton: "btn btn-primary" }
                        }).then((result) => {
                            if (result.isConfirmed) {
                                n.hide();
                                document.body.innerHTML = html;
                                KTUtil.onDOMContentLoaded(() => {
                                    KTvariantsUpdateVariant.init();
                                });
                            }
                        });
                    })
                    .catch(error => {
                        i.removeAttribute("data-kt-indicator");
                        i.disabled = false;
                        Swal.fire({
                            text: "Sorry, there was an error updating the variant.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: { confirmButton: "btn btn-primary" }
                        });
                    });
            });

            t.querySelector('[data-kt-variants-modal-action="cancel"]').addEventListener("click", (t => {
                t.preventDefault();
                e.reset();
                n.hide();
            }));

            t.querySelector('[data-kt-variants-modal-action="close"]').addEventListener("click", (t => {
                t.preventDefault();
                e.reset();
                n.hide();
            }));
        }
    };
}();

KTUtil.onDOMContentLoaded((function () {
    KTvariantsUpdateVariant.init();
}));
