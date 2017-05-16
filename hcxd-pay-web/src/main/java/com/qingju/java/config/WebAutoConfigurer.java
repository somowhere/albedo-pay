package com.qingju.java.config;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.albedo.java.util.domain.Globals;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebAutoConfigurer implements EmbeddedServletContainerCustomizer {

	private final Logger log = LoggerFactory.getLogger(com.albedo.java.common.config.WebConfigurer.class);
	@Autowired
	private Environment env;

	/**
	 * Customize the Servlet engine: Mime types, the document root, the cache.
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		// IE issue, see https://github.com/albedo/generator-albedo/pull/711
		mappings.add("html", "text/html;charset=utf-8");
		// CloudFoundry issue, see
		// https://github.com/cloudfoundry/gorouter/issues/64
		mappings.add("json", "text/html;charset=utf-8");
		container.setMimeMappings(mappings);
		// When running in an IDE or with ./mvnw spring-boot:run, set location
		// of the static web assets.
		setLocationForStaticAssets(container);
	}

	private void setLocationForStaticAssets(ConfigurableEmbeddedServletContainer container) {
		File root;
		String prefixPath = resolvePathPrefix();
		if (env.acceptsProfiles(Globals.SPRING_PROFILE_PRODUCTION)) {
			root = new File(prefixPath + "target/www/");
		} else {
			root = new File(prefixPath + "src/main/webapp/");
		}
		if (root.exists() && root.isDirectory()) {
			log.info("root {}", root.getAbsolutePath());
			container.setDocumentRoot(root);
		}
	}

	/**
	 * Resolve path prefix to static resources.
	 */
	private String resolvePathPrefix() {
		String fullExecutablePath = this.getClass().getResource("").getPath();
		String rootPath = Paths.get(".").toUri().normalize().getPath();
		String extractedPath = fullExecutablePath.replace(rootPath, "");
		int extractionEndIndex = extractedPath.indexOf("target/");
		if (extractionEndIndex <= 0) {
			return "";
		}
		return extractedPath.substring(0, extractionEndIndex);
	}


}
