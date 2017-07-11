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
import modelo.Questaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avaliacaodocentesuperior;
import modelo.Pontuacaoconjugada;

/**
 *
 * @author ali_sualei
 */
public class AvaliacaodocentesuperiorJpaController implements Serializable {

    public AvaliacaodocentesuperiorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacaodocentesuperior avaliacaodocentesuperior) {
        if (avaliacaodocentesuperior.getQuestaodocentesuperiorList() == null) {
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(new ArrayList<Questaodocentesuperior>());
        }
        if (avaliacaodocentesuperior.getPontuacaoconjugadaList() == null) {
            avaliacaodocentesuperior.setPontuacaoconjugadaList(new ArrayList<Pontuacaoconjugada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina iddisciplina = avaliacaodocentesuperior.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina = em.getReference(iddisciplina.getClass(), iddisciplina.getIddisciplina());
                avaliacaodocentesuperior.setIddisciplina(iddisciplina);
            }
            Professor idprofessor = avaliacaodocentesuperior.getIdprofessor();
            if (idprofessor != null) {
                idprofessor = em.getReference(idprofessor.getClass(), idprofessor.getIdprofessor());
                avaliacaodocentesuperior.setIdprofessor(idprofessor);
            }
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorList = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperiorToAttach : avaliacaodocentesuperior.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorList.add(questaodocentesuperiorListQuestaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(attachedQuestaodocentesuperiorList);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaList = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugadaToAttach : avaliacaodocentesuperior.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaList.add(pontuacaoconjugadaListPontuacaoconjugadaToAttach);
            }
            avaliacaodocentesuperior.setPontuacaoconjugadaList(attachedPontuacaoconjugadaList);
            em.persist(avaliacaodocentesuperior);
            if (iddisciplina != null) {
                iddisciplina.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                iddisciplina = em.merge(iddisciplina);
            }
            if (idprofessor != null) {
                idprofessor.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                idprofessor = em.merge(idprofessor);
            }
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : avaliacaodocentesuperior.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperior.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : avaliacaodocentesuperior.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacaodocentesuperior avaliacaodocentesuperior) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocentesuperior persistentAvaliacaodocentesuperior = em.find(Avaliacaodocentesuperior.class, avaliacaodocentesuperior.getIdavaliacaodocentesuperior());
            Disciplina iddisciplinaOld = persistentAvaliacaodocentesuperior.getIddisciplina();
            Disciplina iddisciplinaNew = avaliacaodocentesuperior.getIddisciplina();
            Professor idprofessorOld = persistentAvaliacaodocentesuperior.getIdprofessor();
            Professor idprofessorNew = avaliacaodocentesuperior.getIdprofessor();
            List<Questaodocentesuperior> questaodocentesuperiorListOld = persistentAvaliacaodocentesuperior.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> questaodocentesuperiorListNew = avaliacaodocentesuperior.getQuestaodocentesuperiorList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListOld = persistentAvaliacaodocentesuperior.getPontuacaoconjugadaList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListNew = avaliacaodocentesuperior.getPontuacaoconjugadaList();
            if (iddisciplinaNew != null) {
                iddisciplinaNew = em.getReference(iddisciplinaNew.getClass(), iddisciplinaNew.getIddisciplina());
                avaliacaodocentesuperior.setIddisciplina(iddisciplinaNew);
            }
            if (idprofessorNew != null) {
                idprofessorNew = em.getReference(idprofessorNew.getClass(), idprofessorNew.getIdprofessor());
                avaliacaodocentesuperior.setIdprofessor(idprofessorNew);
            }
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorListNew = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperiorToAttach : questaodocentesuperiorListNew) {
                questaodocentesuperiorListNewQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorListNew.add(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach);
            }
            questaodocentesuperiorListNew = attachedQuestaodocentesuperiorListNew;
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(questaodocentesuperiorListNew);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaListNew = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugadaToAttach : pontuacaoconjugadaListNew) {
                pontuacaoconjugadaListNewPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaListNew.add(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach);
            }
            pontuacaoconjugadaListNew = attachedPontuacaoconjugadaListNew;
            avaliacaodocentesuperior.setPontuacaoconjugadaList(pontuacaoconjugadaListNew);
            avaliacaodocentesuperior = em.merge(avaliacaodocentesuperior);
            if (iddisciplinaOld != null && !iddisciplinaOld.equals(iddisciplinaNew)) {
                iddisciplinaOld.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                iddisciplinaOld = em.merge(iddisciplinaOld);
            }
            if (iddisciplinaNew != null && !iddisciplinaNew.equals(iddisciplinaOld)) {
                iddisciplinaNew.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                iddisciplinaNew = em.merge(iddisciplinaNew);
            }
            if (idprofessorOld != null && !idprofessorOld.equals(idprofessorNew)) {
                idprofessorOld.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                idprofessorOld = em.merge(idprofessorOld);
            }
            if (idprofessorNew != null && !idprofessorNew.equals(idprofessorOld)) {
                idprofessorNew.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                idprofessorNew = em.merge(idprofessorNew);
            }
            for (Questaodocentesuperior questaodocentesuperiorListOldQuestaodocentesuperior : questaodocentesuperiorListOld) {
                if (!questaodocentesuperiorListNew.contains(questaodocentesuperiorListOldQuestaodocentesuperior)) {
                    questaodocentesuperiorListOldQuestaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                    questaodocentesuperiorListOldQuestaodocentesuperior = em.merge(questaodocentesuperiorListOldQuestaodocentesuperior);
                }
            }
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperior : questaodocentesuperiorListNew) {
                if (!questaodocentesuperiorListOld.contains(questaodocentesuperiorListNewQuestaodocentesuperior)) {
                    questaodocentesuperiorListNewQuestaodocentesuperior.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                    questaodocentesuperiorListNewQuestaodocentesuperior = em.merge(questaodocentesuperiorListNewQuestaodocentesuperior);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListOldPontuacaoconjugada : pontuacaoconjugadaListOld) {
                if (!pontuacaoconjugadaListNew.contains(pontuacaoconjugadaListOldPontuacaoconjugada)) {
                    pontuacaoconjugadaListOldPontuacaoconjugada.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                    pontuacaoconjugadaListOldPontuacaoconjugada = em.merge(pontuacaoconjugadaListOldPontuacaoconjugada);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugada : pontuacaoconjugadaListNew) {
                if (!pontuacaoconjugadaListOld.contains(pontuacaoconjugadaListNewPontuacaoconjugada)) {
                    pontuacaoconjugadaListNewPontuacaoconjugada.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                    pontuacaoconjugadaListNewPontuacaoconjugada = em.merge(pontuacaoconjugadaListNewPontuacaoconjugada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = avaliacaodocentesuperior.getIdavaliacaodocentesuperior();
                if (findAvaliacaodocentesuperior(id) == null) {
                    throw new NonexistentEntityException("The avaliacaodocentesuperior with id " + id + " no longer exists.");
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
            Avaliacaodocentesuperior avaliacaodocentesuperior;
            try {
                avaliacaodocentesuperior = em.getReference(Avaliacaodocentesuperior.class, id);
                avaliacaodocentesuperior.getIdavaliacaodocentesuperior();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacaodocentesuperior with id " + id + " no longer exists.", enfe);
            }
            Disciplina iddisciplina = avaliacaodocentesuperior.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                iddisciplina = em.merge(iddisciplina);
            }
            Professor idprofessor = avaliacaodocentesuperior.getIdprofessor();
            if (idprofessor != null) {
                idprofessor.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                idprofessor = em.merge(idprofessor);
            }
            List<Questaodocentesuperior> questaodocentesuperiorList = avaliacaodocentesuperior.getQuestaodocentesuperiorList();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : questaodocentesuperiorList) {
                questaodocentesuperiorListQuestaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            List<Pontuacaoconjugada> pontuacaoconjugadaList = avaliacaodocentesuperior.getPontuacaoconjugadaList();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : pontuacaoconjugadaList) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.remove(avaliacaodocentesuperior);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities() {
        return findAvaliacaodocentesuperiorEntities(true, -1, -1);
    }

    public List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities(int maxResults, int firstResult) {
        return findAvaliacaodocentesuperiorEntities(false, maxResults, firstResult);
    }

    private List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliacaodocentesuperior.class));
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

    public Avaliacaodocentesuperior findAvaliacaodocentesuperior(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacaodocentesuperior.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaodocentesuperiorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliacaodocentesuperior> rt = cq.from(Avaliacaodocentesuperior.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
