/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Regime;

/**
 *
 * @author ali_sualei
 */
public class RegimeJpaController implements Serializable {

    public RegimeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regime regime) {
        if (regime.getProfessorList() == null) {
            regime.setProfessorList(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Professor> attachedProfessorList = new ArrayList<Professor>();
            for (Professor professorListProfessorToAttach : regime.getProfessorList()) {
                professorListProfessorToAttach = em.getReference(professorListProfessorToAttach.getClass(), professorListProfessorToAttach.getIdprofessor());
                attachedProfessorList.add(professorListProfessorToAttach);
            }
            regime.setProfessorList(attachedProfessorList);
            em.persist(regime);
            for (Professor professorListProfessor : regime.getProfessorList()) {
                Regime oldIdregimeOfProfessorListProfessor = professorListProfessor.getIdregime();
                professorListProfessor.setIdregime(regime);
                professorListProfessor = em.merge(professorListProfessor);
                if (oldIdregimeOfProfessorListProfessor != null) {
                    oldIdregimeOfProfessorListProfessor.getProfessorList().remove(professorListProfessor);
                    oldIdregimeOfProfessorListProfessor = em.merge(oldIdregimeOfProfessorListProfessor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regime regime) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regime persistentRegime = em.find(Regime.class, regime.getIdregime());
            List<Professor> professorListOld = persistentRegime.getProfessorList();
            List<Professor> professorListNew = regime.getProfessorList();
            List<Professor> attachedProfessorListNew = new ArrayList<Professor>();
            for (Professor professorListNewProfessorToAttach : professorListNew) {
                professorListNewProfessorToAttach = em.getReference(professorListNewProfessorToAttach.getClass(), professorListNewProfessorToAttach.getIdprofessor());
                attachedProfessorListNew.add(professorListNewProfessorToAttach);
            }
            professorListNew = attachedProfessorListNew;
            regime.setProfessorList(professorListNew);
            regime = em.merge(regime);
            for (Professor professorListOldProfessor : professorListOld) {
                if (!professorListNew.contains(professorListOldProfessor)) {
                    professorListOldProfessor.setIdregime(null);
                    professorListOldProfessor = em.merge(professorListOldProfessor);
                }
            }
            for (Professor professorListNewProfessor : professorListNew) {
                if (!professorListOld.contains(professorListNewProfessor)) {
                    Regime oldIdregimeOfProfessorListNewProfessor = professorListNewProfessor.getIdregime();
                    professorListNewProfessor.setIdregime(regime);
                    professorListNewProfessor = em.merge(professorListNewProfessor);
                    if (oldIdregimeOfProfessorListNewProfessor != null && !oldIdregimeOfProfessorListNewProfessor.equals(regime)) {
                        oldIdregimeOfProfessorListNewProfessor.getProfessorList().remove(professorListNewProfessor);
                        oldIdregimeOfProfessorListNewProfessor = em.merge(oldIdregimeOfProfessorListNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = regime.getIdregime();
                if (findRegime(id) == null) {
                    throw new NonexistentEntityException("The regime with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regime regime;
            try {
                regime = em.getReference(Regime.class, id);
                regime.getIdregime();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regime with id " + id + " no longer exists.", enfe);
            }
            List<Professor> professorList = regime.getProfessorList();
            for (Professor professorListProfessor : professorList) {
                professorListProfessor.setIdregime(null);
                professorListProfessor = em.merge(professorListProfessor);
            }
            em.remove(regime);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regime> findRegimeEntities() {
        return findRegimeEntities(true, -1, -1);
    }

    public List<Regime> findRegimeEntities(int maxResults, int firstResult) {
        return findRegimeEntities(false, maxResults, firstResult);
    }

    private List<Regime> findRegimeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regime.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Regime findRegime(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regime.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegimeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regime> rt = cq.from(Regime.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
