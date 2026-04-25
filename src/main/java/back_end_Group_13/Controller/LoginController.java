package back_end_Group_13.Controller;

import org.springframework.web.bind.annotation.RestController;

import back_end_Group_13.Domain.User;
import back_end_Group_13.Service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User u = this.userService.checklogin(user.getUsername(), user.getPassword());
        if (user != null) {
            // Đăng nhập thành công -> Trả về thông tin (không trả về password cho an toàn)
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Đăng nhập thành công");
            response.put("username", user.getUsername());
            response.put("fullName", user.getFullName());

            return ResponseEntity.ok(response);
        } else {
            // Đăng nhập thất bại
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Sai tài khoản hoặc mật khẩu!");

            return ResponseEntity.status(401).body(response);
        }
    }
}
