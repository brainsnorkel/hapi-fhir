package ca.uhn.fhir.jpa.config;

import java.util.Properties;

/*import org.hibernate.jpa.HibernatePersistenceProvider;*/
import org.datanucleus.api.jpa.PersistenceProviderImpl;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ca.uhn.fhir.jpa.dao.FulltextSearchSvcImpl;
import ca.uhn.fhir.jpa.dao.IFulltextSearchSvc;

@Configuration
@EnableTransactionManagement()
public class TestR4WithoutLuceneConfig extends TestR4Config {

	/**
	 * Disable fulltext searching
	 */
	@Override
	public IFulltextSearchSvc searchDaoR4() {
		return null;
	}

	@Override
	@Bean()
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean retVal = new LocalContainerEntityManagerFactoryBean();
		retVal.setPersistenceUnitName("PU_HapiFhirJpaR4");
		retVal.setDataSource(dataSource());
		retVal.setPackagesToScan("ca.uhn.fhir.jpa.entity");
		retVal.setPersistenceProvider(new PersistenceProviderImpl());
		retVal.setJpaProperties(jpaProperties());
		return retVal;
	}

	private Properties jpaProperties() {
		Properties extraProperties = new Properties();
		extraProperties.put("hibernate.format_sql", "false");
		extraProperties.put("hibernate.show_sql", "false");
		extraProperties.put("hibernate.hbm2ddl.auto", "update");
		extraProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
		extraProperties.put("hibernate.search.autoregister_listeners", "false");
		return extraProperties;
	}

}
