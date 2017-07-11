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
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor p")
    , @NamedQuery(name = "Professor.findByIdprofessor", query = "SELECT p FROM Professor p WHERE p.idprofessor = :idprofessor")
    , @NamedQuery(name = "Professor.findByNome", query = "SELECT p FROM Professor p WHERE p.nome = :nome")})
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short idprofessor;
    @Column(length = 255)
    private String nome;
    @OneToMany(mappedBy = "idprofessor")
    private List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList;
    @OneToMany(mappedBy = "idprofessor")
    private List<Autoavaliacao> autoavaliacaoList;
    @OneToMany(mappedBy = "idprofessor")
    private List<Users> usersList;
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne
    private Categoria idcategoria;
    @JoinColumn(name = "iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne
    private Departamento iddepartamento;
    @JoinColumn(name = "idfuncao", referencedColumnName = "idfuncao")
    @ManyToOne
    private Funcao idfuncao;
    @JoinColumn(name = "idregime", referencedColumnName = "idregime")
    @ManyToOne
    private Regime idregime;
    @OneToMany(mappedBy = "idprofessor")
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;

    public Professor() {
    }

    public Professor(Short idprofessor) {
        this.idprofessor = idprofessor;
    }

    public Short getIdprofessor() {
        return idprofessor;
    }

    public void setIdprofessor(Short idprofessor) {
        this.idprofessor = idprofessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Avaliacaodocentesuperior> getAvaliacaodocentesuperiorList() {
        return avaliacaodocentesuperiorList;
    }

    public void setAvaliacaodocentesuperiorList(List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList) {
        this.avaliacaodocentesuperiorList = avaliacaodocentesuperiorList;
    }

    @XmlTransient
    public List<Autoavaliacao> getAutoavaliacaoList() {
        return autoavaliacaoList;
    }

    public void setAutoavaliacaoList(List<Autoavaliacao> autoavaliacaoList) {
        this.autoavaliacaoList = autoavaliacaoList;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Departamento getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Departamento iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public Funcao getIdfuncao() {
        return idfuncao;
    }

    public void setIdfuncao(Funcao idfuncao) {
        this.idfuncao = idfuncao;
    }

    public Regime getIdregime() {
        return idregime;
    }

    public void setIdregime(Regime idregime) {
        this.idregime = idregime;
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
        hash += (idprofessor != null ? idprofessor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((this.idprofessor == null && other.idprofessor != null) || (this.idprofessor != null && !this.idprofessor.equals(other.idprofessor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Professor[ idprofessor=" + idprofessor + " ]";
    }
    
}
