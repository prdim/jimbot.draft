/**
 * 
 */
package ru.jimbot.core;

import java.util.LinkedHashMap;

/**
 * Map, который может использоваться в качестве кеша: хранит заданное число значений, при превышении начинает удалять самые старые.
 * @author spec
 * @param <E>
 *
 */
public class CashMap<E> extends LinkedHashMap<String, E>{
	private int maxElements = 100;

	/**
	 * @return the maxElements
	 */
	public int getMaxElements() {
		return maxElements;
	}

	/**
	 * @param maxElements the maxElements to set
	 */
	public void setMaxElements(int maxElements) {
		this.maxElements = maxElements;
	}

	/* (non-Javadoc)
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override
	protected boolean removeEldestEntry(
			java.util.Map.Entry<String, E> eldest) {
		return size() > maxElements;
	}
}
