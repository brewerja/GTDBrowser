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
	public static final int INCREMENT = 50;
	public static final String SERVER_URI = "http://23.21.214.235";
	public static final String API_PATH = "/api/v1";

	public static final String WS_COUNTRY_LIST_URL = SERVER_URI + API_PATH + "/countries/?order_by=-num_attacks&limit="
			+ INCREMENT;
	public static final String WS_REGION_LIST_URL = SERVER_URI + API_PATH + "/regions/?order_by=-num_attacks&limit="
			+ INCREMENT;
	public static final String WS_ATTACKTYPE_LIST_URL = SERVER_URI + API_PATH
			+ "/attacktypes/?order_by=-num_attacks&limit=" + INCREMENT;
	public static final String WS_TARGETTYPE_LIST_URL = SERVER_URI + API_PATH
			+ "/targettypes/?order_by=-num_attacks&limit=" + INCREMENT;
	public static final String WS_WEAPONTYPE_LIST_URL = SERVER_URI + API_PATH
			+ "/weapontypes/?order_by=-num_attacks&limit=" + INCREMENT;
	public static final String WS_DBSOURCE_LIST_URL = SERVER_URI + API_PATH
			+ "/dbsources/?order_by=-num_attacks&limit=" + INCREMENT;
	public static final String WS_YEAR_LIST_URL = SERVER_URI + API_PATH
			+ "/years/?order_by=name&limit=" + INCREMENT;
	public static final String WS_ATTACK_LIST_URL = SERVER_URI + API_PATH + "/attacks/?order_by=-id&limit=" + INCREMENT;
}
