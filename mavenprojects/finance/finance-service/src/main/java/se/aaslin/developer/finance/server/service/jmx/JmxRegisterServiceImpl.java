package se.aaslin.developer.finance.server.service.jmx;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import se.aaslin.developer.finance.server.service.scraper.TransactionScraperService;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JmxRegisterServiceImpl implements JmxRegisterServiceLocalBusiness {
	
	@Inject TransactionScraperService transactionScraperService;

	Map<ObjectName, Object> mBeans = new HashMap<ObjectName, Object>();
	
	@PostConstruct
	public void regiseterBeans() throws Exception {
		ObjectName transactionServiceName = new ObjectName("bean:name=transactionScraperServiceMBean");
		mBeans.put(transactionServiceName, transactionScraperService);
		
		MBeanServer mbs =  MBeanServerFactory.findMBeanServer(null).get(0);
		for(Entry<ObjectName, Object> entry : mBeans.entrySet()) {
			mbs.registerMBean(entry.getValue(), entry.getKey() );
		}
	}

	@PreDestroy
	public void unregisterBeans() throws Exception {
		MBeanServer mbs = MBeanServerFactory.findMBeanServer(null).get(0);
		for(Entry<ObjectName, Object> entry : mBeans.entrySet()) {
			mbs.unregisterMBean(entry.getKey() );
		}
	}
}
