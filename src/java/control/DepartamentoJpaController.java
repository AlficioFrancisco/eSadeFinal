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
import modelo.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Departamento;

/**
 *
 * @author ali_sualei
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getProfessorList() == null) {
            departamento.setProfessorList(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Professor> attachedProfessorList = new ArrayList<Professor>();
            for (Professor professorListProfessorToAttach : departamento.getProfessorList()) {
                professorListProfessorToAttach = em.getReference(professorListProfessorToAttach.getClass(), professorListProfessorToAttach.getIdprofessor());
                attachedProfessorList.add(professorListProfessorToAttach);
            }
            departamento.setProfessorList(attachedProfessorList);
            em.persist(departamento);
            for (Professor professorListProfessor : departamento.getProfessorList()) {
                Departamento oldIddepartamentoOfProfessorListProfessor = professorListProfessor.getIddepartamento();
                professorListProfessor.setIddepartamento(departamento);
                professorListProfessor = em.merge(professorListProfessor);
                if (oldIddepartamentoOfProfessorListProfessor != null) {
                    oldIddepartamentoOfProfessorListProfessor.getProfessorList().remove(professorListProfessor);
                    oldIddepartamentoOfProfessorListProfessor = em.merge(oldIddepartamentoOfProfessorListProfessor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIddepartamento());
            List<Professor> professorListOld = persistentDepartamento.getProfessorList();
            List<Professor> professorListNew = departamento.getProfessorList();
            List<Professor> attachedProfessorListNew = new ArrayList<Professor>();
            for (Professor professorListNewProfessorToAttach : professorListNew) {
                professorListNewProfessorToAttach = em.getReference(professorListNewProfessorToAttach.getClass(), professorListNewProfessorToAttach.getIdprofessor());
                attachedProfessorListNew.add(professorListNewProfessorToAttach);
            }
            professorListNew = attachedProfessorListNew;
            departamento.setProfessorList(professorListNew);
            departamento = em.merge(departamento);
            for (Professor professorListOldProfessor : professorListOld) {
                if (!professorListNew.contains(professorListOldProfessor)) {
                    professorListOldProfessor.setIddepartamento(null);
                    professorListOldProfessor = em.merge(professorListOldProfessor);
                }
            }
            for (Professor professorListNewProfessor : professorListNew) {
                if (!professorListOld.contains(professorListNewProfessor)) {
                    Departamento oldIddepartamentoOfProfessorListNewProfessor = professorListNewProfessor.getIddepartamento();
                    professorListNewProfessor.setIddepartamento(departamento);
                    professorListNewProfessor = em.merge(professorListNewProfessor);
                    if (oldIddepartamentoOfProfessorListNewProfessor != null && !oldIddepartamentoOfProfessorListNewProfessor.equals(departamento)) {
                        oldIddepartamentoOfProfessorListNewProfessor.getProfessorList().remove(professorListNewProfessor);
                        oldIddepartamentoOfProfessorListNewProfessor = em.merge(oldIddepartamentoOfProfessorListNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = departamento.getIddepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIddepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<Professor> professorList = departamento.getProfessorList();
            for (Professor professorListProfessor : professorList) {
                professorListProfessor.setIddepartamento(null);
                professorListProfessor = em.merge(professorListProfessor);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
