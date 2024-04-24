package com.dboperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.Condition;
import com.utils.ConditionType;
import com.utils.DbUtils;

import proto.Column;
import proto.TableColumn;
import proto.TableData;

public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String dob = request.getParameter("dob");

		try {
			isValidUser(userName, dob);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	private String isValidUser(String username, String dob) {
		try {
			return getUserId(username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getUserData(String username) throws Exception {
		TableData.Builder userDataBuilder = TableData.newBuilder().setTableName("User");

		Column.Builder usernameBuilder = Column.newBuilder().setName(TableColumn.USERNAME).setValue(username);
//		Column.Builder userIdBuilder = Column.newBuilder().setName(TableColumn.USER_ID).setValue();
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(new Condition(TableColumn.USER_ID.toString(), username, ConditionType.EQUALS));

		String result = DbUtils.performSelect(userDataBuilder.build(), null, conditions, 1);
		return 0;
	}

	public static String getUserId(String username) {
		try {
			int userId = getUserData(username);

			if (userId > 0) {
				String emailId = getEmailAddressFromDatabase(userId);
//
//				if (emailId != null) {
					return emailId;
//				} else {
//					System.out.println("Invalid Email...");
//				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return null;
	}

	public static String getEmailAddressFromDatabase(int userId) {
		try {
			TableData.Builder emailDataBuilder = TableData.newBuilder().setTableName("User");

			List<Condition> conditions = new ArrayList<>();
			conditions.add(new Condition(TableColumn.USER_ID.toString(), userId, ConditionType.EQUALS));

			String emailAddress = DbUtils.performSelect(emailDataBuilder.build(), null, conditions, 1);

			if (emailAddress != null) {
				System.out.println("Success...");
				return emailAddress;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return null;
	}
}
