/*******************************************************************************
 * Copyright (C) 2017 mizuuenikaze inc.
 *  
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *******************************************************************************/
package com.muk.security;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * Works with a keystore to store random sensitive data.
 *
 */
public interface KeystoreService {
	String getPBEKey(String alias) throws KeyStoreException, CertificateException, NoSuchAlgorithmException,
			UnrecoverableEntryException, InvalidKeySpecException, IOException;

	void addPBEKey(String alias, String sensitiveValue) throws KeyStoreException, CertificateException,
			InvalidKeySpecException, NoSuchAlgorithmException, IOException;
}
