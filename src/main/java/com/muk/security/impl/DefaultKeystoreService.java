/*******************************************************************************
 * Copyright (C) 2017 mizuuenikaze inc.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *******************************************************************************/
package com.muk.security.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.muk.security.KeystoreService;

/**
 * A jce keystore gets and sets sensitive values in an arbitrary keystore.
 *
 */
public class DefaultKeystoreService implements KeystoreService {
	private Path keystore;
	private String keystorePass;

	@Override
	public String getPBEKey(String alias) throws KeyStoreException, CertificateException, InvalidKeySpecException,
			NoSuchAlgorithmException, UnrecoverableEntryException, IOException {
		final KeyStore ks = KeyStore.getInstance("JCEKS");

		InputStream ksIn = null;

		try {
			ksIn = Files.newInputStream(keystore, StandardOpenOption.READ);
			ks.load(ksIn, keystorePass.toCharArray());
		} finally {
			if (ksIn != null) {
				ksIn.close();
			}
		}

		final KeyStore.PasswordProtection entryPassword = new KeyStore.PasswordProtection(keystorePass.toCharArray());

		final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");

		final KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) ks.getEntry(alias, entryPassword);

		final PBEKeySpec keySpec = (PBEKeySpec) factory.getKeySpec(secretKeyEntry.getSecretKey(), PBEKeySpec.class);

		return new String(keySpec.getPassword());
	}

	@Override
	public void addPBEKey(String alias, String sensitiveValue) throws KeyStoreException, CertificateException,
			InvalidKeySpecException, NoSuchAlgorithmException, IOException {

		addPBEKeyInternal(alias, sensitiveValue.toCharArray());
	}

	@Override
	public void addPBEKey(String alias, Path keyFile) throws KeyStoreException, CertificateException,
			InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		String privateKey = new String(Files.readAllBytes(keyFile), StandardCharsets.UTF_8);
		privateKey = privateKey.replace("\n", "\\n");
		addPBEKeyInternal(alias, privateKey.toCharArray());

	}

	private void addPBEKeyInternal(String alias, char[] sensitiveValue) throws KeyStoreException, CertificateException,
			InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
		final SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(sensitiveValue));

		final KeyStore ks = KeyStore.getInstance("JCEKS");

		InputStream ksIn = null;

		try {
			ksIn = Files.newInputStream(keystore, StandardOpenOption.READ);
			ks.load(ksIn, keystorePass.toCharArray());
		} finally {
			if (ksIn != null) {
				ksIn.close();
			}
		}

		final KeyStore.PasswordProtection entryPassword = new KeyStore.PasswordProtection(keystorePass.toCharArray());

		ks.setEntry(alias, new KeyStore.SecretKeyEntry(generatedSecret), entryPassword);

		final OutputStream ksOut = Files.newOutputStream(keystore, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);

		try {
			ks.store(ksOut, keystorePass.toCharArray());
		} finally {
			if (ksOut != null) {
				ksOut.close();
			}
		}
	}

	public void setKeystore(Path keystore) {
		this.keystore = keystore;
	}

	public void setKeystorePass(String keystorePass) {
		this.keystorePass = keystorePass;
	}
}
