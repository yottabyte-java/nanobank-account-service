package com.nanobank.account_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NanoBank Account Service — main entry point.
 *
 * <p>{@code @SpringBootApplication} covers component scan, auto-config, and
 * {@code @EnableAutoConfiguration}. Don't add bean definitions here; use a
 * dedicated {@code @Configuration} class so startup stays clean.
 */
@SpringBootApplication
public class AccountServiceApplication {

	/**
	 * @param args forwarded to Spring — handy for profile activation or property
	 *             overrides at launch (e.g. {@code --server.port=9090})
	 */
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
