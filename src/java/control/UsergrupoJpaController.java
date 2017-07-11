/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Grupo;
import modelo.Usergrupo;
import modelo.UsergrupoPK;
import modelo.Users;

/**
 *
 * @author ali_sualei
 */
public class UsergrupoJpaController implements Serializable {

    public UsergrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usergrupo usergrupo) throws PreexistingEntityException, Exception {
        if (usergrupo.getUsergrupoPK() == null) {
            usergrupo.setUsergrupoPK(new UsergrupoPK());
        }
        usergrupo.getUsergrupoPK().setIdGrupo(usergrupo.getGrupo().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = usergrupo.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getIdGrupo());
                usergrupo.setGrupo(grupo);
            }
            Users users = usergrupo.getUsers();
            if (users != null) {
                users = em.getReference(users.getClass(), users.getUtilizador());
                usergrupo.setUsers(users);
            }
            em.persist(usergrupo);
            if (grupo != null) {
                grupo.getUsergrupoList().add(usergrupo);
                grupo = em.merge(grupo);
            }
            if (users != null) {
                users.getUsergrupoList().add(usergrupo);
                users = em.merge(users);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsergrupo(usergrupo.getUsergrupoPK()) != null) {
                throw new PreexistingEntityException("Usergrupo " + usergrupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usergrupo usergrupo) throws NonexistentEntityException, Exception {
        usergrupo.getUsergrupoPK().setIdGrupo(usergrupo.getGrupo().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usergrupo persistentUsergrupo = em.find(Usergrupo.class, usergrupo.getUsergrupoPK());
            Grupo grupoOld = persistentUsergrupo.getGrupo();
            Grupo grupoNew = usergrupo.getGrupo();
            Users usersOld = persistentUsergrupo.getUsers();
            Users usersNew = usergrupo.getUsers();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getIdGrupo());
                usergrupo.setGrupo(grupoNew);
            }
            if (usersNew != null) {
                usersNew = em.getReference(usersNew.getClass(), usersNew.getUtilizador());
                usergrupo.setUsers(usersNew);
            }
            usergrupo = em.merge(usergrupo);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getUsergrupoList().remove(usergrupo);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getUsergrupoList().add(usergrupo);
                grupoNew = em.merge(grupoNew);
            }
            if (usersOld != null && !usersOld.equals(usersNew)) {
                usersOld.getUsergrupoList().remove(usergrupo);
                usersOld = em.merge(usersOld);
            }
            if (usersNew != null && !usersNew.equals(usersOld)) {
                usersNew.getUsergrupoList().add(usergrupo);
                usersNew = em.merge(usersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsergrupoPK id = usergrupo.getUsergrupoPK();
                if (findUsergrupo(id) == null) {
                    throw new NonexistentEntityException("The usergrupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsergrupoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usergrupo usergrupo;
            try {
                usergrupo = em.getReference(Usergrupo.class, id);
                usergrupo.getUsergrupoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usergrupo with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = usergrupo.getGrupo();
            if (grupo != null) {
                grupo.getUsergrupoList().remove(usergrupo);
                grupo = em.merge(grupo);
            }
            Users users = usergrupo.getUsers();
            if (users != null) {
                users.getUsergrupoList().remove(usergrupo);
                users = em.merge(users);
            }
            em.remove(usergrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usergrupo> findUsergrupoEntities() {
        return findUsergrupoEntities(true, -1, -1);
    }

    public List<Usergrupo> findUsergrupoEntities(int maxResults, int firstResult) {
        return findUsergrupoEntities(false, maxResults, firstResult);
    }

    private List<Usergrupo> findUsergrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usergrupo.class));
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

    public Usergrupo findUsergrupo(UsergrupoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usergrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsergrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usergrupo> rt = cq.from(Usergrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
