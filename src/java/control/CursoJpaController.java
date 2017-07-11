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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Curso;
import modelo.Estudante;

/**
 *
 * @author ali_sualei
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getDisciplinaList() == null) {
            curso.setDisciplinaList(new ArrayList<Disciplina>());
        }
        if (curso.getEstudanteList() == null) {
            curso.setEstudanteList(new ArrayList<Estudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : curso.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIddisciplina());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            curso.setDisciplinaList(attachedDisciplinaList);
            List<Estudante> attachedEstudanteList = new ArrayList<Estudante>();
            for (Estudante estudanteListEstudanteToAttach : curso.getEstudanteList()) {
                estudanteListEstudanteToAttach = em.getReference(estudanteListEstudanteToAttach.getClass(), estudanteListEstudanteToAttach.getIdestudante());
                attachedEstudanteList.add(estudanteListEstudanteToAttach);
            }
            curso.setEstudanteList(attachedEstudanteList);
            em.persist(curso);
            for (Disciplina disciplinaListDisciplina : curso.getDisciplinaList()) {
                Curso oldIdcursoOfDisciplinaListDisciplina = disciplinaListDisciplina.getIdcurso();
                disciplinaListDisciplina.setIdcurso(curso);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldIdcursoOfDisciplinaListDisciplina != null) {
                    oldIdcursoOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldIdcursoOfDisciplinaListDisciplina = em.merge(oldIdcursoOfDisciplinaListDisciplina);
                }
            }
            for (Estudante estudanteListEstudante : curso.getEstudanteList()) {
                Curso oldIdcursoOfEstudanteListEstudante = estudanteListEstudante.getIdcurso();
                estudanteListEstudante.setIdcurso(curso);
                estudanteListEstudante = em.merge(estudanteListEstudante);
                if (oldIdcursoOfEstudanteListEstudante != null) {
                    oldIdcursoOfEstudanteListEstudante.getEstudanteList().remove(estudanteListEstudante);
                    oldIdcursoOfEstudanteListEstudante = em.merge(oldIdcursoOfEstudanteListEstudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getIdcurso());
            List<Disciplina> disciplinaListOld = persistentCurso.getDisciplinaList();
            List<Disciplina> disciplinaListNew = curso.getDisciplinaList();
            List<Estudante> estudanteListOld = persistentCurso.getEstudanteList();
            List<Estudante> estudanteListNew = curso.getEstudanteList();
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIddisciplina());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            curso.setDisciplinaList(disciplinaListNew);
            List<Estudante> attachedEstudanteListNew = new ArrayList<Estudante>();
            for (Estudante estudanteListNewEstudanteToAttach : estudanteListNew) {
                estudanteListNewEstudanteToAttach = em.getReference(estudanteListNewEstudanteToAttach.getClass(), estudanteListNewEstudanteToAttach.getIdestudante());
                attachedEstudanteListNew.add(estudanteListNewEstudanteToAttach);
            }
            estudanteListNew = attachedEstudanteListNew;
            curso.setEstudanteList(estudanteListNew);
            curso = em.merge(curso);
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setIdcurso(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Curso oldIdcursoOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getIdcurso();
                    disciplinaListNewDisciplina.setIdcurso(curso);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldIdcursoOfDisciplinaListNewDisciplina != null && !oldIdcursoOfDisciplinaListNewDisciplina.equals(curso)) {
                        oldIdcursoOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldIdcursoOfDisciplinaListNewDisciplina = em.merge(oldIdcursoOfDisciplinaListNewDisciplina);
                    }
                }
            }
            for (Estudante estudanteListOldEstudante : estudanteListOld) {
                if (!estudanteListNew.contains(estudanteListOldEstudante)) {
                    estudanteListOldEstudante.setIdcurso(null);
                    estudanteListOldEstudante = em.merge(estudanteListOldEstudante);
                }
            }
            for (Estudante estudanteListNewEstudante : estudanteListNew) {
                if (!estudanteListOld.contains(estudanteListNewEstudante)) {
                    Curso oldIdcursoOfEstudanteListNewEstudante = estudanteListNewEstudante.getIdcurso();
                    estudanteListNewEstudante.setIdcurso(curso);
                    estudanteListNewEstudante = em.merge(estudanteListNewEstudante);
                    if (oldIdcursoOfEstudanteListNewEstudante != null && !oldIdcursoOfEstudanteListNewEstudante.equals(curso)) {
                        oldIdcursoOfEstudanteListNewEstudante.getEstudanteList().remove(estudanteListNewEstudante);
                        oldIdcursoOfEstudanteListNewEstudante = em.merge(oldIdcursoOfEstudanteListNewEstudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = curso.getIdcurso();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdcurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<Disciplina> disciplinaList = curso.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setIdcurso(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            List<Estudante> estudanteList = curso.getEstudanteList();
            for (Estudante estudanteListEstudante : estudanteList) {
                estudanteListEstudante.setIdcurso(null);
                estudanteListEstudante = em.merge(estudanteListEstudante);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
