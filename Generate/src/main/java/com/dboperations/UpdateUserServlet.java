package com.dboperations;

import java.io.IOException;
import java.sql.Timestamp;
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

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		Timestamp changetime = new Timestamp(System.currentTimeMillis());
		// Update proper validation
		try {
			boolean userDetails = updateUserData(userName, firstName, lastName, dob, email, changetime);

			if(userDetails) {
				updateEmailAddressFromDatabase(userDetails);
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	private boolean updateUserData(String username, String firstName, String lastName,String dob,String email, 
			Timestamp changetime){
		try {
			TableData.Builder userDataBuilder = TableData.newBuilder().setTableName("User");
			TableData.Builder emailDataBuilder = TableData.newBuilder().setTableName("Email");

			Column.Builder emailBuilder = Column.newBuilder().setName(TableColumn.EMAIL_ADDRESS).setValue(email);
			emailDataBuilder.addColumns(emailBuilder.build());

			Column.Builder usernameBuilder = Column.newBuilder().setName(TableColumn.USERNAME).setValue(username);
			Column.Builder dobBuilder = Column.newBuilder().setName(TableColumn.DOB).setValue(dob);
			Column.Builder firstNameBuilder = Column.newBuilder().setName(TableColumn.FIRST_NAME).setValue(firstName);
			Column.Builder lastNameBuilder = Column.newBuilder().setName(TableColumn.LAST_NAME).setValue(lastName);
			Column.Builder changetimeBuilder = Column.newBuilder().setName(TableColumn.CHANGE_TIME_MS)
					.setValue(String.valueOf(changetime.getTime()));

			userDataBuilder.addColumns(usernameBuilder.build());
			userDataBuilder.addColumns(firstNameBuilder.build());
			userDataBuilder.addColumns(lastNameBuilder.build());
			userDataBuilder.addColumns(dobBuilder.build());
			userDataBuilder.addColumns(changetimeBuilder.build());
			emailDataBuilder.addColumns(changetimeBuilder.build());

			List<Condition> conditions = new ArrayList<>();
//			String userID = UserDetails.getUserId(username);
			conditions.add(new Condition(TableColumn.USERNAME.toString(), username, ConditionType.EQUALS));
//			conditions.add(new Condition(TableColumn.USER_ID.toString(), get, ConditionType.EQUALS));
	
			DbUtils.performUpdate(userDataBuilder.build(), conditions);
//			DbUtils.performUpdate(emailDataBuilder.build(), conditions);
			
		} catch (Exception ee) {
			System.out.println("Error! while updation...");
			ee.printStackTrace();
		}
		return false;
	}

	private static boolean updateEmailAddressFromDatabase(boolean userDetails) {
		try {
			TableData.Builder emailDataBuilder = TableData.newBuilder().setTableName("Email");

			List<Condition> conditions = new ArrayList<>();
			conditions.add(new Condition(TableColumn.USER_ID.toString(), userDetails, ConditionType.EQUALS));

			DbUtils.performUpdate(emailDataBuilder.build(), conditions);

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}
}
