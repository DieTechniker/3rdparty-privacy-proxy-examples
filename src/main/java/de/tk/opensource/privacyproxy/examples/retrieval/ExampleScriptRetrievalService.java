/*--- (C) 1999-2019 Techniker Krankenkasse ---*/

package de.tk.opensource.privacyproxy.examples.retrieval;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.tk.opensource.privacyproxy.config.RetrievalEndpoint;
import de.tk.opensource.privacyproxy.retrieval.AssetRetrievalService;

/**
 * This is an example implementation of the AssetRetrievalService. Please implement your own
 * DeliveryControllers per provider and asset type. Download URL for this example:
 * http://localhost:2907/example/jquery.js
 */
@Component
@ConditionalOnProperty("example.scripts.update")
@ConfigurationProperties(prefix = "example.scripts")
public class ExampleScriptRetrievalService extends AssetRetrievalService {

	private List<RetrievalEndpoint> endpoints;

	public List<RetrievalEndpoint> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<RetrievalEndpoint> endpoints) {
		this.endpoints = endpoints;
	}

	/**
	 * Downloads the Example Scripts into the configured folder. This method is scheduled and evicts
	 * the script cache.
	 */
	@CacheEvict(
		cacheNames = "js",
		allEntries = true
	)
	@Override
	@Scheduled(cron = "${example.scripts.interval}")
	public void updateAssets() {
		updateAssetsInternal("example", endpoints);
	}
}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
