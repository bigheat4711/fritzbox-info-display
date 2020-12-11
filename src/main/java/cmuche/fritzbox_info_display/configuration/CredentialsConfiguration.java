package cmuche.fritzbox_info_display.configuration;

import cmuche.fritzbox_info_display.model.Credentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CredentialsConfiguration {

	@Bean
	public Credentials credentials(Environment env) {
		String ip = env.getRequiredProperty("ip");
		String username = env.getRequiredProperty("credentials.username");
		String password = env.getRequiredProperty("credentials.password");
		return new Credentials(ip, username, password);
	}
}
