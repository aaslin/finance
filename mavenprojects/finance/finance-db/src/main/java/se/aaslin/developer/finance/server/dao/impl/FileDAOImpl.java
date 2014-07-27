package se.aaslin.developer.finance.server.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.File_;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.TimeFrame_;
import se.aaslin.developer.finance.server.dao.FileDAO;
import se.aaslin.developer.finance.shared.dto.file.FileDTO;

@Stateless
public class FileDAOImpl extends GenericDAOImpl<Integer, File> implements FileDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<FileDTO> listAllFiles() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FileDTO> query = cb.createQuery(FileDTO.class);
		
		Root<File> file = query.from(File.class);
		Join<File, TimeFrame> timeFrame = file.join(File_.timeFrame, JoinType.LEFT);
		
		query.multiselect(file.get(File_.name), timeFrame.get(TimeFrame_.start));
		query.orderBy(cb.desc(timeFrame.get(TimeFrame_.start)));
		
		return em.createQuery(query).getResultList();
	}

	@Override
	public File findByName(String fileName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<File> query = cb.createQuery(File.class);
		
		Root<File> file = query.from(File.class);
		query.where(cb.equal(file.get(File_.name), fileName));
		
		return getSingelOrNull(query);
	}

	@Override
	public File findFile(Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<File> query = cb.createQuery(File.class);
		
		Root<File> file = query.from(File.class);
		Join<File, TimeFrame> timeFrame = file.join(File_.timeFrame);
		
		Predicate p1 = cb.lessThanOrEqualTo(timeFrame.get(TimeFrame_.start), date);
		Predicate p2 = cb.greaterThanOrEqualTo(timeFrame.get(TimeFrame_.stop), date);
		
		query.select(file);
		query.where(p1, p2);
		
		return getSingelOrNull(query);
	}
}
