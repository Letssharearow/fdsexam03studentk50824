package de.fhws.fiw.fds.exam02.api.states;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MyKeyGenerator
{
	private static MyKeyGenerator INSTANCE;

	public static MyKeyGenerator getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new MyKeyGenerator();
		}

		return INSTANCE;
	}

	private Key key;

	private MyKeyGenerator()
	{
		try
		{
			final SecureRandom rand = new SecureRandom();
			final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256, rand);
			this.key = keyGen.generateKey();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			this.key = null;
		}
	}

	public Key getKey()
	{
		return this.key;
	}

	public static void main(String[] args)
	{
		try
		{
			final SecureRandom rand = new SecureRandom();
			final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			final KeyGenerator keyGen2 = KeyGenerator.getInstance("AES");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
}
