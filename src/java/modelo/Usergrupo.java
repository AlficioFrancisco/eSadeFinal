/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ali_sualei
 */
@Entity
@Table(catalog = "esade", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usergrupo.findAll", query = "SELECT u FROM Usergrupo u")
    , @NamedQuery(name = "Usergrupo.findByIdGrupo", query = "SELECT u FROM Usergrupo u WHERE u.usergrupoPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Usergrupo.findByUtilizador", query = "SELECT u FROM Usergrupo u WHERE u.usergrupoPK.utilizador = :utilizador")
    , @NamedQuery(name = "Usergrupo.findByDataAlocacao", query = "SELECT u FROM Usergrupo u WHERE u.usergrupoPK.dataAlocacao = :dataAlocacao")})
public class Usergrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsergrupoPK usergrupoPK;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "utilizador", referencedColumnName = "utilizador", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Usergrupo() {
    }

    public Usergrupo(UsergrupoPK usergrupoPK) {
        this.usergrupoPK = usergrupoPK;
    }

    public Usergrupo(String idGrupo, String utilizador, Date dataAlocacao) {
        this.usergrupoPK = new UsergrupoPK(idGrupo, utilizador, dataAlocacao);
    }

    public UsergrupoPK getUsergrupoPK() {
        return usergrupoPK;
    }

    public void setUsergrupoPK(UsergrupoPK usergrupoPK) {
        this.usergrupoPK = usergrupoPK;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usergrupoPK != null ? usergrupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usergrupo)) {
            return false;
        }
        Usergrupo other = (Usergrupo) object;
        if ((this.usergrupoPK == null && other.usergrupoPK != null) || (this.usergrupoPK != null && !this.usergrupoPK.equals(other.usergrupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usergrupo[ usergrupoPK=" + usergrupoPK + " ]";
    }
    
}
