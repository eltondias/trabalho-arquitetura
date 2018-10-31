package com.arquitetira.trabalho.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DescontoPlanoContabil.
 */
@Entity
@Table(name = "desconto_plano_contabil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "descontoplanocontabil")
public class DescontoPlanoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JsonIgnoreProperties("descontoPlanoContabils")
    private PeriodoPagamento periodoPagamento;

    @ManyToOne
    @JsonIgnoreProperties("descontoPlanoContabils")
    private PlanoContabil planoContabil;

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

    public DescontoPlanoContabil estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return periodoPagamento;
    }

    public DescontoPlanoContabil periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
        return this;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public PlanoContabil getPlanoContabil() {
        return planoContabil;
    }

    public DescontoPlanoContabil planoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
        return this;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
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
        DescontoPlanoContabil descontoPlanoContabil = (DescontoPlanoContabil) o;
        if (descontoPlanoContabil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), descontoPlanoContabil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DescontoPlanoContabil{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
