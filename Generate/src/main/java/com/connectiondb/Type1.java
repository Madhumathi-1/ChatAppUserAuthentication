package com.connectiondb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sessions.SessionInfo;
import com.utils.Condition;
import com.utils.ConditionType;
import com.utils.DbUtils;

import proto.Column;
import proto.TableColumn;
import proto.TableData;


public class Type1 {
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
			return size() > MAX_ACTIVE_SESSIONS;
		}
	};
 
	public static void updateSessionActivityInDatabase(String sessionId, long lastActivityTime) {
		try {
			long currentTime = System.currentTimeMillis();

			if (sessionId != null) {
				SessionInfo sessionInfo = activeSessions.get(sessionId);

				if (sessionInfo != null) {
					long sessionTimeout = SESSION_TIMEOUT * 1000;

					if (currentTime - lastActivityTime > sessionTimeout) {
//						invalidateSession(sessionId);
					} else {
						sessionInfo.setLastActivityTime(currentTime);
//						activeSessions.put(sessionId, sessionInfo);

						long threshold = 2 * 60 * 1000;
						if (currentTime - lastActivityTime >= threshold) {
							updateSessionInDatabase(sessionInfo);
						}
					}
				}
			}
		} catch (Exception ee) {
			System.out.println("Error! While updating the session last activity time in db...");
			ee.printStackTrace();
		}
	}

	private static void updateSessionInDatabase(SessionInfo sessionInfo) {
		try {
			TableData.Builder sessionDataBuilder = TableData.newBuilder().setTableName("Sessions");

			Column sessionIdBuilder = Column.newBuilder().setName(TableColumn.SESSION_ID)
					.setValue(sessionInfo.getSessionId()).build();
			Column lastActivityTimeBuilder = Column.newBuilder().setName(TableColumn.SESSION_LAST_ACTIVITY_TIME)
					.setValue(String.valueOf(sessionInfo.getLastActivityTime())).build();

			sessionDataBuilder.addColumns(sessionIdBuilder);
			sessionDataBuilder.addColumns(lastActivityTimeBuilder);

			List<Condition> conditions = new ArrayList<>();
			conditions.add(
					new Condition(TableColumn.SESSION_ID.toString(), sessionInfo.getSessionId(), ConditionType.EQUALS));

			DbUtils.performUpdate(sessionDataBuilder.build(), conditions);
		} catch (Exception ee) {
			System.out.println("Error! While updating the session in the database...");
			ee.printStackTrace();
		}
	}


}
