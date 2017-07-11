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
    @NamedQuery(name = "Pontuacaosuperiorhierarquico.findAll", query = "SELECT p FROM Pontuacaosuperiorhierarquico p")
    , @NamedQuery(name = "Pontuacaosuperiorhierarquico.findByIdpontuacaosuperiorhier", query = "SELECT p FROM Pontuacaosuperiorhierarquico p WHERE p.idpontuacaosuperiorhier = :idpontuacaosuperiorhier")
    , @NamedQuery(name = "Pontuacaosuperiorhierarquico.findByDescricao", query = "SELECT p FROM Pontuacaosuperiorhierarquico p WHERE p.descricao = :descricao")})
public class Pontuacaosuperiorhierarquico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idpontuacaosuperiorhier;
    @Column(length = 255)
    private String descricao;
    @OneToMany(mappedBy = "idpontuacaosuperiorhierarquico")
    private List<Questaodocentesuperior> questaodocentesuperiorList;

    public Pontuacaosuperiorhierarquico() {
    }

    public Pontuacaosuperiorhierarquico(Short idpontuacaosuperiorhier) {
        this.idpontuacaosuperiorhier = idpontuacaosuperiorhier;
    }

    public Short getIdpontuacaosuperiorhier() {
        return idpontuacaosuperiorhier;
    }

    public void setIdpontuacaosuperiorhier(Short idpontuacaosuperiorhier) {
        this.idpontuacaosuperiorhier = idpontuacaosuperiorhier;
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
        hash += (idpontuacaosuperiorhier != null ? idpontuacaosuperiorhier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pontuacaosuperiorhierarquico)) {
            return false;
        }
        Pontuacaosuperiorhierarquico other = (Pontuacaosuperiorhierarquico) object;
        if ((this.idpontuacaosuperiorhier == null && other.idpontuacaosuperiorhier != null) || (this.idpontuacaosuperiorhier != null && !this.idpontuacaosuperiorhier.equals(other.idpontuacaosuperiorhier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pontuacaosuperiorhierarquico[ idpontuacaosuperiorhier=" + idpontuacaosuperiorhier + " ]";
    }
    
}
