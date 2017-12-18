package ru.startandroid.onlinelibrary.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by user on 23/07/2017.
 */

@Scope
@Retention(RUNTIME)
public @interface PerScreen {}