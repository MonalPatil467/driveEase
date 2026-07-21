const params = new URLSearchParams(window.location.search);

const vehicleId = params.get("id");

const API = "http://localhost:8080/api/bookings";

document.getElementById("bookBtn").addEventListener("click", createBooking);

async function createBooking() {

    const token = localStorage.getItem("token");

    const pickupDate = document.getElementById("pickupDate").value;

    const returnDate = document.getElementById("returnDate").value;

    if (pickupDate === "" || returnDate === "") {

        document.getElementById("message").innerHTML =
            "Please select both dates.";

        return;
    }

    const booking = {

        vehicleId: vehicleId,

        pickupDate: pickupDate,

        returnDate: returnDate

    };

    try {

        const response = await fetch(API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(booking)

        });

        if (!response.ok) {

            throw new Error();

        }

        const data = await response.json();

        document.getElementById("message").className =
            "text-success mt-3";

        document.getElementById("message").innerHTML =
            "Booking Successful.";

        document.getElementById("totalAmount").innerHTML =
            "Total Amount : ₹ " + data.totalAmount;

        setTimeout(() => {

            window.location.href = "my-bookings.html";

        }, 1500);

    }

    catch {

        document.getElementById("message").className =
            "text-danger mt-3";

        document.getElementById("message").innerHTML =
            "Booking Failed.";

    }

}