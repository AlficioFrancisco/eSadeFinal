/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ali_sualei
 */
@Entity
@Table(catalog = "esade", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caracteristiquestaosuperior.findAll", query = "SELECT c FROM Caracteristiquestaosuperior c")
    , @NamedQuery(name = "Caracteristiquestaosuperior.findByIdcaracteristicasuperior", query = "SELECT c FROM Caracteristiquestaosuperior c WHERE c.idcaracteristicasuperior = :idcaracteristicasuperior")
    , @NamedQuery(name = "Caracteristiquestaosuperior.findByDescricao", query = "SELECT c FROM Caracteristiquestaosuperior c WHERE c.descricao = :descricao")})
public class Caracteristiquestaosuperior implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idcaracteristicasuperior;
    @Column(length = 255)
    private String descricao;
    @OneToMany(mappedBy = "idcaracteristicaquestaosuperior")
    private List<Questaodocentesuperior> questaodocentesuperiorList;

    public Caracteristiquestaosuperior() {
    }

    public Caracteristiquestaosuperior(Short idcaracteristicasuperior) {
        this.idcaracteristicasuperior = idcaracteristicasuperior;
    }

    public Short getIdcaracteristicasuperior() {
        return idcaracteristicasuperior;
    }

    public void setIdcaracteristicasuperior(Short idcaracteristicasuperior) {
        this.idcaracteristicasuperior = idcaracteristicasuperior;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Questaodocentesuperior> getQuestaodocentesuperiorList() {
        return questaodocentesuperiorList;
    }

    public void setQuestaodocentesuperiorList(List<Questaodocentesuperior> questaodocentesuperiorList) {
        this.questaodocentesuperiorList = questaodocentesuperiorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcaracteristicasuperior != null ? idcaracteristicasuperior.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caracteristiquestaosuperior)) {
            return false;
        }
        Caracteristiquestaosuperior other = (Caracteristiquestaosuperior) object;
        if ((this.idcaracteristicasuperior == null && other.idcaracteristicasuperior != null) || (this.idcaracteristicasuperior != null && !this.idcaracteristicasuperior.equals(other.idcaracteristicasuperior))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Caracteristiquestaosuperior[ idcaracteristicasuperior=" + idcaracteristicasuperior + " ]";
    }
    
}
