package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private Ramo ramo;

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private Enquadramento enquadramento;

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoEmpresa> planoEmpresas = new HashSet<>();
    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Endereco> enderecos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Empresa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Ramo getRamo() {
        return ramo;
    }

    public Empresa ramo(Ramo ramo) {
        this.ramo = ramo;
        return this;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public Enquadramento getEnquadramento() {
        return enquadramento;
    }

    public Empresa enquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
        return this;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public Set<PlanoEmpresa> getPlanoEmpresas() {
        return planoEmpresas;
    }

    public Empresa planoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
        return this;
    }

    public Empresa addPlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.add(planoEmpresa);
        planoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removePlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.remove(planoEmpresa);
        planoEmpresa.setEmpresa(null);
        return this;
    }

    public void setPlanoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Empresa enderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    public Empresa addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setEmpresa(this);
        return this;
    }

    public Empresa removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setEmpresa(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Empresa empresa = (Empresa) o;
        if (empresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
