package org.landal.wines.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.landal.wines.model.Wine;

@Path("/wines")
@RequestScoped
public class WineService {

	private static Map<Long, Wine> wines = new ConcurrentHashMap<Long, Wine>();
	private static Long idGenerator = 0L;

	static {
		Wine wine = new Wine(idGenerator, "san giovese", 12);
		wines.put(wine.getId(), wine);
		wine = new Wine(++idGenerator, "chianti riserva", 14);
		wines.put(wine.getId(), wine);
		wine = new Wine(++idGenerator, "chianti classico", 14);
		wines.put(wine.getId(), wine);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Wine> getAll() {
		ArrayList<Wine> result = new ArrayList<>(wines.values());
		Collections.sort(result, new Comparator<Wine>() {

			@Override
			public int compare(Wine o1, Wine o2) {				
				return o1.getId().compareTo(o2.getId());
			}
		});
		
		return result;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Wine getById(@PathParam("id") Long id) {
		return wines.get(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Wine save(Wine wine) {
		synchronized (idGenerator) {
			wine.setId(++idGenerator);
		}
		wines.put(wine.getId(), wine);
		return wine;
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Wine update(Wine wine) {
		wines.put(wine.getId(), wine);
		return wine;
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		wines.remove(id);
	}

}
