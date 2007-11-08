package com.gnizr.core.web.action.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gnizr.core.search.OpenSearchDirectory;
import com.gnizr.core.search.OpenSearchService;
import com.gnizr.core.web.action.AbstractLoggedInUserAction;

public class ListSearchEngines extends AbstractLoggedInUserAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7674261654917938966L;
	private Logger logger = Logger.getLogger(ListSearchEngines.class);

	private OpenSearchDirectory openSearchDirectory;
	
	private List<OpenSearchService> services;

	public List<OpenSearchService> getServices() {
		return services;
	}

	public void setOpenSearchDirectory(OpenSearchDirectory openSearchDirectory) {
		this.openSearchDirectory = openSearchDirectory;
	}

	@Override
	protected boolean isStrictLoggedInUserMode() {		
		return false;
	}


	@Override
	protected String go() throws Exception {
		logger.debug("ListSearchEngine.go(): loggedInUser=" + getLoggedInUser());
		if(openSearchDirectory.getWebApplicationUrl() == null){
			String prefixUrl = getGnizrConfiguration().getWebApplicationUrl();
			openSearchDirectory.setWebApplicationUrl(prefixUrl);
		}
		openSearchDirectory.init();
		List<OpenSearchService> allServices = openSearchDirectory.getServices();
		services = new ArrayList<OpenSearchService>();
		if(getLoggedInUser() == null && allServices.isEmpty() == false){
			for(OpenSearchService srv : allServices){
				if(srv.isLoginRequired() == false){
					services.add(srv);
				}
			}			
		}else if (allServices.isEmpty() == false){
			services.addAll(allServices);
		}
		return SUCCESS;
	}

}
