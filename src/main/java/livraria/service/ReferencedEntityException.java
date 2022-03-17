package livraria.service;

import livraria.service.ServiceException;

public class ReferencedEntityException extends ServiceException {

	public ReferencedEntityException() {
	}

	public ReferencedEntityException(String message) {
		super(message);
	}

	public ReferencedEntityException(Throwable cause) {
		super(cause);
	}

	public ReferencedEntityException(String message, Throwable cause) {
		super(message, cause);
	}
}
