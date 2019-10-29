package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

abstract class AbstractObservableList<Payload> implements List<Payload> {

	private static final int INITIAL_CAPACITY = 8;

	private final List<Payload> list;

	public AbstractObservableList() {
		this(new ArrayList<>(INITIAL_CAPACITY));
	}

	private AbstractObservableList(List<Payload> list) {
		this.list = list;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object object) {
		return list.contains(object);
	}

	@Override
	public Iterator<Payload> iterator() {
		return new Iterator<Payload>() {

			private final Iterator<Payload> iterator = list.iterator();

			private Payload currentPayload;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Payload next() {
				currentPayload = iterator.next();
				return currentPayload;
			}

			@Override
			public void remove() {
				iterator.remove();
				onRemove(currentPayload);
			}

		};
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <Element> Element[] toArray(Element[] array) {
		return list.toArray(array);
	}

	@Override
	public boolean add(Payload payload) {
		beforeInsert(payload);
		boolean result = list.add(payload);
		onInsert(payload);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object object) {
		boolean removed = list.remove(object);
		if (removed) {
			onRemove((Payload) object);
		}
		return removed;
	}

	@Override
	public boolean containsAll(Collection<?> payloads) {
		return list.contains(payloads);
	}

	@Override
	public boolean addAll(Collection<? extends Payload> payloads) {
		return addAll(size(), payloads);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Payload> payloads) {
		for (Payload payload : payloads) {
			add(index, payload);
		}
		return !payloads.isEmpty();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> payloads) {
		Objects.requireNonNull(payloads);
		boolean result = false;
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (payloads.contains(object)) {
				iterator.remove();
				onRemove((Payload) object);
				result = true;
			}
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean retainAll(Collection<?> payloads) {
		Objects.requireNonNull(payloads);
		boolean result = false;
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (!payloads.contains(object)) {
				iterator.remove();
				onRemove((Payload) object);
				result = true;
			}
		}
		return result;
	}

	@Override
	public void clear() {
		for (Payload payload : list) {
			onRemove(payload);
		}
		list.clear();
	}

	@Override
	public Payload get(int index) {
		return list.get(index);
	}

	@Override
	public Payload set(int index, Payload payload) {
		beforeInsert(payload);
		Payload previousPayload = list.set(index, payload);
		onRemove(previousPayload);
		onInsert(payload);
		return previousPayload;
	}

	@Override
	public void add(int index, Payload payload) {
		beforeInsert(payload);
		list.add(index, payload);
		onInsert(payload);
	}

	@Override
	public Payload remove(int index) {
		Payload payload = list.remove(index);
		onRemove(payload);
		return payload;
	}

	@Override
	public int indexOf(Object payload) {
		return list.indexOf(payload);
	}

	@Override
	public int lastIndexOf(Object payload) {
		return list.lastIndexOf(payload);

	}

	@Override
	public ListIterator<Payload> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<Payload> listIterator(int index) {
		return new ListIterator<Payload>() {

			private final ListIterator<Payload> iterator = list.listIterator(index);

			private Payload currentPayload;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Payload next() {
				currentPayload = iterator.next();
				return currentPayload;
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}

			@Override
			public Payload previous() {
				currentPayload = previous();
				return currentPayload;
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void remove() {
				iterator.remove();
				onRemove(currentPayload);
			}

			@Override
			public void set(Payload payload) {
				beforeInsert(payload);
				iterator.set(payload);
				onRemove(currentPayload);
				onInsert(payload);
			}

			@Override
			public void add(Payload payload) {
				beforeInsert(payload);
				iterator.add(payload);
				onInsert(payload);
			}

		};
	}

	@Override
	public List<Payload> subList(int fromIndex, int toIndex) {
		return new AbstractObservableList<Payload>(list.subList(fromIndex, toIndex)) {

			@Override
			protected void beforeInsert(Payload payload) {
				AbstractObservableList.this.beforeInsert(payload);
			}

			@Override
			protected void onInsert(Payload payload) {
				AbstractObservableList.this.onInsert(payload);
			}

			@Override
			protected void onRemove(Payload payload) {
				AbstractObservableList.this.onRemove(payload);
			}
		};

	}

	protected abstract void beforeInsert(Payload payload);

	protected abstract void onInsert(Payload payload);

	protected abstract void onRemove(Payload payload);

}
