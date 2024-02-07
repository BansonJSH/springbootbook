package me.banson.springbootbook.config.oauth2;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.User;
import me.banson.springbootbook.dto.OAuth2UserInfo;
import me.banson.springbootbook.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.builder()
                .name(user.getAttribute("email"))
                .attributes(user.getAttributes())
                .authorities(user.getAuthorities()).build();
        UsernamePasswordAuthenticationToken.authenticated(oAuth2UserInfo, "", List.of(new SimpleGrantedAuthority("user")));
        return oAuth2UserInfo;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String password = String.valueOf(UUID.randomUUID());
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .password(password)
                        .build());
        return userRepository.save(user);
    }
}
