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
import modelo.Parametroautoavaliacao;
import modelo.Questaoautoavaliacao;

/**
 *
 * @author ali_sualei
 */
public class QuestaoautoavaliacaoJpaController implements Serializable {

    public QuestaoautoavaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questaoautoavaliacao questaoautoavaliacao) {
        if (questaoautoavaliacao.getAutoavaliacaoList() == null) {
            questaoautoavaliacao.setAutoavaliacaoList(new ArrayList<Autoavaliacao>());
        }
        if (questaoautoavaliacao.getParametroautoavaliacaoList() == null) {
            questaoautoavaliacao.setParametroautoavaliacaoList(new ArrayList<Parametroautoavaliacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Autoavaliacao> attachedAutoavaliacaoList = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListAutoavaliacaoToAttach : questaoautoavaliacao.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacaoToAttach = em.getReference(autoavaliacaoListAutoavaliacaoToAttach.getClass(), autoavaliacaoListAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoList.add(autoavaliacaoListAutoavaliacaoToAttach);
            }
            questaoautoavaliacao.setAutoavaliacaoList(attachedAutoavaliacaoList);
            List<Parametroautoavaliacao> attachedParametroautoavaliacaoList = new ArrayList<Parametroautoavaliacao>();
            for (Parametroautoavaliacao parametroautoavaliacaoListParametroautoavaliacaoToAttach : questaoautoavaliacao.getParametroautoavaliacaoList()) {
                parametroautoavaliacaoListParametroautoavaliacaoToAttach = em.getReference(parametroautoavaliacaoListParametroautoavaliacaoToAttach.getClass(), parametroautoavaliacaoListParametroautoavaliacaoToAttach.getIdparametroautoavaliacao());
                attachedParametroautoavaliacaoList.add(parametroautoavaliacaoListParametroautoavaliacaoToAttach);
            }
            questaoautoavaliacao.setParametroautoavaliacaoList(attachedParametroautoavaliacaoList);
            em.persist(questaoautoavaliacao);
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : questaoautoavaliacao.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacao.getQuestaoautoavaliacaoList().add(questaoautoavaliacao);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            for (Parametroautoavaliacao parametroautoavaliacaoListParametroautoavaliacao : questaoautoavaliacao.getParametroautoavaliacaoList()) {
                Questaoautoavaliacao oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListParametroautoavaliacao = parametroautoavaliacaoListParametroautoavaliacao.getIdquestaoautoavaliacao();
                parametroautoavaliacaoListParametroautoavaliacao.setIdquestaoautoavaliacao(questaoautoavaliacao);
                parametroautoavaliacaoListParametroautoavaliacao = em.merge(parametroautoavaliacaoListParametroautoavaliacao);
                if (oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListParametroautoavaliacao != null) {
                    oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListParametroautoavaliacao.getParametroautoavaliacaoList().remove(parametroautoavaliacaoListParametroautoavaliacao);
                    oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListParametroautoavaliacao = em.merge(oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListParametroautoavaliacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questaoautoavaliacao questaoautoavaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questaoautoavaliacao persistentQuestaoautoavaliacao = em.find(Questaoautoavaliacao.class, questaoautoavaliacao.getIdquestaoautoavaliacao());
            List<Autoavaliacao> autoavaliacaoListOld = persistentQuestaoautoavaliacao.getAutoavaliacaoList();
            List<Autoavaliacao> autoavaliacaoListNew = questaoautoavaliacao.getAutoavaliacaoList();
            List<Parametroautoavaliacao> parametroautoavaliacaoListOld = persistentQuestaoautoavaliacao.getParametroautoavaliacaoList();
            List<Parametroautoavaliacao> parametroautoavaliacaoListNew = questaoautoavaliacao.getParametroautoavaliacaoList();
            List<Autoavaliacao> attachedAutoavaliacaoListNew = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacaoToAttach : autoavaliacaoListNew) {
                autoavaliacaoListNewAutoavaliacaoToAttach = em.getReference(autoavaliacaoListNewAutoavaliacaoToAttach.getClass(), autoavaliacaoListNewAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoListNew.add(autoavaliacaoListNewAutoavaliacaoToAttach);
            }
            autoavaliacaoListNew = attachedAutoavaliacaoListNew;
            questaoautoavaliacao.setAutoavaliacaoList(autoavaliacaoListNew);
            List<Parametroautoavaliacao> attachedParametroautoavaliacaoListNew = new ArrayList<Parametroautoavaliacao>();
            for (Parametroautoavaliacao parametroautoavaliacaoListNewParametroautoavaliacaoToAttach : parametroautoavaliacaoListNew) {
                parametroautoavaliacaoListNewParametroautoavaliacaoToAttach = em.getReference(parametroautoavaliacaoListNewParametroautoavaliacaoToAttach.getClass(), parametroautoavaliacaoListNewParametroautoavaliacaoToAttach.getIdparametroautoavaliacao());
                attachedParametroautoavaliacaoListNew.add(parametroautoavaliacaoListNewParametroautoavaliacaoToAttach);
            }
            parametroautoavaliacaoListNew = attachedParametroautoavaliacaoListNew;
            questaoautoavaliacao.setParametroautoavaliacaoList(parametroautoavaliacaoListNew);
            questaoautoavaliacao = em.merge(questaoautoavaliacao);
            for (Autoavaliacao autoavaliacaoListOldAutoavaliacao : autoavaliacaoListOld) {
                if (!autoavaliacaoListNew.contains(autoavaliacaoListOldAutoavaliacao)) {
                    autoavaliacaoListOldAutoavaliacao.getQuestaoautoavaliacaoList().remove(questaoautoavaliacao);
                    autoavaliacaoListOldAutoavaliacao = em.merge(autoavaliacaoListOldAutoavaliacao);
                }
            }
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacao : autoavaliacaoListNew) {
                if (!autoavaliacaoListOld.contains(autoavaliacaoListNewAutoavaliacao)) {
                    autoavaliacaoListNewAutoavaliacao.getQuestaoautoavaliacaoList().add(questaoautoavaliacao);
                    autoavaliacaoListNewAutoavaliacao = em.merge(autoavaliacaoListNewAutoavaliacao);
                }
            }
            for (Parametroautoavaliacao parametroautoavaliacaoListOldParametroautoavaliacao : parametroautoavaliacaoListOld) {
                if (!parametroautoavaliacaoListNew.contains(parametroautoavaliacaoListOldParametroautoavaliacao)) {
                    parametroautoavaliacaoListOldParametroautoavaliacao.setIdquestaoautoavaliacao(null);
                    parametroautoavaliacaoListOldParametroautoavaliacao = em.merge(parametroautoavaliacaoListOldParametroautoavaliacao);
                }
            }
            for (Parametroautoavaliacao parametroautoavaliacaoListNewParametroautoavaliacao : parametroautoavaliacaoListNew) {
                if (!parametroautoavaliacaoListOld.contains(parametroautoavaliacaoListNewParametroautoavaliacao)) {
                    Questaoautoavaliacao oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao = parametroautoavaliacaoListNewParametroautoavaliacao.getIdquestaoautoavaliacao();
                    parametroautoavaliacaoListNewParametroautoavaliacao.setIdquestaoautoavaliacao(questaoautoavaliacao);
                    parametroautoavaliacaoListNewParametroautoavaliacao = em.merge(parametroautoavaliacaoListNewParametroautoavaliacao);
                    if (oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao != null && !oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao.equals(questaoautoavaliacao)) {
                        oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao.getParametroautoavaliacaoList().remove(parametroautoavaliacaoListNewParametroautoavaliacao);
                        oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao = em.merge(oldIdquestaoautoavaliacaoOfParametroautoavaliacaoListNewParametroautoavaliacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = questaoautoavaliacao.getIdquestaoautoavaliacao();
                if (findQuestaoautoavaliacao(id) == null) {
                    throw new NonexistentEntityException("The questaoautoavaliacao with id " + id + " no longer exists.");
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
            Questaoautoavaliacao questaoautoavaliacao;
            try {
                questaoautoavaliacao = em.getReference(Questaoautoavaliacao.class, id);
                questaoautoavaliacao.getIdquestaoautoavaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questaoautoavaliacao with id " + id + " no longer exists.", enfe);
            }
            List<Autoavaliacao> autoavaliacaoList = questaoautoavaliacao.getAutoavaliacaoList();
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : autoavaliacaoList) {
                autoavaliacaoListAutoavaliacao.getQuestaoautoavaliacaoList().remove(questaoautoavaliacao);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            List<Parametroautoavaliacao> parametroautoavaliacaoList = questaoautoavaliacao.getParametroautoavaliacaoList();
            for (Parametroautoavaliacao parametroautoavaliacaoListParametroautoavaliacao : parametroautoavaliacaoList) {
                parametroautoavaliacaoListParametroautoavaliacao.setIdquestaoautoavaliacao(null);
                parametroautoavaliacaoListParametroautoavaliacao = em.merge(parametroautoavaliacaoListParametroautoavaliacao);
            }
            em.remove(questaoautoavaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Questaoautoavaliacao> findQuestaoautoavaliacaoEntities() {
        return findQuestaoautoavaliacaoEntities(true, -1, -1);
    }

    public List<Questaoautoavaliacao> findQuestaoautoavaliacaoEntities(int maxResults, int firstResult) {
        return findQuestaoautoavaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Questaoautoavaliacao> findQuestaoautoavaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questaoautoavaliacao.class));
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

    public Questaoautoavaliacao findQuestaoautoavaliacao(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questaoautoavaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestaoautoavaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questaoautoavaliacao> rt = cq.from(Questaoautoavaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
