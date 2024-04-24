package com.utils;

public enum ConditionType {
	EQUALS("="), GREATER_THAN(">"), LESS_THAN("<");

	private final String operator;

	ConditionType(String Operator) {
		this.operator = Operator;
	}

	public String getOperator() {
		return operator;
	}
}
