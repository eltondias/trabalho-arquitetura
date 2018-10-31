package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PlanoEmpresa.
 */
@Entity
@Table(name = "plano_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "planoempresa")
public class PlanoEmpresa implements Serializable {

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
    private String dataContratacao;

    @Column(name = "data_encerramento")
    private String dataEncerramento;

    @ManyToOne
    @JsonIgnoreProperties("planoEmpresas")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("planoEmpresas")
    private PlanoContabil planoContabil;

    @ManyToOne
    @JsonIgnoreProperties("planoEmpresas")
    private PlanoContaAzul planoContaAzul;

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

    public PlanoEmpresa funcionarios(Long funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public void setFuncionarios(Long funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Long getSocios() {
        return socios;
    }

    public PlanoEmpresa socios(Long socios) {
        this.socios = socios;
        return this;
    }

    public void setSocios(Long socios) {
        this.socios = socios;
    }

    public Long getFaturamentoMensal() {
        return faturamentoMensal;
    }

    public PlanoEmpresa faturamentoMensal(Long faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
        return this;
    }

    public void setFaturamentoMensal(Long faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public PlanoEmpresa dataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
        return this;
    }

    public void setDataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public PlanoEmpresa dataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
        return this;
    }

    public void setDataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public PlanoEmpresa empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public PlanoContabil getPlanoContabil() {
        return planoContabil;
    }

    public PlanoEmpresa planoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
        return this;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public PlanoContaAzul getPlanoContaAzul() {
        return planoContaAzul;
    }

    public PlanoEmpresa planoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
        return this;
    }

    public void setPlanoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
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
        PlanoEmpresa planoEmpresa = (PlanoEmpresa) o;
        if (planoEmpresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoEmpresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoEmpresa{" +
            "id=" + getId() +
            ", funcionarios=" + getFuncionarios() +
            ", socios=" + getSocios() +
            ", faturamentoMensal=" + getFaturamentoMensal() +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", dataEncerramento='" + getDataEncerramento() + "'" +
            "}";
    }
}
