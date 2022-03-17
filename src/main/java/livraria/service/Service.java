package livraria.service;

import livraria.dao.DAOFactory;

public abstract class Service {
	
	protected ServiceFactory serviceFactory;
	protected DAOFactory daoFactory;
	
	protected Service() {
		this.serviceFactory = ServiceFactory.getInstance();
		this.daoFactory = DAOFactory.getInstance();
	}
}
