package com.utils;

public class Condition {
	private final String column;
	private final Object value;
	private final ConditionType conditionType;
	private final LogicalOperator logicalOperator;

	public Condition(String column, Object value, ConditionType conditionType) {
		this(column, value, conditionType, null);
	}

	public Condition(String column, Object value, ConditionType conditionType, LogicalOperator logicalOperator) {
		this.column = column;
		this.value = value;
		this.conditionType = conditionType;
		this.logicalOperator = logicalOperator;
	}

	public String getColumn() {
		return column;
	}

	public Object getValue() {
		return value;
	}

	public ConditionType getConditionType() {
		return conditionType;
	}

	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}
}