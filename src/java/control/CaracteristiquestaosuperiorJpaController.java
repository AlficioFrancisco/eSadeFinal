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
import modelo.Questaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Caracteristiquestaosuperior;

/**
 *
 * @author ali_sualei
 */
public class CaracteristiquestaosuperiorJpaController implements Serializable {

    public CaracteristiquestaosuperiorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caracteristiquestaosuperior caracteristiquestaosuperior) {
        if (caracteristiquestaosuperior.getQuestaodocentesuperiorList() == null) {
            caracteristiquestaosuperior.setQuestaodocentesuperiorList(new ArrayList<Questaodocentesuperior>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorList = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperiorToAttach : caracteristiquestaosuperior.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorList.add(questaodocentesuperiorListQuestaodocentesuperiorToAttach);
            }
            caracteristiquestaosuperior.setQuestaodocentesuperiorList(attachedQuestaodocentesuperiorList);
            em.persist(caracteristiquestaosuperior);
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : caracteristiquestaosuperior.getQuestaodocentesuperiorList()) {
                Caracteristiquestaosuperior oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListQuestaodocentesuperior = questaodocentesuperiorListQuestaodocentesuperior.getIdcaracteristicaquestaosuperior();
                questaodocentesuperiorListQuestaodocentesuperior.setIdcaracteristicaquestaosuperior(caracteristiquestaosuperior);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
                if (oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListQuestaodocentesuperior != null) {
                    oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListQuestaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperiorListQuestaodocentesuperior);
                    oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListQuestaodocentesuperior = em.merge(oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListQuestaodocentesuperior);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracteristiquestaosuperior caracteristiquestaosuperior) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristiquestaosuperior persistentCaracteristiquestaosuperior = em.find(Caracteristiquestaosuperior.class, caracteristiquestaosuperior.getIdcaracteristicasuperior());
            List<Questaodocentesuperior> questaodocentesuperiorListOld = persistentCaracteristiquestaosuperior.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> questaodocentesuperiorListNew = caracteristiquestaosuperior.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorListNew = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperiorToAttach : questaodocentesuperiorListNew) {
                questaodocentesuperiorListNewQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorListNew.add(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach);
            }
            questaodocentesuperiorListNew = attachedQuestaodocentesuperiorListNew;
            caracteristiquestaosuperior.setQuestaodocentesuperiorList(questaodocentesuperiorListNew);
            caracteristiquestaosuperior = em.merge(caracteristiquestaosuperior);
            for (Questaodocentesuperior questaodocentesuperiorListOldQuestaodocentesuperior : questaodocentesuperiorListOld) {
                if (!questaodocentesuperiorListNew.contains(questaodocentesuperiorListOldQuestaodocentesuperior)) {
                    questaodocentesuperiorListOldQuestaodocentesuperior.setIdcaracteristicaquestaosuperior(null);
                    questaodocentesuperiorListOldQuestaodocentesuperior = em.merge(questaodocentesuperiorListOldQuestaodocentesuperior);
                }
            }
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperior : questaodocentesuperiorListNew) {
                if (!questaodocentesuperiorListOld.contains(questaodocentesuperiorListNewQuestaodocentesuperior)) {
                    Caracteristiquestaosuperior oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior = questaodocentesuperiorListNewQuestaodocentesuperior.getIdcaracteristicaquestaosuperior();
                    questaodocentesuperiorListNewQuestaodocentesuperior.setIdcaracteristicaquestaosuperior(caracteristiquestaosuperior);
                    questaodocentesuperiorListNewQuestaodocentesuperior = em.merge(questaodocentesuperiorListNewQuestaodocentesuperior);
                    if (oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior != null && !oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior.equals(caracteristiquestaosuperior)) {
                        oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperiorListNewQuestaodocentesuperior);
                        oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior = em.merge(oldIdcaracteristicaquestaosuperiorOfQuestaodocentesuperiorListNewQuestaodocentesuperior);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = caracteristiquestaosuperior.getIdcaracteristicasuperior();
                if (findCaracteristiquestaosuperior(id) == null) {
                    throw new NonexistentEntityException("The caracteristiquestaosuperior with id " + id + " no longer exists.");
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
            Caracteristiquestaosuperior caracteristiquestaosuperior;
            try {
                caracteristiquestaosuperior = em.getReference(Caracteristiquestaosuperior.class, id);
                caracteristiquestaosuperior.getIdcaracteristicasuperior();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracteristiquestaosuperior with id " + id + " no longer exists.", enfe);
            }
            List<Questaodocentesuperior> questaodocentesuperiorList = caracteristiquestaosuperior.getQuestaodocentesuperiorList();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : questaodocentesuperiorList) {
                questaodocentesuperiorListQuestaodocentesuperior.setIdcaracteristicaquestaosuperior(null);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            em.remove(caracteristiquestaosuperior);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caracteristiquestaosuperior> findCaracteristiquestaosuperiorEntities() {
        return findCaracteristiquestaosuperiorEntities(true, -1, -1);
    }

    public List<Caracteristiquestaosuperior> findCaracteristiquestaosuperiorEntities(int maxResults, int firstResult) {
        return findCaracteristiquestaosuperiorEntities(false, maxResults, firstResult);
    }

    private List<Caracteristiquestaosuperior> findCaracteristiquestaosuperiorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caracteristiquestaosuperior.class));
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

    public Caracteristiquestaosuperior findCaracteristiquestaosuperior(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caracteristiquestaosuperior.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaracteristiquestaosuperiorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caracteristiquestaosuperior> rt = cq.from(Caracteristiquestaosuperior.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
