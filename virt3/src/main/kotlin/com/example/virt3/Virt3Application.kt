package com.example.virt3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication(exclude = [HibernateJpaAutoConfiguration::class])
class Virt3Application

fun main(args: Array<String>) = runApplication<Virt3Application>(*args) {
	setDefaultProperties(Properties().apply {
		put("server.port", 8080)
		put("spring.session.jdbc.initialize-schema", "always")
	})
}.run {}
