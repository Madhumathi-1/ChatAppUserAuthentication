package com.sessions;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.Condition;
import com.utils.ConditionType;
import com.utils.DbUtils;

import proto.Column;
import proto.TableColumn;
import proto.TableData;

public class SessionUtils {

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final int SESSION_TIMEOUT = Integer.parseInt(System.getProperty("session.timeout", "1800"));
	private static final int MAX_ACTIVE_SESSIONS = 10;

	private static final Map<String, SessionInfo> activeSessions = new LinkedHashMap<String, SessionInfo>(
			MAX_ACTIVE_SESSIONS + 1, 0.75F, true) {
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, SessionInfo> eldest) {
			if (size() > MAX_ACTIVE_SESSIONS) {
				SessionInfo sessionToRemove = eldest.getValue();
				long lastActivityTime = sessionToRemove.getLastActivityTime();

				updateSessionActivityInDatabase(sessionToRemove.getSessionId(), lastActivityTime);

				return true;
			}
			return false;
		}
	};

	// Store the session in the map and database
	public static void createAndStoreSession(int userIdGeneratedId, HttpServletResponse response) {
		try {
			String sessionId = generateSessionId();
			Timestamp creationTime = new Timestamp(System.currentTimeMillis());

			boolean storeSessionInDb = storeSessionInDatabase(sessionId, userIdGeneratedId, creationTime);

			if (storeSessionInDb) {
				SessionInfo sessionInfo = new SessionInfo(userIdGeneratedId, sessionId, creationTime.getTime());
				setSessionCookie(response, sessionId);
				activeSessions.put(sessionId, sessionInfo);
			}

			System.out.println("Successfully created and stored session for user ID: " + userIdGeneratedId
					+ "\nSession ID: " + sessionId);

		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public static String generateSessionId() {
		byte[] randomBytes = new byte[32];
		secureRandom.nextBytes(randomBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	}

	private static void setSessionCookie(HttpServletResponse response, String sessionId) {
		Cookie sessionCookie = new Cookie("session_id", sessionId);
		sessionCookie.setMaxAge(SESSION_TIMEOUT);
		sessionCookie.setHttpOnly(true);
		sessionCookie.setSecure(true);
		response.addCookie(sessionCookie);
	}

	// Session Store in database
	public static boolean storeSessionInDatabase(String sessionId, int userIdGeneratedId, Timestamp creationTime) {
		try {
			TableData.Builder sessionDataBuilder = TableData.newBuilder().setTableName("Sessions");

			Column sessionIdBuilder = Column.newBuilder().setName(TableColumn.SESSION_ID).setValue(sessionId).build();
			Column userIdBuilder = Column.newBuilder().setName(TableColumn.USER_ID)
					.setValue(String.valueOf(userIdGeneratedId)).build();
			Column creationTimeBuilder = Column.newBuilder().setName(TableColumn.SESSION_CREATION_TIME)
					.setValue(String.valueOf(creationTime.getTime())).build();

			sessionDataBuilder.addColumns(sessionIdBuilder);
			sessionDataBuilder.addColumns(userIdBuilder);
			sessionDataBuilder.addColumns(creationTimeBuilder);

			TableData sessionData = sessionDataBuilder.build();

			int sessionResult = DbUtils.performInsert(sessionData);
			if (sessionResult > 0) {
				System.out.println("Successfully stored session in the database for sessionId: " + sessionId);
			}

			return sessionResult > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("Error storing session in the database...");
		}
		return false;
	}

	// Session validation
	public static boolean isValidSession(String sessionId) {
		try {
			SessionInfo sessionInfo = activeSessions.get(sessionId);

			if (sessionInfo == null) {
				return false;
			}
			long creationTime = sessionInfo.getCreationTime();
			long currentTime = System.currentTimeMillis();
			long sessionTimeout = SESSION_TIMEOUT * 1000;

			if (currentTime - creationTime > sessionTimeout) {
				invalidateSession(sessionId);
				return false;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}

	public static void updateSessionActivity(String sessionId) {
		SessionInfo sessionInfo = activeSessions.get(sessionId);
		long currentTime = System.currentTimeMillis();

		if (sessionInfo != null) {
			sessionInfo.setLastActivityTime(currentTime);
			activeSessions.put(sessionId, sessionInfo);
		}
	}

	public static void updateSessionActivityInDatabase(String sessionId, long lastActivityTime) {
		try {
			long currentTime = System.currentTimeMillis();

			if (sessionId != null) {
				long sessionTimeout = SESSION_TIMEOUT * 1000;

				TableData.Builder sessionDataBuilder = TableData.newBuilder().setTableName("Sessions");

				Column lastActivityTimeBuilder = Column.newBuilder().setName(TableColumn.SESSION_LAST_ACTIVITY_TIME)
						.setValue(String.valueOf(lastActivityTime)).build();

				sessionDataBuilder.addColumns(lastActivityTimeBuilder);

				List<Condition> conditions = new ArrayList<>();
				conditions.add(new Condition(TableColumn.SESSION_ID.toString(), sessionId, ConditionType.EQUALS));

				long threshold = 2 * 60 * 1000; // set time for 2mins remains active

				if (currentTime - lastActivityTime <= sessionTimeout) {
					// checks whether the session expired or not
					if (currentTime - lastActivityTime >= threshold) {
						// if the session still and make the sessions active state into more than 2 mins
						// remains active
						DbUtils.performUpdate(sessionDataBuilder.build(), conditions);// update the session last
																						// activity time into the
																						// database
					}
				} else if (currentTime - lastActivityTime > sessionTimeout) {
					// invalidate the session if the sessions are timedout
					invalidateSession(sessionId);
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	// Session invalidation
	public static String invalidateSession(String sessionId) {
		if (sessionId != null) {
//			activeSessions.remove(sessionId);
			invalidateSessionInDb(sessionId);
		}
		return sessionId;
	}

	private static void invalidateSessionInDb(String sessionId) {
		try {
			TableData.Builder sessionDataBuilder = TableData.newBuilder().setTableName("Sessions");

			List<Condition> conditions = new ArrayList<>();
			conditions.add(new Condition(TableColumn.SESSION_ID.toString(), sessionId, ConditionType.EQUALS));

			DbUtils.performDelete(sessionDataBuilder.build(), conditions);
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("Error! invalidating session in the database...");
		}
	}

	public static void invalidateSessionInCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("session_id".equals(cookie.getName())) {
					String sessionId = cookie.getValue();
					invalidateSession(sessionId);
					cookie.setMaxAge(0);
					break;
				}
			}
		}
	}
}
