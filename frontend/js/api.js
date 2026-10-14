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

function getAnhUrl(path) {
    if (!path) return "";
    if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("data:")) return path;
    const baseHost = window.location.hostname || "localhost";
    if (path.startsWith("/")) return `http://${baseHost}:8080${path}`;
    return `http://${baseHost}:8080/${path}`;
}
window.getAnhUrl = getAnhUrl;

async function cropAndCompressImage(file, targetSize = 600) {
    return new Promise((resolve, reject) => {
        if (!file.type.startsWith('image/')) {
            resolve(file);
            return;
        }
        const reader = new FileReader();
        reader.onerror = () => resolve(file);
        reader.onload = (e) => {
            const img = new Image();
            img.onerror = () => resolve(file);
            img.onload = () => {
                try {
                    // Cắt ảnh theo tỉ lệ vuông 1:1 từ tâm ảnh
                    const minDim = Math.min(img.width, img.height);
                    const startX = (img.width - minDim) / 2;
                    const startY = (img.height - minDim) / 2;

                    const canvas = document.createElement('canvas');
                    canvas.width = targetSize;
                    canvas.height = targetSize;
                    const ctx = canvas.getContext('2d');

                    ctx.imageSmoothingEnabled = true;
                    ctx.imageSmoothingQuality = 'high';

                    // Vẽ phần tâm vuông vào canvas đích
                    ctx.drawImage(img, startX, startY, minDim, minDim, 0, 0, targetSize, targetSize);

                    canvas.toBlob((blob) => {
                        if (!blob) {
                            resolve(file);
                            return;
                        }
                        const croppedFile = new File([blob], file.name.replace(/\.[^/.]+$/, "") + ".jpg", {
                            type: "image/jpeg",
                            lastModified: Date.now()
                        });
                        resolve(croppedFile);
                    }, "image/jpeg", 0.88);
                } catch(err) {
                    resolve(file);
                }
            };
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    });
}
window.cropAndCompressImage = cropAndCompressImage;

async function taiLenAnh(file) {
    // Tự động crop vuông và nén ảnh nếu ảnh to trước khi gửi lên server
    const processedFile = await cropAndCompressImage(file, 600);
    
    const token = localStorage.getItem("hung_hai_mart_access_token");
    const formData = new FormData();
    formData.append("file", processedFile);

    const response = await fetch(`${API_URL}/upload`, {
        method: "POST",
        headers: {
            ...(token ? { Authorization: `Bearer ${token}` } : {})
        },
        body: formData
    });

    const result = await response.json();
    if (!response.ok) {
        throw new Error(result.message || "Tải ảnh thất bại");
    }
    return result.data.url;
}
window.taiLenAnh = taiLenAnh;


