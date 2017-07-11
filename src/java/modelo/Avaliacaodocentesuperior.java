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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Avaliacaodocentesuperior.findAll", query = "SELECT a FROM Avaliacaodocentesuperior a")
    , @NamedQuery(name = "Avaliacaodocentesuperior.findByIdavaliacaodocentesuperior", query = "SELECT a FROM Avaliacaodocentesuperior a WHERE a.idavaliacaodocentesuperior = :idavaliacaodocentesuperior")
    , @NamedQuery(name = "Avaliacaodocentesuperior.findByAnoletivo", query = "SELECT a FROM Avaliacaodocentesuperior a WHERE a.anoletivo = :anoletivo")})
public class Avaliacaodocentesuperior implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idavaliacaodocentesuperior;
    private Short anoletivo;
    @JoinTable(name = "avaliacaoquestaods", joinColumns = {
        @JoinColumn(name = "idavaliacaodocentesuperior", referencedColumnName = "idavaliacaodocentesuperior", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idquestaodocentesuperior", referencedColumnName = "idquestaodocentesuperior", nullable = false)})
    @ManyToMany
    private List<Questaodocentesuperior> questaodocentesuperiorList;
    @JoinTable(name = "avaliacaosuperiorhierpontuacao", joinColumns = {
        @JoinColumn(name = "idavaliacaosuperiorhierq", referencedColumnName = "idavaliacaodocentesuperior")}, inverseJoinColumns = {
        @JoinColumn(name = "idpontuacaoconjugada", referencedColumnName = "idpontuacaoconjugada")})
    @ManyToMany
    private List<Pontuacaoconjugada> pontuacaoconjugadaList;
    @JoinColumn(name = "iddisciplina", referencedColumnName = "iddisciplina")
    @ManyToOne
    private Disciplina iddisciplina;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne
    private Professor idprofessor;

    public Avaliacaodocentesuperior() {
    }

    public Avaliacaodocentesuperior(Short idavaliacaodocentesuperior) {
        this.idavaliacaodocentesuperior = idavaliacaodocentesuperior;
    }

    public Short getIdavaliacaodocentesuperior() {
        return idavaliacaodocentesuperior;
    }

    public void setIdavaliacaodocentesuperior(Short idavaliacaodocentesuperior) {
        this.idavaliacaodocentesuperior = idavaliacaodocentesuperior;
    }

    public Short getAnoletivo() {
        return anoletivo;
    }

    public void setAnoletivo(Short anoletivo) {
        this.anoletivo = anoletivo;
    }

    @XmlTransient
    public List<Questaodocentesuperior> getQuestaodocentesuperiorList() {
        return questaodocentesuperiorList;
    }

    public void setQuestaodocentesuperiorList(List<Questaodocentesuperior> questaodocentesuperiorList) {
        this.questaodocentesuperiorList = questaodocentesuperiorList;
    }

    @XmlTransient
    public List<Pontuacaoconjugada> getPontuacaoconjugadaList() {
        return pontuacaoconjugadaList;
    }

    public void setPontuacaoconjugadaList(List<Pontuacaoconjugada> pontuacaoconjugadaList) {
        this.pontuacaoconjugadaList = pontuacaoconjugadaList;
    }

    public Disciplina getIddisciplina() {
        return iddisciplina;
    }

    public void setIddisciplina(Disciplina iddisciplina) {
        this.iddisciplina = iddisciplina;
    }

    public Professor getIdprofessor() {
        return idprofessor;
    }

    public void setIdprofessor(Professor idprofessor) {
        this.idprofessor = idprofessor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idavaliacaodocentesuperior != null ? idavaliacaodocentesuperior.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacaodocentesuperior)) {
            return false;
        }
        Avaliacaodocentesuperior other = (Avaliacaodocentesuperior) object;
        if ((this.idavaliacaodocentesuperior == null && other.idavaliacaodocentesuperior != null) || (this.idavaliacaodocentesuperior != null && !this.idavaliacaodocentesuperior.equals(other.idavaliacaodocentesuperior))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avaliacaodocentesuperior[ idavaliacaodocentesuperior=" + idavaliacaodocentesuperior + " ]";
    }
    
}
