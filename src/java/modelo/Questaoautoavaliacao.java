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
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Questaoautoavaliacao.findAll", query = "SELECT q FROM Questaoautoavaliacao q")
    , @NamedQuery(name = "Questaoautoavaliacao.findByIdquestaoautoavaliacao", query = "SELECT q FROM Questaoautoavaliacao q WHERE q.idquestaoautoavaliacao = :idquestaoautoavaliacao")
    , @NamedQuery(name = "Questaoautoavaliacao.findByCodigo", query = "SELECT q FROM Questaoautoavaliacao q WHERE q.codigo = :codigo")
    , @NamedQuery(name = "Questaoautoavaliacao.findByDescricao", query = "SELECT q FROM Questaoautoavaliacao q WHERE q.descricao = :descricao")})
public class Questaoautoavaliacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idquestaoautoavaliacao;
    @Column(length = 255)
    private String codigo;
    @Column(length = 255)
    private String descricao;
    @ManyToMany(mappedBy = "questaoautoavaliacaoList")
    private List<Autoavaliacao> autoavaliacaoList;
    @OneToMany(mappedBy = "idquestaoautoavaliacao")
    private List<Parametroautoavaliacao> parametroautoavaliacaoList;

    public Questaoautoavaliacao() {
    }

    public Questaoautoavaliacao(Short idquestaoautoavaliacao) {
        this.idquestaoautoavaliacao = idquestaoautoavaliacao;
    }

    public Short getIdquestaoautoavaliacao() {
        return idquestaoautoavaliacao;
    }

    public void setIdquestaoautoavaliacao(Short idquestaoautoavaliacao) {
        this.idquestaoautoavaliacao = idquestaoautoavaliacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Autoavaliacao> getAutoavaliacaoList() {
        return autoavaliacaoList;
    }

    public void setAutoavaliacaoList(List<Autoavaliacao> autoavaliacaoList) {
        this.autoavaliacaoList = autoavaliacaoList;
    }

    @XmlTransient
    public List<Parametroautoavaliacao> getParametroautoavaliacaoList() {
        return parametroautoavaliacaoList;
    }

    public void setParametroautoavaliacaoList(List<Parametroautoavaliacao> parametroautoavaliacaoList) {
        this.parametroautoavaliacaoList = parametroautoavaliacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquestaoautoavaliacao != null ? idquestaoautoavaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questaoautoavaliacao)) {
            return false;
        }
        Questaoautoavaliacao other = (Questaoautoavaliacao) object;
        if ((this.idquestaoautoavaliacao == null && other.idquestaoautoavaliacao != null) || (this.idquestaoautoavaliacao != null && !this.idquestaoautoavaliacao.equals(other.idquestaoautoavaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Questaoautoavaliacao[ idquestaoautoavaliacao=" + idquestaoautoavaliacao + " ]";
    }
    
}
