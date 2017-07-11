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
import modelo.Parametroautoavaliacao;
import modelo.Questaoautoavaliacao;

/**
 *
 * @author ali_sualei
 */
public class ParametroautoavaliacaoJpaController implements Serializable {

    public ParametroautoavaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parametroautoavaliacao parametroautoavaliacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questaoautoavaliacao idquestaoautoavaliacao = parametroautoavaliacao.getIdquestaoautoavaliacao();
            if (idquestaoautoavaliacao != null) {
                idquestaoautoavaliacao = em.getReference(idquestaoautoavaliacao.getClass(), idquestaoautoavaliacao.getIdquestaoautoavaliacao());
                parametroautoavaliacao.setIdquestaoautoavaliacao(idquestaoautoavaliacao);
            }
            em.persist(parametroautoavaliacao);
            if (idquestaoautoavaliacao != null) {
                idquestaoautoavaliacao.getParametroautoavaliacaoList().add(parametroautoavaliacao);
                idquestaoautoavaliacao = em.merge(idquestaoautoavaliacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parametroautoavaliacao parametroautoavaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametroautoavaliacao persistentParametroautoavaliacao = em.find(Parametroautoavaliacao.class, parametroautoavaliacao.getIdparametroautoavaliacao());
            Questaoautoavaliacao idquestaoautoavaliacaoOld = persistentParametroautoavaliacao.getIdquestaoautoavaliacao();
            Questaoautoavaliacao idquestaoautoavaliacaoNew = parametroautoavaliacao.getIdquestaoautoavaliacao();
            if (idquestaoautoavaliacaoNew != null) {
                idquestaoautoavaliacaoNew = em.getReference(idquestaoautoavaliacaoNew.getClass(), idquestaoautoavaliacaoNew.getIdquestaoautoavaliacao());
                parametroautoavaliacao.setIdquestaoautoavaliacao(idquestaoautoavaliacaoNew);
            }
            parametroautoavaliacao = em.merge(parametroautoavaliacao);
            if (idquestaoautoavaliacaoOld != null && !idquestaoautoavaliacaoOld.equals(idquestaoautoavaliacaoNew)) {
                idquestaoautoavaliacaoOld.getParametroautoavaliacaoList().remove(parametroautoavaliacao);
                idquestaoautoavaliacaoOld = em.merge(idquestaoautoavaliacaoOld);
            }
            if (idquestaoautoavaliacaoNew != null && !idquestaoautoavaliacaoNew.equals(idquestaoautoavaliacaoOld)) {
                idquestaoautoavaliacaoNew.getParametroautoavaliacaoList().add(parametroautoavaliacao);
                idquestaoautoavaliacaoNew = em.merge(idquestaoautoavaliacaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = parametroautoavaliacao.getIdparametroautoavaliacao();
                if (findParametroautoavaliacao(id) == null) {
                    throw new NonexistentEntityException("The parametroautoavaliacao with id " + id + " no longer exists.");
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
            Parametroautoavaliacao parametroautoavaliacao;
            try {
                parametroautoavaliacao = em.getReference(Parametroautoavaliacao.class, id);
                parametroautoavaliacao.getIdparametroautoavaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroautoavaliacao with id " + id + " no longer exists.", enfe);
            }
            Questaoautoavaliacao idquestaoautoavaliacao = parametroautoavaliacao.getIdquestaoautoavaliacao();
            if (idquestaoautoavaliacao != null) {
                idquestaoautoavaliacao.getParametroautoavaliacaoList().remove(parametroautoavaliacao);
                idquestaoautoavaliacao = em.merge(idquestaoautoavaliacao);
            }
            em.remove(parametroautoavaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parametroautoavaliacao> findParametroautoavaliacaoEntities() {
        return findParametroautoavaliacaoEntities(true, -1, -1);
    }

    public List<Parametroautoavaliacao> findParametroautoavaliacaoEntities(int maxResults, int firstResult) {
        return findParametroautoavaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Parametroautoavaliacao> findParametroautoavaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parametroautoavaliacao.class));
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

    public Parametroautoavaliacao findParametroautoavaliacao(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parametroautoavaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroautoavaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parametroautoavaliacao> rt = cq.from(Parametroautoavaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
