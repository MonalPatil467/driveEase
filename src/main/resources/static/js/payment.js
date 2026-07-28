const PAYMENT_API = "http://localhost:8080/api/payments";

const BOOKING_API = "http://localhost:8080/api/bookings";

const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);

const bookingId = params.get("bookingId");

loadBooking();

document.getElementById("payBtn")
    .addEventListener("click", makePayment);

async function loadBooking() {

    try {

        const response = await fetch(BOOKING_API + "/" + bookingId, {

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error("Unable to load booking.");

        }

        const booking = await response.json();

        document.getElementById("bookingId").innerHTML =
            booking.id;

        document.getElementById("vehicleName").innerHTML =
            booking.vehicle.brand + " " + booking.vehicle.model;

        document.getElementById("amount").innerHTML =
            "₹" + booking.totalAmount;

    }

    catch (error) {

        document.getElementById("message").innerHTML =
            error.message;

    }

}

async function makePayment() {

    const paymentMethod =
        document.getElementById("paymentMethod").value;

    const payment = {

        bookingId: bookingId,

        paymentMethod: paymentMethod

    };

    try {

        const response = await fetch(PAYMENT_API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(payment)

        });

        const result = await response.json();

        if (!response.ok) {

            document.getElementById("message").innerHTML =
                result.message;

            return;

        }

        alert("Payment Successful.");

        window.location.href =
            "my-bookings.html";

    }

    catch (error) {

        document.getElementById("message").innerHTML =
            "Payment Failed.";

    }

}