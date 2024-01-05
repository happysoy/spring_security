package spring.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.security.common.annotation.ApiErrorException;
import spring.security.common.exception.AuthExceptionDocs;
import spring.security.common.exception.response.DataMessageResponse;
import spring.security.config.jwt.JwtUtils;
import spring.security.config.security.UserDetailsImpl;
import spring.security.dto.request.ChangePasswordRequest;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserAndTokenResponse;
import spring.security.dto.response.UserInfoResponse;
import spring.security.repository.UserRepository;
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



    // TODO annotation 중복 해결

    @Operation(summary = "Post Client SignUp", description = "클라이언트의 회원가입 요청을 수행합니다")
    @ApiErrorException(AuthExceptionDocs.class)
    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUpUser(@Validated @RequestBody SignUpRequest request, Errors errors) {
//        if (errors.hasErrors()) {
//            ExceptionStatusProvider.throwError(errors);
//        }
//        return message.getDataResponse(userService.signUp(request));
        return userService.signUp(request);
    }

//    @Operation(summary = "Post Client SignIn", description = "클라이언트의 로그인 요청을 수행합니다")
//    @PostMapping("/signin")
//    public ResponseEntity<?> signInUser(@Validated @RequestBody SignInRequest request, Errors errors) {
////        if (errors.hasErrors()) {
////            ExceptionStatusProvider.throwError(errors);
////        }
//        return userService.signIn(request);
////        return userService.signIn(request);
//    }

    @Operation(summary = "Post Client SignOut", description = "회원의 로그아웃 요청을 수행합니다")
    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return userService.signOut(request);
    }

//    @Operation(summary = "Post Access Token Refresh", description = "회원의 액세스 토큰을 재발급합니다")
//    @PostMapping("/refresh-token")
//    public ResponseEntity<UserAndTokenResponse> refreshToken(HttpServletRequest request) {
//
//        return userService.refreshToken(request);
//    }
//
//    @Operation(summary = "Post Password Change", description = "회원의 비밀번호를 변경합니다")
//    @PostMapping("/password-change") // TODO PatchMapping?
//    public CodeMessageResponse changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Validated @RequestBody ChangePasswordRequest request, Errors errors) {
////        if (errors.hasErrors()) {
////            ExceptionStatusProvider.throwError(errors);
////        }
//        userService.changePassword(userDetails.getUser(), request);
//        return message.getSuccessResponse();
//    }
//
//    @Operation(summary = "Post Profile Image", description = "회원의 프로필 이미지 등록 요청을 수행합니다")
//    @PostMapping("/profile-img")
//    public CodeMessageResponse uploadProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails, @Parameter(name="이미지 URL") @RequestParam("image") String imageURL) {
//        userService.uploadProfile(userDetails.getUser(), imageURL);
//        return message.getSuccessResponse();
//    }
//
//    @Operation(summary = "Patch Password Change", description = "회원의 프로필 이미지 삭제 요청을 수행합니다")
//    @PatchMapping("/delete/profile-img")
//    public CodeMessageResponse deleteProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        userService.deleteProfile(userDetails.getUser());
//        return message.getSuccessResponse();
//    }

}
