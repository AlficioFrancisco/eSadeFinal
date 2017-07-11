/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Parametrodocenteestudante;
import modelo.Pontuacaodocenteestudante;
import modelo.Questaodocenteestudante;

/**
 *
 * @author ali_sualei
 */
public class ParametrodocenteestudanteJpaController implements Serializable {

    public ParametrodocenteestudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parametrodocenteestudante parametrodocenteestudante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontuacaodocenteestudante idpontuacaodocenteestudante = parametrodocenteestudante.getIdpontuacaodocenteestudante();
            if (idpontuacaodocenteestudante != null) {
                idpontuacaodocenteestudante = em.getReference(idpontuacaodocenteestudante.getClass(), idpontuacaodocenteestudante.getIdpontuacaodocenteestudante());
                parametrodocenteestudante.setIdpontuacaodocenteestudante(idpontuacaodocenteestudante);
            }
            Questaodocenteestudante idquestaodocenteestudante = parametrodocenteestudante.getIdquestaodocenteestudante();
            if (idquestaodocenteestudante != null) {
                idquestaodocenteestudante = em.getReference(idquestaodocenteestudante.getClass(), idquestaodocenteestudante.getIdquestaodocenteestudante());
                parametrodocenteestudante.setIdquestaodocenteestudante(idquestaodocenteestudante);
            }
            em.persist(parametrodocenteestudante);
            if (idpontuacaodocenteestudante != null) {
                idpontuacaodocenteestudante.getParametrodocenteestudanteList().add(parametrodocenteestudante);
                idpontuacaodocenteestudante = em.merge(idpontuacaodocenteestudante);
            }
            if (idquestaodocenteestudante != null) {
                idquestaodocenteestudante.getParametrodocenteestudanteList().add(parametrodocenteestudante);
                idquestaodocenteestudante = em.merge(idquestaodocenteestudante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parametrodocenteestudante parametrodocenteestudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametrodocenteestudante persistentParametrodocenteestudante = em.find(Parametrodocenteestudante.class, parametrodocenteestudante.getIdparametrodocenteestudante());
            Pontuacaodocenteestudante idpontuacaodocenteestudanteOld = persistentParametrodocenteestudante.getIdpontuacaodocenteestudante();
            Pontuacaodocenteestudante idpontuacaodocenteestudanteNew = parametrodocenteestudante.getIdpontuacaodocenteestudante();
            Questaodocenteestudante idquestaodocenteestudanteOld = persistentParametrodocenteestudante.getIdquestaodocenteestudante();
            Questaodocenteestudante idquestaodocenteestudanteNew = parametrodocenteestudante.getIdquestaodocenteestudante();
            if (idpontuacaodocenteestudanteNew != null) {
                idpontuacaodocenteestudanteNew = em.getReference(idpontuacaodocenteestudanteNew.getClass(), idpontuacaodocenteestudanteNew.getIdpontuacaodocenteestudante());
                parametrodocenteestudante.setIdpontuacaodocenteestudante(idpontuacaodocenteestudanteNew);
            }
            if (idquestaodocenteestudanteNew != null) {
                idquestaodocenteestudanteNew = em.getReference(idquestaodocenteestudanteNew.getClass(), idquestaodocenteestudanteNew.getIdquestaodocenteestudante());
                parametrodocenteestudante.setIdquestaodocenteestudante(idquestaodocenteestudanteNew);
            }
            parametrodocenteestudante = em.merge(parametrodocenteestudante);
            if (idpontuacaodocenteestudanteOld != null && !idpontuacaodocenteestudanteOld.equals(idpontuacaodocenteestudanteNew)) {
                idpontuacaodocenteestudanteOld.getParametrodocenteestudanteList().remove(parametrodocenteestudante);
                idpontuacaodocenteestudanteOld = em.merge(idpontuacaodocenteestudanteOld);
            }
            if (idpontuacaodocenteestudanteNew != null && !idpontuacaodocenteestudanteNew.equals(idpontuacaodocenteestudanteOld)) {
                idpontuacaodocenteestudanteNew.getParametrodocenteestudanteList().add(parametrodocenteestudante);
                idpontuacaodocenteestudanteNew = em.merge(idpontuacaodocenteestudanteNew);
            }
            if (idquestaodocenteestudanteOld != null && !idquestaodocenteestudanteOld.equals(idquestaodocenteestudanteNew)) {
                idquestaodocenteestudanteOld.getParametrodocenteestudanteList().remove(parametrodocenteestudante);
                idquestaodocenteestudanteOld = em.merge(idquestaodocenteestudanteOld);
            }
            if (idquestaodocenteestudanteNew != null && !idquestaodocenteestudanteNew.equals(idquestaodocenteestudanteOld)) {
                idquestaodocenteestudanteNew.getParametrodocenteestudanteList().add(parametrodocenteestudante);
                idquestaodocenteestudanteNew = em.merge(idquestaodocenteestudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = parametrodocenteestudante.getIdparametrodocenteestudante();
                if (findParametrodocenteestudante(id) == null) {
                    throw new NonexistentEntityException("The parametrodocenteestudante with id " + id + " no longer exists.");
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
            Parametrodocenteestudante parametrodocenteestudante;
            try {
                parametrodocenteestudante = em.getReference(Parametrodocenteestudante.class, id);
                parametrodocenteestudante.getIdparametrodocenteestudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametrodocenteestudante with id " + id + " no longer exists.", enfe);
            }
            Pontuacaodocenteestudante idpontuacaodocenteestudante = parametrodocenteestudante.getIdpontuacaodocenteestudante();
            if (idpontuacaodocenteestudante != null) {
                idpontuacaodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudante);
                idpontuacaodocenteestudante = em.merge(idpontuacaodocenteestudante);
            }
            Questaodocenteestudante idquestaodocenteestudante = parametrodocenteestudante.getIdquestaodocenteestudante();
            if (idquestaodocenteestudante != null) {
                idquestaodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudante);
                idquestaodocenteestudante = em.merge(idquestaodocenteestudante);
            }
            em.remove(parametrodocenteestudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parametrodocenteestudante> findParametrodocenteestudanteEntities() {
        return findParametrodocenteestudanteEntities(true, -1, -1);
    }

    public List<Parametrodocenteestudante> findParametrodocenteestudanteEntities(int maxResults, int firstResult) {
        return findParametrodocenteestudanteEntities(false, maxResults, firstResult);
    }

    private List<Parametrodocenteestudante> findParametrodocenteestudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parametrodocenteestudante.class));
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

    public Parametrodocenteestudante findParametrodocenteestudante(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parametrodocenteestudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametrodocenteestudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parametrodocenteestudante> rt = cq.from(Parametrodocenteestudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
