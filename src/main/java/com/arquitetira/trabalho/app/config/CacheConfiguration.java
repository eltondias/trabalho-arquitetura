package com.arquitetira.trabalho.app.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.arquitetira.trabalho.app.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Endereco.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Ramo.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Ramo.class.getName() + ".empresas", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Enquadramento.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Enquadramento.class.getName() + ".empresas", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Empresa.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Empresa.class.getName() + ".planoEmpresas", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.Empresa.class.getName() + ".enderecos", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PeriodoPagamento.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PeriodoPagamento.class.getName() + ".descontoGrupos", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PeriodoPagamento.class.getName() + ".descontoPlanoContabils", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.GrupoContaAzul.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.GrupoContaAzul.class.getName() + ".planoContaAzuls", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.GrupoContaAzul.class.getName() + ".descontoGrupos", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.DescontoGrupo.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.DescontoPlanoContabil.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoContabil.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoContabil.class.getName() + ".planoEmpresas", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoContabil.class.getName() + ".descontoPlanoContabils", jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoEmpresa.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoContaAzul.class.getName(), jcacheConfiguration);
            cm.createCache(com.arquitetira.trabalho.app.domain.PlanoContaAzul.class.getName() + ".planoEmpresas", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
