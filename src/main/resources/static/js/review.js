const API = "http://localhost:8080/api/reviews";

const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);

const bookingId = params.get("bookingId");

const reviewForm = document.getElementById("reviewForm");

const message = document.getElementById("message");

reviewForm.addEventListener("submit", submitReview);

async function submitReview(event) {

    event.preventDefault();

    const rating =
        document.getElementById("rating").value;

    const comment =
        document.getElementById("comment").value;

    const review = {

        bookingId: bookingId,

        rating: rating,

        comment: comment

    };

    try {

        const response = await fetch(API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(review)

        });

        const result = await response.json();

        if (!response.ok) {

            message.className = "error";

            message.innerHTML =
                result.message || "Review submission failed.";

            return;

        }

        message.className = "success";

        message.innerHTML =
            "Review submitted successfully.";

        setTimeout(() => {

            window.location.href =
                "my-bookings.html";

        }, 1500);

    }

    catch (error) {

        message.className = "error";

        message.innerHTML =
            "Unable to connect to server.";

    }

}