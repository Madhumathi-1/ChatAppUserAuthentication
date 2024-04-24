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

public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");

		try {
			List<Condition> conditions = new ArrayList<>();
			Condition condition = new Condition("username", username, ConditionType.EQUALS);
			conditions.add(condition);

			TableData tableData = TableData.newBuilder().setTableName("User")
					.addColumns(Column.newBuilder().setName(TableColumn.USERNAME).setValue(username).build()).build();

			String deletionResult = DbUtils.performDelete(tableData, conditions);

			if (deletionResult != null) {
				System.out.println("User deleted successfully");
			} else {
				System.out.println("Deletion failed: " + deletionResult);
			}

		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

}
