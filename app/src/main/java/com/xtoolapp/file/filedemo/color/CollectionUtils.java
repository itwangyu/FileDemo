package com.xtoolapp.file.filedemo.color;

import androidx.annotation.Nullable;

import java.util.Collection;

/**
 * Created by banana on 2019/6/13.
 */
public class CollectionUtils {

    public static boolean isEmpty(@Nullable Collection<?> var0) {
        return var0 == null || var0.isEmpty();
    }
}
