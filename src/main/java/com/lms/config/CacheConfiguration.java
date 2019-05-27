package com.lms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.lms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.lms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.lms.domain.User.class.getName());
            createCache(cm, com.lms.domain.Authority.class.getName());
            createCache(cm, com.lms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.lms.domain.ModulePedagogique.class.getName());
            createCache(cm, com.lms.domain.ModulePedagogique.class.getName() + ".parcours");
            createCache(cm, com.lms.domain.Parcour.class.getName());
            createCache(cm, com.lms.domain.Parcour.class.getName() + ".exercices");
            createCache(cm, com.lms.domain.Parcour.class.getName() + ".cours");
            createCache(cm, com.lms.domain.Exercice.class.getName());
            createCache(cm, com.lms.domain.Cours.class.getName());
            createCache(cm, com.lms.domain.ModulePedagogique.class.getName() + ".sujets");
            createCache(cm, com.lms.domain.ModulePedagogique.class.getName() + ".competences");
            createCache(cm, com.lms.domain.ModulePedagogique.class.getName() + ".devoirs");
            createCache(cm, com.lms.domain.Devoir.class.getName());
            createCache(cm, com.lms.domain.Sujet.class.getName());
            createCache(cm, com.lms.domain.Sujet.class.getName() + ".periodes");
            createCache(cm, com.lms.domain.Periode.class.getName());
            createCache(cm, com.lms.domain.Competence.class.getName());
            createCache(cm, com.lms.domain.Exercice.class.getName() + ".questions");
            createCache(cm, com.lms.domain.Exercice.class.getName() + ".ressources");
            createCache(cm, com.lms.domain.Cours.class.getName() + ".questions");
            createCache(cm, com.lms.domain.Cours.class.getName() + ".ressources");
            createCache(cm, com.lms.domain.Situation.class.getName());
            createCache(cm, com.lms.domain.Situation.class.getName() + ".questions");
            createCache(cm, com.lms.domain.Question.class.getName());
            createCache(cm, com.lms.domain.Reponse.class.getName());
            createCache(cm, com.lms.domain.Ressource.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
