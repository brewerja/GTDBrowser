/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.config;

public class WSConfig {
	public static final int INCREMENT = 40;
	public static final String WS_COUNTRY_LIST_URL = "http://23.21.214.235/api/v1/countries/?order_by=-num_attacks&limit="
			+ INCREMENT;
	public static final String WS_REGION_LIST_URL = "http://23.21.214.235/api/v1/regions/?order_by=-num_attacks&limit="
			+ INCREMENT;
	public static final String WS_ATTACK_LIST_URL = "http://23.21.214.235/api/v1/attacks/";
}
