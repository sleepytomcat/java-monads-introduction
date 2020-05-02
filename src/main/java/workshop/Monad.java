package workshop;

import java.util.function.Function;

public class Monad<T> {
	T _value;

	public Monad(T value) {
		_value = value;
	}

	T get() {
		return _value;
	}

	public <V> Monad<V> map(Function<T, V> transform) {
		V newValue = transform.apply(_value);
		return new Monad<>(newValue);
	}
}
