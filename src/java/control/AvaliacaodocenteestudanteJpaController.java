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
import modelo.Questaodocenteestudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avaliacaodocenteestudante;
import modelo.Pontuacaoconjugada;

/**
 *
 * @author ali_sualei
 */
public class AvaliacaodocenteestudanteJpaController implements Serializable {

    public AvaliacaodocenteestudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacaodocenteestudante avaliacaodocenteestudante) {
        if (avaliacaodocenteestudante.getQuestaodocenteestudanteList() == null) {
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(new ArrayList<Questaodocenteestudante>());
        }
        if (avaliacaodocenteestudante.getPontuacaoconjugadaList() == null) {
            avaliacaodocenteestudante.setPontuacaoconjugadaList(new ArrayList<Pontuacaoconjugada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina iddisciplina = avaliacaodocenteestudante.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina = em.getReference(iddisciplina.getClass(), iddisciplina.getIddisciplina());
                avaliacaodocenteestudante.setIddisciplina(iddisciplina);
            }
            Professor idprofessor = avaliacaodocenteestudante.getIdprofessor();
            if (idprofessor != null) {
                idprofessor = em.getReference(idprofessor.getClass(), idprofessor.getIdprofessor());
                avaliacaodocenteestudante.setIdprofessor(idprofessor);
            }
            List<Questaodocenteestudante> attachedQuestaodocenteestudanteList = new ArrayList<Questaodocenteestudante>();
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudanteToAttach : avaliacaodocenteestudante.getQuestaodocenteestudanteList()) {
                questaodocenteestudanteListQuestaodocenteestudanteToAttach = em.getReference(questaodocenteestudanteListQuestaodocenteestudanteToAttach.getClass(), questaodocenteestudanteListQuestaodocenteestudanteToAttach.getIdquestaodocenteestudante());
                attachedQuestaodocenteestudanteList.add(questaodocenteestudanteListQuestaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(attachedQuestaodocenteestudanteList);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaList = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugadaToAttach : avaliacaodocenteestudante.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaList.add(pontuacaoconjugadaListPontuacaoconjugadaToAttach);
            }
            avaliacaodocenteestudante.setPontuacaoconjugadaList(attachedPontuacaoconjugadaList);
            em.persist(avaliacaodocenteestudante);
            if (iddisciplina != null) {
                iddisciplina.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                iddisciplina = em.merge(iddisciplina);
            }
            if (idprofessor != null) {
                idprofessor.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                idprofessor = em.merge(idprofessor);
            }
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudante : avaliacaodocenteestudante.getQuestaodocenteestudanteList()) {
                questaodocenteestudanteListQuestaodocenteestudante.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                questaodocenteestudanteListQuestaodocenteestudante = em.merge(questaodocenteestudanteListQuestaodocenteestudante);
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : avaliacaodocenteestudante.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacaodocenteestudante avaliacaodocenteestudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocenteestudante persistentAvaliacaodocenteestudante = em.find(Avaliacaodocenteestudante.class, avaliacaodocenteestudante.getIdavaliacaodocenteestudante());
            Disciplina iddisciplinaOld = persistentAvaliacaodocenteestudante.getIddisciplina();
            Disciplina iddisciplinaNew = avaliacaodocenteestudante.getIddisciplina();
            Professor idprofessorOld = persistentAvaliacaodocenteestudante.getIdprofessor();
            Professor idprofessorNew = avaliacaodocenteestudante.getIdprofessor();
            List<Questaodocenteestudante> questaodocenteestudanteListOld = persistentAvaliacaodocenteestudante.getQuestaodocenteestudanteList();
            List<Questaodocenteestudante> questaodocenteestudanteListNew = avaliacaodocenteestudante.getQuestaodocenteestudanteList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListOld = persistentAvaliacaodocenteestudante.getPontuacaoconjugadaList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListNew = avaliacaodocenteestudante.getPontuacaoconjugadaList();
            if (iddisciplinaNew != null) {
                iddisciplinaNew = em.getReference(iddisciplinaNew.getClass(), iddisciplinaNew.getIddisciplina());
                avaliacaodocenteestudante.setIddisciplina(iddisciplinaNew);
            }
            if (idprofessorNew != null) {
                idprofessorNew = em.getReference(idprofessorNew.getClass(), idprofessorNew.getIdprofessor());
                avaliacaodocenteestudante.setIdprofessor(idprofessorNew);
            }
            List<Questaodocenteestudante> attachedQuestaodocenteestudanteListNew = new ArrayList<Questaodocenteestudante>();
            for (Questaodocenteestudante questaodocenteestudanteListNewQuestaodocenteestudanteToAttach : questaodocenteestudanteListNew) {
                questaodocenteestudanteListNewQuestaodocenteestudanteToAttach = em.getReference(questaodocenteestudanteListNewQuestaodocenteestudanteToAttach.getClass(), questaodocenteestudanteListNewQuestaodocenteestudanteToAttach.getIdquestaodocenteestudante());
                attachedQuestaodocenteestudanteListNew.add(questaodocenteestudanteListNewQuestaodocenteestudanteToAttach);
            }
            questaodocenteestudanteListNew = attachedQuestaodocenteestudanteListNew;
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(questaodocenteestudanteListNew);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaListNew = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugadaToAttach : pontuacaoconjugadaListNew) {
                pontuacaoconjugadaListNewPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaListNew.add(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach);
            }
            pontuacaoconjugadaListNew = attachedPontuacaoconjugadaListNew;
            avaliacaodocenteestudante.setPontuacaoconjugadaList(pontuacaoconjugadaListNew);
            avaliacaodocenteestudante = em.merge(avaliacaodocenteestudante);
            if (iddisciplinaOld != null && !iddisciplinaOld.equals(iddisciplinaNew)) {
                iddisciplinaOld.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                iddisciplinaOld = em.merge(iddisciplinaOld);
            }
            if (iddisciplinaNew != null && !iddisciplinaNew.equals(iddisciplinaOld)) {
                iddisciplinaNew.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                iddisciplinaNew = em.merge(iddisciplinaNew);
            }
            if (idprofessorOld != null && !idprofessorOld.equals(idprofessorNew)) {
                idprofessorOld.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                idprofessorOld = em.merge(idprofessorOld);
            }
            if (idprofessorNew != null && !idprofessorNew.equals(idprofessorOld)) {
                idprofessorNew.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                idprofessorNew = em.merge(idprofessorNew);
            }
            for (Questaodocenteestudante questaodocenteestudanteListOldQuestaodocenteestudante : questaodocenteestudanteListOld) {
                if (!questaodocenteestudanteListNew.contains(questaodocenteestudanteListOldQuestaodocenteestudante)) {
                    questaodocenteestudanteListOldQuestaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                    questaodocenteestudanteListOldQuestaodocenteestudante = em.merge(questaodocenteestudanteListOldQuestaodocenteestudante);
                }
            }
            for (Questaodocenteestudante questaodocenteestudanteListNewQuestaodocenteestudante : questaodocenteestudanteListNew) {
                if (!questaodocenteestudanteListOld.contains(questaodocenteestudanteListNewQuestaodocenteestudante)) {
                    questaodocenteestudanteListNewQuestaodocenteestudante.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                    questaodocenteestudanteListNewQuestaodocenteestudante = em.merge(questaodocenteestudanteListNewQuestaodocenteestudante);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListOldPontuacaoconjugada : pontuacaoconjugadaListOld) {
                if (!pontuacaoconjugadaListNew.contains(pontuacaoconjugadaListOldPontuacaoconjugada)) {
                    pontuacaoconjugadaListOldPontuacaoconjugada.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                    pontuacaoconjugadaListOldPontuacaoconjugada = em.merge(pontuacaoconjugadaListOldPontuacaoconjugada);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugada : pontuacaoconjugadaListNew) {
                if (!pontuacaoconjugadaListOld.contains(pontuacaoconjugadaListNewPontuacaoconjugada)) {
                    pontuacaoconjugadaListNewPontuacaoconjugada.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                    pontuacaoconjugadaListNewPontuacaoconjugada = em.merge(pontuacaoconjugadaListNewPontuacaoconjugada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = avaliacaodocenteestudante.getIdavaliacaodocenteestudante();
                if (findAvaliacaodocenteestudante(id) == null) {
                    throw new NonexistentEntityException("The avaliacaodocenteestudante with id " + id + " no longer exists.");
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
            Avaliacaodocenteestudante avaliacaodocenteestudante;
            try {
                avaliacaodocenteestudante = em.getReference(Avaliacaodocenteestudante.class, id);
                avaliacaodocenteestudante.getIdavaliacaodocenteestudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacaodocenteestudante with id " + id + " no longer exists.", enfe);
            }
            Disciplina iddisciplina = avaliacaodocenteestudante.getIddisciplina();
            if (iddisciplina != null) {
                iddisciplina.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                iddisciplina = em.merge(iddisciplina);
            }
            Professor idprofessor = avaliacaodocenteestudante.getIdprofessor();
            if (idprofessor != null) {
                idprofessor.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                idprofessor = em.merge(idprofessor);
            }
            List<Questaodocenteestudante> questaodocenteestudanteList = avaliacaodocenteestudante.getQuestaodocenteestudanteList();
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudante : questaodocenteestudanteList) {
                questaodocenteestudanteListQuestaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                questaodocenteestudanteListQuestaodocenteestudante = em.merge(questaodocenteestudanteListQuestaodocenteestudante);
            }
            List<Pontuacaoconjugada> pontuacaoconjugadaList = avaliacaodocenteestudante.getPontuacaoconjugadaList();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : pontuacaoconjugadaList) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.remove(avaliacaodocenteestudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities() {
        return findAvaliacaodocenteestudanteEntities(true, -1, -1);
    }

    public List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities(int maxResults, int firstResult) {
        return findAvaliacaodocenteestudanteEntities(false, maxResults, firstResult);
    }

    private List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliacaodocenteestudante.class));
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

    public Avaliacaodocenteestudante findAvaliacaodocenteestudante(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacaodocenteestudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaodocenteestudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliacaodocenteestudante> rt = cq.from(Avaliacaodocenteestudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
