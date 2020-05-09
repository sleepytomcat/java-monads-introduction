package workshop;

import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
interface FunctionUnchecked<U, V> {
	V apply(U value) throws Throwable;
}

public class Try<T> {
	T _value;
	Throwable _exception;

	public Try(T value) {
		_value = value;
		_exception = null;
	}

	public Try(Throwable exception) {
		_value = null;
		_exception = exception;
	}

	T get() throws Throwable {
		if (_exception == null) {
			return _value;
		} else {
			throw _exception;
		}
	}

	public <V> Try<V> map(FunctionUnchecked<T, V> transform) {
		if (_exception != null) {
			return new Try<>(_exception);
		}

		try {
			V newValue = transform.apply(_value);
            return new Try<>(newValue);
		} catch (Throwable ex) {
			return new Try<>(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public <E extends Throwable> Try<T> recover(Class<E> ex, Function<E, T> recovery) {
		if (ex.isInstance(_exception)) {
			T recoveredValue = recovery.apply((E)_exception);
			return new Try<>(recoveredValue);
		} else {
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	public <E extends Throwable> Try<T> onFailure(Class<E> ex, Consumer<E> onFailure) {
		if (ex.isInstance(_exception)) {
			onFailure.accept((E) _exception);
		}

		return this;
	}
}
