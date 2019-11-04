package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
		return listIterator();
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
		Runnable afterInsert = beforeInsert(payload);
		boolean result = list.add(payload);
		afterInsert.run();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object object) {
		if (contains(object)) {
			Runnable afterRemove = beforeRemove((Payload) object);
			boolean removed = list.remove(object);
			afterRemove.run();
			return removed;
		} else {
			return false;
		}
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
	public boolean removeAll(Collection<?> payloads) {
		Objects.requireNonNull(payloads);
		boolean result = false;
		Iterator<Payload> iterator = iterator();
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (payloads.contains(payload)) {
				iterator.remove();
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> payloads) {
		Objects.requireNonNull(payloads);
		boolean result = false;
		Iterator<Payload> iterator = iterator();
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (!payloads.contains(payload)) {
				iterator.remove();
				result = true;
			}
		}
		return result;
	}

	@Override
	public void clear() {
		retainAll(Collections.EMPTY_LIST);
	}

	@Override
	public Payload get(int index) {
		return list.get(index);
	}

	@Override
	public Payload set(int index, Payload payload) {
		Payload previousPayload = list.get(index);
		Runnable afterRemove = beforeRemove(previousPayload);
		Runnable afterInsert = beforeInsert(payload);
		list.set(index, payload);
		afterInsert.run();
		afterRemove.run();
		return previousPayload;
	}

	@Override
	public void add(int index, Payload payload) {
		Runnable afterInsert = beforeInsert(payload);
		list.add(index, payload);
		afterInsert.run();
	}

	@Override
	public Payload remove(int index) {
		Payload previousPayload = list.get(index);
		Runnable afterRemove = beforeRemove(previousPayload);
		list.remove(index);
		afterRemove.run();
		return previousPayload;
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
				Runnable afterRemove = beforeRemove(currentPayload);
				iterator.remove();
				currentPayload = null;
				afterRemove.run();
			}

			@Override
			public void set(Payload payload) {
				Runnable afterRemove = beforeRemove(currentPayload);
				Runnable afterInsert = beforeInsert(payload);
				iterator.set(payload);
				currentPayload = payload;
				afterInsert.run();
				afterRemove.run();
			}

			@Override
			public void add(Payload payload) {
				Runnable afterInsert = beforeInsert(payload);
				iterator.add(payload);
				afterInsert.run();
			}

		};
	}

	@Override
	public List<Payload> subList(int fromIndex, int toIndex) {
		return new AbstractObservableList<Payload>(list.subList(fromIndex, toIndex)) {

			@Override
			protected Runnable beforeInsert(Payload payload) {
				return AbstractObservableList.this.beforeInsert(payload);
			}

			@Override
			protected Runnable beforeRemove(Payload payload) {
				return AbstractObservableList.this.beforeRemove(payload);
			}
		};

	}

	@Override
	public boolean equals(Object object) {
		return list.equals(object);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	protected abstract Runnable beforeInsert(Payload payload);

	protected abstract Runnable beforeRemove(Payload payload);

}
