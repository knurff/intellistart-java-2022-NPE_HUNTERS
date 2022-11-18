package com.intellias.intellistart.interviewplanning.security;

import com.intellias.intellistart.interviewplanning.controller.dto.FacebookResponseDto;
import com.intellias.intellistart.interviewplanning.exception.InvalidFacebookTokenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Parses Facebook token and retrieves user data.
 */
@Component
@AllArgsConstructor
public class FacebookTokenParser {

  private static final String URL = "https://graph.facebook.com/me/?fields=email&access_token=%s";
  private final RestTemplate restTemplate;

  /**
   * Parses given Facebook token and retrieves user data from it.
   */
  public FacebookResponseDto parseUserData(String token) {
    try {
      return restTemplate.getForObject(
          String.format(URL, token), FacebookResponseDto.class);
    } catch (HttpClientErrorException e) {
      throw new InvalidFacebookTokenException("Invalid Facebook token");
    }
  }
}
