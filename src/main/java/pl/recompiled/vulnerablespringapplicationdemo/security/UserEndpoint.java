package pl.recompiled.vulnerablespringapplicationdemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
class UserEndpoint {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody CreateUserDto dto) {
        final String newUserId = userService.createUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("id", newUserId));
    }

    @GetMapping("activate/{token}")
    public ResponseEntity<Void> activate(@PathVariable String token) {
        userService.activateUser(token);
        return ResponseEntity.ok().build();
    }
}
