package com.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.dbcp2.BasicDataSource;

import proto.Column;
import proto.TableData;

public class DbUtils {
	// Database connection details
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ChatAppUserDetails";
	private static final String DB_USER = "madhumathi";
	private static final String DB_PASSWORD = "mad@1";

	// Setting up the data source using Apache Commons DBCP
	public static final BasicDataSource dataSource = setupDataSource();
	public static final DbUtils instance = new DbUtils();

	public static Connection connection;
//    public static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
	

	public DbUtils() {
		try {
			DbUtils.connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DbUtils getInstance() {
		return instance;
	}

	private static BasicDataSource setupDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl(JDBC_URL);
		ds.setUsername(DB_USER);
		ds.setPassword(DB_PASSWORD);
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(100);
		return ds;
	}

	// Inserting the data's into the database
	public static int performInsert(TableData data) throws Exception {
		try {
			StringBuilder columnNames = new StringBuilder();
			StringBuilder values = new StringBuilder();
			int numOfColumns = data.getColumnsCount();

			for (int i = 0; i < numOfColumns; i++) {
				Column column = data.getColumns(i);
				columnNames.append(column.getName()).append(",");
				values.append("?,");
			}

			columnNames.deleteCharAt(columnNames.length() - 1);
			values.deleteCharAt(values.length() - 1);

			String query = "INSERT INTO " + data.getTableName() + " (" + columnNames.toString() + ") VALUES ("
					+ values.toString() + ")";
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

				for (int i = 0; i < numOfColumns; i++) {
					Column column = data.getColumns(i);
					statement.setString(i + 1, column.getValue());
				}

				statement.executeUpdate();

				ResultSet generatedKeys = statement.getGeneratedKeys();
				// id returns
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return 0;
	}

	// Updating the data into the database
	public static int performUpdate(TableData columnsToUpdate, List<Condition> whereConditions) {
		try {
			StringBuilder updateQuery = new StringBuilder("UPDATE ");
			updateQuery.append(columnsToUpdate.getTableName()).append(" SET ");

			int numColumns = columnsToUpdate.getColumnsCount();
			for (int i = 0; i < numColumns; i++) {
				Column column = columnsToUpdate.getColumns(i);
				updateQuery.append(column.getName()).append(" = ?");
				if (i < numColumns - 1) {
					updateQuery.append(", ");
				}
			}
			// WHERE conditions
			if (!whereConditions.isEmpty()) {
				updateQuery.append(" WHERE ");
				for (int i = 0; i < whereConditions.size(); i++) {
					updateQuery.append(whereConditions.get(i).getColumn()).append(" = ?");
					if (i < whereConditions.size() - 1) {
						updateQuery.append(" AND ");
					}
				}
			}

			try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery.toString())) {
				int parameterIndex = 1;
	            System.out.println("Generated SQL Query: " + updateStatement.toString());
				for (int i = 0; i < numColumns; i++) {
					Column column = columnsToUpdate.getColumns(i);
					updateStatement.setString(parameterIndex++, column.getValue());
				}

				for (Condition condition : whereConditions) {
					updateStatement.setObject(parameterIndex++, condition.getValue());

				}

				int rowsUpdated = updateStatement.executeUpdate();
				return rowsUpdated;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			return 0;
		}
	}

	// selecting a data with conditions and limit
	public static String performSelect(TableData columns, List<Column> joinConditions, List<Condition> whereConditions,
			int limit) throws Exception {
		try {
			StringBuilder selectQuery = new StringBuilder("SELECT ");

			if (!columns.getColumnsList().isEmpty()) {
				List<String> columnNames = new ArrayList<>();
				for (Column column : columns.getColumnsList()) {
					columnNames.add(column.getName().toString());
				}
				selectQuery.append(String.join(",", columnNames));
			} else {
				selectQuery.append("*");
			}

			selectQuery.append(" FROM ").append(columns.getTableName());

			if (whereConditions != null && !whereConditions.isEmpty()) {
				selectQuery.append(" WHERE ");
				boolean firstCondition = true;
				for (Condition condition : whereConditions) {
					if (!firstCondition) {
						selectQuery.append(" AND ");
					}
					selectQuery.append(condition.getColumn()).append(" ")
							.append(condition.getConditionType().getOperator()).append(" ?");
					firstCondition = false;
				}
			}

			selectQuery.append(" LIMIT ?");

			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString())) {
				int parameterIndex = 1;

				if (whereConditions != null) {
					for (Condition condition : whereConditions) {
						preparedStatement.setObject(parameterIndex++, condition.getValue());
					}
				}
				preparedStatement.setInt(parameterIndex, limit);

				ResultSet resultSet = preparedStatement.executeQuery();

				StringBuilder result = new StringBuilder();
				while (resultSet.next()) {
					int columnCount = resultSet.getMetaData().getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						result.append(resultSet.getString(i)).append("\t");
					}
					result.append("\n");
				}
				return result.toString();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return null;
	}
	// Deleting a data in the database
	public static String performDelete(TableData tableData, List<Condition> whereConditions) {
		try {
			StringBuilder deleteQuery = new StringBuilder("DELETE FROM ");
			deleteQuery.append(tableData.getTableName());

			if (!whereConditions.isEmpty()) {
				deleteQuery.append(" WHERE ");
				for (int i = 0; i < whereConditions.size(); i++) {
					deleteQuery.append(whereConditions.get(i).getColumn()).append(" = ?");
					if (i < whereConditions.size() - 1) {
						deleteQuery.append(" AND ");
					}
				}
			}

			try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery.toString())) {
				System.out.println("Generated SQL Query: " + deleteStatement.toString());
				int parameterIndex = 1;
				for (Condition condition : whereConditions) {
					deleteStatement.setObject(parameterIndex++, condition.getValue());
				}

				int rowsDeleted = deleteStatement.executeUpdate();

				if (rowsDeleted > 0) {
					return "Successfully Deleted...";
				} else {
					return "No matching records found...";
				}
			}
		} catch (Exception ee) {
			System.out.println("Error! "+ee.getMessage());
			ee.printStackTrace();
		}
		return null;

	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}