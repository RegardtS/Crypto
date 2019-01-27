package com.regi.crypto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ResourcesCompat.class)
public class ShadowResourcesCompat {

    @Implementation
    public static Typeface getFont(@NonNull Context context, @FontRes int id) {
        return Typeface.DEFAULT;
    }

    @Implementation
    public static Typeface getFont(@NonNull Context context, @FontRes int id, TypedValue value,
                                   int style, @Nullable ResourcesCompat.FontCallback fontCallback) throws Resources.NotFoundException {
        return Typeface.DEFAULT;
    }
}