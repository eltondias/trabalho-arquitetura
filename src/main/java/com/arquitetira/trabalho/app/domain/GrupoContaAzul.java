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
 * A GrupoContaAzul.
 */
@Entity
@Table(name = "grupo_conta_azul")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grupocontaazul")
public class GrupoContaAzul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "grupoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoContaAzul> planoContaAzuls = new HashSet<>();
    @OneToMany(mappedBy = "grupoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DescontoGrupo> descontoGrupos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public GrupoContaAzul estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<PlanoContaAzul> getPlanoContaAzuls() {
        return planoContaAzuls;
    }

    public GrupoContaAzul planoContaAzuls(Set<PlanoContaAzul> planoContaAzuls) {
        this.planoContaAzuls = planoContaAzuls;
        return this;
    }

    public GrupoContaAzul addPlanoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzuls.add(planoContaAzul);
        planoContaAzul.setGrupoContaAzul(this);
        return this;
    }

    public GrupoContaAzul removePlanoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzuls.remove(planoContaAzul);
        planoContaAzul.setGrupoContaAzul(null);
        return this;
    }

    public void setPlanoContaAzuls(Set<PlanoContaAzul> planoContaAzuls) {
        this.planoContaAzuls = planoContaAzuls;
    }

    public Set<DescontoGrupo> getDescontoGrupos() {
        return descontoGrupos;
    }

    public GrupoContaAzul descontoGrupos(Set<DescontoGrupo> descontoGrupos) {
        this.descontoGrupos = descontoGrupos;
        return this;
    }

    public GrupoContaAzul addDescontoGrupo(DescontoGrupo descontoGrupo) {
        this.descontoGrupos.add(descontoGrupo);
        descontoGrupo.setGrupoContaAzul(this);
        return this;
    }

    public GrupoContaAzul removeDescontoGrupo(DescontoGrupo descontoGrupo) {
        this.descontoGrupos.remove(descontoGrupo);
        descontoGrupo.setGrupoContaAzul(null);
        return this;
    }

    public void setDescontoGrupos(Set<DescontoGrupo> descontoGrupos) {
        this.descontoGrupos = descontoGrupos;
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
        GrupoContaAzul grupoContaAzul = (GrupoContaAzul) o;
        if (grupoContaAzul.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoContaAzul.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoContaAzul{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
