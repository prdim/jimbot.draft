/**
 * 
 */
package ru.jimbot.core;

/**
 * @author spec
 *
 */
public class VariableNumber implements Variable {
	private int value;
	
	/**
	 * 
	 */
	public VariableNumber() {
		value=0;
	}

	/**
	 * @param val
	 */
	public VariableNumber(int val) {
		this.value = val;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return "number";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#parse(java.lang.String)
	 */
	@Override
	public boolean parse(String s) {
		try {
			value = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#format()
	 */
	@Override
	public String format() {
		return String.format("%d", value);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void clear() {
		value = 0;
	}
}
