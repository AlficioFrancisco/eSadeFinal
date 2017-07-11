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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Autoavaliacao.findAll", query = "SELECT a FROM Autoavaliacao a")
    , @NamedQuery(name = "Autoavaliacao.findByIdautoavaliacao", query = "SELECT a FROM Autoavaliacao a WHERE a.idautoavaliacao = :idautoavaliacao")
    , @NamedQuery(name = "Autoavaliacao.findByObservacao", query = "SELECT a FROM Autoavaliacao a WHERE a.observacao = :observacao")
    , @NamedQuery(name = "Autoavaliacao.findByPontuacaototal", query = "SELECT a FROM Autoavaliacao a WHERE a.pontuacaototal = :pontuacaototal")
    , @NamedQuery(name = "Autoavaliacao.findByAno", query = "SELECT a FROM Autoavaliacao a WHERE a.ano = :ano")})
public class Autoavaliacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idautoavaliacao;
    @Column(length = 255)
    private String observacao;
    private Short pontuacaototal;
    @Temporal(TemporalType.DATE)
    private Date ano;
    @JoinTable(name = "autoavaliacaopontuacao", joinColumns = {
        @JoinColumn(name = "idautoavaliacao", referencedColumnName = "idautoavaliacao", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idpontuacaoconjugada", referencedColumnName = "idpontuacaoconjugada", nullable = false)})
    @ManyToMany
    private List<Pontuacaoconjugada> pontuacaoconjugadaList;
    @JoinTable(name = "autoavaliacaoquestao", joinColumns = {
        @JoinColumn(name = "idautoavaliacao", referencedColumnName = "idautoavaliacao", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idquestaautoavaliacao", referencedColumnName = "idquestaoautoavaliacao", nullable = false)})
    @ManyToMany
    private List<Questaoautoavaliacao> questaoautoavaliacaoList;
    @JoinColumn(name = "iddisciplina", referencedColumnName = "iddisciplina")
    @ManyToOne
    private Disciplina iddisciplina;
    @JoinColumn(name = "idprofessor", referencedColumnName = "idprofessor")
    @ManyToOne
    private Professor idprofessor;

    public Autoavaliacao() {
    }

    public Autoavaliacao(Short idautoavaliacao) {
        this.idautoavaliacao = idautoavaliacao;
    }

    public Short getIdautoavaliacao() {
        return idautoavaliacao;
    }

    public void setIdautoavaliacao(Short idautoavaliacao) {
        this.idautoavaliacao = idautoavaliacao;
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

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    @XmlTransient
    public List<Pontuacaoconjugada> getPontuacaoconjugadaList() {
        return pontuacaoconjugadaList;
    }

    public void setPontuacaoconjugadaList(List<Pontuacaoconjugada> pontuacaoconjugadaList) {
        this.pontuacaoconjugadaList = pontuacaoconjugadaList;
    }

    @XmlTransient
    public List<Questaoautoavaliacao> getQuestaoautoavaliacaoList() {
        return questaoautoavaliacaoList;
    }

    public void setQuestaoautoavaliacaoList(List<Questaoautoavaliacao> questaoautoavaliacaoList) {
        this.questaoautoavaliacaoList = questaoautoavaliacaoList;
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
        hash += (idautoavaliacao != null ? idautoavaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autoavaliacao)) {
            return false;
        }
        Autoavaliacao other = (Autoavaliacao) object;
        if ((this.idautoavaliacao == null && other.idautoavaliacao != null) || (this.idautoavaliacao != null && !this.idautoavaliacao.equals(other.idautoavaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Autoavaliacao[ idautoavaliacao=" + idautoavaliacao + " ]";
    }
    
}
