package cmuche.fritzbox_info_display.configuration;

import cmuche.fritzbox_info_display.model.Credentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CredentialsConfiguration {

	@Bean
	public Credentials credentials(Environment env) {
		String url = env.getProperty("fritz.url", "fritz.box");
		String username = env.getRequiredProperty("fritz.username");
		String password = env.getRequiredProperty("fritz.password");
		return new Credentials(url, username, password);
	}
}
