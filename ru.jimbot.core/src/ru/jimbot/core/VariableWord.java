/**
 * 
 */
package ru.jimbot.core;

/**
 * @author spec
 *
 */
public class VariableWord implements Variable {
	private String value;

	/**
	 * 
	 */
	public VariableWord() {
		value = "";
	}

	/**
	 * @param value
	 */
	public VariableWord(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return "word";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#parse(java.lang.String)
	 */
	@Override
	public boolean parse(String s) {
		value = s.split(" ")[0];
		return true;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.Variable#format()
	 */
	@Override
	public String format() {
		return value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
