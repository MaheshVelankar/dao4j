package it.mengoni.persistence.dto;

import java.io.Serializable;

import org.javatuples.Tuple;

public interface PersistentObject extends Serializable{

	public boolean isNew();

	public void saved();

	public void deleted();

	public boolean isDeleted();

	public boolean isKeyAssigned();

	public boolean isEdit();

	public void setEdit(boolean edit);

	public Tuple getKey();

	public void setKey(Tuple key);

	public String getDisplayLabel();

	public void beforeSave();

	public void beforeDelete();

	public void beforeInsert();

	public void reload();

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

		@Override
		public boolean isEdit() {
			return false;
		}

		@Override
		public void setEdit(boolean edit) {
		}

		@Override
		public void beforeSave() {

		}

		@Override
		public void beforeDelete() {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeInsert() {
			// TODO Auto-generated method stub

		}

		@Override
		public void setKey(Tuple key) {
			// TODO Auto-generated method stub

		}

		@Override
		public void reload() {
			// TODO Auto-generated method stub

		}
	};
}