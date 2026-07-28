const BOOKING_API = "http://localhost:8080/api/bookings";

const VEHICLE_API = "http://localhost:8080/api/vehicles";

const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);

const vehicleId = params.get("id");

let pricePerDay = 0;

loadVehicle();

document.getElementById("pickupDate")
    .addEventListener("change", calculateAmount);

document.getElementById("returnDate")
    .addEventListener("change", calculateAmount);

document.getElementById("bookBtn")
    .addEventListener("click", bookVehicle);

async function loadVehicle() {

    try {

        const response = await fetch(VEHICLE_API + "/" + vehicleId);

        if (!response.ok) {

            throw new Error("Unable to load vehicle.");

        }

        const vehicle = await response.json();

        pricePerDay = vehicle.pricePerDay;

    }

    catch (error) {

        document.getElementById("message").innerHTML =
            error.message;

    }

}

function calculateAmount() {

    const pickupDate =
        document.getElementById("pickupDate").value;

    const returnDate =
        document.getElementById("returnDate").value;

    if (!pickupDate || !returnDate) {

        return;

    }

    const start = new Date(pickupDate);

    const end = new Date(returnDate);

    const days =
        (end - start) / (1000 * 60 * 60 * 24) + 1;

    if (days <= 0) {

        document.getElementById("totalAmount").innerHTML =
            "Invalid Date Selection";

        return;

    }

    const total = days * pricePerDay;

    document.getElementById("totalAmount").innerHTML =
        "Total Amount : ₹" + total;

}

async function bookVehicle() {

    const pickupDate =
        document.getElementById("pickupDate").value;

    const returnDate =
        document.getElementById("returnDate").value;

    if (!pickupDate || !returnDate) {

        document.getElementById("message").innerHTML =
            "Please select dates.";

        return;

    }

    const booking = {

        vehicleId: vehicleId,

        pickupDate: pickupDate,

        returnDate: returnDate

    };

    try {

        const response = await fetch(BOOKING_API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(booking)

        });

        const result = await response.json();

        if (!response.ok) {

            document.getElementById("message").innerHTML =
                result.message;

            return;

        }

        window.location.href =
            "payment.html?bookingId=" + result.id;

    }

    catch (error) {

        document.getElementById("message").innerHTML =
            "Booking Failed.";

    }

}