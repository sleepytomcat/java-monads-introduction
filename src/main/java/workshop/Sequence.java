package workshop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class Sequence<T> {
	Collection<T> _values;

	public Sequence(Collection<T> values) {
		_values = values;
	}

	Collection<T> get() {
		return _values;
	}

	public <V> Sequence<V> map(Function<T, V> transform) {
		Collection<V> newValues = new ArrayList<>();

		for (T oldValue: _values) {
			V newValue = transform.apply(oldValue);
			newValues.add(newValue);
		}

		return new Sequence<>(newValues);
	}

	public Sequence<T> filter(Predicate<T> predicate) {
		Collection<T> newValues = new ArrayList<>();

		for (T oldValue: _values) {
			if (predicate.test(oldValue)) {
				newValues.add(oldValue);
			}
		}

		return new Sequence<>(newValues);
	}

	public Sequence<T> distinct() {
		Collection<T> newValues = new HashSet<>(_values);
		return new Sequence<>(newValues);
	}
}
