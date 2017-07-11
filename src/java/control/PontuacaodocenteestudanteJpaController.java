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
import modelo.Parametrodocenteestudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pontuacaodocenteestudante;

/**
 *
 * @author ali_sualei
 */
public class PontuacaodocenteestudanteJpaController implements Serializable {

    public PontuacaodocenteestudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pontuacaodocenteestudante pontuacaodocenteestudante) {
        if (pontuacaodocenteestudante.getParametrodocenteestudanteList() == null) {
            pontuacaodocenteestudante.setParametrodocenteestudanteList(new ArrayList<Parametrodocenteestudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Parametrodocenteestudante> attachedParametrodocenteestudanteList = new ArrayList<Parametrodocenteestudante>();
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudanteToAttach : pontuacaodocenteestudante.getParametrodocenteestudanteList()) {
                parametrodocenteestudanteListParametrodocenteestudanteToAttach = em.getReference(parametrodocenteestudanteListParametrodocenteestudanteToAttach.getClass(), parametrodocenteestudanteListParametrodocenteestudanteToAttach.getIdparametrodocenteestudante());
                attachedParametrodocenteestudanteList.add(parametrodocenteestudanteListParametrodocenteestudanteToAttach);
            }
            pontuacaodocenteestudante.setParametrodocenteestudanteList(attachedParametrodocenteestudanteList);
            em.persist(pontuacaodocenteestudante);
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudante : pontuacaodocenteestudante.getParametrodocenteestudanteList()) {
                Pontuacaodocenteestudante oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante = parametrodocenteestudanteListParametrodocenteestudante.getIdpontuacaodocenteestudante();
                parametrodocenteestudanteListParametrodocenteestudante.setIdpontuacaodocenteestudante(pontuacaodocenteestudante);
                parametrodocenteestudanteListParametrodocenteestudante = em.merge(parametrodocenteestudanteListParametrodocenteestudante);
                if (oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante != null) {
                    oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudanteListParametrodocenteestudante);
                    oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante = em.merge(oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pontuacaodocenteestudante pontuacaodocenteestudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontuacaodocenteestudante persistentPontuacaodocenteestudante = em.find(Pontuacaodocenteestudante.class, pontuacaodocenteestudante.getIdpontuacaodocenteestudante());
            List<Parametrodocenteestudante> parametrodocenteestudanteListOld = persistentPontuacaodocenteestudante.getParametrodocenteestudanteList();
            List<Parametrodocenteestudante> parametrodocenteestudanteListNew = pontuacaodocenteestudante.getParametrodocenteestudanteList();
            List<Parametrodocenteestudante> attachedParametrodocenteestudanteListNew = new ArrayList<Parametrodocenteestudante>();
            for (Parametrodocenteestudante parametrodocenteestudanteListNewParametrodocenteestudanteToAttach : parametrodocenteestudanteListNew) {
                parametrodocenteestudanteListNewParametrodocenteestudanteToAttach = em.getReference(parametrodocenteestudanteListNewParametrodocenteestudanteToAttach.getClass(), parametrodocenteestudanteListNewParametrodocenteestudanteToAttach.getIdparametrodocenteestudante());
                attachedParametrodocenteestudanteListNew.add(parametrodocenteestudanteListNewParametrodocenteestudanteToAttach);
            }
            parametrodocenteestudanteListNew = attachedParametrodocenteestudanteListNew;
            pontuacaodocenteestudante.setParametrodocenteestudanteList(parametrodocenteestudanteListNew);
            pontuacaodocenteestudante = em.merge(pontuacaodocenteestudante);
            for (Parametrodocenteestudante parametrodocenteestudanteListOldParametrodocenteestudante : parametrodocenteestudanteListOld) {
                if (!parametrodocenteestudanteListNew.contains(parametrodocenteestudanteListOldParametrodocenteestudante)) {
                    parametrodocenteestudanteListOldParametrodocenteestudante.setIdpontuacaodocenteestudante(null);
                    parametrodocenteestudanteListOldParametrodocenteestudante = em.merge(parametrodocenteestudanteListOldParametrodocenteestudante);
                }
            }
            for (Parametrodocenteestudante parametrodocenteestudanteListNewParametrodocenteestudante : parametrodocenteestudanteListNew) {
                if (!parametrodocenteestudanteListOld.contains(parametrodocenteestudanteListNewParametrodocenteestudante)) {
                    Pontuacaodocenteestudante oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante = parametrodocenteestudanteListNewParametrodocenteestudante.getIdpontuacaodocenteestudante();
                    parametrodocenteestudanteListNewParametrodocenteestudante.setIdpontuacaodocenteestudante(pontuacaodocenteestudante);
                    parametrodocenteestudanteListNewParametrodocenteestudante = em.merge(parametrodocenteestudanteListNewParametrodocenteestudante);
                    if (oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante != null && !oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante.equals(pontuacaodocenteestudante)) {
                        oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudanteListNewParametrodocenteestudante);
                        oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante = em.merge(oldIdpontuacaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = pontuacaodocenteestudante.getIdpontuacaodocenteestudante();
                if (findPontuacaodocenteestudante(id) == null) {
                    throw new NonexistentEntityException("The pontuacaodocenteestudante with id " + id + " no longer exists.");
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
            Pontuacaodocenteestudante pontuacaodocenteestudante;
            try {
                pontuacaodocenteestudante = em.getReference(Pontuacaodocenteestudante.class, id);
                pontuacaodocenteestudante.getIdpontuacaodocenteestudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pontuacaodocenteestudante with id " + id + " no longer exists.", enfe);
            }
            List<Parametrodocenteestudante> parametrodocenteestudanteList = pontuacaodocenteestudante.getParametrodocenteestudanteList();
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudante : parametrodocenteestudanteList) {
                parametrodocenteestudanteListParametrodocenteestudante.setIdpontuacaodocenteestudante(null);
                parametrodocenteestudanteListParametrodocenteestudante = em.merge(parametrodocenteestudanteListParametrodocenteestudante);
            }
            em.remove(pontuacaodocenteestudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pontuacaodocenteestudante> findPontuacaodocenteestudanteEntities() {
        return findPontuacaodocenteestudanteEntities(true, -1, -1);
    }

    public List<Pontuacaodocenteestudante> findPontuacaodocenteestudanteEntities(int maxResults, int firstResult) {
        return findPontuacaodocenteestudanteEntities(false, maxResults, firstResult);
    }

    private List<Pontuacaodocenteestudante> findPontuacaodocenteestudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pontuacaodocenteestudante.class));
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

    public Pontuacaodocenteestudante findPontuacaodocenteestudante(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pontuacaodocenteestudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getPontuacaodocenteestudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pontuacaodocenteestudante> rt = cq.from(Pontuacaodocenteestudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
