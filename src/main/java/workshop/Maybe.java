package workshop;

import java.util.function.Function;

public class Maybe<T> {
	T _value;

	public Maybe(T value) {
		_value = value;
	}

	T get() {
		return _value;
	}

	public <V> Maybe<V> map(Function<T, V> transform) {
		if (_value == null) {
			return new Maybe<>(null);
		} else {
			V newValue = transform.apply(_value);
			return new Maybe<>(newValue);
		}
	}

	T getOrDefault(T defaultValue) {
		if (_value == null) {
			return defaultValue;
		} else {
			return _value;
		}
	}
}
