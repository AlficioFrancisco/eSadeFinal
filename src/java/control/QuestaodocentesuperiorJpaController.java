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
import modelo.Caracteristiquestaosuperior;
import modelo.Pontuacaosuperiorhierarquico;
import modelo.Avaliacaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Questaodocentesuperior;

/**
 *
 * @author ali_sualei
 */
public class QuestaodocentesuperiorJpaController implements Serializable {

    public QuestaodocentesuperiorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questaodocentesuperior questaodocentesuperior) {
        if (questaodocentesuperior.getAvaliacaodocentesuperiorList() == null) {
            questaodocentesuperior.setAvaliacaodocentesuperiorList(new ArrayList<Avaliacaodocentesuperior>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristiquestaosuperior idcaracteristicaquestaosuperior = questaodocentesuperior.getIdcaracteristicaquestaosuperior();
            if (idcaracteristicaquestaosuperior != null) {
                idcaracteristicaquestaosuperior = em.getReference(idcaracteristicaquestaosuperior.getClass(), idcaracteristicaquestaosuperior.getIdcaracteristicasuperior());
                questaodocentesuperior.setIdcaracteristicaquestaosuperior(idcaracteristicaquestaosuperior);
            }
            Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquico = questaodocentesuperior.getIdpontuacaosuperiorhierarquico();
            if (idpontuacaosuperiorhierarquico != null) {
                idpontuacaosuperiorhierarquico = em.getReference(idpontuacaosuperiorhierarquico.getClass(), idpontuacaosuperiorhierarquico.getIdpontuacaosuperiorhier());
                questaodocentesuperior.setIdpontuacaosuperiorhierarquico(idpontuacaosuperiorhierarquico);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorList = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach : questaodocentesuperior.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorList.add(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach);
            }
            questaodocentesuperior.setAvaliacaodocentesuperiorList(attachedAvaliacaodocentesuperiorList);
            em.persist(questaodocentesuperior);
            if (idcaracteristicaquestaosuperior != null) {
                idcaracteristicaquestaosuperior.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                idcaracteristicaquestaosuperior = em.merge(idcaracteristicaquestaosuperior);
            }
            if (idpontuacaosuperiorhierarquico != null) {
                idpontuacaosuperiorhierarquico.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                idpontuacaosuperiorhierarquico = em.merge(idpontuacaosuperiorhierarquico);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : questaodocentesuperior.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questaodocentesuperior questaodocentesuperior) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questaodocentesuperior persistentQuestaodocentesuperior = em.find(Questaodocentesuperior.class, questaodocentesuperior.getIdquestaodocentesuperior());
            Caracteristiquestaosuperior idcaracteristicaquestaosuperiorOld = persistentQuestaodocentesuperior.getIdcaracteristicaquestaosuperior();
            Caracteristiquestaosuperior idcaracteristicaquestaosuperiorNew = questaodocentesuperior.getIdcaracteristicaquestaosuperior();
            Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquicoOld = persistentQuestaodocentesuperior.getIdpontuacaosuperiorhierarquico();
            Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquicoNew = questaodocentesuperior.getIdpontuacaosuperiorhierarquico();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListOld = persistentQuestaodocentesuperior.getAvaliacaodocentesuperiorList();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListNew = questaodocentesuperior.getAvaliacaodocentesuperiorList();
            if (idcaracteristicaquestaosuperiorNew != null) {
                idcaracteristicaquestaosuperiorNew = em.getReference(idcaracteristicaquestaosuperiorNew.getClass(), idcaracteristicaquestaosuperiorNew.getIdcaracteristicasuperior());
                questaodocentesuperior.setIdcaracteristicaquestaosuperior(idcaracteristicaquestaosuperiorNew);
            }
            if (idpontuacaosuperiorhierarquicoNew != null) {
                idpontuacaosuperiorhierarquicoNew = em.getReference(idpontuacaosuperiorhierarquicoNew.getClass(), idpontuacaosuperiorhierarquicoNew.getIdpontuacaosuperiorhier());
                questaodocentesuperior.setIdpontuacaosuperiorhierarquico(idpontuacaosuperiorhierarquicoNew);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorListNew = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach : avaliacaodocentesuperiorListNew) {
                avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorListNew.add(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperiorListNew = attachedAvaliacaodocentesuperiorListNew;
            questaodocentesuperior.setAvaliacaodocentesuperiorList(avaliacaodocentesuperiorListNew);
            questaodocentesuperior = em.merge(questaodocentesuperior);
            if (idcaracteristicaquestaosuperiorOld != null && !idcaracteristicaquestaosuperiorOld.equals(idcaracteristicaquestaosuperiorNew)) {
                idcaracteristicaquestaosuperiorOld.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                idcaracteristicaquestaosuperiorOld = em.merge(idcaracteristicaquestaosuperiorOld);
            }
            if (idcaracteristicaquestaosuperiorNew != null && !idcaracteristicaquestaosuperiorNew.equals(idcaracteristicaquestaosuperiorOld)) {
                idcaracteristicaquestaosuperiorNew.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                idcaracteristicaquestaosuperiorNew = em.merge(idcaracteristicaquestaosuperiorNew);
            }
            if (idpontuacaosuperiorhierarquicoOld != null && !idpontuacaosuperiorhierarquicoOld.equals(idpontuacaosuperiorhierarquicoNew)) {
                idpontuacaosuperiorhierarquicoOld.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                idpontuacaosuperiorhierarquicoOld = em.merge(idpontuacaosuperiorhierarquicoOld);
            }
            if (idpontuacaosuperiorhierarquicoNew != null && !idpontuacaosuperiorhierarquicoNew.equals(idpontuacaosuperiorhierarquicoOld)) {
                idpontuacaosuperiorhierarquicoNew.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                idpontuacaosuperiorhierarquicoNew = em.merge(idpontuacaosuperiorhierarquicoNew);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListOldAvaliacaodocentesuperior : avaliacaodocentesuperiorListOld) {
                if (!avaliacaodocentesuperiorListNew.contains(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior);
                }
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperior : avaliacaodocentesuperiorListNew) {
                if (!avaliacaodocentesuperiorListOld.contains(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getQuestaodocentesuperiorList().add(questaodocentesuperior);
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = questaodocentesuperior.getIdquestaodocentesuperior();
                if (findQuestaodocentesuperior(id) == null) {
                    throw new NonexistentEntityException("The questaodocentesuperior with id " + id + " no longer exists.");
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
            Questaodocentesuperior questaodocentesuperior;
            try {
                questaodocentesuperior = em.getReference(Questaodocentesuperior.class, id);
                questaodocentesuperior.getIdquestaodocentesuperior();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questaodocentesuperior with id " + id + " no longer exists.", enfe);
            }
            Caracteristiquestaosuperior idcaracteristicaquestaosuperior = questaodocentesuperior.getIdcaracteristicaquestaosuperior();
            if (idcaracteristicaquestaosuperior != null) {
                idcaracteristicaquestaosuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                idcaracteristicaquestaosuperior = em.merge(idcaracteristicaquestaosuperior);
            }
            Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquico = questaodocentesuperior.getIdpontuacaosuperiorhierarquico();
            if (idpontuacaosuperiorhierarquico != null) {
                idpontuacaosuperiorhierarquico.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                idpontuacaosuperiorhierarquico = em.merge(idpontuacaosuperiorhierarquico);
            }
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList = questaodocentesuperior.getAvaliacaodocentesuperiorList();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : avaliacaodocentesuperiorList) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.getQuestaodocentesuperiorList().remove(questaodocentesuperior);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            em.remove(questaodocentesuperior);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Questaodocentesuperior> findQuestaodocentesuperiorEntities() {
        return findQuestaodocentesuperiorEntities(true, -1, -1);
    }

    public List<Questaodocentesuperior> findQuestaodocentesuperiorEntities(int maxResults, int firstResult) {
        return findQuestaodocentesuperiorEntities(false, maxResults, firstResult);
    }

    private List<Questaodocentesuperior> findQuestaodocentesuperiorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questaodocentesuperior.class));
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

    public Questaodocentesuperior findQuestaodocentesuperior(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questaodocentesuperior.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestaodocentesuperiorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questaodocentesuperior> rt = cq.from(Questaodocentesuperior.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
