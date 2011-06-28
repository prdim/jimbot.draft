/**
 * 
 */
package ru.jimbot.anekbot.db;

import java.io.File;

import com.amazon.carbonado.Repository;
import com.amazon.carbonado.repo.sleepycat.BDBRepositoryBuilder;
import com.amazon.carbonado.repo.sleepycat.EnvironmentCapability;
import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.RepositoryException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.VerifyConfig;

/**
 * Управление базой анекдотного бота
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class DbManager {
	private BDBRepositoryBuilder builder = null;
	private Repository repo = null;
	
	public DbManager(String name, File patch) throws ConfigurationException, RepositoryException {
		builder = new BDBRepositoryBuilder();
		builder.setName(name);
		builder.setEnvironmentHomeFile(patch);
		builder.setTransactionWriteNoSync(true);
		builder.setCheckpointInterval(1000);
		builder.setCachePercent(10);
		repo = builder.build();
	}
	
	public Repository getRepository() {
		return repo;
	}
	
	public void veryfy() {
		Environment env = (Environment)repo.getCapability(EnvironmentCapability.class).getEnvironment();
		env.verify(VerifyConfig.DEFAULT, System.out);
	}

	public void close() {
		repo.close();
	}
}
