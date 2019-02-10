package ru.startandroid.onlinelibrary.di;

import dagger.Subcomponent;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchActivity;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;

@Subcomponent(modules = {BooksSearchModule.class})
@PerScreen
public interface BooksSearchComponent {
    void inject(BooksSearchActivity activity);
}
