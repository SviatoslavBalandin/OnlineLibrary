package ru.startandroid.onlinelibrary.di;

import dagger.Subcomponent;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchActivity;

/**
 * Created by user on 23/07/2017.
 */
@Subcomponent(modules = {BooksSearchModule.class})
@PerScreen
public interface BooksSearchComponent {
    void inject(BooksSearchActivity activity);
}
