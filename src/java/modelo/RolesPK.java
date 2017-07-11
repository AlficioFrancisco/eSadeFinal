/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ali_sualei
 */
@Embeddable
public class RolesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_grupo", nullable = false, length = 45)
    private String idGrupo;
    @Basic(optional = false)
    @Column(name = "id_categoria", nullable = false, length = 5)
    private String idCategoria;
    @Basic(optional = false)
    @Column(name = "id_item", nullable = false, length = 5)
    private String idItem;

    public RolesPK() {
    }

    public RolesPK(String idGrupo, String idCategoria, String idItem) {
        this.idGrupo = idGrupo;
        this.idCategoria = idCategoria;
        this.idItem = idItem;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolesPK)) {
            return false;
        }
        RolesPK other = (RolesPK) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RolesPK[ idGrupo=" + idGrupo + ", idCategoria=" + idCategoria + ", idItem=" + idItem + " ]";
    }
    
}
