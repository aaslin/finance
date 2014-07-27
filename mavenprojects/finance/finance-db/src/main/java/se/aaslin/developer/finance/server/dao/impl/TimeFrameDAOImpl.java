package se.aaslin.developer.finance.server.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.File_;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.TimeFrame_;
import se.aaslin.developer.finance.server.dao.TimeFrameDAO;

@Stateless
public class TimeFrameDAOImpl extends GenericDAOImpl<Integer, TimeFrame> implements TimeFrameDAO {
	
	private static final DateFormat monthFormat = new SimpleDateFormat("MMMM");
	
	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public TimeFrame getMonthTimeFrame(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String tag = cal.get(Calendar.YEAR)+ " Q%" + monthFormat.format(cal.getTime());
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeFrame> query = cb.createQuery(TimeFrame.class);
		
		Root<TimeFrame> timeFrame = query.from(TimeFrame.class);
		query.where(cb.like(timeFrame.get(TimeFrame_.tag), tag));
		
		return getSingelOrNull(query);
	}

	@Override
	public List<TimeFrame> getMonths() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeFrame> query = cb.createQuery(TimeFrame.class);
		
		Root<File> file = query.from(File.class);
		Join<File, TimeFrame> timeFrame = file.join(File_.timeFrame);
		
		query.select(timeFrame);
		query.orderBy(cb.desc(timeFrame.get(TimeFrame_.start)));
		
		return em.createQuery(query).getResultList();
	}
}
