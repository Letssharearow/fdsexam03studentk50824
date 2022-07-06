package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.AbstractResourceRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.Assert.*;

public abstract class CachingTestHelper<Model extends AbstractModel, Client extends AbstractResourceRestClient<Model>>
	extends AbstractTest<Model, Client>
{

	@Before public void emptyCache()
	{
		File file = new File("src/main/nginx-1.22.0/nginx"); //file to be delete
		try
		{
			if (file.exists())
				deleteDirectoryStream(file.toPath());
			System.out.println("Deleting Cache for testing");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//cd .\src\main\nginx-1.20.2\
	//start nginx
	//tasklist /fi "imagename eq nginx.exe"
	//nginx -s quit

	void deleteDirectoryStream(Path path) throws IOException
	{
		Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

	public void checkForNoCaching(String url) throws IOException
	{
		RestApiResponse<Model> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), url);
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), url);
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	protected void testConditionalPut204() throws IOException
	{
		//user A loads a student
		final RestApiResponse<Model> responseFromGetRequest = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 1);
		final Model student = responseFromGetRequest.getResponseSingleData();

		assertHeaderExists(responseFromGetRequest, ETAG);

		final String initialEtag = responseFromGetRequest.getEtagHeader();

		//user A updates this resource
		changeRessource(student, "Pappa");

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserA.addHeader(IF_MATCH, initialEtag);

		final RestApiResponse<Model> responseFromPutRequest = putRequest(headersForPutRequestForUserA, student);

		assertEquals(204, responseFromPutRequest.getLastStatusCode());

		assertHeaderExists(responseFromPutRequest, ETAG);
	}

	protected void testConditionalPut412() throws IOException
	{
		//user A loads a student
		final RestApiResponse<Model> responseFromGetRequestForUserA = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);
		final Model studentForUserA = responseFromGetRequestForUserA.getResponseSingleData();

		assertHeaderExists(responseFromGetRequestForUserA, ETAG);

		final String initialEtagForUserA = responseFromGetRequestForUserA.getEtagHeader();

		//user B loads the same student
		final RestApiResponse<Model> responseFromGetRequestForUserB = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);
		final Model studentForUserB = responseFromGetRequestForUserB.getResponseSingleData();

		assertHeaderExists(responseFromGetRequestForUserB, ETAG);

		final String initialEtagForUserB = responseFromGetRequestForUserB.getEtagHeader();

		//user B updates this resource
		changeRessource(studentForUserB, "Robert");

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserB.addHeader(IF_MATCH, initialEtagForUserB);

		final RestApiResponse<Model> responseFromPutRequestForUserB = putRequest(headersForPutRequestForUserB,
			studentForUserB);

		assertEquals(204, responseFromPutRequestForUserB.getLastStatusCode());

		final String newEtagForUserB = responseFromPutRequestForUserB.getEtagHeader();

		//user A updates this resource
		changeRessource(studentForUserB, "Klaus");

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserA.addHeader(IF_MATCH, initialEtagForUserA);

		final RestApiResponse<Model> responseFromPutRequestForUserA = putRequest(headersForPutRequestForUserA,
			studentForUserA);

		assertEquals(412, responseFromPutRequestForUserA.getLastStatusCode());
	}

	protected void testConditionalGet304() throws IOException
	{
		//user A loads a studyTrip
		final RestApiResponse<Model> responseFromFirstGetRequest = getSingleRequestById(HeaderMapUtils.withAcceptJson(),
			1);

		assertHeaderExists(responseFromFirstGetRequest, ETAG);

		final String etag = responseFromFirstGetRequest.getEtagHeader();

		//user A revalidates
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson();
		headersForSecondGetRequest.addHeader(IF_NONE_MATCH, etag);

		final RestApiResponse<Model> responseFromSecondGetRequest = getSingleRequestById(headersForSecondGetRequest, 1);

		assertEquals(304, responseFromSecondGetRequest.getLastStatusCode());
	}

	protected void testConditionalGet200() throws IOException
	{
		//user A loads a studyTrip
		final RestApiResponse<Model> responseFromFirstGetRequest = getSingleRequestById(HeaderMapUtils.withAcceptJson(),
			1);
		final Model studyTrip = responseFromFirstGetRequest.getResponseSingleData();

		assertHeaderExists(responseFromFirstGetRequest, ETAG);

		final String initialEtag = responseFromFirstGetRequest.getEtagHeader();

		//user B updates this resource
		changeRessource(studyTrip, "Munich2");

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserB.addHeader(IF_MATCH, initialEtag);

		final RestApiResponse<Model> responseFromPutRequest = putRequest(headersForPutRequestForUserB, studyTrip);

		assertEquals(204, responseFromPutRequest.getLastStatusCode());

		final String newEtag = responseFromPutRequest.getEtagHeader();

		//user A revalidates using old etag
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson();
		headersForSecondGetRequest.addHeader(IF_NONE_MATCH, initialEtag);

		final RestApiResponse<Model> responseFromSecondGetRequest = getSingleRequestById(headersForSecondGetRequest, 1);

		assertEquals(200, responseFromSecondGetRequest.getLastStatusCode());
		assertNotNull(responseFromSecondGetRequest.getResponseSingleData());
	}

	public abstract Model changeRessource(Model ressource, String newData);

	public static void main(String[] args)
	{
		CachingTestHelper helper = new CachingTestHelper()
		{
			@Override public AbstractModel changeRessource(AbstractModel ressource, String newData)
			{
				return null;
			}

			@Override protected AbstractResourceRestClient newRestClient(HeaderMap headers)
			{
				return null;
			}
		};
		helper.emptyCache();
	}
}