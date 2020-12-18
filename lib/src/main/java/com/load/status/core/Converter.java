package com.load.status.core;


import com.load.status.callback.Callback;

public interface Converter<T> {
   Class<?extends Callback> map(T t);
}
