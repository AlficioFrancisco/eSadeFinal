/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Pontuacaoconjugada.findAll", query = "SELECT p FROM Pontuacaoconjugada p")
    , @NamedQuery(name = "Pontuacaoconjugada.findByIdpontuacaoconjugada", query = "SELECT p FROM Pontuacaoconjugada p WHERE p.idpontuacaoconjugada = :idpontuacaoconjugada")
    , @NamedQuery(name = "Pontuacaoconjugada.findByAno", query = "SELECT p FROM Pontuacaoconjugada p WHERE p.ano = :ano")
    , @NamedQuery(name = "Pontuacaoconjugada.findByDatahomologacao", query = "SELECT p FROM Pontuacaoconjugada p WHERE p.datahomologacao = :datahomologacao")})
public class Pontuacaoconjugada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idpontuacaoconjugada;
    private Integer ano;
    @Temporal(TemporalType.DATE)
    private Date datahomologacao;
    @ManyToMany(mappedBy = "pontuacaoconjugadaList")
    private List<Autoavaliacao> autoavaliacaoList;
    @ManyToMany(mappedBy = "pontuacaoconjugadaList")
    private List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList;
    @ManyToMany(mappedBy = "pontuacaoconjugadaList")
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;

    public Pontuacaoconjugada() {
    }

    public Pontuacaoconjugada(Short idpontuacaoconjugada) {
        this.idpontuacaoconjugada = idpontuacaoconjugada;
    }

    public Short getIdpontuacaoconjugada() {
        return idpontuacaoconjugada;
    }

    public void setIdpontuacaoconjugada(Short idpontuacaoconjugada) {
        this.idpontuacaoconjugada = idpontuacaoconjugada;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Date getDatahomologacao() {
        return datahomologacao;
    }

    public void setDatahomologacao(Date datahomologacao) {
        this.datahomologacao = datahomologacao;
    }

    @XmlTransient
    public List<Autoavaliacao> getAutoavaliacaoList() {
        return autoavaliacaoList;
    }

    public void setAutoavaliacaoList(List<Autoavaliacao> autoavaliacaoList) {
        this.autoavaliacaoList = autoavaliacaoList;
    }

    @XmlTransient
    public List<Avaliacaodocentesuperior> getAvaliacaodocentesuperiorList() {
        return avaliacaodocentesuperiorList;
    }

    public void setAvaliacaodocentesuperiorList(List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList) {
        this.avaliacaodocentesuperiorList = avaliacaodocentesuperiorList;
    }

    @XmlTransient
    public List<Avaliacaodocenteestudante> getAvaliacaodocenteestudanteList() {
        return avaliacaodocenteestudanteList;
    }

    public void setAvaliacaodocenteestudanteList(List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList) {
        this.avaliacaodocenteestudanteList = avaliacaodocenteestudanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpontuacaoconjugada != null ? idpontuacaoconjugada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pontuacaoconjugada)) {
            return false;
        }
        Pontuacaoconjugada other = (Pontuacaoconjugada) object;
        if ((this.idpontuacaoconjugada == null && other.idpontuacaoconjugada != null) || (this.idpontuacaoconjugada != null && !this.idpontuacaoconjugada.equals(other.idpontuacaoconjugada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pontuacaoconjugada[ idpontuacaoconjugada=" + idpontuacaoconjugada + " ]";
    }
    
}
