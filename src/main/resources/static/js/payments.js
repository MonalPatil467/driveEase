const token = localStorage.getItem("token");

const bookingAPI = "http://localhost:8080/api/bookings";

const paymentAPI = "http://localhost:8080/api/payments";

const params = new URLSearchParams(window.location.search);

const bookingId = params.get("id");

window.onload = loadBooking;

async function loadBooking() {

    try {

        const response = await fetch(bookingAPI + "/" + bookingId, {

            headers: {

                Authorization: "Bearer " + token

            }

        });

        if (!response.ok) {

            document.getElementById("message").innerHTML =
                    "Unable to load booking details.";

            return;

        }

        const booking = await response.json();

        document.getElementById("bookingId").value =
                booking.id;

        document.getElementById("amount").value =
                "₹" + booking.totalAmount;

    }

    catch (error) {

        document.getElementById("message").innerHTML =
                "Server error.";

    }

}

document.getElementById("payBtn").onclick = async function () {

    const payment = {

        bookingId: bookingId,

        paymentMethod:
            document.getElementById("paymentMethod").value

    };

    try {

        const response = await fetch(paymentAPI, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                Authorization: "Bearer " + token

            },

            body: JSON.stringify(payment)

        });

        if (response.ok) {

            alert("Payment Successful!");

            window.location.href =
                    "review.html?id=" + bookingId;

        }

        else {

            const error = await response.text();

            document.getElementById("message").innerHTML =
                    error;

        }

    }

    catch (error) {

        document.getElementById("message").innerHTML =
                "Payment failed.";

    }

};