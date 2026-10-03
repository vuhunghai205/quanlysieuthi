#!/bin/bash
for file in frontend/*.html; do
    if [ "$file" != "frontend/index.html" ]; then
        # Thêm nút Đổi mật khẩu
        sed -i 's/<button onclick="logout()" class="btn">Đăng xuất<\/button>/<button onclick="showChangePasswordModal()" class="btn" style="border: 1px solid var(--border); margin-right: 10px;">Đổi mật khẩu<\/button><button onclick="logout()" class="btn">Đăng xuất<\/button>/g' "$file"
        
        # Thêm Modal đổi mật khẩu trước </body>
        sed -i '/<\/body>/i \
    <!-- Modal Đổi Mật Khẩu -->\
    <div class="modal" id="changePasswordModal" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1000; align-items: center; justify-content: center;">\
        <div class="modal-content" style="background: var(--card); padding: 2rem; border-radius: var(--radius-lg); width: 100%; max-width: 400px;">\
            <h3 style="margin-bottom: 1rem; color: var(--primary);">Đổi Mật Khẩu</h3>\
            <div id="pwdError" class="alert alert-danger" style="display:none;"></div>\
            <form id="changePasswordForm">\
                <div class="form-group">\
                    <label class="form-label">Mật khẩu hiện tại</label>\
                    <input type="password" id="pwd_current" class="form-control" required>\
                </div>\
                <div class="form-group">\
                    <label class="form-label">Mật khẩu mới</label>\
                    <input type="password" id="pwd_new" class="form-control" required pattern="^[A-Z](?=.*\\\\d)(?=.*[@$!%*?&.])[A-Za-z\\\\d@$!%*?&.]{7,}$" title="Mật khẩu phải dài ít nhất 8 ký tự, bắt đầu bằng chữ hoa, có số và ký tự đặc biệt">\
                </div>\
                <div class="form-group">\
                    <label class="form-label">Xác nhận mật khẩu</label>\
                    <input type="password" id="pwd_confirm" class="form-control" required>\
                </div>\
                <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">\
                    <button type="button" class="btn" style="flex: 1; border: 1px solid var(--border);" onclick="hideChangePasswordModal()">Hủy</button>\
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Xác nhận</button>\
                </div>\
            </form>\
        </div>\
    </div>\
    <script>\
        function showChangePasswordModal() {\
            document.getElementById("changePasswordModal").style.display = "flex";\
        }\
        function hideChangePasswordModal() {\
            document.getElementById("changePasswordModal").style.display = "none";\
        }\
        if (document.getElementById("changePasswordForm")) {\
            document.getElementById("changePasswordForm").addEventListener("submit", async (e) => {\
                e.preventDefault();\
                const current = document.getElementById("pwd_current").value;\
                const newPwd = document.getElementById("pwd_new").value;\
                const confirm = document.getElementById("pwd_confirm").value;\
                if (newPwd !== confirm) {\
                    const err = document.getElementById("pwdError");\
                    err.textContent = "Mật khẩu xác nhận không khớp";\
                    err.style.display = "block";\
                    return;\
                }\
                try {\
                    await goiApi("/auth/change-password", {\
                        method: "POST",\
                        body: JSON.stringify({\
                            currentPassword: current,\
                            newPassword: newPwd,\
                            confirmPassword: confirm\
                        })\
                    });\
                    alert("Đổi mật khẩu thành công! Vui lòng đăng nhập lại.");\
                    logout();\
                } catch (err) {\
                    const errDiv = document.getElementById("pwdError");\
                    errDiv.textContent = err.message;\
                    errDiv.style.display = "block";\
                }\
            });\
        }\
    </script>' "$file"
    fi
done
