package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.resources.AbstractResourceRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public abstract class CachingTestHelper<Model extends AbstractModel, Client extends AbstractResourceRestClient<Model>> extends AbstractTest<Model, Client>
{


	@Before
	public void emptyCache(){
			File file= new File("src/main/nginx-1.20.2/nginx"); //file to be delete
		try
		{
			if(file.exists())
			deleteDirectoryStream(file.toPath());
			System.out.println( "Deleting Cache for testing" );
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

	void deleteDirectoryStream(Path path) throws IOException {
		Files.walk(path)
			.sorted(Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
	}

	public static void main(String[] args)
	{
		CachingTestHelper helper = new CachingTestHelper()
		{
			@Override protected AbstractResourceRestClient newRestClient(HeaderMap headers)
			{
				return null;
			}
		};
		helper.emptyCache();
	}
}