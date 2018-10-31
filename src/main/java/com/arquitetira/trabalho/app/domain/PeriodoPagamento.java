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
 * A PeriodoPagamento.
 */
@Entity
@Table(name = "periodo_pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "periodopagamento")
public class PeriodoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "periodo")
    private String periodo;

    @OneToMany(mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DescontoGrupo> descontoGrupos = new HashSet<>();
    @OneToMany(mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DescontoPlanoContabil> descontoPlanoContabils = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public PeriodoPagamento periodo(String periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Set<DescontoGrupo> getDescontoGrupos() {
        return descontoGrupos;
    }

    public PeriodoPagamento descontoGrupos(Set<DescontoGrupo> descontoGrupos) {
        this.descontoGrupos = descontoGrupos;
        return this;
    }

    public PeriodoPagamento addDescontoGrupo(DescontoGrupo descontoGrupo) {
        this.descontoGrupos.add(descontoGrupo);
        descontoGrupo.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeDescontoGrupo(DescontoGrupo descontoGrupo) {
        this.descontoGrupos.remove(descontoGrupo);
        descontoGrupo.setPeriodoPagamento(null);
        return this;
    }

    public void setDescontoGrupos(Set<DescontoGrupo> descontoGrupos) {
        this.descontoGrupos = descontoGrupos;
    }

    public Set<DescontoPlanoContabil> getDescontoPlanoContabils() {
        return descontoPlanoContabils;
    }

    public PeriodoPagamento descontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        this.descontoPlanoContabils = descontoPlanoContabils;
        return this;
    }

    public PeriodoPagamento addDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.add(descontoPlanoContabil);
        descontoPlanoContabil.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.remove(descontoPlanoContabil);
        descontoPlanoContabil.setPeriodoPagamento(null);
        return this;
    }

    public void setDescontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        this.descontoPlanoContabils = descontoPlanoContabils;
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
        PeriodoPagamento periodoPagamento = (PeriodoPagamento) o;
        if (periodoPagamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodoPagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodoPagamento{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            "}";
    }
}
