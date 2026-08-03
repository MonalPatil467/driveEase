const LOGIN_API = "http://localhost:8080/api/auth/login";

const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");

loginForm.addEventListener("submit", loginUser);

async function loginUser(event) {

    event.preventDefault();

    const loginData = {
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value
    };

    try {

        const response = await fetch(LOGIN_API, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginData)
        });

        const result = await response.json();

        console.log("Login Response:", result);

        if (!response.ok) {

            message.className = "error";
            message.innerHTML = result.message || "Invalid email or password.";
            return;
        }

        // Store JWT and user details
        localStorage.setItem("token", result.token);
        localStorage.setItem("role", result.role);
        localStorage.setItem("userId", result.userId);

        console.log("Saved Token:", localStorage.getItem("token"));

        message.className = "success";
        message.innerHTML = "Login Successful.";

        setTimeout(() => {

            if (result.role === "ADMIN") {
                window.location.href = "admin-dashboard.html";
            }
            else if (result.role === "OWNER") {
                window.location.href = "owner-dashboard.html";
            }
            else {
                window.location.href = "index.html";
            }

        }, 1000);

    } catch (error) {

        console.error(error);

        message.className = "error";
        message.innerHTML = "Unable to connect to server.";

    }

}