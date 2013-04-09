package it.mengoni.persistence.dto;

import java.io.Serializable;

import org.javatuples.Tuple;

public interface PersistentObject extends Serializable{

	public boolean isNew();

	public void saved();

	public void deleted();

	public boolean isDeleted();

	public boolean isKeyAssigned();

	public Tuple getKey();

	public String getDisplayLabel();

	public PersistentObject NULL_OBJECT = new PersistentObject() {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void saved() {
		}

		@Override
		public boolean isNew() {
			return false;
		}

		@Override
		public boolean isDeleted() {
			return false;
		}

		@Override
		public Tuple getKey() {
			return null;
		}

		@Override
		public String getDisplayLabel() {
			return "";
		}

		@Override
		public void deleted() {
		}

		@Override
		public boolean isKeyAssigned() {
			return true;
		}
	};
}