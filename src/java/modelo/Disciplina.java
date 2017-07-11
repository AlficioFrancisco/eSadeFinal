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
    @NamedQuery(name = "Disciplina.findAll", query = "SELECT d FROM Disciplina d")
    , @NamedQuery(name = "Disciplina.findByIddisciplina", query = "SELECT d FROM Disciplina d WHERE d.iddisciplina = :iddisciplina")
    , @NamedQuery(name = "Disciplina.findByDescricao", query = "SELECT d FROM Disciplina d WHERE d.descricao = :descricao")
    , @NamedQuery(name = "Disciplina.findByNivel", query = "SELECT d FROM Disciplina d WHERE d.nivel = :nivel")
    , @NamedQuery(name = "Disciplina.findBySemestre", query = "SELECT d FROM Disciplina d WHERE d.semestre = :semestre")})
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short iddisciplina;
    @Column(length = 255)
    private String descricao;
    @Column(length = 255)
    private String nivel;
    @Column(length = 255)
    private String semestre;
    @OneToMany(mappedBy = "iddisciplina")
    private List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList;
    @OneToMany(mappedBy = "iddisciplina")
    private List<Autoavaliacao> autoavaliacaoList;
    @OneToMany(mappedBy = "iddisciplina")
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;
    @JoinColumn(name = "idcurso", referencedColumnName = "idcurso")
    @ManyToOne
    private Curso idcurso;

    public Disciplina() {
    }

    public Disciplina(Short iddisciplina) {
        this.iddisciplina = iddisciplina;
    }

    public Short getIddisciplina() {
        return iddisciplina;
    }

    public void setIddisciplina(Short iddisciplina) {
        this.iddisciplina = iddisciplina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
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
    public List<Avaliacaodocenteestudante> getAvaliacaodocenteestudanteList() {
        return avaliacaodocenteestudanteList;
    }

    public void setAvaliacaodocenteestudanteList(List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList) {
        this.avaliacaodocenteestudanteList = avaliacaodocenteestudanteList;
    }

    public Curso getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Curso idcurso) {
        this.idcurso = idcurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddisciplina != null ? iddisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.iddisciplina == null && other.iddisciplina != null) || (this.iddisciplina != null && !this.iddisciplina.equals(other.iddisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Disciplina[ iddisciplina=" + iddisciplina + " ]";
    }
    
}
