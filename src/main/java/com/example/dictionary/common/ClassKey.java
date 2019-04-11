package com.example.dictionary.common;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
public class ClassKey implements Comparable<ClassKey>, Serializable {

    private final Class<?> _class;

    public ClassKey(Class<?> _class) {
        this._class = _class;
    }

    public Class<?> get_class() {
        return _class;
    }

    @Override
    public int compareTo(@NotNull ClassKey o) {
        return _class.getName().compareTo(o._class.getName());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        return this._class == ((ClassKey) o)._class;
    }

    @Override
    public String toString() {
        return this._class.getName();
    }
}
