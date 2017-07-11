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
    @NamedQuery(name = "Avaliacaodocenteestudante.findAll", query = "SELECT a FROM Avaliacaodocenteestudante a")
    , @NamedQuery(name = "Avaliacaodocenteestudante.findByIdavaliacaodocenteestudante", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.idavaliacaodocenteestudante = :idavaliacaodocenteestudante")
    , @NamedQuery(name = "Avaliacaodocenteestudante.findByAnoletivo", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.anoletivo = :anoletivo")
    , @NamedQuery(name = "Avaliacaodocenteestudante.findByObservacao", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.observacao = :observacao")
    , @NamedQuery(name = "Avaliacaodocenteestudante.findByPontuacaototal", query = "SELECT a FROM Avaliacaodocenteestudante a WHERE a.pontuacaototal = :pontuacaototal")})
public class Avaliacaodocenteestudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idavaliacaodocenteestudante;
    private Short anoletivo;
    @Column(length = 255)
    private String observacao;
    private Short pontuacaototal;
    @JoinTable(name = "avaliacaoquestaode", joinColumns = {
        @JoinColumn(name = "idavaliacaodocenteestudante", referencedColumnName = "idavaliacaodocenteestudante", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idquestaodocenteestudante", referencedColumnName = "idquestaodocenteestudante", nullable = false)})
    @ManyToMany
    private List<Questaodocenteestudante> questaodocenteestudanteList;
    @JoinTable(name = "docentepeloestudantepontuacao", joinColumns = {
        @JoinColumn(name = "idavaliacaodocenteestudante", referencedColumnName = "idavaliacaodocenteestudante", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idpontuacaoconjugada", referencedColumnName = "idpontuacaoconjugada", nullable = false)})
    @ManyToMany
    private List<Pontuacaoconjugada> pontuacaoconjugadaList;
    @JoinColumn(name = "iddisciplina", referencedColumnName = "iddisciplina")
    @ManyToOne
    private Disciplina iddisciplina;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne
    private Professor idprofessor;

    public Avaliacaodocenteestudante() {
    }

    public Avaliacaodocenteestudante(Short idavaliacaodocenteestudante) {
        this.idavaliacaodocenteestudante = idavaliacaodocenteestudante;
    }

    public Short getIdavaliacaodocenteestudante() {
        return idavaliacaodocenteestudante;
    }

    public void setIdavaliacaodocenteestudante(Short idavaliacaodocenteestudante) {
        this.idavaliacaodocenteestudante = idavaliacaodocenteestudante;
    }

    public Short getAnoletivo() {
        return anoletivo;
    }

    public void setAnoletivo(Short anoletivo) {
        this.anoletivo = anoletivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Short getPontuacaototal() {
        return pontuacaototal;
    }

    public void setPontuacaototal(Short pontuacaototal) {
        this.pontuacaototal = pontuacaototal;
    }

    @XmlTransient
    public List<Questaodocenteestudante> getQuestaodocenteestudanteList() {
        return questaodocenteestudanteList;
    }

    public void setQuestaodocenteestudanteList(List<Questaodocenteestudante> questaodocenteestudanteList) {
        this.questaodocenteestudanteList = questaodocenteestudanteList;
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
        hash += (idavaliacaodocenteestudante != null ? idavaliacaodocenteestudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacaodocenteestudante)) {
            return false;
        }
        Avaliacaodocenteestudante other = (Avaliacaodocenteestudante) object;
        if ((this.idavaliacaodocenteestudante == null && other.idavaliacaodocenteestudante != null) || (this.idavaliacaodocenteestudante != null && !this.idavaliacaodocenteestudante.equals(other.idavaliacaodocenteestudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avaliacaodocenteestudante[ idavaliacaodocenteestudante=" + idavaliacaodocenteestudante + " ]";
    }
    
}
