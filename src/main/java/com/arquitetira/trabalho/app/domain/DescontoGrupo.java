package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DescontoGrupo.
 */
@Entity
@Table(name = "desconto_grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "descontogrupo")
public class DescontoGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JsonIgnoreProperties("descontoGrupos")
    private PeriodoPagamento periodoPagamento;

    @ManyToOne
    @JsonIgnoreProperties("descontoGrupos")
    private GrupoContaAzul grupoContaAzul;

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

    public DescontoGrupo estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return periodoPagamento;
    }

    public DescontoGrupo periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
        return this;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public GrupoContaAzul getGrupoContaAzul() {
        return grupoContaAzul;
    }

    public DescontoGrupo grupoContaAzul(GrupoContaAzul grupoContaAzul) {
        this.grupoContaAzul = grupoContaAzul;
        return this;
    }

    public void setGrupoContaAzul(GrupoContaAzul grupoContaAzul) {
        this.grupoContaAzul = grupoContaAzul;
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
        DescontoGrupo descontoGrupo = (DescontoGrupo) o;
        if (descontoGrupo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), descontoGrupo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DescontoGrupo{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
