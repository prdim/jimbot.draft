/**
 * 
 */
package ru.jimbot.anekbot;

import java.io.File;

import ru.jimbot.core.ExtendPoint;
import ru.jimbot.core.services.BotService;
import ru.jimbot.core.services.IBotServiceBuilder;

/**
 * @author spec
 *
 */
public class AnekBotServiceBuilder implements IBotServiceBuilder, ExtendPoint {
	private CommandConnector cm;

	/**
	 * @param cm
	 */
	public AnekBotServiceBuilder(CommandConnector cm) {
		super();
		this.cm = cm;
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.ExtendPoint#getType()
	 */
	@Override
	public String getType() {
		return "ru.jimbot.core.services.BotServiceBuilder";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.ExtendPoint#getPointName()
	 */
	@Override
	public String getPointName() {
		return "AnekBot";
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.core.ExtendPoint#unreg()
	 */
	@Override
	public void unreg() {
		// TODO Auto-generated method stub

	}

	@Override
	public BotService build(String name) {
		return new AnekBot(name, cm);
	}

	@Override
	public boolean createServiceData(String name) {
		boolean b = false;
		try {
			b = new File("./services/" + name).mkdir();
			if(!b) return b;
			b = new File("./log/" + name).mkdir();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	@Override
	public boolean deleteServiceData(String name) {
		boolean b = false;
		try {
			b = deleteDirectory(new File("./services/" + name));
			if(!b) return b;
			b = deleteDirectory(new File("./log/" + name));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
     * Удаление папки, содержащей файлы
     * @param path
     * @return
     */
    private boolean deleteDirectory(File path) {
        if( path.exists() ) {
          File[] files = path.listFiles();
          for(int i=0; i<files.length; i++) {
             if(files[i].isDirectory()) {
               deleteDirectory(files[i]);
             }
             else {
               files[i].delete();
             }
          }
        }
        return( path.delete() );
      }
}
