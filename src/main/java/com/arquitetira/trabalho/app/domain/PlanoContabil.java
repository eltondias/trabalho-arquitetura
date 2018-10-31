package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlanoContabil.
 */
@Entity
@Table(name = "plano_contabil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "planocontabil")
public class PlanoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "funcionarios")
    private Long funcionarios;

    @Column(name = "socios")
    private Long socios;

    @Column(name = "faturamento_mensal")
    private Long faturamentoMensal;

    @Column(name = "data_contratacao")
    private Instant dataContratacao;

    @Column(name = "data_encerramento")
    private Instant dataEncerramento;

    @OneToMany(mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoEmpresa> planoEmpresas = new HashSet<>();
    @OneToMany(mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DescontoPlanoContabil> descontoPlanoContabils = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncionarios() {
        return funcionarios;
    }

    public PlanoContabil funcionarios(Long funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public void setFuncionarios(Long funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Long getSocios() {
        return socios;
    }

    public PlanoContabil socios(Long socios) {
        this.socios = socios;
        return this;
    }

    public void setSocios(Long socios) {
        this.socios = socios;
    }

    public Long getFaturamentoMensal() {
        return faturamentoMensal;
    }

    public PlanoContabil faturamentoMensal(Long faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
        return this;
    }

    public void setFaturamentoMensal(Long faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
    }

    public Instant getDataContratacao() {
        return dataContratacao;
    }

    public PlanoContabil dataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
        return this;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Instant getDataEncerramento() {
        return dataEncerramento;
    }

    public PlanoContabil dataEncerramento(Instant dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
        return this;
    }

    public void setDataEncerramento(Instant dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Set<PlanoEmpresa> getPlanoEmpresas() {
        return planoEmpresas;
    }

    public PlanoContabil planoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
        return this;
    }

    public PlanoContabil addPlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.add(planoEmpresa);
        planoEmpresa.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removePlanoEmpresa(PlanoEmpresa planoEmpresa) {
        this.planoEmpresas.remove(planoEmpresa);
        planoEmpresa.setPlanoContabil(null);
        return this;
    }

    public void setPlanoEmpresas(Set<PlanoEmpresa> planoEmpresas) {
        this.planoEmpresas = planoEmpresas;
    }

    public Set<DescontoPlanoContabil> getDescontoPlanoContabils() {
        return descontoPlanoContabils;
    }

    public PlanoContabil descontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        this.descontoPlanoContabils = descontoPlanoContabils;
        return this;
    }

    public PlanoContabil addDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.add(descontoPlanoContabil);
        descontoPlanoContabil.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.remove(descontoPlanoContabil);
        descontoPlanoContabil.setPlanoContabil(null);
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
        PlanoContabil planoContabil = (PlanoContabil) o;
        if (planoContabil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoContabil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoContabil{" +
            "id=" + getId() +
            ", funcionarios=" + getFuncionarios() +
            ", socios=" + getSocios() +
            ", faturamentoMensal=" + getFaturamentoMensal() +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", dataEncerramento='" + getDataEncerramento() + "'" +
            "}";
    }
}
