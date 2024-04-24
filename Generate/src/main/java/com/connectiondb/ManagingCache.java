package com.connectiondb;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sessions.SessionInfo;
import com.sessions.SessionUtils;

public class ManagingCache {

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final int MAX_ACTIVE_SESSIONS = 3;

	private static final Map<String, SessionInfo> activeSessions = new LinkedHashMap<String, SessionInfo>(
			MAX_ACTIVE_SESSIONS + 1, 0.75F, true) {
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, SessionInfo> eldest) {
			if (size() > MAX_ACTIVE_SESSIONS) {
				SessionInfo sessionToRemove = eldest.getValue();
				long lastActivityTime = sessionToRemove.getLastActivityTime();
				SessionUtils.updateSessionActivityInDatabase(sessionToRemove.getSessionId(), lastActivityTime);
				return true;
			}
			return false;
		}
		
	};
	private static final int SESSION_TIMEOUT = Integer.parseInt(System.getProperty("session.timeout", "1800"));

	public static String generateSessionId() {
		byte[] randomBytes = new byte[32];
		secureRandom.nextBytes(randomBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	}
	
	private static final Map<String, String> clientMap = new HashMap<>();

	public static void main(String[] args) {

	}
	
}