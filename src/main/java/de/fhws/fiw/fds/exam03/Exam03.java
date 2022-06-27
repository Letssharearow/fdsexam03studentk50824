/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.exam03;

import de.fhws.fiw.fds.sutton.server.api.AbstractApplication;

import java.util.HashSet;
import java.util.Set;

//@ApplicationPath( "api" )
public class Exam03 extends AbstractApplication
{
	@Override protected Set<Class<?>> getServiceClasses( )
	{
		final Set<Class<?>> returnValue = new HashSet<>( );

		/* TODO: Add your service classes here */

		return returnValue;
	}
}
