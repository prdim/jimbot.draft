/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.jimbot.modules.anek.db;

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
