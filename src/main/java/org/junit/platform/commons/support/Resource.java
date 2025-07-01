/*
 * Copyright 2015-2025 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.commons.support;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Predicate;

import org.apiguardian.api.API;

/**
 * {@code Resource} represents a resource on the classpath.
 *
 * @since 1.11
 * @see ReflectionSupport#findAllResourcesInClasspathRoot(URI, Predicate)
 * @see ReflectionSupport#findAllResourcesInPackage(String, Predicate)
 * @see ReflectionSupport#findAllResourcesInModule(String, Predicate)
 * @see ReflectionSupport#streamAllResourcesInClasspathRoot(URI, Predicate)
 * @see ReflectionSupport#streamAllResourcesInPackage(String, Predicate)
 * @see ReflectionSupport#streamAllResourcesInModule(String, Predicate)
 */
@API(status = EXPERIMENTAL, since = "1.11")
public interface Resource {

	/**
	 * Get the name of this resource.
	 *
	 * <p>The resource name is a {@code /}-separated path. The path is relative
	 * to the classpath root in which the resource is located.
	 *
	 * @return the resource name; never {@code null}
	 */
	String getName();

	/**
	 * Get the URI of this resource.
	 *
	 * @return the uri of the resource; never {@code null}
	 */
	URI getUri();

	/**
	 * Get an {@link InputStream} for reading this resource.
	 *
	 * <p>The default implementation delegates to {@link java.net.URL#openStream()}
	 * for this resource's {@link #getUri() URI}.
	 *
	 * @return an input stream for this resource; never {@code null}
	 * @throws IOException if an I/O exception occurs
	 */
	default InputStream getInputStream() throws IOException {
		return getUri().toURL().openStream();
	}

}
