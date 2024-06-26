/*
 * DatabaseUtil.java

 */

package utilities.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.jdbc.Work;
import org.hibernate.jpa.HibernatePersistenceProvider;

import domain.DomainEntity;
import utilities.DatabaseConfig;

public class DatabaseUtil {

	// Constructor ------------------------------------------------------------

	public DatabaseUtil() {
	}

	// Properties -------------------------------------------------------------

	private PersistenceProviderResolver resolver;
	private PersistenceProvider persistenceProvider;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private Map<String, Object> properties;
	private String databaseUrl;
	private String databaseName;
	private String databaseDialectName;
	private Dialect databaseDialect;
	private Configuration configuration;
	private EntityTransaction entityTransaction;
	private List<PersistenceProvider> providers;

	public PersistenceProviderResolver getResolver() {
		return this.resolver;
	}

	public PersistenceProvider getPersistenceProvider() {
		return this.persistenceProvider;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}

	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public String getDatabaseDialectName() {
		return this.databaseDialectName;
	}

	public Dialect getDatabaseDialect() {
		return this.databaseDialect;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public EntityTransaction getEntityTransaction() {
		return this.entityTransaction;
	}

	public List<PersistenceProvider> getProviders() {
		return this.providers;
	}

	// Business methods -------------------------------------------------------

	public void open() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();
		this.providers = this.resolver.getPersistenceProviders();
		this.persistenceProvider = new HibernatePersistenceProvider();
		this.entityManagerFactory = this.persistenceProvider.createEntityManagerFactory(DatabaseConfig.PersistenceUnit,
				null);
		if (this.entityManagerFactory == null) {
			throw new RuntimeException(
					String.format("Couldn't load persistence unit `%s'", DatabaseConfig.PersistenceUnit));
		}
		this.entityManager = this.entityManagerFactory.createEntityManager();
		if (this.entityManager == null) {
			throw new RuntimeException(String.format("Couldn't create an entity manager for persistence unit `%s'",
					DatabaseConfig.PersistenceUnit));
		}
		this.properties = this.entityManagerFactory.getProperties();
		// printProperties(properties);

		this.databaseUrl = this.findProperty("javax.persistence.jdbc.url");

		if (!this.databaseUrl.contains("?")) {
			this.databaseName = StringUtils.substringAfterLast(this.databaseUrl, "/");
		} else {
			this.databaseName = StringUtils.substringBetween(this.databaseUrl,
					StringUtils.substringBeforeLast(this.databaseUrl, "/") + "/", "?");
		}

		this.databaseDialectName = this.findProperty("hibernate.dialect");
		this.databaseDialect = (Dialect) ReflectHelper.classForName(this.databaseDialectName).newInstance();

		this.configuration = this.buildConfiguration();

		this.entityTransaction = this.entityManager.getTransaction();

		this.entityManager.setFlushMode(FlushModeType.AUTO);
	}

	public void recreateDatabase() throws Throwable {
		List<String> databaseScript;
		List<String> schemaScript;
		String[] statements;

		databaseScript = new ArrayList<String>();
		databaseScript.add(String.format("drop database `%s`", this.databaseName));
		databaseScript.add(String.format("create database `%s`", this.databaseName));
		this.executeScript(databaseScript);

		schemaScript = new ArrayList<String>();
		schemaScript.add(String.format("use `%s`", this.databaseName));
		statements = this.configuration.generateSchemaCreationScript(this.databaseDialect);
		schemaScript.addAll(Arrays.asList(statements));
		this.executeScript(schemaScript);
	}

	public void close() {
		if (this.entityTransaction != null && this.entityTransaction.isActive()) {
			this.entityTransaction.rollback();
		}
		if (this.entityManager != null && this.entityManager.isOpen()) {
			this.entityManager.close();
		}
		if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
			this.entityManagerFactory.close();
		}
	}

	public void openTransaction() {
		this.entityTransaction.begin();
	}

	public void closeTransaction() {
		this.entityTransaction.commit();
	}

	public void undoTransaction() {
		this.entityTransaction.rollback();
	}

	public void persist(final DomainEntity entity) {
		this.entityManager.persist(entity);
		// entityManager.flush();
	}

	public int executeUpdate(final String line) {
		int result;
		Query query;

		this.entityManager.clear();
		query = this.entityManager.createQuery(line);
		result = query.executeUpdate();

		return result;
	}

	public List<?> executeSelect(final String line) {
		List<?> result;
		Query query;

		this.entityManager.clear();
		query = this.entityManager.createQuery(line);
		result = query.getResultList();

		return result;
	}

	public void flush() {
		this.entityManager.flush();
	}

	// Ancillary methods ------------------------------------------------------

	protected Configuration buildConfiguration() {
		Configuration result;
		Metamodel metamodel;
		Collection<EntityType<?>> entities;
		Collection<EmbeddableType<?>> embeddables;

		result = new Configuration();
		metamodel = this.entityManagerFactory.getMetamodel();

		entities = metamodel.getEntities();
		for (final EntityType<?> entity : entities) {
			result.addAnnotatedClass(entity.getJavaType());
		}

		embeddables = metamodel.getEmbeddables();
		for (final EmbeddableType<?> embeddable : embeddables) {
			result.addAnnotatedClass(embeddable.getJavaType());
		}

		return result;
	}

	protected void executeScript(final List<String> script) {
		Session session;
		session = this.entityManager.unwrap(Session.class);
		session.doWork(new Work() {

			@Override
			public void execute(final Connection connection) throws SQLException {
				Statement statement;

				statement = connection.createStatement();
				for (final String line : script) {
					statement.execute(line);
				}
				connection.commit();
			}
		});
	}

	protected String findProperty(final String property) {
		String result;
		Object value;

		value = this.properties.get(property);
		if (value == null) {
			throw new RuntimeException(String.format("Property `%s' was not found", property));
		}
		if (!(value instanceof String)) {
			throw new RuntimeException(String.format("Property `%s' is not a string", property));
		}
		result = (String) value;
		if (StringUtils.isBlank(result)) {
			throw new RuntimeException(String.format("Property `%s' is blank", property));
		}

		return result;
	}

	protected void printProperties(final Map<String, Object> properties) {
		for (final Entry<String, Object> entry : properties.entrySet()) {
			System.out.println(String.format("%s=`%s'", entry.getKey(), entry.getValue()));
		}
	}

}
