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
import modelo.Disciplina;
import modelo.Professor;
import modelo.Pontuacaoconjugada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Autoavaliacao;
import modelo.Questaoautoavaliacao;

/**
 *
 * @author ali_sualei
 */
public class AutoavaliacaoJpaController implements Serializable {

    public AutoavaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autoavaliacao autoavaliacao) {
        if (autoavaliacao.getPontuacaoconjugadaList() == null) {
            autoavaliacao.setPontuacaoconjugadaList(new ArrayList<Pontuacaoconjugada>());
        }
        if (autoavaliacao.getQuestaoautoavaliacaoList() == null) {
            autoavaliacao.setQuestaoautoavaliacaoList(new ArrayList<Questaoautoavaliacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina iddisciplina = autoavaliacao.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina = em.getReference(iddisciplina.getClass(), iddisciplina.getIddisciplina());
                autoavaliacao.setIddisciplina(iddisciplina);
            }
            Professor idprofessor = autoavaliacao.getIdprofessor();
            if (idprofessor != null) {
                idprofessor = em.getReference(idprofessor.getClass(), idprofessor.getIdprofessor());
                autoavaliacao.setIdprofessor(idprofessor);
            }
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaList = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugadaToAttach : autoavaliacao.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaList.add(pontuacaoconjugadaListPontuacaoconjugadaToAttach);
            }
            autoavaliacao.setPontuacaoconjugadaList(attachedPontuacaoconjugadaList);
            List<Questaoautoavaliacao> attachedQuestaoautoavaliacaoList = new ArrayList<Questaoautoavaliacao>();
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacaoToAttach : autoavaliacao.getQuestaoautoavaliacaoList()) {
                questaoautoavaliacaoListQuestaoautoavaliacaoToAttach = em.getReference(questaoautoavaliacaoListQuestaoautoavaliacaoToAttach.getClass(), questaoautoavaliacaoListQuestaoautoavaliacaoToAttach.getIdquestaoautoavaliacao());
                attachedQuestaoautoavaliacaoList.add(questaoautoavaliacaoListQuestaoautoavaliacaoToAttach);
            }
            autoavaliacao.setQuestaoautoavaliacaoList(attachedQuestaoautoavaliacaoList);
            em.persist(autoavaliacao);
            if (iddisciplina != null) {
                iddisciplina.getAutoavaliacaoList().add(autoavaliacao);
                iddisciplina = em.merge(iddisciplina);
            }
            if (idprofessor != null) {
                idprofessor.getAutoavaliacaoList().add(autoavaliacao);
                idprofessor = em.merge(idprofessor);
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : autoavaliacao.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugada.getAutoavaliacaoList().add(autoavaliacao);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacao : autoavaliacao.getQuestaoautoavaliacaoList()) {
                questaoautoavaliacaoListQuestaoautoavaliacao.getAutoavaliacaoList().add(autoavaliacao);
                questaoautoavaliacaoListQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListQuestaoautoavaliacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autoavaliacao autoavaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autoavaliacao persistentAutoavaliacao = em.find(Autoavaliacao.class, autoavaliacao.getIdautoavaliacao());
            Disciplina iddisciplinaOld = persistentAutoavaliacao.getIddisciplina();
            Disciplina iddisciplinaNew = autoavaliacao.getIddisciplina();
            Professor idprofessorOld = persistentAutoavaliacao.getIdprofessor();
            Professor idprofessorNew = autoavaliacao.getIdprofessor();
            List<Pontuacaoconjugada> pontuacaoconjugadaListOld = persistentAutoavaliacao.getPontuacaoconjugadaList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListNew = autoavaliacao.getPontuacaoconjugadaList();
            List<Questaoautoavaliacao> questaoautoavaliacaoListOld = persistentAutoavaliacao.getQuestaoautoavaliacaoList();
            List<Questaoautoavaliacao> questaoautoavaliacaoListNew = autoavaliacao.getQuestaoautoavaliacaoList();
            if (iddisciplinaNew != null) {
                iddisciplinaNew = em.getReference(iddisciplinaNew.getClass(), iddisciplinaNew.getIddisciplina());
                autoavaliacao.setIddisciplina(iddisciplinaNew);
            }
            if (idprofessorNew != null) {
                idprofessorNew = em.getReference(idprofessorNew.getClass(), idprofessorNew.getIdprofessor());
                autoavaliacao.setIdprofessor(idprofessorNew);
            }
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaListNew = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugadaToAttach : pontuacaoconjugadaListNew) {
                pontuacaoconjugadaListNewPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaListNew.add(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach);
            }
            pontuacaoconjugadaListNew = attachedPontuacaoconjugadaListNew;
            autoavaliacao.setPontuacaoconjugadaList(pontuacaoconjugadaListNew);
            List<Questaoautoavaliacao> attachedQuestaoautoavaliacaoListNew = new ArrayList<Questaoautoavaliacao>();
            for (Questaoautoavaliacao questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach : questaoautoavaliacaoListNew) {
                questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach = em.getReference(questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach.getClass(), questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach.getIdquestaoautoavaliacao());
                attachedQuestaoautoavaliacaoListNew.add(questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach);
            }
            questaoautoavaliacaoListNew = attachedQuestaoautoavaliacaoListNew;
            autoavaliacao.setQuestaoautoavaliacaoList(questaoautoavaliacaoListNew);
            autoavaliacao = em.merge(autoavaliacao);
            if (iddisciplinaOld != null && !iddisciplinaOld.equals(iddisciplinaNew)) {
                iddisciplinaOld.getAutoavaliacaoList().remove(autoavaliacao);
                iddisciplinaOld = em.merge(iddisciplinaOld);
            }
            if (iddisciplinaNew != null && !iddisciplinaNew.equals(iddisciplinaOld)) {
                iddisciplinaNew.getAutoavaliacaoList().add(autoavaliacao);
                iddisciplinaNew = em.merge(iddisciplinaNew);
            }
            if (idprofessorOld != null && !idprofessorOld.equals(idprofessorNew)) {
                idprofessorOld.getAutoavaliacaoList().remove(autoavaliacao);
                idprofessorOld = em.merge(idprofessorOld);
            }
            if (idprofessorNew != null && !idprofessorNew.equals(idprofessorOld)) {
                idprofessorNew.getAutoavaliacaoList().add(autoavaliacao);
                idprofessorNew = em.merge(idprofessorNew);
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListOldPontuacaoconjugada : pontuacaoconjugadaListOld) {
                if (!pontuacaoconjugadaListNew.contains(pontuacaoconjugadaListOldPontuacaoconjugada)) {
                    pontuacaoconjugadaListOldPontuacaoconjugada.getAutoavaliacaoList().remove(autoavaliacao);
                    pontuacaoconjugadaListOldPontuacaoconjugada = em.merge(pontuacaoconjugadaListOldPontuacaoconjugada);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugada : pontuacaoconjugadaListNew) {
                if (!pontuacaoconjugadaListOld.contains(pontuacaoconjugadaListNewPontuacaoconjugada)) {
                    pontuacaoconjugadaListNewPontuacaoconjugada.getAutoavaliacaoList().add(autoavaliacao);
                    pontuacaoconjugadaListNewPontuacaoconjugada = em.merge(pontuacaoconjugadaListNewPontuacaoconjugada);
                }
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListOldQuestaoautoavaliacao : questaoautoavaliacaoListOld) {
                if (!questaoautoavaliacaoListNew.contains(questaoautoavaliacaoListOldQuestaoautoavaliacao)) {
                    questaoautoavaliacaoListOldQuestaoautoavaliacao.getAutoavaliacaoList().remove(autoavaliacao);
                    questaoautoavaliacaoListOldQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListOldQuestaoautoavaliacao);
                }
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListNewQuestaoautoavaliacao : questaoautoavaliacaoListNew) {
                if (!questaoautoavaliacaoListOld.contains(questaoautoavaliacaoListNewQuestaoautoavaliacao)) {
                    questaoautoavaliacaoListNewQuestaoautoavaliacao.getAutoavaliacaoList().add(autoavaliacao);
                    questaoautoavaliacaoListNewQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListNewQuestaoautoavaliacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = autoavaliacao.getIdautoavaliacao();
                if (findAutoavaliacao(id) == null) {
                    throw new NonexistentEntityException("The autoavaliacao with id " + id + " no longer exists.");
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
            Autoavaliacao autoavaliacao;
            try {
                autoavaliacao = em.getReference(Autoavaliacao.class, id);
                autoavaliacao.getIdautoavaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autoavaliacao with id " + id + " no longer exists.", enfe);
            }
            Disciplina iddisciplina = autoavaliacao.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina.getAutoavaliacaoList().remove(autoavaliacao);
                iddisciplina = em.merge(iddisciplina);
            }
            Professor idprofessor = autoavaliacao.getIdprofessor();
            if (idprofessor != null) {
                idprofessor.getAutoavaliacaoList().remove(autoavaliacao);
                idprofessor = em.merge(idprofessor);
            }
            List<Pontuacaoconjugada> pontuacaoconjugadaList = autoavaliacao.getPontuacaoconjugadaList();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : pontuacaoconjugadaList) {
                pontuacaoconjugadaListPontuacaoconjugada.getAutoavaliacaoList().remove(autoavaliacao);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            List<Questaoautoavaliacao> questaoautoavaliacaoList = autoavaliacao.getQuestaoautoavaliacaoList();
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacao : questaoautoavaliacaoList) {
                questaoautoavaliacaoListQuestaoautoavaliacao.getAutoavaliacaoList().remove(autoavaliacao);
                questaoautoavaliacaoListQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListQuestaoautoavaliacao);
            }
            em.remove(autoavaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autoavaliacao> findAutoavaliacaoEntities() {
        return findAutoavaliacaoEntities(true, -1, -1);
    }

    public List<Autoavaliacao> findAutoavaliacaoEntities(int maxResults, int firstResult) {
        return findAutoavaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Autoavaliacao> findAutoavaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autoavaliacao.class));
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

    public Autoavaliacao findAutoavaliacao(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autoavaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutoavaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autoavaliacao> rt = cq.from(Autoavaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
