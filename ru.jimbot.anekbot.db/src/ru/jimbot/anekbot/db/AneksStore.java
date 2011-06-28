/**
 * 
 */
package ru.jimbot.anekbot.db;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Sequence;
import com.amazon.carbonado.Storable;

/**
 * Сохранение анекдота
 * 
 * @author Prolubnikov Dmitry
 *
 */
@PrimaryKey("id")
public abstract class AneksStore implements Storable {
	
	@Sequence("ANEK_ID_SEQ")
	public abstract long getId();
	public abstract void setId(long id);
	
	public abstract String getText();
	public abstract void setText(String text);
}
