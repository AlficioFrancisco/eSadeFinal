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
import modelo.Avaliacaodocenteestudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Parametrodocenteestudante;
import modelo.Questaodocenteestudante;

/**
 *
 * @author ali_sualei
 */
public class QuestaodocenteestudanteJpaController implements Serializable {

    public QuestaodocenteestudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questaodocenteestudante questaodocenteestudante) {
        if (questaodocenteestudante.getAvaliacaodocenteestudanteList() == null) {
            questaodocenteestudante.setAvaliacaodocenteestudanteList(new ArrayList<Avaliacaodocenteestudante>());
        }
        if (questaodocenteestudante.getParametrodocenteestudanteList() == null) {
            questaodocenteestudante.setParametrodocenteestudanteList(new ArrayList<Parametrodocenteestudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteList = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach : questaodocenteestudante.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteList.add(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach);
            }
            questaodocenteestudante.setAvaliacaodocenteestudanteList(attachedAvaliacaodocenteestudanteList);
            List<Parametrodocenteestudante> attachedParametrodocenteestudanteList = new ArrayList<Parametrodocenteestudante>();
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudanteToAttach : questaodocenteestudante.getParametrodocenteestudanteList()) {
                parametrodocenteestudanteListParametrodocenteestudanteToAttach = em.getReference(parametrodocenteestudanteListParametrodocenteestudanteToAttach.getClass(), parametrodocenteestudanteListParametrodocenteestudanteToAttach.getIdparametrodocenteestudante());
                attachedParametrodocenteestudanteList.add(parametrodocenteestudanteListParametrodocenteestudanteToAttach);
            }
            questaodocenteestudante.setParametrodocenteestudanteList(attachedParametrodocenteestudanteList);
            em.persist(questaodocenteestudante);
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : questaodocenteestudante.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.getQuestaodocenteestudanteList().add(questaodocenteestudante);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudante : questaodocenteestudante.getParametrodocenteestudanteList()) {
                Questaodocenteestudante oldIdquestaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante = parametrodocenteestudanteListParametrodocenteestudante.getIdquestaodocenteestudante();
                parametrodocenteestudanteListParametrodocenteestudante.setIdquestaodocenteestudante(questaodocenteestudante);
                parametrodocenteestudanteListParametrodocenteestudante = em.merge(parametrodocenteestudanteListParametrodocenteestudante);
                if (oldIdquestaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante != null) {
                    oldIdquestaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudanteListParametrodocenteestudante);
                    oldIdquestaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante = em.merge(oldIdquestaodocenteestudanteOfParametrodocenteestudanteListParametrodocenteestudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questaodocenteestudante questaodocenteestudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questaodocenteestudante persistentQuestaodocenteestudante = em.find(Questaodocenteestudante.class, questaodocenteestudante.getIdquestaodocenteestudante());
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListOld = persistentQuestaodocenteestudante.getAvaliacaodocenteestudanteList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListNew = questaodocenteestudante.getAvaliacaodocenteestudanteList();
            List<Parametrodocenteestudante> parametrodocenteestudanteListOld = persistentQuestaodocenteestudante.getParametrodocenteestudanteList();
            List<Parametrodocenteestudante> parametrodocenteestudanteListNew = questaodocenteestudante.getParametrodocenteestudanteList();
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteListNew = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach : avaliacaodocenteestudanteListNew) {
                avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteListNew.add(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudanteListNew = attachedAvaliacaodocenteestudanteListNew;
            questaodocenteestudante.setAvaliacaodocenteestudanteList(avaliacaodocenteestudanteListNew);
            List<Parametrodocenteestudante> attachedParametrodocenteestudanteListNew = new ArrayList<Parametrodocenteestudante>();
            for (Parametrodocenteestudante parametrodocenteestudanteListNewParametrodocenteestudanteToAttach : parametrodocenteestudanteListNew) {
                parametrodocenteestudanteListNewParametrodocenteestudanteToAttach = em.getReference(parametrodocenteestudanteListNewParametrodocenteestudanteToAttach.getClass(), parametrodocenteestudanteListNewParametrodocenteestudanteToAttach.getIdparametrodocenteestudante());
                attachedParametrodocenteestudanteListNew.add(parametrodocenteestudanteListNewParametrodocenteestudanteToAttach);
            }
            parametrodocenteestudanteListNew = attachedParametrodocenteestudanteListNew;
            questaodocenteestudante.setParametrodocenteestudanteList(parametrodocenteestudanteListNew);
            questaodocenteestudante = em.merge(questaodocenteestudante);
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListOldAvaliacaodocenteestudante : avaliacaodocenteestudanteListOld) {
                if (!avaliacaodocenteestudanteListNew.contains(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante.getQuestaodocenteestudanteList().remove(questaodocenteestudante);
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudante : avaliacaodocenteestudanteListNew) {
                if (!avaliacaodocenteestudanteListOld.contains(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getQuestaodocenteestudanteList().add(questaodocenteestudante);
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                }
            }
            for (Parametrodocenteestudante parametrodocenteestudanteListOldParametrodocenteestudante : parametrodocenteestudanteListOld) {
                if (!parametrodocenteestudanteListNew.contains(parametrodocenteestudanteListOldParametrodocenteestudante)) {
                    parametrodocenteestudanteListOldParametrodocenteestudante.setIdquestaodocenteestudante(null);
                    parametrodocenteestudanteListOldParametrodocenteestudante = em.merge(parametrodocenteestudanteListOldParametrodocenteestudante);
                }
            }
            for (Parametrodocenteestudante parametrodocenteestudanteListNewParametrodocenteestudante : parametrodocenteestudanteListNew) {
                if (!parametrodocenteestudanteListOld.contains(parametrodocenteestudanteListNewParametrodocenteestudante)) {
                    Questaodocenteestudante oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante = parametrodocenteestudanteListNewParametrodocenteestudante.getIdquestaodocenteestudante();
                    parametrodocenteestudanteListNewParametrodocenteestudante.setIdquestaodocenteestudante(questaodocenteestudante);
                    parametrodocenteestudanteListNewParametrodocenteestudante = em.merge(parametrodocenteestudanteListNewParametrodocenteestudante);
                    if (oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante != null && !oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante.equals(questaodocenteestudante)) {
                        oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante.getParametrodocenteestudanteList().remove(parametrodocenteestudanteListNewParametrodocenteestudante);
                        oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante = em.merge(oldIdquestaodocenteestudanteOfParametrodocenteestudanteListNewParametrodocenteestudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = questaodocenteestudante.getIdquestaodocenteestudante();
                if (findQuestaodocenteestudante(id) == null) {
                    throw new NonexistentEntityException("The questaodocenteestudante with id " + id + " no longer exists.");
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
            Questaodocenteestudante questaodocenteestudante;
            try {
                questaodocenteestudante = em.getReference(Questaodocenteestudante.class, id);
                questaodocenteestudante.getIdquestaodocenteestudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questaodocenteestudante with id " + id + " no longer exists.", enfe);
            }
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList = questaodocenteestudante.getAvaliacaodocenteestudanteList();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : avaliacaodocenteestudanteList) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.getQuestaodocenteestudanteList().remove(questaodocenteestudante);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            List<Parametrodocenteestudante> parametrodocenteestudanteList = questaodocenteestudante.getParametrodocenteestudanteList();
            for (Parametrodocenteestudante parametrodocenteestudanteListParametrodocenteestudante : parametrodocenteestudanteList) {
                parametrodocenteestudanteListParametrodocenteestudante.setIdquestaodocenteestudante(null);
                parametrodocenteestudanteListParametrodocenteestudante = em.merge(parametrodocenteestudanteListParametrodocenteestudante);
            }
            em.remove(questaodocenteestudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Questaodocenteestudante> findQuestaodocenteestudanteEntities() {
        return findQuestaodocenteestudanteEntities(true, -1, -1);
    }

    public List<Questaodocenteestudante> findQuestaodocenteestudanteEntities(int maxResults, int firstResult) {
        return findQuestaodocenteestudanteEntities(false, maxResults, firstResult);
    }

    private List<Questaodocenteestudante> findQuestaodocenteestudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questaodocenteestudante.class));
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

    public Questaodocenteestudante findQuestaodocenteestudante(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questaodocenteestudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestaodocenteestudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questaodocenteestudante> rt = cq.from(Questaodocenteestudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
