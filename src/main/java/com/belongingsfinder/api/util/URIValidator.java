package com.belongingsfinder.api.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author finbarr
 * 
 */
public final class URIValidator {

	private URIValidator() {
	}

	public static boolean isValid(String uri) {
		try {
			new URI(uri);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}

}
