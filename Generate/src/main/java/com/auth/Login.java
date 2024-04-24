package com.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sessions.SessionUtils;
import com.utils.Condition;
import com.utils.ConditionType;
import com.utils.DbUtils;

import proto.TableColumn;
import proto.TableData;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			int userId = isValidUser(username, password);

			if (userId > 0) {
				SessionUtils.createAndStoreSession(userId, response);
				System.out.println("Stored Successfully...");
				response.sendRedirect("homepage.jsp");
			} else {
				System.out.println("Error!");
				response.sendRedirect("signup.jsp");
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("Error: " + ee.getMessage());
		}
	}

	private int isValidUser(String username, String password) {
		try {
			return validateUserCredentials(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private int getUserIdFromDatabase(String username, String password) {
		try {
			TableData.Builder userDataBuilder = TableData.newBuilder().setTableName("User");

			List<Condition> conditions = new ArrayList<>();
			conditions.add(new Condition(TableColumn.USERNAME.toString(), username, ConditionType.EQUALS));

			String userDataString = DbUtils.performSelect(userDataBuilder.build(), null, conditions, 1);

			String[] userDataParts = userDataString.trim().split("\\s+");

			if (userDataParts.length > 0 && !userDataParts[0].isEmpty()) {
				return Integer.parseInt(userDataParts[0]);
			} else {
				System.out.println("Invalid user data format: " + userDataString);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private String getPasswordHashFromDatabase(int userId) {
		try {
			TableData.Builder passwordDataBuilder = TableData.newBuilder().setTableName("User");

			List<Condition> conditions = new ArrayList<>();
			conditions.add(new Condition(TableColumn.USER_ID.toString(), userId, ConditionType.EQUALS));

			String passwordHash = DbUtils.performSelect(passwordDataBuilder.build(), null, conditions, 1);

			String[] passwordParts = passwordHash.trim().split("\\s+");

			if (passwordParts.length > 0) {
				return passwordParts[0];
			} else {
				System.out.println("Invalid password data format: " + passwordHash);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private int validateUserCredentials(String username, String password) {
		try {
			int userId = getUserIdFromDatabase(username, password);

			if (userId > 0) {
				String storedPasswordHash = getPasswordHashFromDatabase(userId);

				if (storedPasswordHash != null) {
					return userId;
				}
			} else {
				System.out.println("Invalid User...");

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while validating...");
		}
		return 0;
	}
}
