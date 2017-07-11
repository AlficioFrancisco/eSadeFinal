/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Roles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Grupo;
import modelo.Users;
import modelo.Usergrupo;

/**
 *
 * @author ali_sualei
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception {
        if (grupo.getRolesList() == null) {
            grupo.setRolesList(new ArrayList<Roles>());
        }
        if (grupo.getUsersList() == null) {
            grupo.setUsersList(new ArrayList<Users>());
        }
        if (grupo.getUsergrupoList() == null) {
            grupo.setUsergrupoList(new ArrayList<Usergrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Roles> attachedRolesList = new ArrayList<Roles>();
            for (Roles rolesListRolesToAttach : grupo.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getRolesPK());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            grupo.setRolesList(attachedRolesList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : grupo.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            grupo.setUsersList(attachedUsersList);
            List<Usergrupo> attachedUsergrupoList = new ArrayList<Usergrupo>();
            for (Usergrupo usergrupoListUsergrupoToAttach : grupo.getUsergrupoList()) {
                usergrupoListUsergrupoToAttach = em.getReference(usergrupoListUsergrupoToAttach.getClass(), usergrupoListUsergrupoToAttach.getUsergrupoPK());
                attachedUsergrupoList.add(usergrupoListUsergrupoToAttach);
            }
            grupo.setUsergrupoList(attachedUsergrupoList);
            em.persist(grupo);
            for (Roles rolesListRoles : grupo.getRolesList()) {
                Grupo oldGrupoOfRolesListRoles = rolesListRoles.getGrupo();
                rolesListRoles.setGrupo(grupo);
                rolesListRoles = em.merge(rolesListRoles);
                if (oldGrupoOfRolesListRoles != null) {
                    oldGrupoOfRolesListRoles.getRolesList().remove(rolesListRoles);
                    oldGrupoOfRolesListRoles = em.merge(oldGrupoOfRolesListRoles);
                }
            }
            for (Users usersListUsers : grupo.getUsersList()) {
                Grupo oldIdGrupoOfUsersListUsers = usersListUsers.getIdGrupo();
                usersListUsers.setIdGrupo(grupo);
                usersListUsers = em.merge(usersListUsers);
                if (oldIdGrupoOfUsersListUsers != null) {
                    oldIdGrupoOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIdGrupoOfUsersListUsers = em.merge(oldIdGrupoOfUsersListUsers);
                }
            }
            for (Usergrupo usergrupoListUsergrupo : grupo.getUsergrupoList()) {
                Grupo oldGrupoOfUsergrupoListUsergrupo = usergrupoListUsergrupo.getGrupo();
                usergrupoListUsergrupo.setGrupo(grupo);
                usergrupoListUsergrupo = em.merge(usergrupoListUsergrupo);
                if (oldGrupoOfUsergrupoListUsergrupo != null) {
                    oldGrupoOfUsergrupoListUsergrupo.getUsergrupoList().remove(usergrupoListUsergrupo);
                    oldGrupoOfUsergrupoListUsergrupo = em.merge(oldGrupoOfUsergrupoListUsergrupo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupo(grupo.getIdGrupo()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getIdGrupo());
            List<Roles> rolesListOld = persistentGrupo.getRolesList();
            List<Roles> rolesListNew = grupo.getRolesList();
            List<Users> usersListOld = persistentGrupo.getUsersList();
            List<Users> usersListNew = grupo.getUsersList();
            List<Usergrupo> usergrupoListOld = persistentGrupo.getUsergrupoList();
            List<Usergrupo> usergrupoListNew = grupo.getUsergrupoList();
            List<String> illegalOrphanMessages = null;
            for (Roles rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Roles " + rolesListOldRoles + " since its grupo field is not nullable.");
                }
            }
            for (Usergrupo usergrupoListOldUsergrupo : usergrupoListOld) {
                if (!usergrupoListNew.contains(usergrupoListOldUsergrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usergrupo " + usergrupoListOldUsergrupo + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Roles> attachedRolesListNew = new ArrayList<Roles>();
            for (Roles rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getRolesPK());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            grupo.setRolesList(rolesListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            grupo.setUsersList(usersListNew);
            List<Usergrupo> attachedUsergrupoListNew = new ArrayList<Usergrupo>();
            for (Usergrupo usergrupoListNewUsergrupoToAttach : usergrupoListNew) {
                usergrupoListNewUsergrupoToAttach = em.getReference(usergrupoListNewUsergrupoToAttach.getClass(), usergrupoListNewUsergrupoToAttach.getUsergrupoPK());
                attachedUsergrupoListNew.add(usergrupoListNewUsergrupoToAttach);
            }
            usergrupoListNew = attachedUsergrupoListNew;
            grupo.setUsergrupoList(usergrupoListNew);
            grupo = em.merge(grupo);
            for (Roles rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    Grupo oldGrupoOfRolesListNewRoles = rolesListNewRoles.getGrupo();
                    rolesListNewRoles.setGrupo(grupo);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                    if (oldGrupoOfRolesListNewRoles != null && !oldGrupoOfRolesListNewRoles.equals(grupo)) {
                        oldGrupoOfRolesListNewRoles.getRolesList().remove(rolesListNewRoles);
                        oldGrupoOfRolesListNewRoles = em.merge(oldGrupoOfRolesListNewRoles);
                    }
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIdGrupo(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Grupo oldIdGrupoOfUsersListNewUsers = usersListNewUsers.getIdGrupo();
                    usersListNewUsers.setIdGrupo(grupo);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIdGrupoOfUsersListNewUsers != null && !oldIdGrupoOfUsersListNewUsers.equals(grupo)) {
                        oldIdGrupoOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIdGrupoOfUsersListNewUsers = em.merge(oldIdGrupoOfUsersListNewUsers);
                    }
                }
            }
            for (Usergrupo usergrupoListNewUsergrupo : usergrupoListNew) {
                if (!usergrupoListOld.contains(usergrupoListNewUsergrupo)) {
                    Grupo oldGrupoOfUsergrupoListNewUsergrupo = usergrupoListNewUsergrupo.getGrupo();
                    usergrupoListNewUsergrupo.setGrupo(grupo);
                    usergrupoListNewUsergrupo = em.merge(usergrupoListNewUsergrupo);
                    if (oldGrupoOfUsergrupoListNewUsergrupo != null && !oldGrupoOfUsergrupoListNewUsergrupo.equals(grupo)) {
                        oldGrupoOfUsergrupoListNewUsergrupo.getUsergrupoList().remove(usergrupoListNewUsergrupo);
                        oldGrupoOfUsergrupoListNewUsergrupo = em.merge(oldGrupoOfUsergrupoListNewUsergrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = grupo.getIdGrupo();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getIdGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Roles> rolesListOrphanCheck = grupo.getRolesList();
            for (Roles rolesListOrphanCheckRoles : rolesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Roles " + rolesListOrphanCheckRoles + " in its rolesList field has a non-nullable grupo field.");
            }
            List<Usergrupo> usergrupoListOrphanCheck = grupo.getUsergrupoList();
            for (Usergrupo usergrupoListOrphanCheckUsergrupo : usergrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Usergrupo " + usergrupoListOrphanCheckUsergrupo + " in its usergrupoList field has a non-nullable grupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Users> usersList = grupo.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIdGrupo(null);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
