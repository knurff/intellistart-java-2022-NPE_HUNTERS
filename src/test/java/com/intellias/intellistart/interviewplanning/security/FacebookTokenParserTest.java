package com.intellias.intellistart.interviewplanning.security;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.exception.InvalidFacebookTokenException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class FacebookTokenParserTest {

  private final static String INVALID_TOKEN = "invalid_token";
  private static FacebookTokenParser facebookTokenParser;

  @BeforeAll
  public static void init() {
    RestTemplate restTemplate = new RestTemplate();
    facebookTokenParser = new FacebookTokenParser(restTemplate);
  }


  @Test
  void parseUserDataThrowsAnExceptionIfFacebookTokenInvalid() {
    assertThrows(InvalidFacebookTokenException.class,
        () -> facebookTokenParser.parseUserData(INVALID_TOKEN));
  }
}