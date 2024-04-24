package com.sessions;

//In-memory for fast access and retrieval
public class SessionInfo {
	private String sessionId;
	private final int userId;
	private long creationTime;
	private long lastActivityTime;
	
	public SessionInfo(int userId, String sessionId, long creationTime) {
		this.userId = userId;
		this.sessionId = sessionId;
		this.creationTime = creationTime;
		this.lastActivityTime = creationTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(long lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public int getUserId() {
		return userId;
	}
	

}
