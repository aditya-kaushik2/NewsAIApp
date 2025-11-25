package com.incapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.incapp.entity.UserAccount;
import com.incapp.repo.UserRepo;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
	@Autowired
    private UserRepo userRepo;
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // Get user details from Google
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String pictureUrl = oAuth2User.getAttribute("picture");
System.out.println(pictureUrl);
	     
        // Save user if not in DB
        userRepo.findById(email).orElseGet(() -> {
            UserAccount user = new UserAccount();
            user.setEmail(email);
            user.setName(name);
	         // Convert to bytes
	   	     byte[] photoBytes = null;
	   	     try (InputStream in = new URL(pictureUrl).openStream()) {
	   	         photoBytes = in.readAllBytes();
	   	     } catch (MalformedURLException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		} catch (IOException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
            user.setPhoto(photoBytes);
            user.setEnable(true);
            user.setRole("ROLE_USER");
            
            return userRepo.save(user);
        });
        
        // Always assign ROLE_USER
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email"   // use "sub" if email not present
        );
    }
}
