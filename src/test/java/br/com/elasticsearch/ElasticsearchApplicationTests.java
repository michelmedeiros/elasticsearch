package br.com.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.elasticsearch.main.ElasticsearchApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ElasticsearchApplication.class)
@WebAppConfiguration
public class ElasticsearchApplicationTests {

	@Test
	public void contextLoads() {
	}

}
