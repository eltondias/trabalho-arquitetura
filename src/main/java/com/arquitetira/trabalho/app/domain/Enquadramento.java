package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Enquadramento.
 */
@Entity
@Table(name = "enquadramento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enquadramento")
public class Enquadramento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "limite")
    private Long limite;

    @OneToMany(mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Empresa> empresas = new HashSet<>();
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

    public Enquadramento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getLimite() {
        return limite;
    }

    public Enquadramento limite(Long limite) {
        this.limite = limite;
        return this;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public Enquadramento empresas(Set<Empresa> empresas) {
        this.empresas = empresas;
        return this;
    }

    public Enquadramento addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setEnquadramento(null);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
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
        Enquadramento enquadramento = (Enquadramento) o;
        if (enquadramento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enquadramento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enquadramento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", limite=" + getLimite() +
            "}";
    }
}
