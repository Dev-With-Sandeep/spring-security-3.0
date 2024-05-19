package com.spring.security.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security.entity.RefreshToken;
import com.spring.security.repository.RefreshTokenRepository;
import com.spring.security.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public RefreshToken createRefreshToken(String userName) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(userRepository.findByEmail(userName).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(600000));
		refreshToken.setToken(UUID.randomUUID().toString());
		RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
		return savedRefreshToken;
	}

	public Optional<RefreshToken> findByToken(String token) {
		Optional<RefreshToken> findByTokenString = refreshTokenRepository.findByToken(token);
		return findByTokenString;
	}

	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(refreshToken);
			throw new RuntimeException("Session Expired. Please Sign in");
		}
		return refreshToken;
	}

}
