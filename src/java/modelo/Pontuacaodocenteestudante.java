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
    @NamedQuery(name = "Pontuacaodocenteestudante.findAll", query = "SELECT p FROM Pontuacaodocenteestudante p")
    , @NamedQuery(name = "Pontuacaodocenteestudante.findByIdpontuacaodocenteestudante", query = "SELECT p FROM Pontuacaodocenteestudante p WHERE p.idpontuacaodocenteestudante = :idpontuacaodocenteestudante")
    , @NamedQuery(name = "Pontuacaodocenteestudante.findByDescricao", query = "SELECT p FROM Pontuacaodocenteestudante p WHERE p.descricao = :descricao")})
public class Pontuacaodocenteestudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idpontuacaodocenteestudante;
    @Column(length = 255)
    private String descricao;
    @OneToMany(mappedBy = "idpontuacaodocenteestudante")
    private List<Parametrodocenteestudante> parametrodocenteestudanteList;

    public Pontuacaodocenteestudante() {
    }

    public Pontuacaodocenteestudante(Short idpontuacaodocenteestudante) {
        this.idpontuacaodocenteestudante = idpontuacaodocenteestudante;
    }

    public Short getIdpontuacaodocenteestudante() {
        return idpontuacaodocenteestudante;
    }

    public void setIdpontuacaodocenteestudante(Short idpontuacaodocenteestudante) {
        this.idpontuacaodocenteestudante = idpontuacaodocenteestudante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Parametrodocenteestudante> getParametrodocenteestudanteList() {
        return parametrodocenteestudanteList;
    }

    public void setParametrodocenteestudanteList(List<Parametrodocenteestudante> parametrodocenteestudanteList) {
        this.parametrodocenteestudanteList = parametrodocenteestudanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpontuacaodocenteestudante != null ? idpontuacaodocenteestudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pontuacaodocenteestudante)) {
            return false;
        }
        Pontuacaodocenteestudante other = (Pontuacaodocenteestudante) object;
        if ((this.idpontuacaodocenteestudante == null && other.idpontuacaodocenteestudante != null) || (this.idpontuacaodocenteestudante != null && !this.idpontuacaodocenteestudante.equals(other.idpontuacaodocenteestudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pontuacaodocenteestudante[ idpontuacaodocenteestudante=" + idpontuacaodocenteestudante + " ]";
    }
    
}
