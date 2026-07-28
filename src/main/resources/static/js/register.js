const REGISTER_API = "http://localhost:8080/api/auth/register";

const registerForm = document.getElementById("registerForm");

const message = document.getElementById("message");

registerForm.addEventListener("submit", registerUser);

async function registerUser(event) {

    event.preventDefault();

    const user = {

        firstName: document.getElementById("firstName").value,

        lastName: document.getElementById("lastName").value,

        email: document.getElementById("email").value,

        password: document.getElementById("password").value,

        phoneNumber: document.getElementById("phoneNumber").value,

        address: document.getElementById("address").value,

        role: document.getElementById("role").value

    };

    try {

        const response = await fetch(REGISTER_API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(user)

        });

        const result = await response.json();

        if (!response.ok) {

            message.className = "error";

            message.innerHTML =
                result.message || "Registration Failed.";

            return;

        }

        message.className = "success";

        message.innerHTML =
            "Registration Successful.";

        setTimeout(() => {

            window.location.href = "login.html";

        }, 1500);

    }

    catch (error) {

        message.className = "error";

        message.innerHTML =
            "Unable to connect to server.";

    }

}