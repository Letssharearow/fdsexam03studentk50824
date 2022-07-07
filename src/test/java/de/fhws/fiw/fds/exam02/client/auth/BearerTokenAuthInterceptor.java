package de.fhws.fiw.fds.exam02.client.auth;

import okhttp3.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public class BearerTokenAuthInterceptor implements Interceptor
{
	private String credentials;

	public BearerTokenAuthInterceptor(final String userName, final String password)
	{
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password))
		{
			try
			{
				this.credentials = "bearer " + getAdminToken(userName, password);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			this.credentials = "";
		}
	}

	public String getAdminToken(String userName, String password) throws IOException
	{
		OkHttpClient build = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(userName, password))
			.build();
		final Request request = new Request.Builder().url(("http://localhost:8080/exam02/api/me")).get().build();
		Response response = build.newCall(request).execute();

		return response.body().string();
	}

	@Override public Response intercept(final Chain chain) throws IOException
	{
		if (StringUtils.isNotEmpty(this.credentials))
		{
			final Request request = chain.request();
			final Request authenticatedRequest = request.newBuilder().header("Authorization", credentials).build();
			return chain.proceed(authenticatedRequest);
		}
		else
		{
			return chain.proceed(chain.request());
		}
	}
}
