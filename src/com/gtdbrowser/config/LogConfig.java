/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.config;

@SuppressWarnings("all")
public class LogConfig {

	private LogConfig() {
	}

	/**
	 * Log Level variables
	 */
	private static final int DDP_LOG_LEVEL_DEBUG = 4;
	private static final int DDP_LOG_LEVEL_INFO = 3;
	private static final int DDP_LOG_LEVEL_WARNING = 2;
	private static final int DDP_LOG_LEVEL_ERROR = 1;
	private static final int DDP_LOG_LEVEL_NONE = 0;

	/**
	 * Set this flag to {@link LogConfig#DDP_LOG_LEVEL_NONE} when releasing your
	 * application in order to remove all logs generated by DataDroid PoC.
	 */
	private static final int DDP_LOG_LEVEL = DDP_LOG_LEVEL_DEBUG;

	public static final boolean DDP_DEBUG_LOGS_ENABLED = (DDP_LOG_LEVEL == DDP_LOG_LEVEL_DEBUG);
	public static final boolean DDP_INFO_LOGS_ENABLED = DDP_DEBUG_LOGS_ENABLED || (DDP_LOG_LEVEL == DDP_LOG_LEVEL_INFO);
	public static final boolean DDP_WARNING_LOGS_ENABLED = DDP_INFO_LOGS_ENABLED
			|| (DDP_LOG_LEVEL == DDP_LOG_LEVEL_WARNING);
	public static final boolean DDP_ERROR_LOGS_ENABLED = DDP_WARNING_LOGS_ENABLED
			|| (DDP_LOG_LEVEL == DDP_LOG_LEVEL_ERROR);
}
