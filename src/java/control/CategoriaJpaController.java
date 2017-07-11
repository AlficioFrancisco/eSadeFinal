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
import modelo.Categoria;

/**
 *
 * @author ali_sualei
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getProfessorList() == null) {
            categoria.setProfessorList(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Professor> attachedProfessorList = new ArrayList<Professor>();
            for (Professor professorListProfessorToAttach : categoria.getProfessorList()) {
                professorListProfessorToAttach = em.getReference(professorListProfessorToAttach.getClass(), professorListProfessorToAttach.getIdprofessor());
                attachedProfessorList.add(professorListProfessorToAttach);
            }
            categoria.setProfessorList(attachedProfessorList);
            em.persist(categoria);
            for (Professor professorListProfessor : categoria.getProfessorList()) {
                Categoria oldIdcategoriaOfProfessorListProfessor = professorListProfessor.getIdcategoria();
                professorListProfessor.setIdcategoria(categoria);
                professorListProfessor = em.merge(professorListProfessor);
                if (oldIdcategoriaOfProfessorListProfessor != null) {
                    oldIdcategoriaOfProfessorListProfessor.getProfessorList().remove(professorListProfessor);
                    oldIdcategoriaOfProfessorListProfessor = em.merge(oldIdcategoriaOfProfessorListProfessor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            List<Professor> professorListOld = persistentCategoria.getProfessorList();
            List<Professor> professorListNew = categoria.getProfessorList();
            List<Professor> attachedProfessorListNew = new ArrayList<Professor>();
            for (Professor professorListNewProfessorToAttach : professorListNew) {
                professorListNewProfessorToAttach = em.getReference(professorListNewProfessorToAttach.getClass(), professorListNewProfessorToAttach.getIdprofessor());
                attachedProfessorListNew.add(professorListNewProfessorToAttach);
            }
            professorListNew = attachedProfessorListNew;
            categoria.setProfessorList(professorListNew);
            categoria = em.merge(categoria);
            for (Professor professorListOldProfessor : professorListOld) {
                if (!professorListNew.contains(professorListOldProfessor)) {
                    professorListOldProfessor.setIdcategoria(null);
                    professorListOldProfessor = em.merge(professorListOldProfessor);
                }
            }
            for (Professor professorListNewProfessor : professorListNew) {
                if (!professorListOld.contains(professorListNewProfessor)) {
                    Categoria oldIdcategoriaOfProfessorListNewProfessor = professorListNewProfessor.getIdcategoria();
                    professorListNewProfessor.setIdcategoria(categoria);
                    professorListNewProfessor = em.merge(professorListNewProfessor);
                    if (oldIdcategoriaOfProfessorListNewProfessor != null && !oldIdcategoriaOfProfessorListNewProfessor.equals(categoria)) {
                        oldIdcategoriaOfProfessorListNewProfessor.getProfessorList().remove(professorListNewProfessor);
                        oldIdcategoriaOfProfessorListNewProfessor = em.merge(oldIdcategoriaOfProfessorListNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<Professor> professorList = categoria.getProfessorList();
            for (Professor professorListProfessor : professorList) {
                professorListProfessor.setIdcategoria(null);
                professorListProfessor = em.merge(professorListProfessor);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
