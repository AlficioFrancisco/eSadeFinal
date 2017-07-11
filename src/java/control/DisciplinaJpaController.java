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
import modelo.Curso;
import modelo.Avaliacaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Autoavaliacao;
import modelo.Avaliacaodocenteestudante;
import modelo.Disciplina;

/**
 *
 * @author ali_sualei
 */
public class DisciplinaJpaController implements Serializable {

    public DisciplinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disciplina disciplina) {
        if (disciplina.getAvaliacaodocentesuperiorList() == null) {
            disciplina.setAvaliacaodocentesuperiorList(new ArrayList<Avaliacaodocentesuperior>());
        }
        if (disciplina.getAutoavaliacaoList() == null) {
            disciplina.setAutoavaliacaoList(new ArrayList<Autoavaliacao>());
        }
        if (disciplina.getAvaliacaodocenteestudanteList() == null) {
            disciplina.setAvaliacaodocenteestudanteList(new ArrayList<Avaliacaodocenteestudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso idcurso = disciplina.getIdcurso();
            if (idcurso != null) {
                idcurso = em.getReference(idcurso.getClass(), idcurso.getIdcurso());
                disciplina.setIdcurso(idcurso);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorList = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach : disciplina.getAvaliacaodocentesuperiorList()) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorList.add(avaliacaodocentesuperiorListAvaliacaodocentesuperiorToAttach);
            }
            disciplina.setAvaliacaodocentesuperiorList(attachedAvaliacaodocentesuperiorList);
            List<Autoavaliacao> attachedAutoavaliacaoList = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListAutoavaliacaoToAttach : disciplina.getAutoavaliacaoList()) {
                autoavaliacaoListAutoavaliacaoToAttach = em.getReference(autoavaliacaoListAutoavaliacaoToAttach.getClass(), autoavaliacaoListAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoList.add(autoavaliacaoListAutoavaliacaoToAttach);
            }
            disciplina.setAutoavaliacaoList(attachedAutoavaliacaoList);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteList = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach : disciplina.getAvaliacaodocenteestudanteList()) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteList.add(avaliacaodocenteestudanteListAvaliacaodocenteestudanteToAttach);
            }
            disciplina.setAvaliacaodocenteestudanteList(attachedAvaliacaodocenteestudanteList);
            em.persist(disciplina);
            if (idcurso != null) {
                idcurso.getDisciplinaList().add(disciplina);
                idcurso = em.merge(idcurso);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : disciplina.getAvaliacaodocentesuperiorList()) {
                Disciplina oldIddisciplinaOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior = avaliacaodocentesuperiorListAvaliacaodocentesuperior.getIddisciplina();
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.setIddisciplina(disciplina);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
                if (oldIddisciplinaOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior != null) {
                    oldIddisciplinaOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
                    oldIddisciplinaOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(oldIddisciplinaOfAvaliacaodocentesuperiorListAvaliacaodocentesuperior);
                }
            }
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : disciplina.getAutoavaliacaoList()) {
                Disciplina oldIddisciplinaOfAutoavaliacaoListAutoavaliacao = autoavaliacaoListAutoavaliacao.getIddisciplina();
                autoavaliacaoListAutoavaliacao.setIddisciplina(disciplina);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
                if (oldIddisciplinaOfAutoavaliacaoListAutoavaliacao != null) {
                    oldIddisciplinaOfAutoavaliacaoListAutoavaliacao.getAutoavaliacaoList().remove(autoavaliacaoListAutoavaliacao);
                    oldIddisciplinaOfAutoavaliacaoListAutoavaliacao = em.merge(oldIddisciplinaOfAutoavaliacaoListAutoavaliacao);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : disciplina.getAvaliacaodocenteestudanteList()) {
                Disciplina oldIddisciplinaOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = avaliacaodocenteestudanteListAvaliacaodocenteestudante.getIddisciplina();
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIddisciplina(disciplina);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                if (oldIddisciplinaOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante != null) {
                    oldIddisciplinaOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
                    oldIddisciplinaOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(oldIddisciplinaOfAvaliacaodocenteestudanteListAvaliacaodocenteestudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplina disciplina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina persistentDisciplina = em.find(Disciplina.class, disciplina.getIddisciplina());
            Curso idcursoOld = persistentDisciplina.getIdcurso();
            Curso idcursoNew = disciplina.getIdcurso();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListOld = persistentDisciplina.getAvaliacaodocentesuperiorList();
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorListNew = disciplina.getAvaliacaodocentesuperiorList();
            List<Autoavaliacao> autoavaliacaoListOld = persistentDisciplina.getAutoavaliacaoList();
            List<Autoavaliacao> autoavaliacaoListNew = disciplina.getAutoavaliacaoList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListOld = persistentDisciplina.getAvaliacaodocenteestudanteList();
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteListNew = disciplina.getAvaliacaodocenteestudanteList();
            if (idcursoNew != null) {
                idcursoNew = em.getReference(idcursoNew.getClass(), idcursoNew.getIdcurso());
                disciplina.setIdcurso(idcursoNew);
            }
            List<Avaliacaodocentesuperior> attachedAvaliacaodocentesuperiorListNew = new ArrayList<Avaliacaodocentesuperior>();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach : avaliacaodocentesuperiorListNew) {
                avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach = em.getReference(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getClass(), avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach.getIdavaliacaodocentesuperior());
                attachedAvaliacaodocentesuperiorListNew.add(avaliacaodocentesuperiorListNewAvaliacaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperiorListNew = attachedAvaliacaodocentesuperiorListNew;
            disciplina.setAvaliacaodocentesuperiorList(avaliacaodocentesuperiorListNew);
            List<Autoavaliacao> attachedAutoavaliacaoListNew = new ArrayList<Autoavaliacao>();
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacaoToAttach : autoavaliacaoListNew) {
                autoavaliacaoListNewAutoavaliacaoToAttach = em.getReference(autoavaliacaoListNewAutoavaliacaoToAttach.getClass(), autoavaliacaoListNewAutoavaliacaoToAttach.getIdautoavaliacao());
                attachedAutoavaliacaoListNew.add(autoavaliacaoListNewAutoavaliacaoToAttach);
            }
            autoavaliacaoListNew = attachedAutoavaliacaoListNew;
            disciplina.setAutoavaliacaoList(autoavaliacaoListNew);
            List<Avaliacaodocenteestudante> attachedAvaliacaodocenteestudanteListNew = new ArrayList<Avaliacaodocenteestudante>();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach : avaliacaodocenteestudanteListNew) {
                avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach = em.getReference(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getClass(), avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach.getIdavaliacaodocenteestudante());
                attachedAvaliacaodocenteestudanteListNew.add(avaliacaodocenteestudanteListNewAvaliacaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudanteListNew = attachedAvaliacaodocenteestudanteListNew;
            disciplina.setAvaliacaodocenteestudanteList(avaliacaodocenteestudanteListNew);
            disciplina = em.merge(disciplina);
            if (idcursoOld != null && !idcursoOld.equals(idcursoNew)) {
                idcursoOld.getDisciplinaList().remove(disciplina);
                idcursoOld = em.merge(idcursoOld);
            }
            if (idcursoNew != null && !idcursoNew.equals(idcursoOld)) {
                idcursoNew.getDisciplinaList().add(disciplina);
                idcursoNew = em.merge(idcursoNew);
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListOldAvaliacaodocentesuperior : avaliacaodocentesuperiorListOld) {
                if (!avaliacaodocentesuperiorListNew.contains(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior)) {
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior.setIddisciplina(null);
                    avaliacaodocentesuperiorListOldAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListOldAvaliacaodocentesuperior);
                }
            }
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListNewAvaliacaodocentesuperior : avaliacaodocentesuperiorListNew) {
                if (!avaliacaodocentesuperiorListOld.contains(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior)) {
                    Disciplina oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior = avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getIddisciplina();
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior.setIddisciplina(disciplina);
                    avaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                    if (oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior != null && !oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior.equals(disciplina)) {
                        oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                        oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior = em.merge(oldIddisciplinaOfAvaliacaodocentesuperiorListNewAvaliacaodocentesuperior);
                    }
                }
            }
            for (Autoavaliacao autoavaliacaoListOldAutoavaliacao : autoavaliacaoListOld) {
                if (!autoavaliacaoListNew.contains(autoavaliacaoListOldAutoavaliacao)) {
                    autoavaliacaoListOldAutoavaliacao.setIddisciplina(null);
                    autoavaliacaoListOldAutoavaliacao = em.merge(autoavaliacaoListOldAutoavaliacao);
                }
            }
            for (Autoavaliacao autoavaliacaoListNewAutoavaliacao : autoavaliacaoListNew) {
                if (!autoavaliacaoListOld.contains(autoavaliacaoListNewAutoavaliacao)) {
                    Disciplina oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao = autoavaliacaoListNewAutoavaliacao.getIddisciplina();
                    autoavaliacaoListNewAutoavaliacao.setIddisciplina(disciplina);
                    autoavaliacaoListNewAutoavaliacao = em.merge(autoavaliacaoListNewAutoavaliacao);
                    if (oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao != null && !oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao.equals(disciplina)) {
                        oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao.getAutoavaliacaoList().remove(autoavaliacaoListNewAutoavaliacao);
                        oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao = em.merge(oldIddisciplinaOfAutoavaliacaoListNewAutoavaliacao);
                    }
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListOldAvaliacaodocenteestudante : avaliacaodocenteestudanteListOld) {
                if (!avaliacaodocenteestudanteListNew.contains(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante)) {
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante.setIddisciplina(null);
                    avaliacaodocenteestudanteListOldAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListOldAvaliacaodocenteestudante);
                }
            }
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListNewAvaliacaodocenteestudante : avaliacaodocenteestudanteListNew) {
                if (!avaliacaodocenteestudanteListOld.contains(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante)) {
                    Disciplina oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getIddisciplina();
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante.setIddisciplina(disciplina);
                    avaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    if (oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante != null && !oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.equals(disciplina)) {
                        oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                        oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante = em.merge(oldIddisciplinaOfAvaliacaodocenteestudanteListNewAvaliacaodocenteestudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = disciplina.getIddisciplina();
                if (findDisciplina(id) == null) {
                    throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.");
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
            Disciplina disciplina;
            try {
                disciplina = em.getReference(Disciplina.class, id);
                disciplina.getIddisciplina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.", enfe);
            }
            Curso idcurso = disciplina.getIdcurso();
            if (idcurso != null) {
                idcurso.getDisciplinaList().remove(disciplina);
                idcurso = em.merge(idcurso);
            }
            List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList = disciplina.getAvaliacaodocentesuperiorList();
            for (Avaliacaodocentesuperior avaliacaodocentesuperiorListAvaliacaodocentesuperior : avaliacaodocentesuperiorList) {
                avaliacaodocentesuperiorListAvaliacaodocentesuperior.setIddisciplina(null);
                avaliacaodocentesuperiorListAvaliacaodocentesuperior = em.merge(avaliacaodocentesuperiorListAvaliacaodocentesuperior);
            }
            List<Autoavaliacao> autoavaliacaoList = disciplina.getAutoavaliacaoList();
            for (Autoavaliacao autoavaliacaoListAutoavaliacao : autoavaliacaoList) {
                autoavaliacaoListAutoavaliacao.setIddisciplina(null);
                autoavaliacaoListAutoavaliacao = em.merge(autoavaliacaoListAutoavaliacao);
            }
            List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList = disciplina.getAvaliacaodocenteestudanteList();
            for (Avaliacaodocenteestudante avaliacaodocenteestudanteListAvaliacaodocenteestudante : avaliacaodocenteestudanteList) {
                avaliacaodocenteestudanteListAvaliacaodocenteestudante.setIddisciplina(null);
                avaliacaodocenteestudanteListAvaliacaodocenteestudante = em.merge(avaliacaodocenteestudanteListAvaliacaodocenteestudante);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplina> findDisciplinaEntities() {
        return findDisciplinaEntities(true, -1, -1);
    }

    public List<Disciplina> findDisciplinaEntities(int maxResults, int firstResult) {
        return findDisciplinaEntities(false, maxResults, firstResult);
    }

    private List<Disciplina> findDisciplinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplina.class));
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

    public Disciplina findDisciplina(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplina.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplina> rt = cq.from(Disciplina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
