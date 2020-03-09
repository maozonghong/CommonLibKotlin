package com.mao.library.abs;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A generic ViewHolder that works with a {@link ViewDataBinding}.
 * @param <T> The type of the ViewDataBinding.
 */
public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T binding;
    public DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}