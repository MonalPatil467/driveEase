const API = "http://localhost:8080/api/bookings";

const token = localStorage.getItem("token");

const bookingTable = document.getElementById("bookingTable");

const message = document.getElementById("message");

loadBookings();

async function loadBookings() {

    try {

        const response = await fetch(API + "/my-bookings", {

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error("Unable to load bookings.");

        }

        const bookings = await response.json();

        bookingTable.innerHTML = "";

        if (bookings.length === 0) {

            message.innerHTML = "No Bookings Found.";

            return;

        }

        bookings.forEach(booking => {

            bookingTable.innerHTML += `

                <tr>

                    <td>

                        ${booking.id}

                    </td>

                    <td>

                        ${booking.vehicle.brand}
                        ${booking.vehicle.model}

                    </td>

                    <td>

                        ${booking.pickupDate}

                    </td>

                    <td>

                        ${booking.returnDate}

                    </td>

                    <td>

                        ₹${booking.totalAmount}

                    </td>

                    <td>

                        ${booking.bookingStatus}

                    </td>

                    <td>

                        <a href="review.html?bookingId=${booking.id}"

                           class="btn btn-sm btn-secondary">

                            Review

                        </a>

                    </td>

                </tr>

            `;

        });

    }

    catch (error) {

        message.innerHTML = error.message;

    }

}