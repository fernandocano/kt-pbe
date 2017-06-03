/*******************************************************************************
 * Copyright (C) 2017 mizuuenikaze inc.
 *  
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *******************************************************************************/
package com.muk.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muk.secuity.impl.DefaultKeystoreService;
import com.muk.security.KeystoreService;

public class MukMain {
	private static final Logger LOG = LoggerFactory.getLogger(MukMain.class);

	public static void main (String[] args) {
		Map<String, String> ops = new HashMap<String, String>();
		
		for (int i=0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				switch(args[i]) {
				case "-keystore":
					ops.put("keystore", args[i+1]);
					break;
				case "-password":
					ops.put("password", args[i+1]);
					break;
				case "-alias":
					ops.put("alias", args[i+i]);
					break;
				case "-secret":
					ops.put("secret", args[i+1]);
					break;
				default:
					LOG.error("Unknown option {}", args[i]);
					return;
				}
				
				i++;
			}
		}
		
		
		DefaultKeystoreService ks = new DefaultKeystoreService();
		
		ks.setKeystore(Paths.get(ops.get("keystore")));
		ks.setKeystorePass(ops.get("password"));
		
		try {
			ks.addPBEKey(ops.get("alias"), ops.get("secret"));
			
			LOG.info("Entry");
			LOG.info(ks.getPBEKey(ops.get("alias")));
		} catch (IOException ioEx) {
			LOG.error("Failed to access file.", ioEx);
		} catch (GeneralSecurityException genEx) {
			LOG.error("Failed keystore security.", genEx);
		}
	}
}
