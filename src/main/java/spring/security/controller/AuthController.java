package spring.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.security.config.jwt.JwtUtils;
import spring.security.config.security.UserDetailsImpl;
import spring.security.domain.User;
import spring.security.dto.request.ChangePasswordRequest;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.exception.response.CodeMessageResponse;
import spring.security.exception.response.DataMessageResponse;
import spring.security.exception.ExceptionStatusProvider;
import spring.security.exception.response.MessageResponse;
import spring.security.repository.UserRepository;
import spring.security.service.MessageResponseService;
import spring.security.service.UserService;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description="Auth API")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final MessageResponseService message;

    // TODO annotation 중복 해결

    @Operation(summary = "Post Client Signup", description = "클라이언트의 회원가입 요청을 수행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2200", description = "중복된 이메일입니다", content = {@Content(schema = @Schema(implementation = CodeMessageResponse.class))}),
            @ApiResponse(responseCode = "2201", description = "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
            @ApiResponse(responseCode = "2202", description = "아이디 또는 비밀번호를 확인해주세요"),
    })
    @PostMapping("/signup")
    public DataMessageResponse<SignUpRequest> signUpUser(@Validated @RequestBody SignUpRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        return message.getDataResponse(userService.singUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Validated @RequestBody SignInRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        return userService.signIn(request);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return userService.signOut(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        return userService.refreshToken(request);
    }

    @PostMapping("/password-change")
    public CodeMessageResponse changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Validated @RequestBody ChangePasswordRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        userService.changePassword(userDetails.getUser(), request);
        return message.getSuccessResponse();
    }

    @Operation(summary = "Post Profile Image", description = "회원의 프로필 이미지 등록 요청을 수행합니다")
    @PostMapping("/profile-img")
    public CodeMessageResponse uploadProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails, @Parameter(name="이미지 URL") @RequestParam("image") String imageURL) {
        userService.uploadProfile(userDetails.getUser(), imageURL);
        return message.getSuccessResponse();
    }

    @PatchMapping("/delete/profile-img")
    public CodeMessageResponse deleteProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteProfile(userDetails.getUser());
        return message.getSuccessResponse();
    }

}
