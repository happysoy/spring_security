package spring.security.dto.response;

import java.util.List;


public record JwtResponse(String accessToken, String type, Long userId, String username, String email, String role) {

   public JwtResponse(String accessToken, Long userId, String username, String email, String role) {
       this(accessToken, "Bearer", userId, username, email, role);
   }

}