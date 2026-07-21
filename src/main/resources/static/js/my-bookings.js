const API = "http://localhost:8080/api/bookings";

const token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", loadBookings);

async function loadBookings() {

    try {

        const response = await fetch(API + "/my", {

            headers: {

                Authorization: "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error();

        }

        const bookings = await response.json();

        displayBookings(bookings);

    }

    catch {

        document.getElementById("bookingContainer").innerHTML =

            `<h3 class="text-danger text-center">
                Unable to load bookings.
            </h3>`;

    }

}

function displayBookings(bookings) {

    const container = document.getElementById("bookingContainer");

    container.innerHTML = "";

    if (bookings.length === 0) {

        container.innerHTML =

            `<h3 class="text-white text-center">
                No Bookings Found
            </h3>`;

        return;

    }

    bookings.forEach(booking => {

        container.innerHTML += `

        <div class="col-md-6 mb-4">

            <div class="card bg-dark text-white h-100">

                <div class="card-body">

                    <h4>${booking.vehicleBrand} ${booking.vehicleModel}</h4>

                    <hr>

                    <p>

                        Pickup :
                        ${booking.pickupDate}

                    </p>

                    <p>

                        Return :
                        ${booking.returnDate}

                    </p>

                    <p>

                        Amount :
                        ₹${booking.totalAmount}

                    </p>

                    <p>

                        Status :
                        ${booking.bookingStatus}

                    </p>

                    <button
                        class="btn btn-danger w-100"
                        onclick="cancelBooking(${booking.id})">

                        Cancel Booking

                    </button>

                </div>

            </div>

        </div>

        `;

    });

}

async function cancelBooking(id) {

    if (!confirm("Cancel this booking?")) {

        return;

    }

    try {

        const response = await fetch(API + "/" + id + "/cancel", {

            method: "PUT",

            headers: {

                Authorization: "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error();

        }

        alert("Booking Cancelled");

        loadBookings();

    }

    catch {

        alert("Unable to cancel booking.");

    }

}