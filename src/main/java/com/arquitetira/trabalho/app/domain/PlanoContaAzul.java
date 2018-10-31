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
 * A PlanoContaAzul.
 */
@Entity
@Table(name = "plano_conta_azul")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "planocontaazul")
public class PlanoContaAzul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "plano")
    private Long plano;

    @ManyToOne
    @JsonIgnoreProperties("planoContaAzuls")
    private GrupoContaAzul grupoContaAzul;

    @OneToMany(mappedBy = "planoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoEmpresa> planoEmpresas = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlano() {
        return plano;
    }

    public PlanoContaAzul plano(Long plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Long plano) {
        this.plano = plano;
    }

    public GrupoContaAzul getGrupoContaAzul() {
        return grupoContaAzul;
    }

    public PlanoContaAzul grupoContaAzul(GrupoContaAzul grupoContaAzul) {
        this.grupoContaAzul = grupoContaAzul;
        return this;
    }

    public void setGrupoContaAzul(GrupoContaAzul grupoContaAzul) {
        this.grupoContaAzul = grupoContaAzul;
    }

    public Set<PlanoEmpresa> getPlanoEmpresas() {
        return planoEmpresas;
    }

    public PlanoContaAzul planoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
        return this;
    }

    public PlanoContaAzul addPlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.add(planoEmpresa);
        planoEmpresa.setPlanoContaAzul(this);
        return this;
    }

    public PlanoContaAzul removePlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.remove(planoEmpresa);
        planoEmpresa.setPlanoContaAzul(null);
        return this;
    }

    public void setPlanoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
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
        PlanoContaAzul planoContaAzul = (PlanoContaAzul) o;
        if (planoContaAzul.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoContaAzul.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoContaAzul{" +
            "id=" + getId() +
            ", plano=" + getPlano() +
            "}";
    }
}
