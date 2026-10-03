document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    if (!loginForm) return;

    const errorAlert = document.getElementById("loginError");
    
    // Check if already logged in
    if (localStorage.getItem("hung_hai_mart_access_token")) {
        window.location.href = "/tong-quan.html";
    }

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const submitBtn = document.getElementById("submitBtn");

        try {
            submitBtn.disabled = true;
            submitBtn.textContent = "Đang đăng nhập...";
            errorAlert.style.display = "none";

            const result = await goiApi("/auth/login", {
                method: "POST",
                body: JSON.stringify({ email, password })
            });

            if (result.success && result.data.accessToken) {
                localStorage.setItem("hung_hai_mart_access_token", result.data.accessToken);
                localStorage.setItem("hung_hai_mart_user", JSON.stringify(result.data.user));
                window.location.href = "/tong-quan.html";
            }
        } catch (error) {
            errorAlert.textContent = error.message;
            errorAlert.style.display = "block";
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = "Đăng nhập";
        }
    });
});
