package com.example.virt3

import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON
import org.springframework.context.annotation.Scope
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@Scope(SCOPE_SINGLETON) interface Repo : JpaRepository<Element, Int>
