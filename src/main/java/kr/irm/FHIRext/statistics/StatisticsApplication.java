package kr.irm.FHIRext.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class StatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}

}
