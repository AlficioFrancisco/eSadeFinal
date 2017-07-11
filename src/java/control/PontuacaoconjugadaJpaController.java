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
import modelo.Autoavaliacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avaliacaodocentesuperior;
import modelo.Avaliacaodocenteestudante;
import modelo.Pontuacaoconjugada;

/**
 *
 * @author ali_sualei
 */
public class PontuacaoconjugadaJpaController implements Serializable {

    public PontuacaoconjugadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pontuacaoconjugada pontuacaoconjugada) {
        if (pontuacaoconjugada.getAutoavaliacaoList() == null) {
            pontuacaoconjugada.setAutoavaliacaoList(new ArrayList<Autoavaliacao>());
        }
        if (pontuacaoconjugada.getAvaliacaodocentesuperiorList() == null) {
            pontuacaoconjugada.setAvaliacaodocentesuperiorList(new ArrayList<Avaliacaodocentesuperior>());
        }
        if (pontuacaoconjugada.getAvaliacaodocenteestudanteList() == null) {
            pontuacaoconjugada.setAvaliacaodocenteestudanteList(new ArrayList<Avaliacaodocenteestudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Autoavaliacao> attachedAutoavaliacaoList = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListAutoavaliacaoToAttach : pontuacaoconjugada.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacaoToAttach = em.getReference(autoavaliacaoListAutoavaliacaoToAttach.getClass(), autoavaliacaoListAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoList.add(autoavaliacaoListAutoavaliacaoToAttach);
            }
            pontuacaoconjugada.setAutoavaliacaoList(attachedAutoavaliacaoList);
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorList = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach : pontuacaoconjugada.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorList.add(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach);
            }
            pontuacaoconjugada.setAvaliacaodocentesuperiorList(attachedAvaliacaodocentesuperiorList);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteList = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach : pontuacaoconjugada.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteList.add(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach);
            }
            pontuacaoconjugada.setAvaliacaodocenteestudanteList(attachedAvaliacaodocenteestudanteList);
            em.persist(pontuacaoconjugada);
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : pontuacaoconjugada.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacao.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : pontuacaoconjugada.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : pontuacaoconjugada.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pontuacaoconjugada pontuacaoconjugada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontuacaoconjugada persistentPontuacaoconjugada = em.find(Pontuacaoconjugada.class, pontuacaoconjugada.getIdpontuacaoconjugada());
            List<Autoavaliacao> autoavaliacaoListOld = persistentPontuacaoconjugada.getAutoavaliacaoList();
            List<Autoavaliacao> autoavaliacaoListNew = pontuacaoconjugada.getAutoavaliacaoList();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListOld = persistentPontuacaoconjugada.getAvaliacaodocentesuperiorList();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListNew = pontuacaoconjugada.getAvaliacaodocentesuperiorList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListOld = persistentPontuacaoconjugada.getAvaliacaodocenteestudanteList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListNew = pontuacaoconjugada.getAvaliacaodocenteestudanteList();
            List<Autoavaliacao> attachedAutoavaliacaoListNew = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacaoToAttach : autoavaliacaoListNew) {
                autoavaliacaoListNewAutoavaliacaoToAttach = em.getReference(autoavaliacaoListNewAutoavaliacaoToAttach.getClass(), autoavaliacaoListNewAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoListNew.add(autoavaliacaoListNewAutoavaliacaoToAttach);
            }
            autoavaliacaoListNew = attachedAutoavaliacaoListNew;
            pontuacaoconjugada.setAutoavaliacaoList(autoavaliacaoListNew);
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorListNew = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach : avaliacaodocentesuperiorListNew) {
                avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorListNew.add(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperiorListNew = attachedAvaliacaodocentesuperiorListNew;
            pontuacaoconjugada.setAvaliacaodocentesuperiorList(avaliacaodocentesuperiorListNew);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteListNew = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach : avaliacaodocenteestudanteListNew) {
                avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteListNew.add(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudanteListNew = attachedAvaliacaodocenteestudanteListNew;
            pontuacaoconjugada.setAvaliacaodocenteestudanteList(avaliacaodocenteestudanteListNew);
            pontuacaoconjugada = em.merge(pontuacaoconjugada);
            for (Autoavaliacao autoavaliacaoListOldAutoavaliacao : autoavaliacaoListOld) {
                if (!autoavaliacaoListNew.contains(autoavaliacaoListOldAutoavaliacao)) {
                    autoavaliacaoListOldAutoavaliacao.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                    autoavaliacaoListOldAutoavaliacao = em.merge(autoavaliacaoListOldAutoavaliacao);
                }
            }
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacao : autoavaliacaoListNew) {
                if (!autoavaliacaoListOld.contains(autoavaliacaoListNewAutoavaliacao)) {
                    autoavaliacaoListNewAutoavaliacao.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                    autoavaliacaoListNewAutoavaliacao = em.merge(autoavaliacaoListNewAutoavaliacao);
                }
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListOldAvaliacaodocentesuperior : avaliacaodocentesuperiorListOld) {
                if (!avaliacaodocentesuperiorListNew.contains(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior);
                }
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperior : avaliacaodocentesuperiorListNew) {
                if (!avaliacaodocentesuperiorListOld.contains(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListOldAvaliacaodocenteestudante : avaliacaodocenteestudanteListOld) {
                if (!avaliacaodocenteestudanteListNew.contains(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudante : avaliacaodocenteestudanteListNew) {
                if (!avaliacaodocenteestudanteListOld.contains(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getPontuacaoconjugadaList().add(pontuacaoconjugada);
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = pontuacaoconjugada.getIdpontuacaoconjugada();
                if (findPontuacaoconjugada(id) == null) {
                    throw new NonexistentEntityException("The pontuacaoconjugada with id " + id + " no longer exists.");
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
            Pontuacaoconjugada pontuacaoconjugada;
            try {
                pontuacaoconjugada = em.getReference(Pontuacaoconjugada.class, id);
                pontuacaoconjugada.getIdpontuacaoconjugada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pontuacaoconjugada with id " + id + " no longer exists.", enfe);
            }
            List<Autoavaliacao> autoavaliacaoList = pontuacaoconjugada.getAutoavaliacaoList();
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : autoavaliacaoList) {
                autoavaliacaoListAutoavaliacao.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList = pontuacaoconjugada.getAvaliacaodocentesuperiorList();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : avaliacaodocentesuperiorList) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList = pontuacaoconjugada.getAvaliacaodocenteestudanteList();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : avaliacaodocenteestudanteList) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.getPontuacaoconjugadaList().remove(pontuacaoconjugada);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            em.remove(pontuacaoconjugada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pontuacaoconjugada> findPontuacaoconjugadaEntities() {
        return findPontuacaoconjugadaEntities(true, -1, -1);
    }

    public List<Pontuacaoconjugada> findPontuacaoconjugadaEntities(int maxResults, int firstResult) {
        return findPontuacaoconjugadaEntities(false, maxResults, firstResult);
    }

    private List<Pontuacaoconjugada> findPontuacaoconjugadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pontuacaoconjugada.class));
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

    public Pontuacaoconjugada findPontuacaoconjugada(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pontuacaoconjugada.class, id);
        } finally {
            em.close();
        }
    }

    public int getPontuacaoconjugadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pontuacaoconjugada> rt = cq.from(Pontuacaoconjugada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
