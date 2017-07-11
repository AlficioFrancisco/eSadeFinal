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
import modelo.Pontuacaosuperiorhierarquico;

/**
 *
 * @author ali_sualei
 */
public class PontuacaosuperiorhierarquicoJpaController implements Serializable {

    public PontuacaosuperiorhierarquicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pontuacaosuperiorhierarquico pontuacaosuperiorhierarquico) {
        if (pontuacaosuperiorhierarquico.getQuestaodocentesuperiorList() == null) {
            pontuacaosuperiorhierarquico.setQuestaodocentesuperiorList(new ArrayList<Questaodocentesuperior>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorList = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperiorToAttach : pontuacaosuperiorhierarquico.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorList.add(questaodocentesuperiorListQuestaodocentesuperiorToAttach);
            }
            pontuacaosuperiorhierarquico.setQuestaodocentesuperiorList(attachedQuestaodocentesuperiorList);
            em.persist(pontuacaosuperiorhierarquico);
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : pontuacaosuperiorhierarquico.getQuestaodocentesuperiorList()) {
                Pontuacaosuperiorhierarquico oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListQuestaodocentesuperior = questaodocentesuperiorListQuestaodocentesuperior.getIdpontuacaosuperiorhierarquico();
                questaodocentesuperiorListQuestaodocentesuperior.setIdpontuacaosuperiorhierarquico(pontuacaosuperiorhierarquico);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
                if (oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListQuestaodocentesuperior != null) {
                    oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListQuestaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperiorListQuestaodocentesuperior);
                    oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListQuestaodocentesuperior = em.merge(oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListQuestaodocentesuperior);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pontuacaosuperiorhierarquico pontuacaosuperiorhierarquico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontuacaosuperiorhierarquico persistentPontuacaosuperiorhierarquico = em.find(Pontuacaosuperiorhierarquico.class, pontuacaosuperiorhierarquico.getIdpontuacaosuperiorhier());
            List<Questaodocentesuperior> questaodocentesuperiorListOld = persistentPontuacaosuperiorhierarquico.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> questaodocentesuperiorListNew = pontuacaosuperiorhierarquico.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorListNew = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperiorToAttach : questaodocentesuperiorListNew) {
                questaodocentesuperiorListNewQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorListNew.add(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach);
            }
            questaodocentesuperiorListNew = attachedQuestaodocentesuperiorListNew;
            pontuacaosuperiorhierarquico.setQuestaodocentesuperiorList(questaodocentesuperiorListNew);
            pontuacaosuperiorhierarquico = em.merge(pontuacaosuperiorhierarquico);
            for (Questaodocentesuperior questaodocentesuperiorListOldQuestaodocentesuperior : questaodocentesuperiorListOld) {
                if (!questaodocentesuperiorListNew.contains(questaodocentesuperiorListOldQuestaodocentesuperior)) {
                    questaodocentesuperiorListOldQuestaodocentesuperior.setIdpontuacaosuperiorhierarquico(null);
                    questaodocentesuperiorListOldQuestaodocentesuperior = em.merge(questaodocentesuperiorListOldQuestaodocentesuperior);
                }
            }
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperior : questaodocentesuperiorListNew) {
                if (!questaodocentesuperiorListOld.contains(questaodocentesuperiorListNewQuestaodocentesuperior)) {
                    Pontuacaosuperiorhierarquico oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior = questaodocentesuperiorListNewQuestaodocentesuperior.getIdpontuacaosuperiorhierarquico();
                    questaodocentesuperiorListNewQuestaodocentesuperior.setIdpontuacaosuperiorhierarquico(pontuacaosuperiorhierarquico);
                    questaodocentesuperiorListNewQuestaodocentesuperior = em.merge(questaodocentesuperiorListNewQuestaodocentesuperior);
                    if (oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior != null && !oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior.equals(pontuacaosuperiorhierarquico)) {
                        oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperiorListNewQuestaodocentesuperior);
                        oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior = em.merge(oldIdpontuacaosuperiorhierarquicoOfQuestaodocentesuperiorListNewQuestaodocentesuperior);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = pontuacaosuperiorhierarquico.getIdpontuacaosuperiorhier();
                if (findPontuacaosuperiorhierarquico(id) == null) {
                    throw new NonexistentEntityException("The pontuacaosuperiorhierarquico with id " + id + " no longer exists.");
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
            Pontuacaosuperiorhierarquico pontuacaosuperiorhierarquico;
            try {
                pontuacaosuperiorhierarquico = em.getReference(Pontuacaosuperiorhierarquico.class, id);
                pontuacaosuperiorhierarquico.getIdpontuacaosuperiorhier();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pontuacaosuperiorhierarquico with id " + id + " no longer exists.", enfe);
            }
            List<Questaodocentesuperior> questaodocentesuperiorList = pontuacaosuperiorhierarquico.getQuestaodocentesuperiorList();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : questaodocentesuperiorList) {
                questaodocentesuperiorListQuestaodocentesuperior.setIdpontuacaosuperiorhierarquico(null);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            em.remove(pontuacaosuperiorhierarquico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pontuacaosuperiorhierarquico> findPontuacaosuperiorhierarquicoEntities() {
        return findPontuacaosuperiorhierarquicoEntities(true, -1, -1);
    }

    public List<Pontuacaosuperiorhierarquico> findPontuacaosuperiorhierarquicoEntities(int maxResults, int firstResult) {
        return findPontuacaosuperiorhierarquicoEntities(false, maxResults, firstResult);
    }

    private List<Pontuacaosuperiorhierarquico> findPontuacaosuperiorhierarquicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pontuacaosuperiorhierarquico.class));
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

    public Pontuacaosuperiorhierarquico findPontuacaosuperiorhierarquico(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pontuacaosuperiorhierarquico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPontuacaosuperiorhierarquicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pontuacaosuperiorhierarquico> rt = cq.from(Pontuacaosuperiorhierarquico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
