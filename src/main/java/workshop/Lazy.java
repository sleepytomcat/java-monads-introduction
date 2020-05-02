package workshop;

import java.util.function.Function;
import java.util.function.Supplier;

public class Lazy<T> {
	Supplier<T> _valueSupplier;

	public Lazy(Supplier<T> valueSupplier) {
		_valueSupplier = valueSupplier;
	}

	T get() {
		return _valueSupplier.get(); // value evaluated right here
	}

	public <V> Lazy<V> map(Function<T, V> transform) {
		Supplier<V> newValueSupplier = () -> transform.apply(_valueSupplier.get());
		return new Lazy<>(newValueSupplier);
	}
}
