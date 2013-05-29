package org.seerc.seqos.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.seerc.seqos.business.StatusBusiness;
import org.seerc.seqos.business.StatusBusinessImpl;
import org.seerc.seqos.to.ListObservationTO;
import org.seerc.seqos.utils.ApplicationContextLocator;

@Path("/trust")
public class StatusRESTServiceImpl implements Status {

	StatusBusiness business = new StatusBusinessImpl();
	
	public StatusRESTServiceImpl(){
		this.business = (StatusBusiness) 
				ApplicationContextLocator.getApplicationContext().getBean(StatusBusiness.class.getSimpleName());
	}
	@GET
	@Path("status")
	@ProduceMime({"text/plain", "application/xml", "application/json"})
	public ListObservationTO status() {
		try{
			return business.status();
		}catch(Exception e){
			 throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

}
