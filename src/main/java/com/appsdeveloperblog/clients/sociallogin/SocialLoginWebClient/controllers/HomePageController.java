package com.appsdeveloperblog.clients.sociallogin.SocialLoginWebClient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

	@Autowired
	OAuth2AuthorizedClientService oauth2ClientService;

	@GetMapping("/home")
	public String displayHomePage(Model model, @AuthenticationPrincipal OidcUser principal) {
		
		if (principal != null) {
			String name = principal.getAttribute("name");
			model.addAttribute("name",name);
			
			
			Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			
			OAuth2AuthorizedClient oauth2Client = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), 
					oauthToken.getName());
			
			String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
			System.out.println("jwtAccessToken = " + jwtAccessToken);
			
			
			System.out.println("Principal = " + principal);
			
			OidcIdToken idToken = principal.getIdToken();
			String idTokenValue = idToken.getTokenValue();
			System.out.println("idTokenValue = " + idTokenValue);

		}
		
		return "home";
		
	}
	
	
}
