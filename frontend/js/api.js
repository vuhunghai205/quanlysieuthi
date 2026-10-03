const API_URL = "http://localhost:8080/api";

async function goiApi(path, options = {}) {
    const token = localStorage.getItem("hung_hai_mart_access_token");
    const response = await fetch(`${API_URL}${path}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(token ? { Authorization: `Bearer ${token}` } : {}),
            ...(options.headers || {})
        }
    });

    if (response.status === 401) {
        localStorage.removeItem("hung_hai_mart_access_token");
        localStorage.removeItem("hung_hai_mart_user");
        window.location.href = "/dang-nhap.html";
        return null;
    }

    const result = await response.json();
    if (!response.ok) {
        throw new Error(result.message || "Có lỗi xảy ra");
    }
    return result;
}

function checkAuth() {
    const token = localStorage.getItem("hung_hai_mart_access_token");
    if (!token && !window.location.pathname.includes("dang-nhap.html")) {
        window.location.href = "/dang-nhap.html";
    }
}

function getUser() {
    const userStr = localStorage.getItem("hung_hai_mart_user");
    return userStr ? JSON.parse(userStr) : null;
}

function logout() {
    localStorage.removeItem("hung_hai_mart_access_token");
    localStorage.removeItem("hung_hai_mart_user");
    window.location.href = "/dang-nhap.html";
}
