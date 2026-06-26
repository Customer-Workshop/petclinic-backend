/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link CacheConfiguration}
 */
@ExtendWith(MockitoExtension.class)
class CacheConfigurationTests {

	@Mock
	private CacheManager cacheManager;

	@Test
	void shouldCreateVetsCacheViaCustomizer() {
		CacheConfiguration config = new CacheConfiguration();
		JCacheManagerCustomizer customizer = config.petclinicCacheConfigurationCustomizer();
		assertThat(customizer).isNotNull();

		customizer.customize(cacheManager);

		ArgumentCaptor<Configuration> configCaptor = ArgumentCaptor.forClass(Configuration.class);
		verify(cacheManager).createCache(eq("vets"), configCaptor.capture());
		assertThat(configCaptor.getValue()).isNotNull();
	}

}
