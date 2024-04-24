package com.auth;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sessions.SessionUtils;
import com.utils.DbUtils;
import com.utils.PasswordHashing;

import proto.Column;
import proto.TableColumn;
import proto.TableData;

public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());

			int userIdGeneratedId = insertUserData(username, firstName, lastName, dob, currentTime);

			if (userIdGeneratedId > 0) {
				insertEmailData(userIdGeneratedId, email, currentTime);
				insertPasswordData(userIdGeneratedId, password, currentTime);

				SessionUtils.createAndStoreSession(userIdGeneratedId, response);
				System.out.println("Successfully data inserted...");
				response.sendRedirect("homepage.jsp");
				return;
			} else {
				System.out.println("Error during user insertion...");
				response.sendRedirect("signup.jsp");
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	private int insertUserData(String username, String firstName, String lastName, String dob, Timestamp createTime) {
		try {
			// columns for the user table
			Column.Builder usernameColumnBuilder = Column.newBuilder().setName(TableColumn.USERNAME).setValue(username);
			Column.Builder firstNameColumnBuilder = Column.newBuilder().setName(TableColumn.FIRST_NAME)
					.setValue(firstName);
			Column.Builder lastNameColumnBuilder = Column.newBuilder().setName(TableColumn.LAST_NAME)
					.setValue(lastName);
			Column.Builder dobColumnBuilder = Column.newBuilder().setName(TableColumn.DOB).setValue(dob);
			Column.Builder createTimeUserColumnBuilder = Column.newBuilder().setName(TableColumn.CREATE_TIME_MS)
					.setValue(String.valueOf(createTime.getTime()));

			// user table data
			TableData.Builder userDataBuilder = TableData.newBuilder().setTableName("User");
			userDataBuilder.addColumns(usernameColumnBuilder.build());
			userDataBuilder.addColumns(firstNameColumnBuilder.build());
			userDataBuilder.addColumns(lastNameColumnBuilder.build());
			userDataBuilder.addColumns(dobColumnBuilder.build());
			userDataBuilder.addColumns(createTimeUserColumnBuilder.build());

			// Performing insertion
			return DbUtils.performInsert(userDataBuilder.build());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return 0;
	}

	private boolean insertEmailData(int userId, String emailAddress, Timestamp emailCreateTime) {
		try {
			// columns for the email table
			Column.Builder emailColumnBuilder = Column.newBuilder().setName(TableColumn.EMAIL_ADDRESS)
					.setValue(emailAddress);
			Column.Builder emailCreationTimeBuilder = Column.newBuilder().setName(TableColumn.EMAIL_CREATION_TIME_MS)
					.setValue(String.valueOf(emailCreateTime.getTime()));
			Column.Builder userIdBuilder = Column.newBuilder().setName(TableColumn.USER_ID)
					.setValue(String.valueOf(userId));

			// email table data
			TableData.Builder emailDataBuilder = TableData.newBuilder().setTableName("Email");
			emailDataBuilder.addColumns(emailColumnBuilder.build());
			emailDataBuilder.addColumns(userIdBuilder.build());
			emailDataBuilder.addColumns(emailCreationTimeBuilder.build());

			// Performing insertion
			int result = DbUtils.performInsert(emailDataBuilder.build());
			return result > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}

	private boolean insertPasswordData(int userId, String passwordHash, Timestamp passwordCreateTime) {
		try {
			// columns for the password table
			Column.Builder passwordColumnBuilder = Column.newBuilder().setName(TableColumn.PASSWORD_HASH)
					.setValue(PasswordHashing.hashPassword(passwordHash));
			Column.Builder passwordCreationTimeBuilder = Column.newBuilder()
					.setName(TableColumn.PASSWORD_CREATION_TIME_MS)
					.setValue(String.valueOf(passwordCreateTime.getTime()));
			Column.Builder userIdBuilder = Column.newBuilder().setName(TableColumn.USER_ID)
					.setValue(String.valueOf(userId));

			// password table data
			TableData.Builder passwordDataBuilder = TableData.newBuilder().setTableName("Password");
			passwordDataBuilder.addColumns(passwordColumnBuilder.build());
			passwordDataBuilder.addColumns(userIdBuilder.build());
			passwordDataBuilder.addColumns(passwordCreationTimeBuilder.build());

			// Performing insertion
			int result = DbUtils.performInsert(passwordDataBuilder.build());
			return result > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}
}
